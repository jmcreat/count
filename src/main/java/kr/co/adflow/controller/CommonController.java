package kr.co.adflow.controller;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.PreparedStatement;

import kr.co.adflow.domain.Item;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CommonController {
	
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView itemList() throws SQLException {
		Connection con = null;
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/counter", "root", "0000");

			java.sql.Statement stmt = null;
			ResultSet rs = null;

			stmt = con.createStatement();
			String sql = "select * from item";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("ITEM_ID") + rs.getString("ITEM_PRICE"));
				Item item = new Item();
				item.setNo(rs.getInt("no"));
				item.setItemId(rs.getString("ITEM_ID"));
				item.setItemPrice(rs.getInt("ITEM_PRICE"));
				System.out.println("get"+item.getNo());
				System.out.println("get"+item.getItemId());
				System.out.println("get"+item.getItemPrice());
				list.add(item);
			}
			
			
			for(int i=0; i<list.size();i++){
				
				System.out.println(list.get(i));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("itemList", list);
		mv.setViewName("home");
		return mv;

	}

	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public String itemPop() {

		return "itemPopup";
	}

	@RequestMapping(value = "/insertItem")
	@ResponseBody
	public Item insertItem(@RequestBody Item json_data) throws Exception {
		Item itemInsert = new Item();
		String itemId = json_data.getItemId();
		System.out.println("itemId ...." + itemId);

		int itemPrice = json_data.getItemPrice();
		System.out.println("itemPrice..." + itemPrice);

		System.out.println("데이터 검증" + json_data);
		try {
			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.0.70:3306/counter", "root", "0000");

			java.sql.PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuffer sql = new StringBuffer("insert into item (ITEM_ID,ITEM_PRICE) values(?,?) ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, itemId);
			pstmt.setLong(2, itemPrice);
			pstmt.executeUpdate();

			itemInsert.setItemId(itemId);
			itemInsert.setItemPrice(itemPrice);

			// con.close();
			// pstmt.close();
			// rs.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

		return itemInsert;

	}
	//insert into user(ITEM_ID,ITEM_PRICE,COUNT,no) select ITEM_ID,ITEM_PRICE,3,no from item where no=17
	//create table user(id int not null, ITEM_ID varchar(50), ITEM_PRICE int, COUNT int, no int);
	@RequestMapping(value = "/addItem")
	@ResponseBody
	public ModelAndView addItem(@RequestBody Item json_data) throws Exception {
		
		int no = json_data.getNo();
		int id = json_data.getId();
		ArrayList<Object> list = new ArrayList<Object>();
		System.out.println("no.." + no);
		int count = json_data.getCount();
		System.out.println("count.." + count);
		
		Connection con = null;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://192.168.0.70:3306/counter", "root", "0000");

		java.sql.PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer("insert into user(id,ITEM_ID,ITEM_PRICE,COUNT,no) select ?,ITEM_ID,ITEM_PRICE,?,no from item where no=?");
		pstmt = con.prepareStatement(sql.toString()); 
		pstmt.setLong(1, id);
		pstmt.setLong(2, count);
		pstmt.setLong(3, no);
		pstmt.executeUpdate();

		
		String sqlUser = "select * from user where id=?";
		pstmt = con.prepareStatement(sqlUser.toString());
		pstmt.setLong(1, id);
		rs =pstmt.executeQuery();
			
		while (rs.next()) {
			Item addItem = new Item();
		addItem.setNo(rs.getInt("no"));
		addItem.setItemId(rs.getString("ITEM_ID"));
		addItem.setItemPrice(rs.getInt("ITEM_PRICE"));
		addItem.setCount(rs.getInt("COUNT"));
		
		System.out.println("USER"+addItem.getCount());
		System.out.println("USER"+addItem.getNo());
		System.out.println("USER"+addItem.getItemId());
		System.out.println("USER"+addItem.getItemPrice());
		list.add(addItem);
		}
		for(int i=0; i<list.size();i++){
			
			System.out.println(list.get(i));
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("addList", list);
		mv.setViewName("home");
		
		return mv;
		
		
	}
	
	

}
