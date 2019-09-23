package com.kwon.smb.mahout;

import java.math.BigDecimal;
import java.util.List;

public class RecommendUserBase {
	private List<BigDecimal> movieid;

	public RecommendUserBase() {
		// TODO Auto-generated constructor stub
	}
	
	public RecommendUserBase(List<BigDecimal> movieid) {
		super();
		this.movieid = movieid;
	}

	public List<BigDecimal> getMovieid() {
		return movieid;
	}

	public void setMovieid(List<BigDecimal> movieid) {
		this.movieid = movieid;
	}
	
	
}
