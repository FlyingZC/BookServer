package com.zc.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.zc.dao.BookDao;
import com.zc.entity.BanJi;
import com.zc.entity.Book;
import com.zc.entity.Book;
import com.zc.util.DataSourceHelper;
import com.zc.util.PageBean;

public class BookDaoImpl implements BookDao{

	@Override
	public int loginQuery(String userName, String password) {
		int result=0;
		// TODO Auto-generated method stub
		try {
			QueryRunner runner=new QueryRunner(DataSourceHelper.getSource());
			String sql="SELECT COUNT(teacherId) FROM teacher WHERE teacherName=? AND PASSWORD=?";
			result=runner.query(sql, new ScalarHandler<Long>(),userName,password).intValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public PageBean<List<Book>> findBook(int currentPage, int pageSize) {
		PageBean<List<Book>> pageBean=new PageBean<List<Book>>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);	
		try {
			QueryRunner runner=new QueryRunner(DataSourceHelper.getSource());
			System.out.println("BookDaoImpl->findBook");
			String sql="select count(*) as totalRows from textbook";
			Long rows=runner.query(sql,new ScalarHandler<Long>("totalRows"));
			
			pageBean.setTotalRows(rows.intValue());
			int start=(currentPage-1)*pageSize;
			sql="select * from textbook limit ?,?";
			List<Book> _data = runner.query(sql, new BeanListHandler<Book>(Book.class),start,pageSize);
			pageBean.setData(_data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pageBean;
	}

	@Override
	public List<BanJi> findBanJi() {
		List<BanJi> banJis=null;
		// TODO Auto-generated method stub
		try {
			QueryRunner runner=new QueryRunner(DataSourceHelper.getSource());
			String sql="SELECT * FROM banji";
			banJis=runner.query(sql, new BeanListHandler<BanJi>(BanJi.class));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return banJis;
	}

	public int addBook(int banJiId,int bookId,int bookCount) {
		// TODO Auto-generated method stub
		int result=0;
		try {
			QueryRunner runner=new QueryRunner(DataSourceHelper.getSource());
			String sql="INSERT INTO banji_book SET banJiId=? AND bookId=? AND bookCount=?";
			result=runner.insert(sql, new ScalarHandler<Integer>(),banJiId,bookId,bookCount);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	
}
