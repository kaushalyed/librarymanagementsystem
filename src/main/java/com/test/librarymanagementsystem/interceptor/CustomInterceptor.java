package com.test.librarymanagementsystem.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomInterceptor implements HandlerInterceptor{
    Logger logger = LoggerFactory.getLogger(CustomInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //This is used to perform operations before sending the request to the controller. 
    	//This method should return true,then only request will go the controller,
    	//otherwise interceptor itself request will block.
        logger.info("Pre-Handle request");
        logger.info("URI Request--> "+request.getRequestURI());

        //use case--> on every saturday and sunday, every request should be reverted back to the user with appropriate message, like website not working
       /* Calendar cal=Calendar.getInstance();
        int dayOfWeek=cal.get(cal.DAY_OF_WEEK);

        if(dayOfWeek==1 || dayOfWeek==7){
            response.getWriter().write("The website is closed on Saturday and Sunday, will be resumed on Monday");
            return false;
        }*/
    	return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	//This is used to perform operations before sending the response to the client.
        logger.info("Post-Handle request");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	//This is used to perform operations after completing the request and response
        logger.info("Request Completed");
    }
}
