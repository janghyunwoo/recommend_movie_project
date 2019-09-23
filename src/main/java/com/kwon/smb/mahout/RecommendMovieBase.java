package com.kwon.smb.mahout;

import java.math.BigDecimal;

public class RecommendMovieBase {
	private BigDecimal movieid;
	private String img;
	private String title;
	private BigDecimal rating;
	private String comment;
	private String recommendTitle;
	private BigDecimal recommendIndex;
	
	public RecommendMovieBase() {
		
	}

	public RecommendMovieBase(BigDecimal movieid, String img, String title, BigDecimal rating, String comment,
			String recommendTitle, BigDecimal recommendIndex) {
		super();
		this.movieid = movieid;
		this.img = img;
		this.title = title;
		this.rating = rating;
		this.comment = comment;
		this.recommendTitle = recommendTitle;
		this.recommendIndex = recommendIndex;
	}



	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public BigDecimal getMovieid() {
		return movieid;
	}

	public void setMovieid(BigDecimal movieid) {
		this.movieid = movieid;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRecommendTitle() {
		return recommendTitle;
	}

	public void setRecommendTitle(String recommendTitle) {
		this.recommendTitle = recommendTitle;
	}

	public BigDecimal getRecommendIndex() {
		return recommendIndex;
	}

	public void setRecommendIndex(BigDecimal recommendIndex) {
		this.recommendIndex = recommendIndex;
	}

}
