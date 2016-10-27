<%@ page import="${basepackage}.core.po.*" %>
<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "do"> 
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><%=${className}.TABLE_ALIAS%>新增</title>
	<%@ include file="/gen-commons/taglibs.jsp" %>
</head>
<body>
<div class="title01">
	<h2>新增<%=${className}.TABLE_ALIAS%></h2>
</div>
	<form id="form1" action="<@jspEl 'yk'/>${actionBasePath}/save.${actionExtension}" method="post">
	
		<table cellpadding="0" cellspacing="0" class="table00" style="width: 100%">
		<#list table.columns as column>
		<#if column.htmlHidden>
			<input type="hidden" id="${column.columnNameLower}" name="${column.columnNameLower}" value="<c:out value="<@jspEl 'model.'+column.columnNameLower/>"/>"/>
		</#if>
		</#list>
		
		<#list table.columns as column>
			<#if !column.htmlHidden>
			
			<tr>	
				<td class="txtr" style="width: 188px">
					${column.columnAlias}：
				</td>	
				<td>
				<#if column.isDateTimeColumn>
				<input onClick="WdatePicker()" id="${column.columnNameLower}String" name="${column.columnNameLower}String" size="10" <#if !column.nullable>required="true"</#if> readonly="readonly"/>
				<#else>
				<input id="${column.columnNameLower}" name="${column.columnNameLower}" type="text" class="input04"  <#if !column.nullable>required="true"</#if> />
				</#if>
				<#if !column.nullable><span class="required">*</span></#if>
				</td>
			</tr>
			</#if>
		</#list>
			<tr>	
				<td class="txtc" colspan="2">
					<input type="button" class="btn01"  value="提交" onclick="$('#form1').submit();"/>
					<input type="button" class="btn01"  value="取消" onclick="window.close();"/>
					<input type="reset" class="btn01"  value="重置"/>
				</td>	
			</tr>
		</table>
	</form>
	
</body>
<script type="text/javascript">
$(function(){
	var options = {
	         beforeSubmit:   validateForm,   // pre-submit callback 表单提交前被调用的回调函数
	         success:        callBack   // post-submit callback   表单提交成功后被调用的回调函数
	     }; 
	$('#form1').ajaxForm(options);
});

window.onbeforeunload=function(){
	window.opener.document.queryForm.submit();
}

function validateForm(){
	return true;
}
function callBack(data){
	if(data==1)	{
		alert("保存成功！");
		window.close();
	}else{
		alert("保存失败！");
	}
}
</script>
</html>

