package net.nice19.smboard.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.nice19.smboard.login.model.LoginSessionModel;
import net.nice19.smboard.login.service.LoginService;
import net.nice19.smboard.login.service.LoginValidator;

@Controller
public class LoginController {

	private ApplicationContext context;

	@RequestMapping("/login.do")
	public String login() {
		return "/board/login";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public ModelAndView loginProc(@ModelAttribute("LoginModel") LoginSessionModel loginModel, BindingResult result,
			HttpSession session) {
		// BindingResult 의 경우 ModelAttribute 을 이용해 
		// 매개변수를 Bean 에 binding 할 때 
		// 발생한 오류 정보를 받기 위해 선언해야 하는 애노테이션입니다
		
		ModelAndView mav = new ModelAndView();

		// form validation
		new LoginValidator().validate(loginModel, result);
		if (result.hasErrors()) {
			mav.setViewName("/board/login");
			return mav;
		}

		String userId = loginModel.getUserId();
		String userPw = loginModel.getUserPw();

		context = new ClassPathXmlApplicationContext("/config/applicationContext.xml");
		LoginService loginService = (LoginService) context.getBean("loginService");
		LoginSessionModel loginCheckResult = loginService.checkUserId(userId);
		
		// check joined user
		if (loginCheckResult == null) {
			mav.addObject("userId", userId);
			mav.addObject("errCode", 1);
			mav.setViewName("/board/login");
			return mav;
		}

		// check password
		if (loginCheckResult.getUserPw().equals(userPw)) {
			// session에 올리는 작업
			session.setAttribute("userId", userId);
			session.setAttribute("userName", loginCheckResult.getUserName());
			mav.setViewName("redirect:/board/list.do");
			return mav;
		} else {
			mav.addObject("userId", userId);
			mav.addObject("errCode", 2);
			mav.setViewName("/board/login");
			return mav;
		}

	}

	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login.do";
	}
}
