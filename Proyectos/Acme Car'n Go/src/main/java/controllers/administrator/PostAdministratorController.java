
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PostService;
import controllers.AbstractController;
import domain.Post;

@Controller
@RequestMapping("/post/customer")
public class PostAdministratorController extends AbstractController {

	public PostAdministratorController() {
		super();
	}


	@Autowired
	private PostService	postService;


	@RequestMapping("/ban")
	public ModelAndView ban(@RequestParam final int postId) {
		ModelAndView result;
		Post post;
		post = this.postService.findOne(postId);
		this.postService.ban(post);

		result = new ModelAndView("redirect: /welcome/index.do");

		return result;
	}
}
