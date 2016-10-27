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
	<title><%=${className}.TABLE_ALIAS%> 维护</title>
	<%@ include file="/gen-commons/taglibs.jsp" %>
</head>
<body>
<div class="title01 title01_x">
	<h2>搜索<span class="zksq"><!-- <a href="" title="收起展开"><img alt="展开收起按钮" height="7" src="<@jspEl 'yk'/>/images/zksq.png" width="13" /></a> --></span></h2>
</div>
<form id="queryForm" name="queryForm" action="<c:url value="${actionBasePath}/list.${actionExtension}"/>" method="post" style="display: inline;">
	<input type="hidden" id="pageNumber" name="pageNumber" value="<c:out value="${r"${pageNumber}"}" default="1"/>"/>
	<input type="hidden" id="pageSize" name="pageSize" value="<c:out value="${r"${pageSize}"}" default="10"/>"/>
	<input type="hidden" id="queryType" name="queryType" value="<c:out value="${r"${queryType}"}" default="0"/>"/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table00">
		<#list table.notPkColumns?chunk(4) as row>
		<tr>	
			<#list row as column>
			<#if !column.htmlHidden>	
			<td class="txtr" style="width: 100px">${column.columnAlias}：</td>		
			<td>
				<#if column.isDateTimeColumn>
				&nbsp;<input onClick="WdatePicker()" value="<c:out value="<@jspEl column.columnNameLower+'Begin'/>"/>" id="${column.columnNameLower}Begin" name="${column.columnNameLower}Begin" size="10"/>
				<input onClick="WdatePicker()" value="<c:out value="<@jspEl column.columnNameLower+'End'/>"/>" id="${column.columnNameLower}End" name="${column.columnNameLower}End" size="10"/>
				<#else>
				&nbsp;<input value="<c:out value="<@jspEl column.columnNameLower/>"/>" id="${column.columnNameLower}" name="${column.columnNameLower}" maxlength="${column.size}"/>
				</#if>
			</td>
			</#if>
			</#list>
		</tr>	
		</#list>			
	</table>
	<br/>
	<div align="center">
		<input type="submit" class="btn01" value="精确查询" onclick="$('#pageNumber').val('1');$('#pageSize').val('10');$('#queryType').val('0');"/>
		<input type="submit" class="btn01" value="模糊查询" onclick="$('#pageNumber').val('1');$('#pageSize').val('10');$('#queryType').val('1');"/>
		<input type="button" class="btn01" value="清 空" id="btnclear"/>
	</div>
</form>
	<br></br>
	<div class="title01">
	<h2><%=${className}.TABLE_ALIAS%>列表</h2>
</div>
		<div class="groupbtn">
			<a href="javascript:void(0)" title="新建" class="btn001" id="btnadd">新增</a>
			<a href="javascript:void(0)" title="修改" class="btn002" id="btnedit">修改</a>
			<a href="javascript:void(0)" title="删除"  class="btn004" id="btndelete">删除</a>
		</div>
		<div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="tb" class="table02">
			<thead>
			 <tr>
			 	<th><input id="allChoose" type="checkbox"/></th>
			 	<th>序号<div style="width: 65px; height: 1px; overflow: hidden;"></div></th>
			 <#list table.columns as column>
				<#if !column.htmlHidden>
				<th>${column.columnAlias}<div style="width: 65px; height: 1px; overflow: hidden;"></div></th>
				</#if>
			</#list>
			    <th>操作<div style="width: 75px; height: 1px; overflow: hidden;"></div></th>
	        </tr>
	        </thead>
			<c:forEach var="entry" items="<@jspEl 'page.result'/>" varStatus="v">
			<tr>
				 <#list table.columns as column>
				 <#if column.pk>
				<td><input name="ids" type="checkbox" value="<@jspEl 'entry.'+column.columnNameLower/>"/>
				<input name="pkName" type="hidden" id="pkName" value="${column.columnNameLower}"/>
				</td>
				</#if>
				</#list>
				<td><@jspEl 'v.index + 1 '/></td>
			 <#list table.columns as column>
				<#if !column.htmlHidden>
				<td>
				<#if column.isDateTimeColumn>
				<div><c:out value="<@jspEl 'entry.'+column.columnNameLower+'String'/>"/></div>
				<#else>
				<div><c:out value="<@jspEl 'entry.'+column.columnNameLower/>"/></div>
				</#if>
				</td>
				</#if>
			</#list>
				<td>
				 <#list table.columns as column>
				 <#if column.pk>
						<a href="#" onclick="show('<@jspEl 'entry.'+column.columnNameLower/>')">详情</a>&nbsp;
						<a href="#" onclick="edit('<@jspEl 'entry.'+column.columnNameLower/>')">修改</a>&nbsp;
						<a href="#" onclick="del('<@jspEl 'entry.'+column.columnNameLower/>')">删除</a>&nbsp;
				    </#if>
				</#list>
				</td>
			</tr>
			</c:forEach>
			</table>
     	 <yk:page url="<@jspEl 'yk'/>${actionBasePath}/list.${actionExtension}" id="logForm" submitForm="queryForm" joy="true"/>
