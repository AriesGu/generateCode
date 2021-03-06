package com.generateCode.jbot.web.service;

import com.generateCode.jbot.web.entity.ConnectInfo;
import com.generateCode.jbot.web.entity.DbInfo;
import com.generateCode.jbot.common.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author generateCode
 */
@Service
public interface JbotService {

    /**
     * 初始化
     *
     * @return
     * @throws CustomException
     */
    Map<String, Object> initProject() throws CustomException;

    /**
     * 设置数据库连接信息
     *
     * @param connectInfo 用户自定义数据库连接信息
     * @param response
     * @return
     * @throws CustomException
     */
    Map<String, Object> settingProject(ConnectInfo connectInfo, HttpServletResponse response) throws CustomException;

    /**
     * 生成目标项目
     *
     * @param projectName 项目名
     * @param packageName 包名
     * @param dbInfo      数据库实体类（选择的表的集合）
     * @param response
     * @return
     * @throws CustomException
     */
    Map<String, Object> produceProject(String projectName, String packageName, String authorName , DbInfo dbInfo, HttpServletResponse response) throws CustomException;
}
