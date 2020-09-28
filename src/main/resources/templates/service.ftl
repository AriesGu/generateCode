package ${servicePack};

import ${entityClassName};
import java.util.List;

/**
* @author ${authorName}
* @date  ${authorDate}
*/
public interface ${className}Service {

         boolean insert(${className}Entity entity);

         boolean deleteWithId(Integer id);

         boolean updateWithId(${className}Entity entity);

         ${className}Entity selectWithId(Integer id);

         List<${className}Entity> selectByCondition(${className}Entity entity);

}
