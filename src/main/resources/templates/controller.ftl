package ${restPack};

import ${serviceClassName};
import ${entityClassName};
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
* @author ${authorName}
* @date  ${authorDate}
*/
@Api(tags = "/${className} 相关增删改查接口")
@RestController
@RequestMapping("/${classname}")
public class ${className}Controller {

    @Autowired
    private ${className}Service  ${classname}Service;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @ApiOperation(value = "插入" , notes = "新增${className}数据")
    @PostMapping(value = "/${classname}/insert")
    public boolean insert(HttpServletRequest request,HttpServletResponse response,@RequestBody ${className}Entity entity){
        logger.info("insert entity:{}",entity);
        return ${classname}Service.insert(entity);
    }

    @ApiOperation(value = "删除" , notes = "根据id删除${className}数据")
    @DeleteMapping(value = "/${classname}/delete/{id}")
    public boolean deleteWithId(HttpServletRequest request,HttpServletResponse response,@PathVariable("id") Integer id){
        logger.info("delete id:{}",id);
        return ${classname}Service.deleteWithId(id);
    }

    @ApiOperation(value = "修改" , notes = "根据id修改${className}数据")
    @PutMapping(value = "/${classname}/update")
    public boolean updateWithId(HttpServletRequest request,HttpServletResponse response,@RequestBody ${className}Entity entity){
        logger.info("update entity:{}",entity);
        return ${classname}Service.updateWithId(entity);
    }

    @ApiOperation(value = "按ID查询" , notes = "根据id查询${className}数据")
    @GetMapping(value = "/${classname}/get/{id}")
    public ${className}Entity selectWithId(@PathVariable("id") Integer id,HttpServletRequest request,HttpServletResponse response){
        logger.info("select id:{}",id);
        return ${classname}Service.selectWithId(id);
    }

    @ApiOperation(value = "查询列表" , notes = "根据多个条件查询${className}数据")
    @PostMapping(value = "/${classname}/list")
    public List<${className}Entity> selectByCondition(@RequestBody ${className}Entity entity,HttpServletRequest request,HttpServletResponse response){
        logger.info("selects params:{}",entity);
        return ${classname}Service.selectByCondition(entity);
    }

}

