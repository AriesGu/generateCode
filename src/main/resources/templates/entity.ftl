package ${package};

import java.io.Serializable;
import java.util.Date;
 
/**
* @author ${authorName}
* @date  ${authorDate}
*/
@Data
public class ${className}Entity implements Serializable {
	
	private static final long serialVersionUID = ${serialVersionUID};
	
	<#list attrList as attr> 
	private ${attr.value} ${attr.name};

    </#list>

}