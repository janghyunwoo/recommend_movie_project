package com.kwon.smb.movie;

import java.util.ArrayList;
import java.util.List;

public interface MovieMapper {
	public abstract ArrayList<Movie> getMovies();
	public abstract ArrayList<Movie> searchMovies(Movie a);
	public abstract List<Genre> getGenre();
	// returnŸ��
		// insert, update, delete : int
		// select : List<�ڹٺ�> or �ڹٺ�
	// method�� : id�� ���߱�
	// parameter : parameterType�� ���߱�
}












