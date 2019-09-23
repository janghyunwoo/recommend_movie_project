package com.kwon.smb.rating;


import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwon.smb.DBase;
import com.kwon.smb.movie.Movie;
import com.kwon.smb.user.User;
import com.kwon.smb.user.UserMapper;
import com.mysql.jdbc.Connection;


// servlet-context.xml에 객체 하나 만들어짐
@Service
public class RatingDAO {

	@Autowired
	private SqlSession ss;

	public Rating ratingObject;
	public Movie movie;
	public ArrayList<Comment> comments;
	public ArrayList<MyRating> myRating;
	public Connection conn = null;
	
	public String registerComment(
			HttpServletRequest req, HttpServletResponse res) {
		try {
			User user = (User)req.getSession().getAttribute("loginMember");
			if(user == null) {
				return "로그인 해주세요.";
			}
			BigDecimal userid = user.getUserid();
			
			BigDecimal movieid = new BigDecimal(req.getParameter("movieid"));
			BigDecimal rating = new BigDecimal(req.getParameter("rating"));
			String comment = req.getParameter("comment");
			comment = comment.replace("\n", "<br>");
			
			ratingObject = new Rating(userid,movieid,rating,comment);
			
			if (ss.getMapper(RatingMapper.class).registerRating(ratingObject) == 1) {
				
				File CSVFile = new File("/var/lib/mysql-files/output1.csv");
//				File CSVFile = new File("C:\\Users\\Jang_home_n\\git\\machinelearning\\Apr13_1_Spring_MyBatis_MyProjectDemo\\src\\main\\resources\\output1.csv");
				if(!CSVFile.exists()) {
				 DBase db = new DBase();
			     	conn = db.connect(
			                "jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
			     
			    	 db.exportData(conn,"/var/lib/mysql-files/output1.csv");
			     }else {
			    	if(CSVFile.delete()) {
			    		System.out.println("파일 삭제 성공");
			    		DBase db = new DBase();
			    		conn = db.connect(
			    				"jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
			    		
			    		db.exportData(conn,"/var/lib/mysql-files/output1.csv");
			    	}else {
			    		System.out.println("파일 삭제 실패");
			    	}
			    	 
			     }
				
				return "정상 등록 되었습니다.";
			} else {
				return "등록에 실패하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "등록에 실패하였습니다.";
		}finally {
			try {
			conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	

	public void getComment(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		try {
			BigDecimal movieid = new BigDecimal(req.getParameter("movieid"));
			ratingObject = new Rating(null,movieid,null,null);
			
			comments = ss.getMapper(RatingMapper.class).getComment(ratingObject);
			System.out.println(comments.size());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	public void paging( HttpServletRequest request, HttpServletResponse response) {
		// 전체 페이지 수 계산
		if(comments.size()==0) {
			return;
		}
		
		int page = Integer.parseInt(request.getParameter("selectNum"));
		double cnt = 10; // 한 페이지당 나올 후기 수
		int commentsSize = comments.size(); // 총 후기 수
		int pageCount = (int) Math.ceil(commentsSize / cnt);
		//request.setAttribute("pageCount", pageCount);

		int start = commentsSize - ((int) cnt * (page - 1));
		int end = (page == pageCount) ? -1 : start - ((int) cnt + 1);

		ArrayList<Comment> comments2 = new ArrayList<Comment>();

		// 22 21 20 19 18 17 16 15 14 13
		for (int i = start - 1; i > end; i--) {
			comments2.add(comments.get(i));
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		 HashMap<String,Object> map=new HashMap<String,Object>();
		 map.put("Comments",comments2);
		 map.put("resert","등록 성공");
		 map.put("pageCount",pageCount);

		 System.out.println("rating jsonobject 실행됨1");
		JSONObject jsonObject= new JSONObject();

		 jsonObject.putAll(map);
		 System.out.println("rating jsonobject 실행됨2");
		
		 try {
				response.getWriter().print(mapper.writeValueAsString(jsonObject));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

	}


	public void getMyRating(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		try {
			User user = (User) req.getSession().getAttribute("loginMember");
			
			myRating = ss.getMapper(RatingMapper.class).getMyRating(user);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	public void paging(int page, HttpServletRequest request, HttpServletResponse res) {
		// TODO Auto-generated method stub
		// 전체 페이지 수 계산
//		int page = Integer.parseInt(request.getParameter("selectNum"));
		if(myRating.size()==0) {
			return;
		}
		
		double cnt = 8; // 한 페이지당 나올 후기 수
		int commentsSize = myRating.size(); // 총 후기 수
		int pageCount = (int) Math.ceil(commentsSize / cnt);

		int start = commentsSize - ((int) cnt * (page - 1));
		int end = (page == pageCount) ? -1 : start - ((int) cnt + 1);

		ArrayList<MyRating> comments2 = new ArrayList<MyRating>();

		// 22 21 20 19 18 17 16 15 14 13
		// 22 21 20 19 18 17 16 15 14 13
		for (int i = start - 1; i > end; i--) {
			comments2.add(myRating.get(i));
		}
		
		int startPageNumBegin = ((page-1)/10)*10+1;
		int endPageNumBegin = ((pageCount-1)/10)*10;
		int startPageNumEnd;
		if((startPageNumBegin + 9)>pageCount){
			startPageNumEnd = pageCount;
		}else{
			startPageNumEnd = startPageNumBegin + 9;
		}
		

		request.setAttribute("MyRating", comments2);
		request.setAttribute("pageMax", pageCount);
		request.setAttribute("pageBegin", startPageNumBegin);
		request.setAttribute("pageEnd", startPageNumEnd);
		request.setAttribute("pageEndBegin", endPageNumBegin);
		request.setAttribute("pageNow", page);
		
	}


	public void deleteMyRating(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		try {
			User user = (User) req.getSession().getAttribute("loginMember");
			Rating rating = new Rating(user.getUserid(), new BigDecimal(req.getParameter("movieid")), null, null); 
			
			if(ss.getMapper(RatingMapper.class).deleteMyRating(rating)==1) {
				//삭제에 성공하면
				File CSVFile = new File("/var/lib/mysql-files/output1.csv");
				if(!CSVFile.exists()) {
					DBase db = new DBase();
					conn = db.connect(
							"jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
					
					db.exportData(conn,"/var/lib/mysql-files/output1.csv");
				}else {
					if(CSVFile.delete()) {
			    		System.out.println("파일 삭제 성공");
			    		DBase db = new DBase();
			    		conn = db.connect(
			    				"jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
			    		
			    		db.exportData(conn,"/var/lib/mysql-files/output1.csv");
			    	}else {
			    		System.out.println("파일 삭제 실패");
			    	}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}


	public void updateMyRating(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		User user = (User) req.getSession().getAttribute("loginMember");
		Rating rating = new Rating(user.getUserid(), new BigDecimal(req.getParameter("movieid")),  new BigDecimal(req.getParameter("rating")), req.getParameter("comment")); 
	
		if(ss.getMapper(RatingMapper.class).updateMyRating(rating)==1) {
			 try {
				 File CSVFile = new File("/var/lib/mysql-files/output1.csv");
					if(!CSVFile.exists()) {
						DBase db = new DBase();
						conn = db.connect(
								"jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
						
						db.exportData(conn,"/var/lib/mysql-files/output1.csv");
					}else {
						if(CSVFile.delete()) {
				    		System.out.println("파일 삭제 성공");
				    		DBase db = new DBase();
				    		conn = db.connect(
				    				"jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
				    		
				    		db.exportData(conn,"/var/lib/mysql-files/output1.csv");
				    	}else {
				    		System.out.println("파일 삭제 실패");
				    	}
					}
					
				 res.getWriter().print("수정 성공!");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally {
					try {
						conn.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
		}else {
			try {
				 res.getWriter().print("수정 실패!");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
		}
	}
}
