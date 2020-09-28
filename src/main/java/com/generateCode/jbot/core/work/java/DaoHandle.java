package com.generateCode.jbot.core.work.java;

import com.generateCode.jbot.common.exception.CustomException;
import com.generateCode.jbot.common.model.RequestModel;
import com.generateCode.jbot.common.model.TableModel;
import com.generateCode.jbot.common.utils.FileUtil;
import com.generateCode.jbot.common.utils.StringUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 处理dao文件
 *
 * @author generateCode
 */
public class DaoHandle {

    public static void start(RequestModel model) throws CustomException {
        //1、生成对应的dao文件
        renderFile(model);
    }

    private static void renderFile(RequestModel model) throws CustomException {
        for (TableModel t : model.getTableModels()) {
            HashMap<String, Object> renderMap = renderData(model.getAuthorName(), t.getTableNameFUDTU(), t.getEntityClassPack(), model.getDaoPack(), model.getUtilsPack());
            FileUtil.createFileByFreemarker("repository.ftl", model.getJavaPath() + "/" + StringUtil.spotToSlash(t.getDaoClassPack()) + ".java", renderMap);
        }
    }

    private static HashMap<String, Object> renderData(String authorName, String tableNameFUDTU, String entityClassName, String daoPack, String utilsPack) {
        HashMap<String, Object> renderMap = new HashMap<>();
        // 1. 包名
        renderMap.put("daoPack", daoPack);

        // 2. 引用entity
        renderMap.put("entityClassName", entityClassName+"Entity");

        // 3. 引用util
        renderMap.put("utilsPack", utilsPack);

        // 4. 文档注解  @author @date
        renderMap.put("authorName", authorName);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        renderMap.put("authorDate", df.format(new Date()));

        // 4. 类名
        renderMap.put("className", tableNameFUDTU);

        return renderMap;
    }


}
