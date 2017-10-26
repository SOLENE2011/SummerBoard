package net.nice19.smboard.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter{
 
@Override

//요청이 Contoller 가기전에 잡아챔. 세션이 있는지 없는지 체크!
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
	throws Exception {
	// check variable
	Object userId = request.getSession().getAttribute("userId");
	
	// pass through when access login.do, join.do
	if(request.getRequestURI().equals("/SummerBoard/login.do") || 
				request.getRequestURI().equals("/SummerBoard/member/join.do")) {
			if(userId != null) {
				response.sendRedirect(request.getContextPath() + "/board/list.do");
				return true;
			} else {
				return true;
			}
	}
	
	// where other pages
			if(userId == null) {
				response.sendRedirect(request.getContextPath() + "/login.do");
				return false;
			} else {
				return true;
			}
	
}

@Override
//요청이 끝난 후 
public void postHandle(HttpServletRequest request, 
		HttpServletResponse response, Object handler, 
		ModelAndView modelAndView) throws Exception {
	
}

}
