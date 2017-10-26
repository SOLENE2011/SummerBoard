package net.nice19.smboard.board.model;

public class BoardCommentModel {

	private int idx; //SEQ.
	private String writer; //아이디
	private String content; //내용
	private String writeDate; //날짜
	private int linkedArticleNum; //해당글번호
	private String writerId; 

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public int getLinkedArticleNum() {
		return linkedArticleNum;
	}

	public void setLinkedArticleNum(int linkedArticleNum) {
		this.linkedArticleNum = linkedArticleNum;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

}
