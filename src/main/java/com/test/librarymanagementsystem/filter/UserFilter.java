package com.test.librarymanagementsystem.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class UserFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(UserFilter.class);


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("------------------------Filter-------------------");
		
		//System.out.println(request.getRemoteAddr());
		chain.doFilter(request,response);
		
	}

}
