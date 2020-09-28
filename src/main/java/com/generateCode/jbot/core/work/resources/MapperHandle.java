package com.generateCode.jbot.core.work.resources;

import com.generateCode.jbot.web.entity.FieldInfo;
import com.generateCode.jbot.web.entity.TableInfo;
import com.generateCode.jbot.common.exception.CustomException;
import com.generateCode.jbot.common.model.RequestModel;
import com.generateCode.jbot.common.model.TableModel;
import com.generateCode.jbot.common.utils.FileUtil;
import com.generateCode.jbot.common.utils.StringUtil;

import javax.management.Attribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 处理mapper文件
 *
 * @author generateCode
 */
public class MapperHandle {

    public static void start(RequestModel model) throws CustomException {
        //1、生成对应的xml文件
        renderFile(model);
    }

    private static void renderFile(RequestModel model) throws CustomException {
        for (TableModel t : model.getTableModels()) {
            HashMap<String, Object> renderMap = renderData(t.getDaoClassPack(), t.getEntityClassPack(), t.getTableInfo(), t.getTableNameFUDTU());
            FileUtil.createFileByFreemarker("dao.ftl", model.getResourcesPath() + "/mapper/" + t.getTableNameFUDTU() + "Dao.xml", renderMap);
        }
    }

    private static HashMap<String, Object> renderData(String daoClassName, String entityClassName, TableInfo tableInfo, String tableNameFUDTU) {
        HashMap<String, Object> renderMap = new HashMap<>();

        // 1. namespace
        renderMap.put("daoClassName", daoClassName);
        // 2. resultMap
        renderMap.put("entityClassName", entityClassName + "Entity");
        // 3. baseColumn
        List<Attribute> attrList = new ArrayList<>();
        for (FieldInfo field : tableInfo.getFields()) {
            attrList.add(new Attribute(StringUtil.underlineToHump(field.getFieldName()), field.getFieldName()));
        }
        renderMap.put("attrList", attrList);
        // 4. tableName
        renderMap.put("tableName", tableInfo.getTableName());

        return renderMap;
    }

}

