<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

package ${basepackage}.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javazx.base.BaseManager;
import com.javazx.base.EntityDao;
import ${basepackage}.core.po.${className};
import ${basepackage}.core.service.I${className}Manager;
import ${basepackage}.core.dao.I${className}Dao;

<#include "/java_imports.include">
@Service("${classNameLower}Manager")
@Transactional
public class ${className}ManagerImpl extends BaseManager<${className},${table.idColumn.javaType}> implements I${className}Manager{

	@Autowired
	private I${className}Dao ${classNameLower}Dao;

	public EntityDao<${className}, ${table.idColumn.javaType}> getEntityDao() {
		return this.${classNameLower}Dao;
	}
	
<#list table.columns as column>
	<#if column.unique && !column.pk>
	@Transactional(readOnly=true)
	public ${className} getBy${column.columnName}(${column.javaType} v) {
		return ${classNameLower}Dao.getBy${column.columnName}(v);
	}	
	
	</#if>
</#list>
}
