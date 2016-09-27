package cn.leepon.conf;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import cn.leepon.service.impl.UserDetailServiceImpl;

/**
 * This class is used for ...
 * 
 * @author leepon1990
 * @version 1.0, 2016年9月1日 下午6:01:47
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//允许进入页面方法前检验
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailServiceImpl userDetailServiceImpl;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/css/**", "/js/**", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		  .authorizeRequests()
		  //.antMatchers("/css/**", "/js/**", "/webjars/**").permitAll()
		  //.antMatchers("/").permitAll()
          //.antMatchers("/user").hasRole("ADMIN")
		  .anyRequest().fullyAuthenticated()
		  .and().csrf().csrfTokenRepository(csrfTokenRepository())
          .and().addFilterAfter(csrfHeaderFilter(), SessionManagementFilter.class);
		http
		  .formLogin()
		  .loginProcessingUrl("/login")
		  //.loginPage("/login")
		  .failureUrl("/login?error")
		  .permitAll()
		  .and()
		  .logout()
		  .logoutUrl("/logout")
		  .logoutSuccessUrl("/login")
          .deleteCookies("JSESSIONID","X-XSRF-TOKEN")
		  .permitAll();
		http
		  .exceptionHandling()
		  .accessDeniedHandler(new DefaultAccessDeniedHandler());
//		http
//		  .rememberMe()
//		  .rememberMeCookieName("")
//		  .tokenValiditySeconds(200000)
//		  .rememberMeServices(rememberMeServices);

	}
		
	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 
		 auth.userDetailsService(userDetailServiceImpl);
//	        auth
//	            .inMemoryAuthentication()
//	                .withUser("leepon").password("leepon").roles("USER","GUEST")
//	            .and()
//	                .withUser("admin").password("admin").roles("ADMIN","USER","GUEST"); 
	    }
	 
	 private Filter csrfHeaderFilter() {
	        return new OncePerRequestFilter() {

	            @Override
	            protected void doFilterInternal(HttpServletRequest request,
	                                            HttpServletResponse response,
	                                            FilterChain filterChain) throws ServletException, IOException {

	                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
	                if (csrf != null) {
	                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
	                    String token = csrf.getToken();
	                    if (cookie == null || token != null
	                            && !token.equals(cookie.getValue())) {
	                        cookie = new Cookie("XSRF-TOKEN", token);
	                        cookie.setPath("/");
	                        response.addCookie(cookie);
	                    }
	                }
	                filterChain.doFilter(request, response);
	            }
	        };
	    }

	    private CsrfTokenRepository csrfTokenRepository() {
	        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	        repository.setHeaderName("X-XSRF-TOKEN");
	        //repository.setSessionAttributeName(("X-XSRF-TOKEN"));
	        return repository;
	    }
	
}
