package com.kwon.smb.user;


public interface UserMapper {

	public abstract User getUserById(User m);
	// returnŸ��
		// insert, update, delete : int
		// select : List<�ڹٺ�> or �ڹٺ�
	// method�� : id�� ���߱�
	// parameter : parameterType�� ���߱�

	public abstract int join(User m);
}












