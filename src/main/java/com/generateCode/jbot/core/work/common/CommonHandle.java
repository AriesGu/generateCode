package com.generateCode.jbot.core.work.common;

import com.generateCode.jbot.common.constant.Collective;
import com.generateCode.jbot.common.exception.CustomException;
import com.generateCode.jbot.common.model.RequestModel;
import com.generateCode.jbot.common.utils.FileUtil;
import com.generateCode.jbot.common.utils.StringUtil;

/**
 * 通用Handle,用来替换文件中的“项目名”和“包名”
 *
 * @author generateCode
 */
public class CommonHandle {

    public static void start(RequestModel requestModel) throws CustomException {
        //1、修改里面的“包名”和“项目名”
        replace(requestModel);
    }

    private static void replace(RequestModel requestModel) throws CustomException {
        String[] paths = {
                //base
                requestModel.getPackageBasePath() + "/" + StringUtil.toFirstCharUpperCase(requestModel.getProjectName()) + "Application.java",
                //aop
                requestModel.getPackageBasePath() + "/aop/LogAspect.java",
                //configs
                requestModel.getPackageBasePath() + "/configs/Swagger2Config.java",
                //utils
                requestModel.getPackageBasePath() + "/utils/MyMapper.java",
                //exception
                requestModel.getPackageBasePath() + "/exception/CustomException.java",
                requestModel.getPackageBasePath() + "/exception/GlobalExceptionHandler.java",
                requestModel.getPackageBasePath() + "/exception/WebResponse.java",
                //resources
                requestModel.getResourcesPath() + "/application.yml",
                requestModel.getResourcesPath() + "/logback.xml",
                //build
                requestModel.getProjectPath() + "/Dockerfile",
                requestModel.getProjectPath() + "/pom.xml",
                requestModel.getProjectPath() + "/README.md",
                requestModel.getProjectPath() + "/startup.sh"
        };
        for (String path : paths) {
            FileUtil.replace(
                    path,
                    new String[]{
                            Collective.MODEL_PACKAGE_NAME,
                            Collective.MODEL_PROJECT_NAME,
                            StringUtil.toFirstCharUpperCase(Collective.MODEL_PROJECT_NAME)},
                    new String[]{
                            requestModel.getPackageName(),
                            requestModel.getProjectName(),
                            StringUtil.toFirstCharUpperCase(requestModel.getProjectName())}
            );
        }
    }
}
