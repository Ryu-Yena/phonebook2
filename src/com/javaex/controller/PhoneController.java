package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;


@WebServlet("/pbc")
public class PhoneController extends HttpServlet {
	
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
   		//컨트롤러 테스트
   		System.out.println("Controller");
   		
   		//파라미터 action 값을 읽어서
   		String action = request.getParameter("action");
   		System.out.println(action);
   		
   		if("list".equals(action)){
   			
	   		
	   		//action = list 리스트 출력 관련 
			
			//리스트 출력 처리
			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> personList = phoneDao.getPersonList();
			
			System.out.println(personList.toString());
			
			//html -> 매우복잡 > jsp가 편하다
			
			//데이터 전달
			request.setAttribute("pList", personList);
			
			
			//jsp에 포워드 시킨다
			RequestDispatcher rd = request.getRequestDispatcher("./WEB-INF/list.jsp"); //jsp파일 위치 알려주기
			rd.forward(request, response);
			
   		} else if("wform".equals(action)) {
   			System.out.println("등록 폼 처리");
   			
   			
   			RequestDispatcher rd = request.getRequestDispatcher("./WEB-INF/wform.jsp");
   			rd.forward(request, response);
   			
   		} else if("insert".equals(action)) {
   			System.out.println("전화번호 저장");
   			
   			//파라미터 3개의 값
   			String name = request.getParameter("name");
   			String hp = request.getParameter("hp");
   			String company = request.getParameter("company");
   			
   			
   			//personVo 묶고
   			PersonVo personVo = new PersonVo(name, hp, company);
   			
   			//new dao > 저장
   			PhoneDao phoneDao = new PhoneDao();
   			phoneDao.personInsert(personVo);
   			
   			response.sendRedirect("/phonebook2/pbc?action=list");

   		} else if("delete".equals(action)) {
   			System.out.println("전화번호 삭제");
   			
   			//personId 값
   			int personId = Integer.parseInt(request.getParameter("id"));	
   		
   			//new dao 삭제
   			PhoneDao phoneDao = new PhoneDao();
   			phoneDao.personDelete(personId);
   			
   			response.sendRedirect("/phonebook2/pbc?action=list");
   		} else if("upform".equals(action)){
   			System.out.println("수정 폼 처리");
   			
   			RequestDispatcher rd = request.getRequestDispatcher("./WEB-INF/upform.jsp");
   			rd.forward(request, response);
   			
   		} else if("update".equals(action)) {
   			System.out.println("전화번호 수정");
   			
   			//파라미터 값
   			String name = request.getParameter("name");
   			String hp = request.getParameter("hp");
   			String company = request.getParameter("company");
   			int id = Integer.parseInt(request.getParameter("id"));
   			
   			//personVo 묶고
   			PersonVo personVo = new PersonVo(id, name, hp, company);
   			
   			//new dao > 수정
   			PhoneDao phoneDao = new PhoneDao();
   			phoneDao.personUpdate(personVo);
   			
   			response.sendRedirect("/phonebook2/pbc?action=list");
   		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}

}
