package ${serviceImplPack};

import ${serviceClassName};
import ${daoClassName};
import ${entityClassName};
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @author ${authorName}
* @date  ${authorDate}
*/
@Service
public class ${className}ServiceImpl implements ${className}Service {

        @Autowired
        private ${className}Dao ${classname}Dao;

        private Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public boolean insert(${className}Entity entity) {
            logger.info("insert entity:{}",entity);
            return ${classname}Dao.insertEntity(entity)!=0;
        }

        @Override
        public boolean deleteWithId(Integer id) {
            logger.info("delete id:{}",id);
            return ${classname}Dao.deleteWithId(id)!=0;
        }

        @Override
        public boolean updateWithId(${className}Entity entity) {
            logger.info("update entity:{}",entity);
            return ${classname}Dao.updateWithId(entity)!=0;
        }

        @Override
        public ${className}Entity selectWithId(Integer id) {
            logger.info("select id:{}",id);
            return ${classname}Dao.selectWithId(id);
        }

        @Override
        public List<${className}Entity> selectByCondition(${className}Entity entity) {
            logger.info("selects params:{}",entity);
            return ${classname}Dao.selectByCondition(entity);
        }

}
