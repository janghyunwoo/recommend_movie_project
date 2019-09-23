package com.kwon.smb.movie;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwon.smb.rating.Comment;


// servlet-context.xml�� ��ü �ϳ� �������
@Service
public class MovieDAO {

	@Autowired
	private SqlSession ss;

	public ArrayList<Movie> movies;
	
	public void getTop100Movies(
			HttpServletRequest req, HttpServletResponse res) {
		
		List<Genre> genres = ss.getMapper(MovieMapper.class).getGenre();
		
		movies = ss.getMapper(MovieMapper.class).getMovies();
		String temp[];
		String idtochangeGenre=""; 
		for (Movie movie : movies) {
			String movieGenre = movie.getGenreid();
			temp = movieGenre.split(":");
			for (Genre genre : genres) {
				for(int i = 0;i<temp.length;i++) {
					if(genre.getGenreid().toString().equals(temp[i])) {
						idtochangeGenre += genre.getName()+" ";
					}
					
				}
			}
			movie.setGenreid(idtochangeGenre);
			idtochangeGenre="";
			
		}
		
//		req.setAttribute("MovieContent", movies);
	}
	
	public void paging(int page, HttpServletRequest request, HttpServletResponse response) {
		// ��ü ������ �� ���
//		int page = Integer.parseInt(request.getParameter("selectNum"));
		double cnt = 12; // �� �������� ���� �ı� ��
		int commentsSize = movies.size(); // �� �ı� ��
		int pageCount = (int) Math.ceil(commentsSize / cnt);

		int start = ((int) cnt * (page - 1)) ;
//		int end = (page == pageCount) ? commentsSize -1 : start + ((int) cnt - 1);
		int end = (page == pageCount) ? commentsSize -1 : start + ((int) cnt - 1);
		

		ArrayList<Movie> comments2 = new ArrayList<Movie>();

		// 22 21 20 19 18 17 16 15 14 13
		for (int i = start; i <= end; i++) {
			comments2.add(movies.get(i));
		}
		
		int startPageNumBegin = ((page-1)/10)*10+1;
		int endPageNumBegin = ((pageCount-1)/10)*10;
		int startPageNumEnd;
		if((startPageNumBegin + 9)>pageCount){
			startPageNumEnd = pageCount;
		}else{
			startPageNumEnd = startPageNumBegin + 9;
		}
		

		request.setAttribute("MovieContent", comments2);
		request.setAttribute("pageMax", pageCount);
		request.setAttribute("pageBegin", startPageNumBegin);
		request.setAttribute("pageEnd", startPageNumEnd);
		request.setAttribute("pageEndBegin", endPageNumBegin);
		request.setAttribute("pageNow", page);
		

	}

	public void searchMovie(HttpServletRequest req, HttpServletResponse res) {
		// TODO �ڵ� ������ �޼ҵ� ����
		List<Genre> genres = ss.getMapper(MovieMapper.class).getGenre();
		
		movies = ss.getMapper(MovieMapper.class).searchMovies(new Movie(null, req.getParameter("searcheMovie"), null, null, null, null));
		String temp[];
		String idtochangeGenre=""; 
		for (Movie movie : movies) {
			String movieGenre = movie.getGenreid();
			temp = movieGenre.split(":");
			for (Genre genre : genres) {
				for(int i = 0;i<temp.length;i++) {
					if(genre.getGenreid().toString().equals(temp[i])) {
						idtochangeGenre += genre.getName()+" ";
					}
					
				}
			}
			movie.setGenreid(idtochangeGenre);
			idtochangeGenre="";
			
		}
	}
	
	
}
