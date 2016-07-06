package com.zc.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.zc.dao.BookDao;
import com.zc.dao.impl.BookDaoImpl;
import com.zc.entity.BanJi;
import com.zc.entity.Book;
import com.zc.util.PageBean;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet(name = "bookServlet", urlPatterns = { "/bookServlet" })
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String method=request.getParameter("method");
		System.out.println("BookServlet->"+method);
		if("findBook".equals(method)){
			this.dealFindBook(request, response);
		}
		if("login".equals(method)){
			this.dealLogin(request, response);
		}
		if("findBanJi".equals(method)){
			this.dealFindBanJi(request, response);
		}
		if("addBook".equals(method)){
			this.dealAddBook(request, response);
		}
	}
	
	protected void dealFindBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String _currentPage=request.getParameter("currentPage");
		String _pageSize=request.getParameter("pageSize");
		PageBean<List<Book>> pageBean=null;
		int currentPage=1;
		int pageSize=10;
		if(_currentPage!=null)
		{currentPage=Integer.parseInt(_currentPage);}
		if(_pageSize!=null)
		{pageSize=Integer.parseInt(_pageSize);}
		BookDao dao=new BookDaoImpl();
		PageBean<List<Book>> result = dao.findBook(currentPage, pageSize);
		ObjectMapper mapper=new ObjectMapper();
		String jsonResult=null;
		try {
			jsonResult = mapper.writeValueAsString(result);
			response.setContentType("application/json;charset=utf-8");
			System.out.println("BookServlet->dealFindBook->jsonResult"+jsonResult);
			response.getWriter().write(jsonResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void dealLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		BookDao dao=new BookDaoImpl();
		int result=dao.loginQuery(userName, password);
		response.setContentType("text/json;charset=utf-8");
		String resultJson="{\"result\":"+"\""+result+"\"}";
		response.getOutputStream().write(resultJson.toString().getBytes());
		System.out.println(resultJson);
	}
	
	protected void dealFindBanJi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BookDao dao=new BookDaoImpl();
		List<BanJi> result = dao.findBanJi();
		ObjectMapper mapper=new ObjectMapper();
		String jsonResult=null;
		try {
			jsonResult = mapper.writeValueAsString(result);
			response.setContentType("application/json;charset=utf-8");
			System.out.println("BookServlet->dealFindBanJi->jsonResult"+jsonResult);
			response.getWriter().write(jsonResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void dealAddBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String _banJiId=request.getParameter("banJiId");
		String _bookCount=request.getParameter("bookCount");
		String _bookId=request.getParameter("bookId");
		System.out.println("BookServlet->dealAddBook->banJiId:"+_banJiId+"bookId:"+_bookId+"bookCount:"+_bookCount);
		int banJiId=Integer.parseInt(_banJiId);
		int bookId=Integer.parseInt(_bookId);
		int bookCount=Integer.parseInt(_bookCount);
		BookDao dao=new BookDaoImpl();
		int result=dao.addBook(banJiId, bookId, bookCount);
		ObjectMapper mapper=new ObjectMapper();
		String jsonResult=null;
		try {
			jsonResult ="{\"result\":"+result+"}";
			response.setContentType("application/json;charset=utf-8");
			System.out.println("BookServlet->dealAddBook->jsonResult"+jsonResult);
			response.getWriter().write(jsonResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
