package com.kwon.smb.rating;

import java.math.BigDecimal;

public class MyRating {
	private BigDecimal movieid;
	private String img;
	private String title;
	private BigDecimal rating;
	private String comment;
	
	public MyRating() {
		
	}

	public MyRating(BigDecimal movieid, String img, String title, BigDecimal rating, String comment) {
		super();
		this.movieid = movieid;
		this.img = img;
		this.title = title;
		this.rating = rating;
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
	
}
