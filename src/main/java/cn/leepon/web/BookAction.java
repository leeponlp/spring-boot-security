package cn.leepon.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年9月5日 下午7:08:26   
 */
@Controller
@RequestMapping("/book")
public class BookAction {
	
	@RequestMapping("/view")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String book(){
		
		return "book";
	}
	
	
	@RequestMapping("/add")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@ResponseBody
	public String addbook(){
		
		return "添加书操作成功";
	}
	
	@RequestMapping("/delete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ResponseBody
	public String deletebook(){
		
		return "删除书操作成功";
	}
	
	
	@RequestMapping("/query")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@ResponseBody
	public String querybook(){
		
		return "查询书操作成功";
	}



}
