package cn.leepon.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年9月2日 上午9:48:27   
 */

@Controller
public class LoginAction {
	
	@Autowired
	SessionRegistry sessionRegistry;
	
	@RequestMapping("/")
	public String home(Model model){
		//获取认证信息
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;
		String username = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		List<String> authlist = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : authorities) {
			String authority = grantedAuthority.getAuthority();
			authlist.add(authority);
		}
		
		int size = sessionRegistry.getAllPrincipals().size();
		
		//当前登陆用户
		model.addAttribute("username", username);
		//当前用户拥有权限
		model.addAttribute("authlist", authlist);
		model.addAttribute("loginNum", size);
		return "home";
		
	}
	
	@RequestMapping("/login")
	public String login(){
		
		return "login";
	}
	
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		
		return "login";
	}

	
	@RequestMapping("/login?error")
	public String login_error(){
		
		return "login";
	}
	
	
	@RequestMapping("/login?expired")
	public String login_expired(){
		
		return "login";
	}

	
	@RequestMapping("/unauthorized")
	public String error(){
		
		return "unauthorized";
	}

	
	@RequestMapping("/demo")
	@ResponseBody
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String demo(){
		
		return "demo";
		
	}
	
	
	public static void main(String[] args) {
		String rawPassword = "leepon";
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		String encode = bc.encode(rawPassword);
		System.err.println(encode);
		
	}

}
