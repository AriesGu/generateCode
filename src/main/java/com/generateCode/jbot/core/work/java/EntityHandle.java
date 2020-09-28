package com.generateCode.jbot.core.work.java;

import com.generateCode.jbot.web.entity.FieldInfo;
import com.generateCode.jbot.web.entity.TableInfo;
import com.generateCode.jbot.common.exception.CustomException;
import com.generateCode.jbot.common.model.RequestModel;
import com.generateCode.jbot.common.model.TableModel;
import com.generateCode.jbot.common.utils.FileUtil;
import com.generateCode.jbot.common.utils.StringUtil;

import javax.management.Attribute;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 处理entity文件
 *
 * @author generateCode
 */
public class EntityHandle {

    public static void start(RequestModel model) throws CustomException {
//        genFile(model);
        renderFile(model);
    }

    private static void renderFile(RequestModel model) throws CustomException {
        for (TableModel t : model.getTableModels()) {
            HashMap<String, Object> renderMap = renderData(model.getAuthorName(), t.getTableNameFUDTU(), model.getEntityPack(), t.getTableInfo());
            FileUtil.createFileByFreemarker("entity.ftl", model.getJavaPath() + "/" + StringUtil.spotToSlash(t.getEntityClassPack()) + "Entity.java", renderMap);
        }
    }

    private static HashMap<String, Object> renderData(String authorName, String tableNameFUDTU, String entityPack, TableInfo tableInfo) {
        HashMap<String, Object> renderMap = new HashMap<>();
        // 1. 包名
        renderMap.put("package", entityPack);

        // 2. serialVersionUID
        renderMap.put("serialVersionUID", "1L");

        // 3. 文档注解  @author @date
        renderMap.put("authorName", authorName);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        renderMap.put("authorDate", df.format(new Date()));

        // 4. 类名
        renderMap.put("className", tableNameFUDTU);

        // 5. 属性
        List<Attribute> attrList = new ArrayList<>();
        for (FieldInfo field : tableInfo.getFields()) {
            attrList.add(new Attribute(StringUtil.underlineToHump(field.getFieldName()), StringUtil.sqlType2JavaType(field.getFieldType())));
        }
        renderMap.put("attrList", attrList);

        // 5. 属性
        List<Map> getSetList = new ArrayList<>();
        for (FieldInfo field : tableInfo.getFields()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("attr",StringUtil.underlineToHump(field.getFieldName()));
            map.put("firstUpper",StringUtil.toFirstCharUpperCase(StringUtil.underlineToHump(field.getFieldName())));
            map.put("type",StringUtil.sqlType2JavaType(field.getFieldType()));
            getSetList.add(map);
        }
        renderMap.put("getSetList", getSetList);

        return renderMap;
    }

}
