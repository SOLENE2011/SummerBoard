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
	
	// 로그인이 되어있는데 login.do 나 join.do로 요청이 들어온다면
	// list.do로 Redirect 시킴 true.
	if(request.getRequestURI().equals("/SummerBoard/login.do") || 
				request.getRequestURI().equals("/SummerBoard/member/join.do")) {
			if(userId != null) {
				response.sendRedirect(request.getContextPath() + "/board/list.do");
				return true;
			} else {
				return true;
			}
	}
	
	// 로그인을 하지 않았으면 무조건 login do로 보냄. intercepter 후 요청 컨트롤러로 가지 않음 false
			if(userId == null) {
				response.sendRedirect(request.getContextPath() + "/login.do");
				return false;
				// false 무효
				// return false면 controller로 요청이 가지 않는다.
			} else {
				return true;
			}
	
}

@Override
//요청이 끝난 후 
public void postHandle(HttpServletRequest request, 
		HttpServletResponse response, Object handler, 
		ModelAndView modelAndView) throws Exception {
		System.out.println("Test");
}

}
