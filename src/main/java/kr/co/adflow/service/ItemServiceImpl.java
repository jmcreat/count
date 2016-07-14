package kr.co.adflow.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import kr.co.adflow.common.CommonDBCP;
import kr.co.adflow.domain.AllTotal;
import kr.co.adflow.domain.BuyList;
import kr.co.adflow.domain.Item;

@Service("sampleService")
public class ItemServiceImpl implements ItemService {
	Logger log = Logger.getLogger(this.getClass());
	ArrayList<Item> itemList;
	int userNo;
	private Object sendAll;

	@Override
	public void insertItem(Item json_data) {
		Statement stmt = null;
		Connection con = null;
		java.sql.PreparedStatement pstmt = null;

		Item itemInsert = new Item();

		int no = json_data.getNo();
		System.out.println("no..." + no);

		String itemId = json_data.getItemId();
		System.out.println("itemId ...." + itemId);

		int itemPrice = json_data.getItemPrice();
		System.out.println("itemPrice..." + itemPrice);

		System.out.println("데이터 검증" + json_data);

		try {

			String sql = "insert into item (item_no,item_name,item_price) values(?,?,?);";

			con = CommonDBCP.getConn();
			pstmt = con.prepareStatement(sql);

			pstmt.setLong(1, no);
			pstmt.setString(2, itemId);
			pstmt.setLong(3, itemPrice);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("에러입니다.");
		}
	}

	@Override
	public ModelAndView itemList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		itemList = new ArrayList<Item>();
		String sql;
		AllTotal allTotal = new AllTotal();

		ArrayList<Object> listBuy = new ArrayList<Object>();

		try {

			Connection con = CommonDBCP.getConn();
			sql = "select * from item;";
			// 전체 상품 리스트 조회
			System.out.println("connection..." + con);
			Statement stmt = con.createStatement();
			System.out.println("stmt..." + stmt);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Item item = new Item();
				item.setNo(rs.getInt("item_no"));
				item.setItemId(rs.getString("item_name"));
				item.setItemPrice(rs.getInt("item_price"));
				itemList.add(item);
				// 구매리스트 조회
			}
			stmt.close();
			rs.close();
			///////////////

			sql = "select max(user_no) from user;";
			Statement stmtUserNo = con.createStatement();
			ResultSet rsUserNo = stmtUserNo.executeQuery(sql);
			while (rsUserNo.next()) {
				userNo = rsUserNo.getInt("max(user_no)");
			}

