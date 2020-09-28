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
 * 处理serviceImpl文件
 *
 * @author generateCode
 */
public class ServiceImplHandle {

    public static void start(RequestModel model) throws CustomException {
        renderFile(model);
    }

    private static void renderFile(RequestModel model) throws CustomException {
        for (TableModel t : model.getTableModels()) {
            HashMap<String, Object> renderMap = renderData(model.getAuthorName(),t.getTableNameFLDTU(), t.getTableNameFUDTU(), t.getDaoClassPack(), t.getEntityClassPack(), model.getServiceImplPack(), t.getServiceClassPack(), model.getExceptionPack());
            FileUtil.createFileByFreemarker("serviceImpl.ftl", model.getJavaPath() + "/" + StringUtil.spotToSlash(t.getServiceImplClassPack()) + ".java", renderMap);
//            FileUtil.createFile(model.getJavaPath() + "/" + StringUtil.spotToSlash(t.getServiceImplClassPack()) + ".java", serviceImpl);
        }
    }

    private static HashMap<String, Object> renderData(String authorName, String tableNameFLDTU, String tableNameFUDTU, String daoClassName, String entityClassName, String serviceImplPack, String serviceClassName, String exceptionPack) {
        HashMap<String, Object> renderMap = new HashMap<>();
        // 1. 包名
        renderMap.put("serviceImplPack", serviceImplPack);

        // 2. 应用service接口
        renderMap.put("serviceClassName", serviceClassName);

        // 3. 引用exception
        renderMap.put("exceptionPack", exceptionPack);

        // 4. 引用dao
        renderMap.put("daoClassName", daoClassName);

        // 5. 引用entity
        renderMap.put("entityClassName", entityClassName+"Entity");

        // 6. 文档注解  @author @date
        renderMap.put("authorName", authorName);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        renderMap.put("authorDate", df.format(new Date()));

        // 7. 类名
        renderMap.put("className", tableNameFUDTU);
        renderMap.put("classname", tableNameFLDTU);


        return renderMap;
    }
//
//    private static void genFile(RequestModel model) throws CustomException {
//        for (TableModel t : model.getTableModels()) {
//            String serviceImpl = genData(t.getTableNameFLDTU(), t.getTableNameFUDTU(), t.getDaoClassPack(), t.getEntityClassPack(), model.getServiceImplPack(), t.getServiceClassPack(), model.getExceptionPack());
//            FileUtil.createFile(model.getJavaPath() + "/" + StringUtil.spotToSlash(t.getServiceImplClassPack()) + ".java", serviceImpl);
//        }
//    }
//
//
//
//    private static String genData(String tableNameFLDTU, String tableNameFUDTU, String daoClassName, String entityClassName, String serviceImplPack, String serviceClassName, String exceptionPack) {
//        StringBuffer sb = new StringBuffer();
//        sb.append(genHead(tableNameFUDTU, serviceImplPack, daoClassName, entityClassName, serviceClassName, exceptionPack));
//        sb.append(genMember(tableNameFLDTU, tableNameFUDTU));
//        sb.append(genInsert(tableNameFLDTU, tableNameFUDTU));
//        sb.append(genDelete(tableNameFLDTU));
//        sb.append(genUpdate(tableNameFLDTU, tableNameFUDTU));
//        sb.append(genSelect(tableNameFLDTU));
//        sb.append(genSelectList(tableNameFLDTU, tableNameFUDTU));
//        sb.append(genTail());
//        return sb.toString();
//    }
//
//    /**
//     * 生成头
//     *
//     * @return
//     */
//    private static StringBuffer genHead(String tableNameFUDTU, String serviceImplPack, String daoClassName, String entityClassName, String serviceClassName, String exceptionPack) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("package " + serviceImplPack + ";\r\n\r\n");
//        sb.append("import " + serviceClassName + ";\r\n");
//        sb.append("import " + exceptionPack + ".CustomException;\r\n");
//        sb.append("import " + daoClassName + ";\r\n");
//        sb.append("import " + entityClassName + ";\r\n");
//        sb.append("import org.slf4j.Logger;\r\n");
//        sb.append("import org.slf4j.LoggerFactory;\r\n");
//        sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
//        sb.append("import org.springframework.stereotype.Service;\r\n");
//        sb.append("import java.util.HashMap;\r\n");
//        sb.append("import java.util.Map;\r\n\r\n");
//        sb.append("/**\r\n");
//        sb.append(" * @author\r\n");
//        sb.append(" */\r\n");
//        sb.append("@Service\r\n");
//        sb.append("public class " + tableNameFUDTU + "ServiceImpl implements " + tableNameFUDTU + "Service {\r\n\r\n");
//        return sb;
//    }
//
//    /**
//     * 生成尾
//     *
//     * @return
//     */
//    private static String genTail() {
//        return "\r\n}\r\n";
//    }
//
//    /**
//     * 生成成员变量
//     *
//     * @return
//     */
//    private static StringBuffer genMember(String tableNameFLDTU, String tableNameFUDTU) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("\t@Autowired\r\n");
//        sb.append("\tprivate " + tableNameFUDTU + "Dao " + tableNameFLDTU + "Dao;\r\n");
//        sb.append("\tprivate Logger logger = LoggerFactory.getLogger(getClass());\r\n\r\n");
//        return sb;
//    }
//
//    /**
//     * 生成插入语句
//     *
//     * @return
//     */
//    private static StringBuffer genInsert(String tableNameFLDTU, String tableNameFUDTU) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("\t@Override\r\n");
//        sb.append("\tpublic Map<String, Object> insert(" + tableNameFUDTU + " entity, String sessionId)throws CustomException {\r\n");
//        sb.append("\t\tlogger.info(\"insert entity:{}\",entity);\r\n");
//        sb.append("\t\tMap<String, Object> result = new HashMap<>();\r\n");
//        sb.append("\t\t" + tableNameFLDTU + "Dao.insert(entity);\r\n");
//        sb.append("\t\treturn result;\r\n");
//        sb.append("\t}\r\n\r\n");
//        return sb;
//    }
//
//    /**
//     * 生成删除语句
//     *
//     * @return
//     */
//    private static StringBuffer genDelete(String tableNameFLDTU) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("\t@Override\r\n");
//        sb.append("\tpublic Map<String, Object> delete(Integer id, String sessionId)throws CustomException {\r\n");
//        sb.append("\t\tlogger.info(\"delete id:{}\",id);\r\n");
//        sb.append("\t\tMap<String, Object> result = new HashMap<>();\r\n");
//        sb.append("\t\t" + tableNameFLDTU + "Dao.deleteByPrimaryKey(id);\r\n");
//        sb.append("\t\treturn result;\r\n");
//        sb.append("\t}\r\n\r\n");
//        return sb;
//    }
//
//    /**
//     * 生成更新语句
//     *
//     * @return
//     */
//    private static StringBuffer genUpdate(String tableNameFLDTU, String tableNameFUDTU) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("\t@Override\r\n");
//        sb.append("\tpublic Map<String, Object> update(" + tableNameFUDTU + " entity, String sessionId)throws CustomException {\r\n");
//        sb.append("\t\tlogger.info(\"update entity:{}\",entity);\r\n");
//        sb.append("\t\tMap<String, Object> result = new HashMap<>();\r\n");
//        sb.append("\t\t" + tableNameFLDTU + "Dao.updateByPrimaryKey(entity);\r\n");
//        sb.append("\t\treturn result;\r\n");
//        sb.append("\t}\r\n\r\n");
//        return sb;
//    }
//
//    /**
//     * 生成查询语句（单条）
//     *
//     * @return
//     */
//    private static StringBuffer genSelect(String tableNameFLDTU) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("\t@Override\r\n");
//        sb.append("\tpublic Map<String, Object> select(Integer id, String sessionId)throws CustomException {\r\n");
//        sb.append("\t\tlogger.info(\"select id:{}\",id);\r\n");
//        sb.append("\t\tMap<String, Object> result = new HashMap<>();\r\n");
//        sb.append("\t\tresult.put(\"data\"," + tableNameFLDTU + "Dao.selectByPrimaryKey(id));\r\n");
//        sb.append("\t\treturn result;\r\n");
//        sb.append("\t}\r\n\r\n");
//        return sb;
//    }
//
//    /**
//     * 生成查询语句（多条）
//     *
//     * @return
//     */
//    private static StringBuffer genSelectList(String tableNameFLDTU, String tableNameFUDTU) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("\t@Override\r\n");
//        sb.append("\tpublic Map<String, Object> selects(Map<String, Object> params, String sessionId)throws CustomException {\r\n");
//        sb.append("\t\tlogger.info(\"selects params:{}\",params);\r\n");
//        sb.append("\t\tMap<String, Object> result = new HashMap<>();\r\n");
//        sb.append("\t\tresult.put(\"data\"," + tableNameFLDTU + "Dao.select" + tableNameFUDTU + "s(params));\r\n");
//        sb.append("\t\tresult.put(\"total\"," + tableNameFLDTU + "Dao.select" + tableNameFUDTU + "sCount(params));\r\n");
//        sb.append("\t\treturn result;\r\n");
//        sb.append("\t}\r\n");
//        return sb;
//    }
}