</div>

<script type="text/javascript">
function show(id){
	window.open('<@jspEl 'yk'/>${actionBasePath}/show.${actionExtension}?'+$('#pkName').val()+'='+id,'','height=600,width=600,resizable=yes,location=no,scrollbars');
}
function edit(id){
	window.open('<@jspEl 'yk'/>${actionBasePath}/edit.${actionExtension}?'+$('#pkName').val()+'='+id,'','height=600,width=600,resizable=yes,location=no,scrollbars');
}

$(function(){
	
	$('#btnedit').click(function(){
		var $id = $('[name=ids]:checkbox:checked');
		
		if($id.length==0){
			alert("请选择要修改的信息！");
			return false;
		}
		window.open('<@jspEl 'yk'/>${actionBasePath}/edit.${actionExtension}?'+$('#pkName').val()+'='+$id.val(),'','height=600,width=600,resizable=yes,location=no,scrollbars');
	});
	
	$('#btnadd').click(function(){
		window.open('<@jspEl 'yk'/>${actionBasePath}/create.${actionExtension}','','height=600,width=600,resizable=yes,location=no,scrollbars');
	});
	
	$('#btndelete').click(function(){
		if($('[name=ids]:checkbox:checked').length>0){
			var ids = [];
			$('[name=ids]:checkbox:checked').each(function(){ids.push($(this).val())});
			v_deleteItems(ids);
		}else{
			alert("请选择后进行删除操作！");
		}
		
	});
	
	$('#btnclear').click(function(){
		<#list table.notPkColumns as column>
			<#if column.isDateTimeColumn>
			$('#${column.columnNameLower}Begin').val('');
			$('#${column.columnNameLower}End').val('');
			<#else>
			$('#${column.columnNameLower}').val('');
			</#if>
		</#list>
	});
});

function del(id){
	var ids = [];
	ids.push(id);
	v_deleteItems(ids);
}

function v_deleteItems(ids){
	if(ids.length>0 && confirm("确定要删除吗？")){
		$.post( "<@jspEl 'yk'/>${actionBasePath}/delete.${actionExtension}", {"items":ids.join(',')}, 
				function(data){
					if(data=='1'){
						alert('删除成功');
						$('#queryForm').submit();
					}else{
						alert('删除失败');
					}
				}
		);
	}
};
</script>
</body>
</html>


<#macro generateIdQueryString>
	<#if table.compositeId>
		<#assign itemPrefix = 'item.id.'>
	<#else>
		<#assign itemPrefix = 'item.'>
	</#if>
<#compress>
		<#list table.compositeIdColumns as column>
			<#t>${column.columnNameLower}=<@jspEl itemPrefix + column.columnNameLower/>&
		</#list>				
</#compress>
</#macro>