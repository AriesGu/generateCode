package com.generateCode.jbot.core.before;

import com.generateCode.jbot.common.constant.Collective;
import com.generateCode.jbot.web.entity.DbInfo;
import com.generateCode.jbot.web.entity.TableInfo;
import com.generateCode.jbot.common.exception.CustomException;
import com.generateCode.jbot.common.utils.FileUtil;
import com.generateCode.jbot.common.utils.StringUtil;
import com.generateCode.jbot.common.model.RequestModel;
import com.generateCode.jbot.common.model.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责core开始前的准备工作
 *
 * @author generateCode
 */
public class BeforeHandle {

    private static Logger logger = LoggerFactory.getLogger(BeforeHandle.class);

    public static RequestModel start(String projectName, String packageName, String authorName, DbInfo dbInfo) throws CustomException {
        //0、建立请求的模型
        RequestModel model = buildRequestModel(projectName, packageName, authorName, dbInfo);
        //1、创建项目名文件夹
        createProjectName(model.getProjectPath());
        //2、创建doc文档文件夹
        createDocName(model.getDocPath());
        //3、创建包
        createPackageName(model.getPackageBasePath());
        //4、复制dockerfile、pom、startup、readme等文件
        copyConfFile(model.getProjectPath());
        //5、复制configs、utils、exception、aop等文件夹
        copyConfDir(model.getPackageBasePath());
        //6、复制resources中的logback、application等文件
        copyResource(model.getResourcesPath());
        //7、复制Application文件
        copyApplication(model.getPackageBasePath(), model.getProjectName());
        return model;
    }

    private static RequestModel buildRequestModel(String projectName, String packageName, String authorName, DbInfo dbInfo) throws CustomException {
        RequestModel model = new RequestModel();
        model.setAuthorName(authorName);
        model.setProjectName(projectName);
        model.setPackageName(packageName);
        model.setProjectPath(Collective.TARGET_PROJECT_HOME + "/" + projectName);
        model.setDocPath(model.getProjectPath() + "/doc");
        model.setJavaPath(model.getProjectPath() + "/src/main/java");
        model.setResourcesPath(model.getProjectPath() + "/src/main/resources");
        model.setPackageBasePath(model.getJavaPath() + "/" + StringUtil.spotToSlash(packageName) + "/" + projectName);
        model.setRestPack(packageName + "." + projectName + ".rest");
        model.setEntityPack(packageName + "." + projectName + ".entity");
        model.setDaoPack(packageName + "." + projectName + ".dao");
        model.setServicePack(packageName + "." + projectName + ".service");
        model.setServiceImplPack(packageName + "." + projectName + ".service.impl");
        model.setAopPack(packageName + "." + projectName + ".aop");
        model.setConfigsPack(packageName + "." + projectName + ".configs");
        model.setExceptionPack(packageName + "." + projectName + ".exception");
        model.setUtilsPack(packageName + "." + projectName + ".utils");
        //处理table信息
        List<TableModel> tables = new ArrayList<>();
        for (TableInfo tableInfo : dbInfo.getTables()) {
            TableModel table = new TableModel();
            String entityName = tableInfo.getTableName().substring(tableInfo.getTableName().indexOf('_'));
            table.setTableNameFUDTU(StringUtil.toFirstCharUpperCase(StringUtil.underlineToHump(entityName)));
            table.setTableNameFLDTU(StringUtil.toFirstCharLowCase(StringUtil.underlineToHump(entityName)));
            table.setEntityClassPack(model.getEntityPack() + "." + table.getTableNameFUDTU());
            table.setDaoClassPack(model.getDaoPack() + "." + table.getTableNameFUDTU() + "Dao");
            table.setServiceClassPack(model.getServicePack() + "." + table.getTableNameFUDTU() + "Service");
            table.setServiceImplClassPack(model.getServiceImplPack() + "." + table.getTableNameFUDTU() + "ServiceImpl");
            table.setTableInfo(tableInfo);
            tables.add(table);
        }
        model.setTableModels(tables);
        logger.info("数据信息已经打包完毕，准备生成项目，数据：{}" + model);
        return model;
    }

    private static void createDocName(String docPath) throws CustomException {
        FileUtil.createDir(docPath);
    }

    private static void createProjectName(String projectPath) throws CustomException {
        FileUtil.createDir(projectPath);
    }

    private static void createPackageName(String packageBasePath) throws CustomException {
        FileUtil.createDir(packageBasePath);
    }

    private static void copyConfFile(String projectPath) throws CustomException {
        String[] fileName = {"/Dockerfile",
                "/pom.xml",
                "/README.md",
                "/startup.sh"};
        for (String f : fileName) {
            FileUtil.copyFile(Collective.MODEL_PROJECT_HOME + f, projectPath + "/" + f);
        }
    }

    private static void copyConfDir(String packageBasePath) throws CustomException {
        String[] fileName = {"/configs/Swagger2Config.java",
                "/utils/MyMapper.java",
                "/exception/CustomException.java",
                "/exception/GlobalExceptionHandler.java",
                "/exception/WebResponse.java",
                "/aop/LogAspect.java"};
        for (String f : fileName) {
            FileUtil.copyFile(Collective.MODEL_PROJECT_HOME_JAVA_PACKAGE_BASE + f, packageBasePath + "/" + f);
        }
    }

    private static void copyResource(String resourcesPath) throws CustomException {
        String[] fileName = {"/logback.xml",
                "/application.yml"};
        for (String f : fileName) {
            FileUtil.copyFile(Collective.MODEL_PROJECT_HOME_RESOURCES + f, resourcesPath + "/" + f);
        }
    }

    private static void copyApplication(String packageBasePath, String projectName) throws CustomException {
        FileUtil.copyFile(Collective.MODEL_PROJECT_HOME_JAVA_PACKAGE_BASE + "/ModelApplication.java", packageBasePath + "/" + StringUtil.toFirstCharUpperCase(projectName) + "Application.java");
    }
}
