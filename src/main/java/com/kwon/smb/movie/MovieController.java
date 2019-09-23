package com.kwon.smb.movie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kwon.smb.movie.MovieDAO;

//	1. ����⸸�ؼ� ����
//
//	2. post��� parameter�ѱ�ó�� - web.xml
//		���� 6��
//
//	3. jar���ϵ� - maven - pom.xml
//		���� 7��(2.1,  2.2)
//	---------------------------------------
//	4. mapper.xml - SQL ���� ����
//	   Java Interface - mapper.xml�� �� ��Ʈ
//	---------------------------------------
//	5. �ʿ��� ��ü(SqlSession) ���鷯 - servlet-context.xml
//		����7��(3)

@Controller
public class MovieController {
	
	// servlet-context.xml�� �ִ� ��ü �ҷ�����
	@Autowired 
	private MovieDAO d;
	
	@RequestMapping(value = "/top100.go", method = RequestMethod.GET)
	public String home(HttpServletRequest req, HttpServletResponse res) {
		//���õ� ���̰��̼� 
		
		String nev = req.getParameter("selectNV");
		if(nev == null) {
			nev = "'top100'";
		}
		
		req.setAttribute("selectNV", nev);
		
		int page;
		
		if(req.getParameter("selectNum")==null){
			page = 1;
		}else{
			page = Integer.parseInt(req.getParameter("selectNum"));
		}
		
		d.getTop100Movies(req, res);
		d.paging(page,req, res);
		
		req.setAttribute("ContentPage", "top100.jsp");
//		req.setAttribute("ContentPage", "mypage.jsp");
		
		return "home";
	}
	
	//����
	@RequestMapping(value = "/searcheMovie.do", method = RequestMethod.GET)
	public String searchMovieGo(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("serchMovie.do ����");
		System.out.println(req.getParameter("searcheMovie"));
		
		int page;
		
		if(req.getParameter("selectNum")==null){
			page = 1;
		}else{
			page = Integer.parseInt(req.getParameter("selectNum"));
		}
		d.searchMovie(req, res);
		d.paging(page,req, res);
		
		req.setAttribute("searchMovieTitle", req.getParameter("searcheMovie"));
		req.setAttribute("ContentPage", "searchMovies.jsp");
		
		return "home";
	}
	


	
}

















