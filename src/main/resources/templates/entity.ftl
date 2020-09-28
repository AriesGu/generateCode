package ${package};

import java.io.Serializable;
import java.util.Date;
 
/**
* @author ${authorName}
* @date  ${authorDate}
*/
public class ${className}Entity implements Serializable {
	
	private static final long serialVersionUID = ${serialVersionUID};
	
	<#list attrList as attr> 
	private ${attr.value} ${attr.name};

    </#list>

	<#list getSetList as gs>
		public ${gs.type} get${gs.firstUpper}() {
			return ${gs.attr};
		}

		public void set${gs.firstUpper}(${gs.type} ${gs.attr}) {
			this.${gs.attr} = ${gs.attr};
		}

	</#list>



}