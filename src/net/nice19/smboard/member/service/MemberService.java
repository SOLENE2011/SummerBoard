package net.nice19.smboard.member.service;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import net.nice19.smboard.member.dao.MemberDao;
import net.nice19.smboard.member.model.MemberModel;


//service는 DB에서 꺼내오는 작업과 연관되어 있음 그래서 applicationContext에 bean으로 등록되어있음.

public class MemberService implements MemberDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public boolean addMember(MemberModel memberModel) {
		sqlMapClientTemplate.insert("member.addMember", memberModel);
		// insert sql문으로 db에 회원가입 정보들을 넣는 작업
		MemberModel checkAddMember = findByUserId(memberModel.getUserId());
		// DB에 잘 들어갔는지 확인함
		// 확인해서 자료가 없으면 false를 return 있으면 true를 return 한다.
		if (checkAddMember == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public MemberModel findByUserId(String userId) {
		// TODO Auto-generated method stub
		return (MemberModel) sqlMapClientTemplate.queryForObject("member.findByUserId", userId);
	}

}