			sql = "select b.user_no, a.item_no, a.item_name, a.item_price, b.count from item a right outer join purchase b on a.item_no = b.item_no and b.user_no = ?;";
			java.sql.PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, userNo);
			ResultSet rsBuy = pstmt.executeQuery();
			int allSum = 0;
			while (rsBuy.next()) {
				BuyList item2 = new BuyList();
				item2.setNo(rsBuy.getInt("item_no"));
				item2.setItemId(rsBuy.getString("item_name"));
				item2.setItemPrice(rsBuy.getInt("item_price"));
				item2.setCount(rsBuy.getInt("count"));
				item2.setTotalPrice(rsBuy.getInt("count") * rsBuy.getInt("item_price"));
				listBuy.add(item2);
				allSum += item2.getTotalPrice();
			}

			pstmt.close();
			rsBuy.close();
			System.out.println("allSum..." + allSum);
			// sendAll = allSum;

			allTotal.setAllTotal(allSum);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("itemsize..." + itemList.size());
		System.out.println("itemsize..." + listBuy.size());
		ModelAndView mv = new ModelAndView();
		mv.addObject("itemList", itemList);
		mv.addObject("listBuy", listBuy);
		mv.addObject("allTotal", allTotal);
		mv.setViewName("main");
		return mv;

	}

	@Override
	public void addItem(Item item) {
		Connection con = null;
		ResultSet rs = null;
		String sql;
		int no = item.getNo();
		int count = item.getCount();

		try {
			con = CommonDBCP.getConn();

			sql = "select item_no from purchase where item_no=? and user_no=?;";
			java.sql.PreparedStatement pstmtSelect = con.prepareStatement(sql);
			pstmtSelect.setInt(1, no);
			pstmtSelect.setInt(2, userNo);
			rs = pstmtSelect.executeQuery();
			BuyList buyList = new BuyList();
			while (rs.next()) {
				buyList.setId(rs.getInt("item_no"));

			}
			System.out.println("buy...." + buyList.getId());
			if (buyList.getId() > 0) {
				sql = "update purchase set count=count+? where item_no=? and user_no=?;";
				System.out.println("add count..." + count);
				java.sql.PreparedStatement pstmtUpdate = con.prepareStatement(sql);
				pstmtUpdate.setLong(1, count);
				pstmtUpdate.setLong(2, no);
				pstmtUpdate.setLong(3, userNo);
				pstmtUpdate.executeUpdate();
			} else {

				sql = "insert into purchase(item_no,user_no,count) values(?,?,?);";
				java.sql.PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, no);
				pstmt.setLong(2, userNo);

				System.out.println("userNo.." + userNo);
				pstmt.setLong(3, count);
				pstmt.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(Item item) {

		System.out.println("delete...");
		int no = item.getNo();
		Connection con = CommonDBCP.getConn();
		String sql = "delete from purchase where item_no=?";
		java.sql.PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public AllTotal countEnd(AllTotal allTotal) {
		// TODO Auto-generated method stub

		System.out.println("countend...");
		int total = allTotal.getAllTotal();
		Connection con = CommonDBCP.getConn();
		String sql = "update user set total=? where user_no=?; ";
		try {
			java.sql.PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, total);
			pstmt.setInt(2, userNo);
			pstmt.executeUpdate();

			sql = "insert into user(total) values(0);";
			Statement stmtInUser = con.createStatement();
			stmtInUser.executeUpdate(sql);

			sql = "select max(user_no) from user;";
			Statement stmtUserNo = con.createStatement();
			ResultSet rs = stmtUserNo.executeQuery(sql);
			while (rs.next()) {
				userNo = rs.getInt("max(user_no)");
			}

			// sql = "delete from purchase;";
			// Statement stmtDelete = con.createStatement();
			// stmtDelete.executeUpdate(sql);

			sql = "insert into purchase(item_no, user_no, count) values(0,?,0)";
			java.sql.PreparedStatement pstmtBuyList = con.prepareStatement(sql);
			pstmtBuyList.setInt(1, userNo);
			pstmtBuyList.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public ModelAndView itemSearch(HttpServletRequest request) {
		itemList = new ArrayList<Item>();
		String sql;
		ModelAndView mv = new ModelAndView();
		ArrayList<Object> listBuy = new ArrayList<Object>();
		Connection con = CommonDBCP.getConn();
		AllTotal allTotal = new AllTotal();
		
		String itemId = request.getParameter("itemSearch");
		System.out.println("itemId....." + itemId);
		sql = "select * from item where item_name like ?;";
		java.sql.PreparedStatement pstmtSearch;
		try {
			pstmtSearch = con.prepareStatement(sql);

			pstmtSearch.setString(1, "%" + itemId + "%");
			System.out.println("itemId..." + itemId);
			ResultSet rsSearch = pstmtSearch.executeQuery();

			while (rsSearch.next()) {
				Item item = new Item();
				item.setItemId(rsSearch.getString("item_name"));
				item.setItemPrice(rsSearch.getInt("item_price"));
				item.setNo(rsSearch.getInt("item_no"));
				System.out.println("search name..." + item.getItemId());
				System.out.println("search price..." + item.getItemPrice());
				System.out.println("search no..." + item.getNo());
				itemList.add(item);
			}

			rsSearch.close();
			pstmtSearch.close();

			sql = "select max(user_no) from user;";
			Statement stmtUserNo = con.createStatement();
			ResultSet rsUserNo = stmtUserNo.executeQuery(sql);
			while (rsUserNo.next()) {
				userNo = rsUserNo.getInt("max(user_no)");
			}

			sql = "select b.user_no, a.item_no, a.item_name, a.item_price, b.count from item a right outer join purchase b on a.item_no = b.item_no and b.user_no = ?;";
			java.sql.PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, userNo);
			ResultSet rsBuy = pstmt.executeQuery();
			int allSum = 0;
			while (rsBuy.next()) {
				BuyList item2 = new BuyList();
				item2.setNo(rsBuy.getInt("item_no"));
				item2.setItemId(rsBuy.getString("item_name"));
				item2.setItemPrice(rsBuy.getInt("item_price"));
				item2.setCount(rsBuy.getInt("count"));
				item2.setTotalPrice(rsBuy.getInt("count") * rsBuy.getInt("item_price"));
				listBuy.add(item2);
				allSum += item2.getTotalPrice();
			}

			pstmt.close();
			rsBuy.close();
			System.out.println("allSum..." + allSum);
			// sendAll = allSum;

			allTotal.setAllTotal(allSum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mv.addObject("itemList", itemList);
		mv.setViewName("itemSearch");
		mv.addObject("listBuy", listBuy);
		mv.addObject("allTotal", allTotal);
		for (int i = 0; i < itemList.size(); i++) {
			System.out.println("itemList...." + itemList.get(i).getItemId());
			System.out.println("itemList...." + itemList.get(i).getItemId());
			System.out.println("itemList...." + itemList.get(i).getItemPrice());
		}
		return mv;
	}

	@Override
	public int notSearch(String itemId) {
		String sql;
		int item_con = 0;
	
		Connection con = CommonDBCP.getConn();
		try {
			
			sql = "select count(*) from item where item_name like ?";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+itemId+"%");
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				item_con=rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("item_con..."+item_con);
		return item_con;
	}

}
