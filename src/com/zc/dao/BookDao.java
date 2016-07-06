package com.zc.dao;

import java.util.List;

import com.zc.entity.BanJi;
import com.zc.entity.Book;
import com.zc.util.PageBean;

public interface BookDao {
	/**
	 * 登录查询
	 * */
	public int loginQuery(String userName,String password);
	public PageBean<List<Book>> findBook(int currentPage,int pageSize);
	public List<BanJi> findBanJi();
	public int addBook(int banJiId,int bookId,int bookCount);
}
