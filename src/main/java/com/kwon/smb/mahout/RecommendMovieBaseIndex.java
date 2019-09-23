package com.kwon.smb.mahout;

import java.math.BigDecimal;

public class RecommendMovieBaseIndex {
	private BigDecimal movieid;
	private String title;
	private BigDecimal recommendIndex;
	
	public RecommendMovieBaseIndex(BigDecimal movieid, String title, BigDecimal recommendIndex) {
		super();
		this.movieid = movieid;
		this.title = title;
		this.recommendIndex = recommendIndex;
	}

	public RecommendMovieBaseIndex() {
		
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

	public BigDecimal getRecommendIndex() {
		return recommendIndex;
	}

	public void setRecommendIndex(BigDecimal recommendIndex) {
		this.recommendIndex = recommendIndex;
	}
	
	
}
