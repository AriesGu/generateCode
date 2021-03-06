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
 * 处理rest文件
 *
 * @author generateCode
 */
public class RestHandle {

    public static void start(RequestModel model) throws CustomException {
        renderFile(model);
    }

    private static void renderFile(RequestModel model) throws CustomException {
        for (TableModel t : model.getTableModels()) {
            HashMap<String, Object> renderMap = renderData(model.getAuthorName(), t.getTableNameFLDTU(), t.getTableNameFUDTU(), t.getServiceClassPack(), t.getEntityClassPack(), model.getRestPack(), model.getExceptionPack());
            FileUtil.createFileByFreemarker("controller.ftl", model.getJavaPath() + "/" + StringUtil.spotToSlash(model.getRestPack()) + "/" + t.getTableNameFUDTU() + "Controller.java", renderMap);
        }
    }

    private static HashMap<String, Object> renderData(String authorName, String tableNameFLDTU, String tableNameFUDTU, String serviceClassName, String entityClassName, String restPack, String exceptionPack) {
        HashMap<String, Object> renderMap = new HashMap<>();
        // 1. 包名
        renderMap.put("restPack", restPack);

        // 2. 引用service
        renderMap.put("serviceClassName", serviceClassName);

        // 3. 引用exception
        renderMap.put("exceptionPack", exceptionPack);

        // 4. 引用entity
        renderMap.put("entityClassName", entityClassName+"Entity");

        // 5. 文档注解  @author @date
        renderMap.put("authorName", authorName);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        renderMap.put("authorDate", df.format(new Date()));

        // 6. 类名
        renderMap.put("className", tableNameFUDTU);
        renderMap.put("classname", tableNameFLDTU);

        return renderMap;
    }



//    private static void genFile(RequestModel model) throws CustomException {
//        for (TableModel t : model.getTableModels()) {
//            String rest = genData(t.getTableNameFLDTU(), t.getTableNameFUDTU(), t.getServiceClassPack(), t.getEntityClassPack(), model.getRestPack(), model.getExceptionPack());
//            FileUtil.createFile(model.getJavaPath() + "/" + StringUtil.spotToSlash(model.getRestPack()) + "/" + t.getTableNameFUDTU() + "Controller.java", rest);
//        }
//    }
////
//
//    private static String genData(String tableNameFLDTU, String tableNameFUDTU, String serviceClassName, String entityClassName, String restPack, String exceptionPack) {
//        StringBuffer sb = new StringBuffer();
//        sb.append(genHead(tableNameFLDTU, tableNameFUDTU, restPack, serviceClassName, entityClassName, exceptionPack));
//        sb.append(genMember(tableNameFLDTU, tableNameFUDTU));
//        sb.append(genInsert(tableNameFLDTU, tableNameFUDTU));
//        sb.append(genDelete(tableNameFLDTU, tableNameFUDTU));
//        sb.append(genUpdate(tableNameFLDTU, tableNameFUDTU));
//        sb.append(genSelect(tableNameFLDTU));
//        sb.append(genSelectList(tableNameFLDTU));
//        sb.append(genTail());
//        return sb.toString();
//    }
//
//    /**
//     * 生成头
//     *
//     * @return
//     */
//    private static StringBuffer genHead(String tableNameFLDTU, String tableNameFUDTU, String restPack, String serviceClassName, String entityClassName, String exceptionPack) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("package " + restPack + ";\r\n\r\n");
//        sb.append("import " + serviceClassName + ";\r\n");
//        sb.append("import " + exceptionPack + ".CustomException;\r\n");
//        sb.append("import " + entityClassName + ";\r\n");
//        sb.append("import io.swagger.annotations.*;\r\n");
//        sb.append("import org.slf4j.Logger;\r\n");
//        sb.append("import org.slf4j.LoggerFactory;\r\n");
//        sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
//        sb.append("import org.springframework.web.bind.annotation.*;\r\n");
//        sb.append("import javax.servlet.http.HttpServletRequest;\r\n");
//        sb.append("import javax.servlet.http.HttpServletResponse;\r\n");
//        sb.append("import java.util.*;\r\n\r\n");
//        sb.append("/**\r\n");
//        sb.append(" * @author\r\n");
//        sb.append(" */\r\n");
//        sb.append("@RestController\r\n");
//        sb.append("@RequestMapping(\"/" + tableNameFLDTU + "s\")\r\n");
//        sb.append("public class " + tableNameFUDTU + "Rest {\r\n\r\n");
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
//        sb.append("\tprivate " + tableNameFUDTU + "Service " + tableNameFLDTU + "Service;\r\n");
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
//        sb.append("\t@ApiOperation(value = \"插入\", httpMethod = \"POST\")\r\n");
//        sb.append("\t@ApiImplicitParam(name = \"sessionId\", value = \"SessionId\", required = true, dataType = \"string\", paramType = \"header\")\r\n");
//        sb.append("\t@ApiResponses({\r\n");
//        sb.append("\t\t\t@ApiResponse(code = 400, message = \"IllegalParam\")\r\n");
//        sb.append("\t})\r\n");
//        sb.append("\t@RequestMapping(value = \"/" + tableNameFLDTU + "\", method = RequestMethod.POST)\r\n");
//        sb.append("\tpublic Map<String, Object> insert(\r\n");
//        sb.append("\t\t\t@RequestHeader(value = \"sessionId\") String sessionId,\r\n");
//        sb.append("\t\t\tHttpServletRequest request,\r\n");
//        sb.append("\t\t\tHttpServletResponse response,\r\n");
//        sb.append("\t\t\t@RequestBody " + tableNameFUDTU + " entity)throws CustomException {\r\n");
//        sb.append("\t\tlogger.info(\"insert entity:{}\",entity);\r\n");
//        sb.append("\t\treturn " + tableNameFLDTU + "Service.insert(entity,sessionId);\r\n");
//        sb.append("\t}\r\n\r\n");
//        return sb;
//    }
//
//    /**
//     * 生成删除语句
//     *
//     * @return
//     */
//    private static StringBuffer genDelete(String tableNameFLDTU, String tableNameFUDTU) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("\t@ApiOperation(value = \"删除\", httpMethod = \"DELETE\")\r\n");
//        sb.append("\t@ApiImplicitParams({\r\n");
//        sb.append("\t\t\t@ApiImplicitParam(name = \"id\", value = \"ID\", required = true, dataType = \"int\", paramType = \"path\"),\r\n");
//        sb.append("\t\t\t@ApiImplicitParam(name = \"sessionId\", value = \"SessionId\", required = true, dataType = \"string\", paramType = \"header\")\r\n");
//        sb.append("\t})\r\n");
//        sb.append("\t@ApiResponses({\r\n");
//        sb.append("\t\t\t@ApiResponse(code = 400, message = \"IllegalParam\")\r\n");
//        sb.append("\t})\r\n");
//        sb.append("\t@RequestMapping(value = \"/" + tableNameFLDTU + "/{id}\", method = RequestMethod.DELETE)\r\n");
//        sb.append("\tpublic Map<String, Object> delete(\r\n");
//        sb.append("\t\t\t@PathVariable(\"id\") Integer id,\r\n");
//        sb.append("\t\t\t@RequestHeader(value = \"sessionId\") String sessionId,\r\n");
//        sb.append("\t\t\tHttpServletRequest request,\r\n");
//        sb.append("\t\t\tHttpServletResponse response)throws CustomException {\r\n");
//        sb.append("\t\tlogger.info(\"delete id:{}\",id);\r\n");
//        sb.append("\t\treturn " + tableNameFLDTU + "Service.delete(id,sessionId);\r\n");
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
//        sb.append("\t@ApiOperation(value = \"修改\", httpMethod = \"PUT\")\r\n");
//        sb.append("\t@ApiImplicitParam(name = \"sessionId\", value = \"SessionId\", required = true, dataType = \"string\", paramType = \"header\")\r\n");
//        sb.append("\t@ApiResponses({\r\n");
//        sb.append("\t\t\t@ApiResponse(code = 400, message = \"IllegalParam\")\r\n");
//        sb.append("\t})\r\n");
//        sb.append("\t@RequestMapping(value = \"/" + tableNameFLDTU + "\", method = RequestMethod.PUT)\r\n");
//        sb.append("\tpublic Map<String, Object> update(\r\n");
//        sb.append("\t\t\t@RequestHeader(value = \"sessionId\") String sessionId,\r\n");
//        sb.append("\t\t\tHttpServletRequest request,\r\n");
//        sb.append("\t\t\tHttpServletResponse response,\r\n");
//        sb.append("\t\t\t@RequestBody " + tableNameFUDTU + " entity)throws CustomException {\r\n");
//        sb.append("\t\tlogger.info(\"update entity:{}\",entity);\r\n");
//        sb.append("\t\treturn " + tableNameFLDTU + "Service.update(entity,sessionId);\r\n");
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
//        sb.append("\t@ApiOperation(value = \"按ID查询\", httpMethod = \"GET\")\r\n");
//        sb.append("\t@ApiImplicitParams({\r\n");
//        sb.append("\t\t\t@ApiImplicitParam(name = \"id\", value = \"ID\", required = true, dataType = \"int\", paramType = \"path\"),\r\n");
//        sb.append("\t\t\t@ApiImplicitParam(name = \"sessionId\", value = \"SessionId\", required = true, dataType = \"string\", paramType = \"header\")\r\n");
//        sb.append("\t})\r\n");
//        sb.append("\t@ApiResponses({\r\n");
//        sb.append("\t\t\t@ApiResponse(code = 400, message = \"IllegalParam\")\r\n");
//        sb.append("\t})\r\n");
//        sb.append("\t@RequestMapping(value = \"/" + tableNameFLDTU + "/{id}\", method = RequestMethod.GET)\r\n");
//        sb.append("\tpublic Map<String, Object> select(\r\n");
//        sb.append("\t\t\t@PathVariable(\"id\") Integer id,\r\n");
//        sb.append("\t\t\t@RequestHeader(value = \"sessionId\") String sessionId,\r\n");
//        sb.append("\t\t\tHttpServletRequest request,\r\n");
//        sb.append("\t\t\tHttpServletResponse response)throws CustomException {\r\n");
//        sb.append("\t\tlogger.info(\"select id:{}\",id);\r\n");
//        sb.append("\t\treturn " + tableNameFLDTU + "Service.select(id,sessionId);\r\n");
//        sb.append("\t}\r\n\r\n");
//        return sb;
//    }
//
//    /**
//     * 生成查询语句（多条）
//     *
//     * @return
//     */
//    private static StringBuffer genSelectList(String tableNameFLDTU) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("\t@ApiOperation(value = \"查询\", httpMethod = \"GET\")\r\n");
//        sb.append("\t@ApiImplicitParams({\r\n");
//        sb.append("\t\t\t@ApiImplicitParam(name = \"pageNo\", value = \"页数(从0开始，默认0)\", dataType = \"int\", paramType = \"query\"),\r\n");
//        sb.append("\t\t\t@ApiImplicitParam(name = \"pageSize\", value = \"每页的数量(默认10)\", dataType = \"int\", paramType = \"query\"),\r\n");
//        sb.append("\t\t\t@ApiImplicitParam(name = \"sessionId\", value = \"SessionId\", required = true, dataType = \"string\", paramType = \"header\")\r\n");
//        sb.append("\t})\r\n");
//        sb.append("\t @ApiResponses({\r\n");
//        sb.append("\t\t\t@ApiResponse(code = 400, message = \"IllegalParam\")\r\n");
//        sb.append("\t})\r\n");
//        sb.append("\t@RequestMapping(value = \"/\",method = RequestMethod.GET)\r\n");
//        sb.append("\tpublic Map<String, Object> selects(\r\n");
//        sb.append("\t\t\t@RequestParam(value = \"pageNo\",required = false,defaultValue = \"0\") Integer pageNo,\r\n");
//        sb.append("\t\t\t@RequestParam(value = \"pageSize\",required = false,defaultValue = \"10\") Integer pageSize,\r\n");
//        sb.append("\t\t\t@RequestHeader(value = \"sessionId\") String sessionId,\r\n");
//        sb.append("\t\t\tHttpServletRequest request,\r\n");
//        sb.append("\t\t\tHttpServletResponse response)throws CustomException {\r\n");
//        sb.append("\t\tMap<String,Object> params = new HashMap<>();\r\n");
//        sb.append("\t\tparams.put(\"pageNo\",pageNo);\r\n");
//        sb.append("\t\tparams.put(\"pageSize\",pageSize);\r\n");
//        sb.append("\t\tlogger.info(\"selects params:{}\",params);\r\n");
//        sb.append("\t\treturn " + tableNameFLDTU + "Service.selects(params,sessionId);\r\n");
//        sb.append("\t}\r\n");
//        return sb;
//    }
}
