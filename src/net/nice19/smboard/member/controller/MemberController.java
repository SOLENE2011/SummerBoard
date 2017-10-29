package net.nice19.smboard.member.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.nice19.smboard.member.model.MemberModel;
import net.nice19.smboard.member.service.MemberService;
import net.nice19.smboard.member.service.MemberValidatior;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private ApplicationContext context;

	@RequestMapping("/join.do")
	public String memberJoin() {
		return "board/join";
	}

	@RequestMapping(value = "/join.do", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("MemberModel") MemberModel memberModel, BindingResult result) {
		// BindingResult 의 경우 ModelAttribute 을 이용해 
		// 매개변수를 Bean 에 binding 할 때 
		// 발생한 오류 정보를 받기 위해 선언해야 하는 annotation
		// 폼 값을 커맨드 객체에 바인딩(binding)한 결과를 저장하고 
		// 에러 Code로부터 에러 Message를 가져온다
		ModelAndView mav = new ModelAndView();
		new MemberValidatior().validate(memberModel, result);
		if (result.hasErrors()) {
			mav.setViewName("/board/join");
			return mav;
		}

		context = new ClassPathXmlApplicationContext("/config/applicationContext.xml");
		MemberService memberService = (MemberService) context.getBean("memberService");
		//       	<bean id="memberService" class="net.nice19.smboard.member.service.MemberService">
   		//				<property name="sqlMapClientTemplate" ref="sqlMapClientTemplate" />
   	
		MemberModel checkMemberModel = memberService.findByUserId(memberModel.getUserId());

		if (checkMemberModel != null) {
			mav.addObject("errCode", 1);
			mav.setViewName("/board/join");
			return mav;
		}

		if (memberService.addMember(memberModel)) {
			mav.addObject("errCode", 3);
			mav.setViewName("/board/login");
			return mav;
		} else {
			mav.addObject("errCode", 2);
			// failed to add new member
			mav.setViewName("/board/join");
			return mav;

		}
	}
}
