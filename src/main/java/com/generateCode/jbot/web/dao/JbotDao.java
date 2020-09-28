package com.generateCode.jbot.web.dao;

import com.generateCode.jbot.web.entity.DbInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author generateCode
 */
@Repository
public interface JbotDao {

    /**
     * 获取指定数据库中表结构
     *
     * @param dbName
     * @return
     */
    List<DbInfo> getDbInfo(String dbName);

}
