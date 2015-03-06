package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class AddAnswerController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String writer = request.getParameter("writer");
		String contents = request.getParameter("contents");
		long questionId = Integer.parseInt(request.getParameter("questionId"));

		answerDao.insert(new Answer(writer, contents, questionId));
		questionDao.increaseCountOfCommentById(questionId);
		
		ModelAndView mav = jstlView("redirect:/show.next?questionId="+questionId);

		return mav;
	}
}
