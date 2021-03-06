package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class AddAnswerController extends AbstractController {
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String writer = request.getParameter("writer");
		String contents = request.getParameter("contents");
		long questionId = Integer.parseInt(request.getParameter("questionId"));

		answerDao.insert(new Answer(writer, contents, questionId));
		questionDao.increaseCountOfCommentById(questionId);
		
		ModelAndView mav = jstlView("redirect:/show.next?questionId="+questionId); // 여기는 돌아가게 하기 위해 넣은 것 사실 jsonView()를 넣어도 상관 없다.

		return mav;
	}
}
