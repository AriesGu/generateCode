package ${daoPack};

import  ${entityClassName};
import  ${utilsPack}.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* @author ${authorName}
* @date  ${authorDate}
*/
@Mapper
@Component
public interface  ${className}Dao extends  MyMapper<${className}Entity>  {

    int insertEntity(${className}Entity entity);

    ${className}Entity selectWithId(Integer id);

    int updateWithId(${className}Entity entity);

    int deleteWithId(Integer id);

	List<${className}Entity>  selectByCondition(${className}Entity entity);

}