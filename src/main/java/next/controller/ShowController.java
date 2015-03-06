package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class ShowController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(ShowController.class);
	
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		logger.debug("questionId : {}", questionId);
		Question question = questionDao.findById(questionId);
		List<Answer> answers = answerDao.findAllByQuestionId(questionId);
		ModelAndView mav = jstlView("show.jsp");
		mav.addObject("question", question);
		mav.addObject("answers", answers);
		return mav;
	}
}
