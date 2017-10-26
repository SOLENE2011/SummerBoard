package net.nice19.smboard.board.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import net.nice19.smboard.board.dao.BoardDao;
import net.nice19.smboard.board.model.BoardCommentModel;
import net.nice19.smboard.board.model.BoardModel;

public class BoardService implements BoardDao {

	private SqlMapClientTemplate sqlMapClientTemplate;
	private HashMap<String, Object> valueMap = new HashMap<String, Object>();

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	// 여기까진 모든 service가 동일한 내용임
	
	
	@Override
	public List<BoardModel> getBoardList(int startArticleNum, int endArticleNum) {
		// TODO Auto-generated method stub
		valueMap.put("startArticleNum", startArticleNum);
		valueMap.put("endArticleNum", endArticleNum);
		return sqlMapClientTemplate.queryForList("board.getBoardList", valueMap);

	}

	@Override
	public BoardModel getOneArticle(int idx) {
		// TODO Auto-generated method stub
		return (BoardModel) sqlMapClientTemplate.queryForObject("board.getOneArticle", idx);
	}

	@Override
	public List<BoardModel> searchArticle(String type, String keyword, int startArticleNum, int endArticleNum) {
		// TODO Auto-generated method stub
		valueMap.put("type", type);
		valueMap.put("keyword", keyword);
		valueMap.put("startArticleNum", startArticleNum);
		valueMap.put("endArticleNum", endArticleNum);
		System.out.println(valueMap.size());
		return sqlMapClientTemplate.queryForList("board.searchArticle", valueMap);
	}

	@Override
	public List<BoardCommentModel> getCommentList(int idx) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("board.getCommentList", idx);
	}

	@Override
	public BoardCommentModel getOneComment(int idx) {
		// TODO Auto-generated method stub
		return (BoardCommentModel) sqlMapClientTemplate.queryForObject("board.getOneComment", idx);
	}

	@Override
	public boolean modifyArticle(BoardModel board) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.update("board.modifyArticle", board);
		return true;
	}

	@Override
	public boolean writeArticle(BoardModel board) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.insert("board.writeArticle", board);
		return true;
	}

	@Override
	public boolean writeComment(BoardCommentModel comment) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.insert("board.writeComment", comment);
		return true;
	}

	@Override
	public void updateHitcount(int hitcount, int idx) {
		// TODO Auto-generated method stub
		valueMap.put("hitcount", hitcount);
		valueMap.put("idx", idx);
		sqlMapClientTemplate.update("board.updateHitcount", valueMap);

	}

	@Override
	public void updateRecommendCount(int recommendcount, int idx) {
		// TODO Auto-generated method stub
		valueMap.put("recommendcount", recommendcount);
		valueMap.put("idx", idx);
		sqlMapClientTemplate.update("board.updateRecommendcount", valueMap);
	}

	@Override
	public int getTotalNum() {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClientTemplate.queryForObject("board.getTotalNum");
	}

	@Override
	public int getSearchTotalNum(String type, String keyword) {
		// TODO Auto-generated method stub
		valueMap.put("type", type);
		valueMap.put("keyword", keyword);
		return (Integer) sqlMapClientTemplate.queryForObject("board.getSearchTotalNum", valueMap);
	}

	@Override
	public void deleteComment(int idx) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.delete("board.deleteComment", idx);
	}

	@Override
	public void deleteArticle(int idx) {
		// TODO Auto-generated method stub
		sqlMapClientTemplate.delete("board.deleteArticle", idx);
	}

}
