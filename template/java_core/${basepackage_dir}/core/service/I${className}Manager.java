<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

package ${basepackage}.core.service;

import ${basepackage}.core.po.${className};
import com.javazx.base.IBaseManager;

<#include "/java_imports.include">
public interface I${className}Manager extends IBaseManager<${className}, ${table.idColumn.javaType}>{
	
	<#list table.columns as column>
		<#if column.unique && !column.pk>
		@Transactional(readOnly=true)
		public ${className} getBy${column.columnName}(${column.javaType} v);
		
		</#if>
	</#list>
}
