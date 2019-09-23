package com.kwon.smb.mahout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kwon.smb.movie.Movie;
import com.kwon.smb.user.User;

public interface RecommendMapper {
	public abstract ArrayList<RecommendMovieBase> getMyRating_4StarOver(User a);
	// return타입
		// insert, update, delete : int
		// select : List<자바빈> or 자바빈
	// method명 : id와 맞추기
	// parameter : parameterType과 맞추기
}












