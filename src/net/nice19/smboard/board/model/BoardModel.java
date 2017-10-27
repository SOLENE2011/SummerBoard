package net.nice19.smboard.board.model;

public class BoardModel {

	private int rnum; // 조건에맞는
	// SQL문에서 rownum 쿼리(가상 컬럼)를 이용해 rnum이라는 행을 추가한 후
	// 서브쿼리 결과에 번호를 부여해 재정렬하기 위해 사용함
	// DB는 추가할 필요 없지만 SQL문(sql.xml)에서 정렬한것을 
	// Model에 넣기위해 int rnum을 만듬
	private int idx; //SEQ.
	private String writer; //글쓴이
	private String subject; //제목
	private String content; //내용
	private int hitcount = 0; //조회수
	private int recommendcount = 0; //추천수
	private int comment = 0; //코멘트
	private String writeDate; //쓴날짜
	private String writerId; // 글쓴이 ID
	private String fileName; //첨부파일이름

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHitcount() {
		return hitcount;
	}

	public void setHitcount(int hitcount) {
		this.hitcount = hitcount;
	}

	public int getRecommendcount() {
		return recommendcount;
	}

	public void setRecommendcount(int recommendcount) {
		this.recommendcount = recommendcount;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
