package com.kwon.smb.mahout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kwon.smb.movie.Movie;
import com.kwon.smb.user.User;

public interface RecommendMapper {
	public abstract ArrayList<RecommendMovieBase> getMyRating_4StarOver(User a);
	// returnŸ��
		// insert, update, delete : int
		// select : List<�ڹٺ�> or �ڹٺ�
	// method�� : id�� ���߱�
	// parameter : parameterType�� ���߱�
}












