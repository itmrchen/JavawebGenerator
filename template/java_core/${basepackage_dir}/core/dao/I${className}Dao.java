<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   

package ${basepackage}.core.dao;

import com.javazx.base.EntityDao;
import ${basepackage}.core.po.${className};

<#include "/java_imports.include">
public interface I${className}Dao extends EntityDao<${className}, ${table.idColumn.javaType}>{
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public ${className} getBy${column.columnName}(${column.javaType} v);
	</#if>
	</#list>

}