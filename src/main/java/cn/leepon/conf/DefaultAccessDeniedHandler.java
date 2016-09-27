package cn.leepon.conf;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * This class is used for ...
 * 
 * @author leepon1990
 * @version 1.0, 2016年9月9日 下午2:11:08
 */

@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
			request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			//转发
			response.sendRedirect("/unauthorized");
			//重定向
			//RequestDispatcher dispatcher = request.getRequestDispatcher("/unauthorized");
			//dispatcher.forward(request, response);
	}
}
