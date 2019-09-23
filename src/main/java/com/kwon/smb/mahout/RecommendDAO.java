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


// servlet-context.xml에 객체 하나 만들어짐
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
		System.out.println("getMyRating_4StarOver 실행 완료");
		return myRating_4StarOver;
	}
	
	@SuppressWarnings({ "unchecked", "restriction" })
	public void MovieRecommend(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		logger.info("Movie Based 들어옴!");
		//Connection conn = null;
		try {
			/* 아이템 기반 추천 코드*/			
//			File CSVFile = new File("C:\\Users\\Jang_home_n\\git\\machinelearning\\Apr13_1_Spring_MyBatis_MyProjectDemo\\src\\main\\resources\\output1.csv");
			File CSVFile = new File("/var/lib/mysql-files/output1.csv");

			DataModel model = new FileDataModel(CSVFile);
			
			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
			
			ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
			
			// getItemIDs 나 getUserIDs는 단지 유저나 아이템의 id 값을 가져오는 것에 불과
			// 연산하는데 영향을 미치지 않는다.
			long itemID = Long.parseLong(req.getParameter("movieid"));
			List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemID, 5);
			
			//추천된 영화와 비슷한 지수변수를 list에 저장
			Map<String, Object> param = new HashMap<String, Object>(); //추천된 영화 id
			List<BigDecimal> tempValue = new ArrayList<BigDecimal>(); //비슷한 지수
			List<BigDecimal> movies = new ArrayList<BigDecimal>();
			for (RecommendedItem recommendation : recommendations) {
				movies.add(new BigDecimal(recommendation.getItemID()));
				tempValue.add(new BigDecimal(recommendation.getValue()));
			}
			param.put("code_list", movies); //map에 list를 넣는다.
			
			//추천된 영화 id 값을 가지고 해당 영화의 정보를 가지고 온다(img,title 등등)
			mbIndex = (ArrayList<RecommendMovieBaseIndex>) ss.selectList("RecommendMovieBase", param);
			BigDecimal temp_number = new BigDecimal(1);
			for (int i = 0; i < movies.size(); i++) {
				for(RecommendMovieBaseIndex recommendMovie : mbIndex) {
					if(recommendMovie.getMovieid().equals(movies.get(i))) { //divide는 bigdecimal 나눗셈 연상인데 1로 나줘줌으로써 원 값을 유지하고 반올림만 해주것.
						recommendMovie.setRecommendIndex(tempValue.get(i).divide(temp_number, 2, BigDecimal.ROUND_UP));
						break;
					}
				}
			}
			
			//비슷한 지수를 기준으로 내림차순 정렬
			Collections.sort(mbIndex,new CompareNameDesc2());
			/* 아이템 기반 추천 코드 끝*/	
			
			/* json으로 변환 */
			ObjectMapper mapper = new ObjectMapper();
			
			 HashMap<String,Object> map=new HashMap<String,Object>();
			 map.put("Comments",mbIndex);
			 map.put("resert","등록 성공");

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
		logger.info("User Based 들어옴!");
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

			//24개 추천
			List<RecommendedItem> recommendations = recommender.recommend(userID, 24);
			
			//추천된 영화 id와 추천 지수를 임의의 list 객체에 넣는다.
			Map<String, Object> param = new HashMap<String, Object>();
			List<BigDecimal> movies = new ArrayList<BigDecimal>();
		    List<BigDecimal> tempValue = new ArrayList<BigDecimal>();
			for (RecommendedItem recommendation : recommendations) {
				movies.add(new BigDecimal(recommendation.getItemID()));
				tempValue.add(new BigDecimal(recommendation.getValue()));
			}
			param.put("code_list", movies); //map에 list를 넣는다.
			
			//추천된 영화 id 값을 가지고 해당 영화의 정보를 가지고 온다(img,title 등등)
			getedMovies = (ArrayList<RecommendMovieOfUserBase>) ss.selectList("RecommendUserBase", param);
			
			if(getedMovies == null) {
				logger.info("Recommend Userbase is null");
				return null;
			}
			//가져온 영화 정보에 추천지수를 넣는다.(2번째 자리에서 반올림해서)
			BigDecimal temp_number = new BigDecimal(1);
			for (int i = 0; i < movies.size(); i++) {
				for(RecommendMovieOfUserBase recommendMovie : getedMovies) {
					if(recommendMovie.getMovieid().equals(movies.get(i))) { //divide는 bigdecimal 나눗셈 연상인데 1로 나줘줌으로써 원 값을 유지하고 반올림만 해주것.
						recommendMovie.setRecommentRating(tempValue.get(i).divide(temp_number, 2, BigDecimal.ROUND_UP));
						break;
					}
				}
			}
			//추천 지수를 기준으로 내림차순 정렬
			Collections.sort(getedMovies,new CompareNameDesc());

			// 숫자로 되어있는 장르를 글자로 변환
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
		// 전체 페이지 수 계산
		if(item == null) {
			return;
		}else if(item.size()==0) {
			return;
		}
		
		// cnt 한 페이지당 나올 후기 수
		int commentsSize = item.size(); // 총 후기 수
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

//bigdecimal 내림차순으로 정렬하기 위한 클래스
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


