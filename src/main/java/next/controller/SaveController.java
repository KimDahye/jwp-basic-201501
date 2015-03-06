package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class SaveController extends AbstractController{
	private QuestionDao questionDao = new QuestionDao();
	private List<Question> questions;
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		questionDao.insert(new Question(writer, title, contents));

		questions = questionDao.findAll();		
		ModelAndView mav = jstlView("list.jsp");
		mav.addObject("questions", questions);
		
		return mav;
	}
}
