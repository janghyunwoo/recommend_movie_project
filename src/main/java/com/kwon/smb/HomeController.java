package com.kwon.smb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kwon.smb.user.UserDAO;

//	1. ����⸸�ؼ� ����
//
//	2. post��� parameter�ѱ�ó�� - web.xml
//		���� 6��
//
//	3. jar���ϵ� - maven - pom.xml
//		���� 7��(2.1,  2.2)
//	---------------------------------------
//	4. mapper.xml - SQL ���� ����
//	   Java Interface - mapper.xml�� �� ��Ʈ
//	---------------------------------------
//	5. �ʿ��� ��ü(SqlSession) ���鷯 - servlet-context.xml
//		����7��(3)

@Controller
public class HomeController {
	@Autowired
	private UserDAO d;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest req, HttpServletResponse res) {
		d.autologin(req, res);
		
		
		return "redirect:/top100.go";
	}
	


	
}

















