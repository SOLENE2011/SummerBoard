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
		
		// TODO Auto-generated method stub
		MemberModel memberModel = (MemberModel) target;

		if (memberModel.getUserId() == null || memberModel.getUserId().trim().isEmpty()) {
			errors.rejectValue("userId", "required");
		}

		if (memberModel.getUserPw() == null || memberModel.getUserPw().trim().isEmpty()) {
			errors.rejectValue("userPw", "required");
		}

		if (memberModel.getUserName() == null || memberModel.getUserName().trim().isEmpty()) {
			errors.rejectValue("userName", "required");
		}
	}

}
