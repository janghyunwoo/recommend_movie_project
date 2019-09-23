package com.kwon.smb.movie;

import java.math.BigDecimal;
import java.util.Date;
// MyBatis
//		DB�ʵ�� = ���������
//		���ڴ� BigDecimal

// Spring
//		��û�Ķ���͸� = ���������

// => DB�ʵ�� = ��������� = ��û�Ķ���͸�
//	   ���ڴ� BigDecimal
public class Genre {
	private BigDecimal genreid;
	private String name;

	public Genre(BigDecimal genreid, String name) {
		super();
		this.genreid = genreid;
		this.name = name;
	}

	public BigDecimal getGenreid() {
		return genreid;
	}

	public void setGenreid(BigDecimal genreid) {
		this.genreid = genreid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Genre() {
		// TODO Auto-generated constructor stub
	}
	
	

}
