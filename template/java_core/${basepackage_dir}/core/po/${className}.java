<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

package ${basepackage}.core.po;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.javazx.base.util.DateConvertUtils;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.javazx.base.BaseEntity;

<#include "/java_imports.include">
public class ${className} extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "${table.tableAlias}";
//	<#list table.columns as column>
//	public static final String ALIAS_${column.constantName} = "${column.columnAlias}";
//	</#list>
	
	//date formats
	<#list table.columns as column>
	<#if column.isDateTimeColumn>
	public static final String FORMAT_${column.constantName} = DATE_FORMAT;
	</#if>
	</#list>
	
	//columns START
	<#list table.columns as column>
	//${column.columnAlias}
	private ${column.simpleJavaType} ${column.columnNameLower};
	</#list>
	//columns END

<@generateConstructor className/>
<@generateJavaColumns/>
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		<#list table.columns as column>
			.append("${column.columnName}",get${column.columnName}())
		</#list>
			.toString();
	}
	
	public String toString(String separator) {
		StringBuffer sb = new StringBuffer();

		<#list table.columns as column>
			<#if column.isDateTimeColumn>
			sb.append("${column.columnAlias}:").append(get${column.columnName}String()).append(separator);
			<#else>
			sb.append("${column.columnAlias}:").append(${column.columnNameLower}).append(separator);
			</#if>
		</#list>
		
		return sb.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
		<#list table.pkColumns as column>
			.append(get${column.columnName}())
		</#list>
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ${className} == false) return false;
		if(this == obj) return true;
		${className} other = (${className})obj;
		return new EqualsBuilder()
			<#list table.pkColumns as column>
			.append(get${column.columnName}(),other.get${column.columnName}())
			</#list>
			.isEquals();
	}
	
	<#if table.pkCount==1>
	public String getPkValue() {
		<#if table.pkColumn.isNumberColumn>
		if(get${table.pkColumn.columnName}() != null){
			return String.valueOf(get${table.pkColumn.columnName}());
		}
		<#elseif table.pkColumn.isStringColumn>
			return get${table.pkColumn.columnName}();
		</#if>
		
		return "";
	}
	</#if> 
}

<#macro generateJavaColumns>
	<#list table.columns as column>
		<#if column.isDateTimeColumn>
	public String get${column.columnName}String() {
		return DateConvertUtils.format(get${column.columnName}(), FORMAT_${column.constantName});
	}
	public void set${column.columnName}String(String value) {
		set${column.columnName}(DateConvertUtils.parse(value, FORMAT_${column.constantName},${column.javaType}.class));
	}
	
		</#if>	
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
	
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private Set ${fkPojoClassVar}s = new HashSet(0);
	public void set${fkPojoClass}s(Set<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	
	public Set<${fkPojoClass}> get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private ${fkPojoClass} ${fkPojoClassVar};
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>
