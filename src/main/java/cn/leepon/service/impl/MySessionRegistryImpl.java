package cn.leepon.service.impl;

import java.util.List;

import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Component;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年11月24日 下午6:15:38   
 */
@Component
public class MySessionRegistryImpl extends SessionRegistryImpl {
	
	@Override
	public List<Object> getAllPrincipals() {
		List<Object> list = super.getAllPrincipals();
		System.err.println(list.size());
		return list;
	}

}
