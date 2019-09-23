package com.kwon.smb.rating;

import java.util.ArrayList;

import com.kwon.smb.user.User;

public interface RatingMapper {
	// returnŸ��
		// insert, update, delete : int
		// select : List<�ڹٺ�> or �ڹٺ�
	// method�� : id�� ���߱�
	// parameter : parameterType�� ���߱�
	public abstract int registerRating(Rating r);
	public abstract ArrayList<Comment> getComment(Rating r);
	public abstract ArrayList<MyRating> getMyRating(User r);
	public abstract int deleteMyRating(Rating r);
	public abstract int updateMyRating(Rating r);
}












