package cn.leepon.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import cn.leepon.mapper.UserMapper;
import cn.leepon.po.UserInfo;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年9月1日 下午6:02:22   
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	UserMapper userMapper;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserInfo userInfo = userMapper.getUserInfoByName(username);
		if(userInfo == null){
            throw new UsernameNotFoundException("用户名或密码错误！");
        }
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		String[] role_arr = StringUtils.split(userInfo.getRole(), ",");
		for (String role : role_arr) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		UserDetails userDetails = (UserDetails)new User(userInfo.getUsername(),userInfo.getPassword(), authorities);
		
		return userDetails;
	}
	
	
	

}
