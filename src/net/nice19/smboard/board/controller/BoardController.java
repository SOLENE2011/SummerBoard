package net.nice19.smboard.board.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import net.nice19.smboard.board.model.BoardCommentModel;
import net.nice19.smboard.board.model.BoardModel;
import net.nice19.smboard.board.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	//DI
	private ApplicationContext context = new ClassPathXmlApplicationContext("/config/applicationContext.xml");
	//service를 갖다 쓰기 위해 context 생성함. 컨테이너 객체 만들고
	//서비스를 객체생성해서 가져온당.
	private BoardService boardService = (BoardService) context.getBean("boardService");
	
	
	// * User variable
	// article, page variables
	private int currentPage=1;
	private int showArticleLimit = 10;
	// 한페이지에 보여줄 게시글
	// change value if want to show more articles by one page
	private int showPageLimit = 10;
	// change value if want to show more page links
	private int startArticleNum = 0;
	private int endArticleNum = 0;
	private int totalNum = 0;
	
	// file upload path
	
	private String uploadPath = "C:\\Java\\App\\SummerBoard\\WebContent\\files\\";

	// 게시판 리스트 띄우기!!! + 페이징
	@RequestMapping("/list.do")
	public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response) {
		
		String type = null;
		String keyword = null;
		
		// set variables from request parameter request parameter의 세부 설정.
		if(request.getParameter("page") == null || request.getParameter("page").trim().isEmpty() || 
					request.getParameter("page").equals("0")) {
				currentPage = 1;
		} else {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		if(request.getParameter("type") != null) {
			type = request.getParameter("type").trim();
		}
		
		if(request.getParameter("keyword") != null) {
			keyword = request.getParameter("keyword").trim();
		}
		
		// expression article variables value	
		startArticleNum = (currentPage - 1) * showArticleLimit +1;
		endArticleNum = startArticleNum + showArticleLimit-1;
		

		// get boardList and get page html code
		List<BoardModel> boardList;
		if(type != null && keyword != null) {
			boardList = boardService.searchArticle(type, keyword, startArticleNum, endArticleNum);
			totalNum = boardService.getSearchTotalNum(type, keyword);
			// 검색 결과가 boardList에 들어가는거
			// 총 숫자를 구하는 이유는 아래 pageHtml에서 사용하기 위해서임! paging을 위해!!!
		} else {
			boardList = boardService.getBoardList(startArticleNum, endArticleNum);
			totalNum = boardService.getTotalNum();
		}
		
		StringBuffer pageHtml = getPageHtml(currentPage, totalNum, showArticleLimit, showPageLimit, type, keyword);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardList", boardList);
		mav.addObject("pageHtml", pageHtml);
		// Map에 저장된 <키,값> 쌍 전체를 뷰에 전달할 값으로 ModelAndView 객체에 추가하고 싶다면
		// Map referenceMap = referenceData();
		// 	mav.addAllObjects(referenceMap);
		mav.setViewName("/board/list");
		
		return mav;
	}
	

	private StringBuffer getPageHtml(int currentPage, int totalNum, int showArticleLimit, int showPageLimit, String type, String keyword) {
		StringBuffer pageHtml = new StringBuffer();
		int startPage = 0;
		int lastPage = 0;
		
		// expression page variables
		startPage = ((currentPage-1) / showPageLimit) * showPageLimit + 1;
		lastPage = startPage + showPageLimit -1;
		
		if(lastPage > totalNum / showArticleLimit) {
			lastPage = (totalNum / showArticleLimit) +1;
		}
		// create page html code
		// if: when no search
		
		if(type == null && keyword == null) {
			if(currentPage ==1) {
				pageHtml.append("<span>");
			} else {
				pageHtml.append("<span><a href=\"list.do?page=" + (currentPage-1) + "\"><이전></a>&nbsp;&nbsp;");
			}
			
			for(int i = startPage ; i<=lastPage ; i++) {
				if(i == currentPage) {
					pageHtml.append(".&nbsp;<strong>");
					pageHtml.append("<a href=\"list.do?page=" + i + "\" class=\"page\">" + i + "</a>");
					pageHtml.append("&nbsp;</strong>");
				} else {
					pageHtml.append(".&nbsp;<a href=\"list.do?page=" + i + "\" class=\"page\">" + i + "</a>&nbsp;");
				}
			}
			
			if(currentPage == lastPage) {
				pageHtml.append(".</span>");
			} else {
				pageHtml.append(".&nbsp;&nbsp;<a href=\"list.do?page=" + (currentPage+1) + "\"><다음></a></span>");
				
			}
			
		} else {
			if(currentPage ==1) {
				pageHtml.append("<span>");
			} else {
				pageHtml.append("<span><a href=\"list.do?page=" + (currentPage-1) + "&type=" + type + "&keyword=" + keyword + "\"><이전></a>&nbsp;&nbsp;");
			}
			
			for(int i = startPage ; i <= lastPage ; i++) {
				if(i == currentPage) {
					pageHtml.append(".&nbsp;<strong>");
					pageHtml.append("<a href=\"list.do?page=" +i + "&type=" + type + "&keyword=" + keyword + "\" class=\"page\">" + i + "</a>&nbsp;");
					pageHtml.append("&nbsp;</strong>");
				} else {
					pageHtml.append(".&nbsp;<a href=\"list.do?page=" +i + "&type=" + type + "&keyword=" + keyword + "\" class=\"page\">" + i + "</a>&nbsp;");
				}
			}
			if(currentPage == lastPage) {
				pageHtml.append("</span>");
			} else {
				pageHtml.append(".&nbsp;&nbsp;<a href=\"list.do?page=" + (currentPage+1) + "&type=" + type + "&keyword=" + keyword + "\"><다음></a></span>");
			}
		}
		return pageHtml;
	}
	
	// 글 보여주기 VIEW!
	@RequestMapping("/view.do")
	public ModelAndView boardView(HttpServletRequest request) {
		int idx = Integer.parseInt(request.getParameter("idx"));
		BoardModel board = boardService.getOneArticle(idx);
		// 1.한줄 짜리 데이터를 가져와야함
		boardService.updateHitcount(board.getHitcount()+1, idx);
		// 2.그리고 조회수 1을 증가시켜야함.
		List<BoardCommentModel> commentList = boardService.getCommentList(idx);
		// get comment list 댓글 리스트도 뽑아옴
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("board", board);
		mav.addObject("commentList", commentList);
		mav.setViewName("/board/view");
		return mav;
		
	}
	
	// 글 쓰기 FORM!
	@RequestMapping("/write.do")
	public String boardWrite(@ModelAttribute("BoardModel") BoardModel boardModel) {
		return "/board/write";
		//  String 타입을 리턴할 경우, 문자열 값이 뷰 이름으로 사용된다
		// 컨트롤러의 처리 결과(메서드 실행)를 생성할 뷰를 결정하는 건 Resolver
		// 그래서 /WEB-INF/view/ return값.jsp 로 찾아감
		// <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		// <property name="prefix" value="/WEB-INF/view/" />
		// <property name="suffix" value=".jsp" />
		// </bean>
	}
	
	// 글 쓰기 작업!
	@RequestMapping(value="/write.do", method = RequestMethod.POST)
	public String boardWriteProc(@ModelAttribute("BoardModel") BoardModel boardModel, MultipartHttpServletRequest request) {
		// get upload file
		MultipartFile file = request.getFile("file");
		String fileName = file.getOriginalFilename();
		File uploadFile = new File(uploadPath + fileName);
		// 
		//private String uploadPath = "C:\\Java\\App\\SummerBoard\\WebContent\\files\\";
		
		// 같은 이름의 파일이 존재할때!
		if(uploadFile.exists()) {
			fileName = new Date().getTime() + fileName;
			uploadFile = new File(uploadPath + fileName);
								// new File : java.io.File.File(String pathname)
								// 경로를 지정하기 위해 파일이라는 객체 생성
		}
		
		try {
			file.transferTo(uploadFile);
			 // jsp에서 업로드한 file의 데이터를
			 // "C:\\Java\\App\\SummerBoard\\WebContent\\files\\fileName" 경로에
			 // 생성한 uploadFile에 파일 데이터를 저장한다
		} catch (Exception e) {
			
		}
		
		boardModel.setFileName(fileName);
		
		// enter를 <br />로 대체해서 jsp에서 enter 친대로 데이터가 보이도록 함.
		String content = boardModel.getContent().replaceAll("\r\n", "<br />");
		boardModel.setContent(content);
		
		boardService.writeArticle(boardModel);
		
		return "redirect:list.do";
		// redirect: 기존의 정보를 가져가지않음
		// viewResolver로 가지 않고 브라우저로 감
        //	redirect:/bbs/list - 현재 Servlet 컨텍스트에 대한 상대적인 경로로 리다이렉트
        // redirect:http://host/bbs/list - 지정한 절대 URL로 리다이렉트
	}
	
	// 댓글 달기 작업!
	@RequestMapping("/commentWrite.do")
	public ModelAndView commentWriteProc(@ModelAttribute("CommentModel") BoardCommentModel commentModel) {
		
		
		String content = commentModel.getContent().replaceAll("\r\n", "<br />");
		commentModel.setContent(content);
		
		boardService.writeComment(commentModel);
		ModelAndView mav = new ModelAndView();
		mav.addObject("idx", commentModel.getLinkedArticleNum());
		mav.setViewName("redirect:view.do");;
		// 입력이 된 다음에 바로 view.jsp 보여줌
		return mav;
	}

	// 글 수정 폼!
	@RequestMapping("/modify.do")
	public ModelAndView boardModify(HttpServletRequest request, HttpSession session) {
		// 남이 내꺼를 수정하면 안돼니깐 HttpSession session으로 본인 여부 확인
		String userId = (String) session.getAttribute("userId");
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		BoardModel board = boardService.getOneArticle(idx);
		String content = board.getContent().replaceAll("<br />", "\r\n");
		board.setContent(content);
		
		ModelAndView mav = new ModelAndView();
		
		if(!userId.equals(board.getWriterId())) {
			// ID가 일치하지 않는다면 view.jsp로 가서 
			//case 1: alert("잘못된 접근 경로입니다!"); 에러창이 출력됨.
			mav.addObject("errCode", "1");
			mav.addObject("idx", idx);
			mav.setViewName("redirect:view.do");
		} else {
			mav.addObject("board", board);
			mav.setViewName("/board/modify");
		}
		
		return mav;
	}
	
	// 글 수정	작업!
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	public ModelAndView boardModifyProc(@ModelAttribute("BoardModel") BoardModel boardModel, MultipartHttpServletRequest request) {
		String orgFileName = request.getParameter("orgFile");
		MultipartFile newFile = request.getFile("newFile");
		String newFileName = newFile.getOriginalFilename();
		
		boardModel.setFileName(orgFileName);
		
		// 사진 변경을 원할 때
		if(newFile != null && !newFileName.equals("")) {
			if(orgFileName != null || !orgFileName.equals("")) {
			// 처음올린 파일을 삭제한다.
			File removeFile = new File(uploadPath + orgFileName);
										// java.io.File.File(String pathname)
										// 파일 경로를 지정하기 위해 객체를 생성합니당.
			removeFile.delete();
		}
		
		// 새로 생성한 파일에 다시 newFile을 저장시킨다.
		File newUploadFile = new File(uploadPath + newFileName);
		
		try {
			newFile.transferTo(newUploadFile);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		boardModel.setFileName(newFileName);
	}
		
		String content = boardModel.getContent().replaceAll("\r\n", "<br />");
		boardModel.setContent(content);
		
		boardService.modifyArticle(boardModel);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("idx", boardModel.getIdx());
		mav.setViewName("redirect:/board/view.do");
		return mav;
	}
	
	// 글 삭제 작업!
	@RequestMapping("delete.do")
	public ModelAndView boardDelete(HttpServletRequest request, HttpSession session) {
		// 남이 내꺼를 삭제하면 안돼니깐 HttpSession session으로 본인 여부 확인
		String userId = (String) session.getAttribute("userId");
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		BoardModel board = boardService.getOneArticle(idx);
		
		ModelAndView mav = new ModelAndView();
		
		if(!userId.equals(board.getWriterId())) {
			mav.addObject("errCode", "1");
			mav.addObject("idx", idx);
			mav.setViewName("redirect:view.do");
		} else {
			List<BoardCommentModel> commentList = boardService.getCommentList(idx);
			// check comments
			if(commentList.size() > 0) {
				mav.addObject("errCode", "2");
				mav.addObject("idx", idx);
				mav.setViewName("redirect:view.do");
				// switch(errCode)
				//	case 2:
				// alert("댓글이 있어 글을 삭제하실 수 없습니다!");
				// 댓글이 있으면 삭제하지 못하도록 함.
			} else {
				// C:\Java\App\SummerBoard\WebContent\files
				// Eclipse에 업로드된 파일까지 삭제하기 위한 작업임.
				if(board.getFileName() != null) {
					File removeFile = new File(uploadPath + board.getFileName());
					removeFile.delete();
				}
				
				boardService.deleteArticle(idx);
				
				mav.setViewName("redirect:list.do");
				
			}
		}
		return mav;
	}
	
	// 댓글 삭제 작업
	@RequestMapping("/commentDelete.do")
	public ModelAndView commendDelete(HttpServletRequest request, HttpSession session) {
		// 남이 내꺼를 수정하면 안돼니깐 HttpSession session으로 본인 여부 확인
		int idx = Integer.parseInt(request.getParameter("idx"));
		int linkedArticleNum = Integer.parseInt(request.getParameter("linkedArticleNum"));
		
		String userId = (String) session.getAttribute("userId");
		BoardCommentModel comment = boardService.getOneComment(idx);
		
		ModelAndView mav = new ModelAndView();
		
		if(!userId.equals(comment.getWriterId())) {
			mav.addObject("errCode", "1");
			// case 1: alert("잘못된 접근 경로입니다!");
		} else {
			boardService.deleteComment(idx);
		}
		
		mav.addObject("idx", linkedArticleNum);
		// move back to the article
		mav.setViewName("redirect:view.do");
		
		return mav;
	}
	
	// 추천하기!
	@RequestMapping("/recommend.do")
	public ModelAndView updateRecommendcount(HttpServletRequest request, HttpSession session) {
		// 본인은 본인의 글을 추천하지 못하게 하기 위해 session 함께.
		int idx = Integer.parseInt(request.getParameter("idx"));
		String userId = (String) session.getAttribute("userId");
		BoardModel board = boardService.getOneArticle(idx);
		
		ModelAndView mav = new ModelAndView();
		
		if(userId.equals(board.getWriterId())) {
			mav.addObject("errCode", "1");
			// 본인은 본인 글을 추천할 수 없도록 설정함.
		} else {
			boardService.updateRecommendCount(board.getRecommendcount()+1, idx);
			// 본인이 아니면 추천수 1 증가
		}
		mav.addObject("idx", idx);
		mav.setViewName("redirect:/board/view.do");
		
		return mav;
	}
}
