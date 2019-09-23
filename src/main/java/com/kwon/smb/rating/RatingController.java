package com.kwon.smb.rating;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kwon.smb.DateManager;
import com.kwon.smb.user.UserDAO;


@Controller
public class RatingController {
	
	// servlet-context.xml에 있는 객체 불러오기
	@Autowired 
	private RatingDAO d;
	
	@Autowired 
	private UserDAO DAO;

	@RequestMapping(value = "/rating.do", method = RequestMethod.POST)
	public void ratingdo(HttpServletRequest req, HttpServletResponse res) throws IOException {

		System.out.println("rating.do 접속");
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().println(d.registerComment(req, res));
		
	}

	@RequestMapping(value = "/getrating.do", method = RequestMethod.POST)
	public void getrating(HttpServletRequest req, HttpServletResponse res) throws IOException {

		System.out.println("getrating.do 접속");
		res.setContentType("text/html;charset=UTF-8");
		d.getComment(req,res);
		d.paging(req, res);
		
		
	}
	
	@RequestMapping(value = "/myrating.go", method = RequestMethod.GET)
	public String myRating(HttpServletRequest req, HttpServletResponse res) throws IOException {
		DateManager.getCurrentYear(req, res);
		String nev = req.getParameter("selectNV");
		if(nev == null) {
			nev = "'myRating'";
		}
		
		req.setAttribute("selectNV", nev);
		
		DAO.autologin(req,res);
		
		if (DAO.loginCheck(req, res)) {
			int page;
			
			if(req.getParameter("selectNum")==null){
				page = 1;
			}else{
				page = Integer.parseInt(req.getParameter("selectNum"));
			}
			
			d.getMyRating(req,res);
			d.paging(page,req, res);
			
			req.setAttribute("ContentPage", "mypage.jsp");
			return "home";
		} else {
			return "user/login";
		}
		
	}
	
	@RequestMapping(value = "/deleteRating.do", method = RequestMethod.GET)
	public String deleteMyRating(HttpServletRequest req, HttpServletResponse res) throws IOException {
		DateManager.getCurrentYear(req, res);
		DAO.autologin(req,res);
		
		if (DAO.loginCheck(req, res)) {
/*			int page;
			
			if(req.getParameter("selectNum")==null){
				page = 1;
			}else{
				page = Integer.parseInt(req.getParameter("selectNum"));
			}*/
			
			d.deleteMyRating(req,res);
//			d.getMyRating(req,res);
//			d.paging(page,req, res);
			
//			req.setAttribute("ContentPage", "mypage.jsp");
			return "redirect:/myrating.go";
		} else {
			return "user/login";
		}
		
	}
	
	@RequestMapping(value = "/updateRating.do", method = RequestMethod.POST)
	public void updateMyRating(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html;charset=UTF-8");
		DAO.autologin(req,res);
		
		if (DAO.loginCheck(req, res)) {
			
			d.updateMyRating(req,res);
//			d.getMyRating(req,res);
//			d.paging(page,req, res);
			
//			req.setAttribute("ContentPage", "mypage.jsp");
		} else {
			res.getWriter().print("로그인 해주세요!");
		}
		
	}
	

	
}

















