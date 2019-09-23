package com.kwon.smb.mahout;

import java.math.BigDecimal;
import java.util.Date;
// MyBatis
//		DB�ʵ�� = ���������
//		���ڴ� BigDecimal

// Spring
//		��û�Ķ���͸� = ���������

// => DB�ʵ�� = ��������� = ��û�Ķ���͸�
//	   ���ڴ� BigDecimal
public class RecommendMovieOfUserBase  {
	private BigDecimal movieid;
	private String title;
	private Date releaseday;
	private String genreid;
	private String img;
	private BigDecimal recommentRating;

	public RecommendMovieOfUserBase() {
		// TODO Auto-generated constructor stub
	}
	
	public RecommendMovieOfUserBase(BigDecimal movieid, String title, Date releaseday, String genreid, String img, BigDecimal recommentRating) {
		super();
		this.movieid = movieid;
		this.title = title;
		this.releaseday = releaseday;
		this.genreid = genreid;
		this.img = img;
		this.recommentRating = recommentRating;
	}
	
	
	public BigDecimal getRecommentRating() {
		return recommentRating;
	}

	public void setRecommentRating(BigDecimal recommentRating) {
		this.recommentRating = recommentRating;
	}

	public BigDecimal getMovieid() {
		return movieid;
	}

	public void setMovieid(BigDecimal movieid) {
		this.movieid = movieid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getReleaseday() {
		return releaseday;
	}

	public void setReleaseday(Date releaseday) {
		this.releaseday = releaseday;
	}

	public String getGenreid() {
		return genreid;
	}

	public void setGenreid(String genreid) {
		this.genreid = genreid;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
