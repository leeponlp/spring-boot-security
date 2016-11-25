package cn.leepon.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年11月24日 下午6:50:08   
 */
@Configuration
public class SecurityWebApplicationInitializer  extends AbstractSecurityWebApplicationInitializer{

	@Override
	protected boolean enableHttpSessionEventPublisher() {
		return true;
	}
	

}
