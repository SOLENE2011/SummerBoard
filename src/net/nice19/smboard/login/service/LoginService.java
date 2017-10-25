package net.nice19.smboard.login.service;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import net.nice19.smboard.login.dao.LoginDao;
import net.nice19.smboard.login.model.LoginSessionModel;

//service는 DB에서 꺼내오는 작업과 연관되어 있음 그래서 applicationContext에 bean으로 등록되어있음.

public class LoginService implements LoginDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public LoginSessionModel checkUserId(String userId) {
		// TODO Auto-generated method stub
		return (LoginSessionModel) sqlMapClientTemplate.queryForObject("login.loginCheck", userId);
	}

}
