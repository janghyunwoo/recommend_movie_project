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


// servlet-context.xml�� ��ü �ϳ� �������
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
				return "�α��� ���ּ���.";
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
			    		System.out.println("���� ���� ����");
			    		DBase db = new DBase();
			    		conn = db.connect(
			    				"jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
			    		
			    		db.exportData(conn,"/var/lib/mysql-files/output1.csv");
			    	}else {
			    		System.out.println("���� ���� ����");
			    	}
			    	 
			     }
				
				return "���� ��� �Ǿ����ϴ�.";
			} else {
				return "��Ͽ� �����Ͽ����ϴ�.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ͽ� �����Ͽ����ϴ�.";
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
		// ��ü ������ �� ���
		if(comments.size()==0) {
			return;
		}
		
		int page = Integer.parseInt(request.getParameter("selectNum"));
		double cnt = 10; // �� �������� ���� �ı� ��
		int commentsSize = comments.size(); // �� �ı� ��
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
		 map.put("resert","��� ����");
		 map.put("pageCount",pageCount);

		 System.out.println("rating jsonobject �����1");
		JSONObject jsonObject= new JSONObject();

		 jsonObject.putAll(map);
		 System.out.println("rating jsonobject �����2");
		
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
		// ��ü ������ �� ���
//		int page = Integer.parseInt(request.getParameter("selectNum"));
		if(myRating.size()==0) {
			return;
		}
		
		double cnt = 8; // �� �������� ���� �ı� ��
		int commentsSize = myRating.size(); // �� �ı� ��
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
				//������ �����ϸ�
				File CSVFile = new File("/var/lib/mysql-files/output1.csv");
				if(!CSVFile.exists()) {
					DBase db = new DBase();
					conn = db.connect(
							"jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
					
					db.exportData(conn,"/var/lib/mysql-files/output1.csv");
				}else {
					if(CSVFile.delete()) {
			    		System.out.println("���� ���� ����");
			    		DBase db = new DBase();
			    		conn = db.connect(
			    				"jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
			    		
			    		db.exportData(conn,"/var/lib/mysql-files/output1.csv");
			    	}else {
			    		System.out.println("���� ���� ����");
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
				    		System.out.println("���� ���� ����");
				    		DBase db = new DBase();
				    		conn = db.connect(
				    				"jdbc:mysql://jhwoo.hopto.org:3306/db","jang","Wkdgusdn#01");
				    		
				    		db.exportData(conn,"/var/lib/mysql-files/output1.csv");
				    	}else {
				    		System.out.println("���� ���� ����");
				    	}
					}
					
				 res.getWriter().print("���� ����!");
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
				 res.getWriter().print("���� ����!");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
		}
	}
}
