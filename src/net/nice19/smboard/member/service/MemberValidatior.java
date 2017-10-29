package net.nice19.smboard.member.service;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import net.nice19.smboard.member.model.MemberModel;

public class MemberValidatior implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MemberModel.class.isAssignableFrom(clazz);
		// MemberModel class의 유효성 검사
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		// error가 dispatcher-servlet에 있는
		// <!-- validation massage -->
		// <value>config.validation</value> 와 연결됨
		// SPRING은 객체가 유효한 지의 여부를 검사할 때 사용되는 Validator Interface와 
		// 유효성 검사 결과를 저장할 Errors Interface를 제공하고 있다.
		// 폼 값을 저장한 커맨드 객체의 유효성 여부를 검사하고, 
		// 입력 값이 유효하지 않을 경우 Errors에 에러 Message를 저장해서 뷰에 전달
		
		// TODO Auto-generated method stub
		MemberModel memberModel = (MemberModel) target;

		if (memberModel.getUserId() == null || memberModel.getUserId().trim().isEmpty()) {
			errors.rejectValue("userId", "required");
			// reject() 메서드는 검증 대상 객체의 전체적인 에러를 설정하며, 
			// rejectValue() 메서드는 특정 프로퍼티(필드)의 검증 에러를 설정한다
			// rejectValue(String field, String errorCode)
		}

		if (memberModel.getUserPw() == null || memberModel.getUserPw().trim().isEmpty()) {
			errors.rejectValue("userPw", "required");
		}

		if (memberModel.getUserName() == null || memberModel.getUserName().trim().isEmpty()) {
			errors.rejectValue("userName", "required");
		}
	}

}
