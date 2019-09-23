package com.kwon.smb.mahout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kwon.smb.DBase;
import com.kwon.smb.DateManager;
import com.kwon.smb.movie.MovieDAO;
import com.kwon.smb.rating.RatingDAO;
import com.kwon.smb.user.UserDAO;
import com.mysql.jdbc.Connection;

//	1. 만들기만해서 실행
//
//	2. post방식 parameter한글처리 - web.xml
//		교재 6번
//
//	3. jar파일들 - maven - pom.xml
//		교재 7번(2.1,  2.2)
//	---------------------------------------
//	4. mapper.xml - SQL 적을 파일
//	   Java Interface - mapper.xml과 한 세트
//	---------------------------------------
//	5. 필요한 객체(SqlSession) 만들러 - servlet-context.xml
//		교재7번(3)

@Controller
public class RecommendController {

	@Autowired
	private UserDAO DAO;
	
	@Autowired
	public RecommendDAO d;
	
	
	@RequestMapping(value = "/mahoutUser.go", method = RequestMethod.GET)
	public String mahoutUser(HttpServletRequest req, HttpServletResponse res)  {
		DateManager.getCurrentYear(req, res);
		String nev = req.getParameter("selectNV");
		if(nev == null) {
			nev = "'userRecommend'";
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
//			d.userRecommend(req,res);
			d.RecommendPaging(d.getedMovies,12.0,page,req, res);
			
			req.setAttribute("ContentPage", "userecommend.jsp");
			return "home";
		} else {
			return "user/login";
		}


	}


	@RequestMapping(value = "/mahoutMovie.go", method = RequestMethod.GET)
	public String mahoutMovie(HttpServletRequest req, HttpServletResponse res)  {
		DateManager.getCurrentYear(req, res);
		String nev = req.getParameter("selectNV");
		if(nev == null) {
			nev = "'movieRecommend'";
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
			
			d.RecommendPaging(d.myRating_4StarOver,8.0,page,req, res);
//			d.RecommendPaging(d.getMyRating_4StarOver(req, res),8.0,page,req, res);
			
			req.setAttribute("ContentPage", "movierecommend.jsp");
			return "home";
		} else {
			return "user/login";
		}


	}
	
	@RequestMapping(value = "/mahoutMovie.do", method = RequestMethod.POST)
	public void mahoutMovieDo(HttpServletRequest req, HttpServletResponse res)  {
			System.out.println("mahoutMovie.do 접속");
			res.setContentType("text/html;charset=UTF-8");
			d.MovieRecommend(req,res);
		
	}
	
	@RequestMapping(value = "/check.recommendmovie.count.do", method = RequestMethod.POST)
	public void checkRecommendMovieCount(HttpServletRequest req, HttpServletResponse res)  {
		System.out.println("check.recommend.moviecount.do 접속");
		res.setContentType("text/html;charset=UTF-8");

		if(d.getMyRating_4StarOver(req,res).size()==0) {
			try {
				res.getWriter().print("평가한 영화가 없거나, 평가 점수가 4점이상인 영화가 없습니다 .\n평가를 하신 후 다시 클릭해주세요");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try {
				res.getWriter().print("정상");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@RequestMapping(value = "/check.recommenduser.count.do", method = RequestMethod.POST)
	public void checkRecommendUserCount(HttpServletRequest req, HttpServletResponse res)  {
		System.out.println("check.recommenduser.count.do 접속");
		res.setContentType("text/html;charset=UTF-8");
		
		ArrayList<RecommendMovieOfUserBase> rr = d.userRecommend(req,res);
		
		if( rr==null) {
			try {
				res.getWriter().print("평가한 영화가 없거나, 평가한 영화 수가 부족합니다.\n평가를 하신 후 다시 클릭해주세요");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(rr.size()==0) {
			try {
				res.getWriter().print("평가한 영화가 없거나, 평가한 영화 수가 부족합니다.\n평가를 하신 후 다시 클릭해주세요");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try {
				res.getWriter().print("정상");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

















