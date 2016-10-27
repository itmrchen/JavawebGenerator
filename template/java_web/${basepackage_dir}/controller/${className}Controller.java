<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign pkColumn = table.pkColumn?cap_first>
<#assign pkJavaType = table.pkColumn.simpleJavaType>

package ${basepackage}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.javazx.base.BaseController;
import com.javazx.base.page.Page;

import ${basepackage}.core.query.${className}Query;
import ${basepackage}.core.po.${className};
import ${basepackage}.core.service.I${className}Manager;

<#include "/java_imports.include">
@RequestMapping("${className}")
@Controller
public class ${className}Controller extends BaseController {
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	
	//forward paths
	protected static final String LIST_JSP= "${jspFileBasePath}/list";
	protected static final String CREATE_JSP = "${jspFileBasePath}/create";
	protected static final String EDIT_JSP = "${jspFileBasePath}/edit";
	protected static final String SHOW_JSP = "${jspFileBasePath}/show";
	//redirect paths,startWith: !
	
	@Autowired
	private I${className}Manager ${classNameLower}Manager;
	
	@RequestMapping(value="/list")
	public ModelAndView list() {
		
		${className}Query query = newQuery(${className}Query.class,DEFAULT_SORT_COLUMNS);
		Page page = ${classNameLower}Manager.findPage(query);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(LIST_JSP);
		modelAndView.addObject("page", page);
		
		return modelAndView;
	}
	
	/** 进入新增页面*/
	@RequestMapping(value="/create")
	public ModelAndView create() {
		return new ModelAndView(CREATE_JSP);
	}
	
	/** 保存新增对象 */
	@RequestMapping(value="/save")
	@ResponseBody
	public String save(${className} ${classNameLower}) {
		${classNameLower}Manager.save(${classNameLower});
		return writeAjaxResponse("1");
	}
	
	/** 查看对象*/
	@RequestMapping(value="/show")
	public ModelAndView show(${className} ${classNameLower}) {
		${className} model = ${classNameLower}Manager.getById(${classNameLower}.get${pkColumn}());
		ModelAndView mav = new ModelAndView(SHOW_JSP);
		mav.addObject("model", model);
		return mav;
	}
	
	/**进入更新页面*/
	@RequestMapping(value="/edit")
	public ModelAndView edit(${className} ${classNameLower}) {
		${className} model = ${classNameLower}Manager.getById(${classNameLower}.get${pkColumn}());
		ModelAndView mav = new ModelAndView(EDIT_JSP);
		mav.addObject("model", model);
		return mav;
	}
	
	/**保存更新对象*/
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(${className} ${classNameLower}) {
		${classNameLower}Manager.update(${classNameLower});
		return writeAjaxResponse("1");
	}
	
	
	/**删除对象*/
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delete(String items) {
		if(items!=null){
			String[] idArray = items.split(",");
			for(String id: idArray){
				${classNameLower}Manager.removeById(Integer.valueOf(id));
			}
		}
		
		return writeAjaxResponse("1");
	}
}
