package kr.co.adflow.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import kr.co.adflow.domain.AllTotal;
import kr.co.adflow.domain.Item;

public interface ItemService {
	
	public void insertItem(Item item);
	
	public void addItem(Item item);
	
	public ModelAndView itemList(HttpServletRequest reqeust);
	
	public void delete(Item item);

	public AllTotal countEnd(AllTotal allTotal);
	
	public ModelAndView itemSearch(HttpServletRequest request);
	
	public int notSearch(String itemId);
}
