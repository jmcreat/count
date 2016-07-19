package kr.co.adflow.controller;

import java.awt.List;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.PreparedStatement;

import kr.co.adflow.common.CommonDBCP;
import kr.co.adflow.domain.AllTotal;
import kr.co.adflow.domain.BuyList;
import kr.co.adflow.domain.Item;
import kr.co.adflow.service.ItemService;

/**
 * Handles requests for the application home page.
 */

@Controller
public class CommonController {
//
	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView itemList(HttpServletRequest request) throws SQLException {
		ModelAndView mv = itemService.itemList(request);
		return mv;

	}

	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public String itemPop() {

		return "itemPopup";
	}

	@RequestMapping(value = "/insertItem", method = RequestMethod.POST)
	@ResponseBody
	public Item insertItem(@RequestBody Item json_data) throws Exception {

		itemService.insertItem(json_data);

		Item itemInsert = new Item();
		int no = json_data.getNo();
		System.out.println("no..." + no);

		String itemId = json_data.getItemId();
		System.out.println("itemId ...." + itemId);

		int itemPrice = json_data.getItemPrice();
		System.out.println("itemPrice..." + itemPrice);

		System.out.println("데이터 검증" + json_data);

		itemInsert.setNo(no);
		itemInsert.setItemId(itemId);
		itemInsert.setItemPrice(itemPrice);
		return itemInsert;

	}

	// 미완
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	@ResponseBody
	public Item addItem(@RequestBody Item json_data) throws Exception {

		Item item = new Item();
		itemService.addItem(json_data);

		item.setNo(json_data.getNo());
		item.setCount(json_data.getCount());

		return item;
	}

	@RequestMapping(value = "/deleteSend", method = RequestMethod.POST)
	@ResponseBody
	public Item deleteSend(@RequestBody Item json_data) {

		itemService.delete(json_data);

		return json_data;
	}

	@RequestMapping(value = "/countEnd", method = RequestMethod.POST)
	@ResponseBody
	public AllTotal countEnd(@RequestBody AllTotal json_data) {

		itemService.countEnd(json_data);

		return json_data;
	}

	// @RequestMapping(value = "/itemSearch" )
	// @ResponseBody
	// public Item itemSearch(@RequestBody Item json_data){
	//
	// itemService.itemSearch(json_data);
	//
	// return json_data;
	// }

	@RequestMapping(value = "/itemSearch", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView itemSearch(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mv = itemService.itemSearch(request);
		return mv;
	}

	@RequestMapping(value = "/notSearch", method = RequestMethod.POST)
	@ResponseBody
	public Item notSearch(@RequestBody Item item) {

		String itemId = item.getItemId();

		int confirm = itemService.notSearch(itemId);
		System.out.println("confrim..." + confirm);
		if (confirm != 0) {
			return null;
		}

		return item;
	}
}
