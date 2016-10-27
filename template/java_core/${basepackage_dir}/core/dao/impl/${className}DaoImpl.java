<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>

package ${basepackage}.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.javazx.base.dao.BaseIbatisDao;
import ${basepackage}.core.dao.I${className}Dao;
import ${basepackage}.core.po.${className};

<#include "/java_imports.include">
@Repository("${classNameLower}Dao")
public class ${className}DaoImpl extends BaseIbatisDao<${className},${table.idColumn.javaType}> implements I${className}Dao{
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "${className}";
	}
	
	@Override
	protected void prepareObjectForSave(${className} entity) {
//		if(entity.get${table.idColumn.columnName}()) == null) {
//			entity.set${table.idColumn.columnName}(IdGenerator.genUUIDStr().longValue());
//		}
	}

	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public ${className} getBy${column.columnName}(${column.javaType} v) {
		return (${className})getSqlSession().selectOne("${className}.getBy${column.columnName}",v);
	}	
	
	</#if>
	</#list>
}
