package com.kwon.smb.mahout;


import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.ibatis.session.SqlSession;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwon.smb.DBase;
import com.kwon.smb.movie.Genre;
import com.kwon.smb.movie.Movie;
import com.kwon.smb.movie.MovieMapper;
import com.kwon.smb.rating.RatingMapper;
import com.kwon.smb.user.User;
import com.mysql.jdbc.Connection;


// servlet-context.xml�� ��ü �ϳ� �������
@Service
public class RecommendDAO {
	private static final Logger logger = LoggerFactory.getLogger(RecommendController.class);
	
	@Autowired
	private SqlSession ss;

	public RecommendUserBase ubm;
	public ArrayList<RecommendMovieOfUserBase> getedMovies;
	public ArrayList<RecommendMovieBase> myRating_4StarOver;
	public ArrayList<RecommendMovieBaseIndex> mbIndex;
	
	public ArrayList<RecommendMovieBase> getMyRating_4StarOver (HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		User user = (User) req.getSession().getAttribute("loginMember");
		
		myRating_4StarOver = ss.getMapper(RecommendMapper.class).getMyRating_4StarOver(user);
		System.out.println("getMyRating_4StarOver ���� �Ϸ�");
		return myRating_4StarOver;
	}
	
	@SuppressWarnings({ "unchecked", "restriction" })
	public void MovieRecommend(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		logger.info("Movie Based ����!");
		//Connection conn = null;
		try {
			/* ������ ��� ��õ �ڵ�*/			
//			File CSVFile = new File("C:\\Users\\Jang_home_n\\git\\machinelearning\\Apr13_1_Spring_MyBatis_MyProjectDemo\\src\\main\\resources\\output1.csv");
			File CSVFile = new File("/var/lib/mysql-files/output1.csv");

			DataModel model = new FileDataModel(CSVFile);
			
			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
			
			ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
			
			// getItemIDs �� getUserIDs�� ���� ������ �������� id ���� �������� �Ϳ� �Ұ�
			// �����ϴµ� ������ ��ġ�� �ʴ´�.
			long itemID = Long.parseLong(req.getParameter("movieid"));
			List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemID, 5);
			
			//��õ�� ��ȭ�� ����� ���������� list�� ����
			Map<String, Object> param = new HashMap<String, Object>(); //��õ�� ��ȭ id
			List<BigDecimal> tempValue = new ArrayList<BigDecimal>(); //����� ����
			List<BigDecimal> movies = new ArrayList<BigDecimal>();
			for (RecommendedItem recommendation : recommendations) {
				movies.add(new BigDecimal(recommendation.getItemID()));
				tempValue.add(new BigDecimal(recommendation.getValue()));
			}
			param.put("code_list", movies); //map�� list�� �ִ´�.
			
			//��õ�� ��ȭ id ���� ������ �ش� ��ȭ�� ������ ������ �´�(img,title ���)
			mbIndex = (ArrayList<RecommendMovieBaseIndex>) ss.selectList("RecommendMovieBase", param);
			BigDecimal temp_number = new BigDecimal(1);
			for (int i = 0; i < movies.size(); i++) {
				for(RecommendMovieBaseIndex recommendMovie : mbIndex) {
					if(recommendMovie.getMovieid().equals(movies.get(i))) { //divide�� bigdecimal ������ �����ε� 1�� ���������ν� �� ���� �����ϰ� �ݿø��� ���ְ�.
						recommendMovie.setRecommendIndex(tempValue.get(i).divide(temp_number, 2, BigDecimal.ROUND_UP));
						break;
					}
				}
			}
			
			//����� ������ �������� �������� ����
			Collections.sort(mbIndex,new CompareNameDesc2());
			/* ������ ��� ��õ �ڵ� ��*/	
			
			/* json���� ��ȯ */
			ObjectMapper mapper = new ObjectMapper();
			
			 HashMap<String,Object> map=new HashMap<String,Object>();
			 map.put("Comments",mbIndex);
			 map.put("resert","��� ����");

			 JSONObject jsonObject= new JSONObject();

			 jsonObject.putAll(map);
			 
			 try {
					res.getWriter().print(mapper.writeValueAsString(jsonObject));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<RecommendMovieOfUserBase> userRecommend(HttpServletRequest req, HttpServletResponse res) {
		logger.info("User Based ����!");
		try {
			User user = (User) req.getSession().getAttribute("loginMember");
//			File CSVFile = new File("C:\\Users\\Jang_home_n\\git\\machinelearning\\Apr13_1_Spring_MyBatis_MyProjectDemo\\src\\main\\resources\\output1.csv");
			File CSVFile = new File("/var/lib/mysql-files/output1.csv");
		     
			// Creating data model
			DataModel datamodel = new FileDataModel(CSVFile); // data
			UserSimilarity usersimilarity = new PearsonCorrelationSimilarity(datamodel);
			UserNeighborhood userneighborhood = new ThresholdUserNeighborhood(0.1, usersimilarity, datamodel);
			UserBasedRecommender recommender = new GenericUserBasedRecommender(datamodel, userneighborhood,
					usersimilarity);
			
			long userID = user.getUserid().longValue();

			//24�� ��õ
			List<RecommendedItem> recommendations = recommender.recommend(userID, 24);
			
			//��õ�� ��ȭ id�� ��õ ������ ������ list ��ü�� �ִ´�.
			Map<String, Object> param = new HashMap<String, Object>();
			List<BigDecimal> movies = new ArrayList<BigDecimal>();
		    List<BigDecimal> tempValue = new ArrayList<BigDecimal>();
			for (RecommendedItem recommendation : recommendations) {
				movies.add(new BigDecimal(recommendation.getItemID()));
				tempValue.add(new BigDecimal(recommendation.getValue()));
			}
			param.put("code_list", movies); //map�� list�� �ִ´�.
			
			//��õ�� ��ȭ id ���� ������ �ش� ��ȭ�� ������ ������ �´�(img,title ���)
			getedMovies = (ArrayList<RecommendMovieOfUserBase>) ss.selectList("RecommendUserBase", param);
			
			if(getedMovies == null) {
				logger.info("Recommend Userbase is null");
				return null;
			}
			//������ ��ȭ ������ ��õ������ �ִ´�.(2��° �ڸ����� �ݿø��ؼ�)
			BigDecimal temp_number = new BigDecimal(1);
			for (int i = 0; i < movies.size(); i++) {
				for(RecommendMovieOfUserBase recommendMovie : getedMovies) {
					if(recommendMovie.getMovieid().equals(movies.get(i))) { //divide�� bigdecimal ������ �����ε� 1�� ���������ν� �� ���� �����ϰ� �ݿø��� ���ְ�.
						recommendMovie.setRecommentRating(tempValue.get(i).divide(temp_number, 2, BigDecimal.ROUND_UP));
						break;
					}
				}
			}
			//��õ ������ �������� �������� ����
			Collections.sort(getedMovies,new CompareNameDesc());

			// ���ڷ� �Ǿ��ִ� �帣�� ���ڷ� ��ȯ
			List<Genre> genres = ss.getMapper(MovieMapper.class).getGenre();
			
			String temp[];
			String idtochangeGenre=""; 
			for (RecommendMovieOfUserBase movie : getedMovies) {
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
			
			return getedMovies;
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			return null;
		}finally {
			/*try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void RecommendPaging(ArrayList<?> item, double cnt, int page, HttpServletRequest req, HttpServletResponse res) {
		// ��ü ������ �� ���
		if(item == null) {
			return;
		}else if(item.size()==0) {
			return;
		}
		
		// cnt �� �������� ���� �ı� ��
		int commentsSize = item.size(); // �� �ı� ��
		int pageCount = (int) Math.ceil(commentsSize / cnt);

		int start = ((int) cnt * (page - 1)) ;
		int end = (page == pageCount) ? commentsSize -1 : start + ((int) cnt - 1);
		
		ArrayList comments2 = new ArrayList();

		// 22 21 20 19 18 17 16 15 14 13
		for (int i = start; i <= end; i++) {
			comments2.add(item.get(i));
		}
		
		int startPageNumBegin = ((page-1)/10)*10+1;
		int endPageNumBegin = ((pageCount-1)/10)*10;
		int startPageNumEnd;
		if((startPageNumBegin + 9)>pageCount){
			startPageNumEnd = pageCount;
		}else{
			startPageNumEnd = startPageNumBegin + 9;
		}

		req.setAttribute("MovieContent", comments2);
		req.setAttribute("pageMax", pageCount);
		req.setAttribute("pageBegin", startPageNumBegin);
		req.setAttribute("pageEnd", startPageNumEnd);
		req.setAttribute("pageEndBegin", endPageNumBegin);
		req.setAttribute("pageNow", page);
		
	}

	
	
}

//bigdecimal ������������ �����ϱ� ���� Ŭ����
class CompareNameDesc implements Comparator<RecommendMovieOfUserBase> {
	 
    @Override
    public int compare(RecommendMovieOfUserBase o1, RecommendMovieOfUserBase o2) {
        // TODO Auto-generated method stub
        return o2.getRecommentRating().compareTo(o1.getRecommentRating());
    }        
}

class CompareNameDesc2 implements Comparator<RecommendMovieBaseIndex> {
	
	@Override
	public int compare(RecommendMovieBaseIndex o1, RecommendMovieBaseIndex o2) {
		// TODO Auto-generated method stub
		return o2.getRecommendIndex().compareTo(o1.getRecommendIndex());
	}        
}


