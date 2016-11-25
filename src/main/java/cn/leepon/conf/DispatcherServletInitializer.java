package cn.leepon.conf;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**   
 * This class is used for ...   
 * @author leepon1990  
 * @version   
 *       1.0, 2016年11月24日 下午6:53:21   
 */
@Configuration
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		super.onStartup(servletContext);    
	    FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", CharacterEncodingFilter.class);    
	    encodingFilter.setInitParameter("encoding", "UTF-8");    
	    encodingFilter.setInitParameter("forceEncoding", "true");    
	    encodingFilter.setAsyncSupported(true);    
	    encodingFilter.addMappingForUrlPatterns(null, false, "/*");    
	    servletContext.addListener(new HttpSessionEventPublisher());   
	}

}
