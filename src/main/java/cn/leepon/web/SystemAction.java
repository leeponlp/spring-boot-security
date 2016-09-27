package cn.leepon.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年9月5日 下午7:06:57   
 */
@Controller
@RequestMapping("/system")
public class SystemAction {
	
	@RequestMapping("/view")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public String system(){
		
		return "system";
	}
	
	@RequestMapping("/updatePassword")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ResponseBody
	public String updatePassword(){
		
		return "更新密码操作成功";
	}

}
