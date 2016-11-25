package cn.leepon.conf;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.ConcurrentSessionFilter;
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
		  .anyRequest().authenticated()
		  .and().csrf().csrfTokenRepository(csrfTokenRepository())
          .and().addFilterAfter(csrfHeaderFilter(), SessionManagementFilter.class);
		http
		  .formLogin()
		  //.loginProcessingUrl("/login")
		  .loginPage("/login")
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
//		  .and()
//          .sessionManagement()
//          .maximumSessions(3).maxSessionsPreventsLogin(true)
//          .sessionRegistry(sessionRegistry()); 
		
		http.sessionManagement()
		    .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
		    .and()
		    .addFilter(concurrentSessionFilter());
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

	    //注册自定义的SessionRegistry
	    @Bean    
	    public SessionRegistry sessionRegistry(){    
	        return new SessionRegistryImpl();    
	    }    
	    
	    //注册自定义的sessionAuthenticationStrategy
	    //ConcurrentSessionControlAuthenticationStrategy控制并发
	    //SessionFixationProtectionStrategy可以防盗session
	    //RegisterSessionAuthenticationStrategy触发了注册新sessin
	    @Bean
	    public CompositeSessionAuthenticationStrategy sessionAuthenticationStrategy(){
	    	ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy=new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
	    	concurrentSessionControlAuthenticationStrategy.setMaximumSessions(2);
	    	concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(true);
	    	SessionFixationProtectionStrategy sessionFixationProtectionStrategy=new SessionFixationProtectionStrategy();
	    	RegisterSessionAuthenticationStrategy registerSessionStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry());
	    	CompositeSessionAuthenticationStrategy sessionAuthenticationStrategy=new CompositeSessionAuthenticationStrategy(
	    			Arrays.asList(concurrentSessionControlAuthenticationStrategy,sessionFixationProtectionStrategy,registerSessionStrategy));
	    	return sessionAuthenticationStrategy;
	    }
	    
	    //注册并发Session Filter
	    @Bean
	    public ConcurrentSessionFilter concurrentSessionFilter(){
	    	return new ConcurrentSessionFilter(sessionRegistry(),"/login?expired");
	    }
	    
	    	
}
