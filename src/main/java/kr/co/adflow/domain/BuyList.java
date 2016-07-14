package kr.co.adflow.domain;

public class BuyList {
	private int no;
	private String itemId;
	private int itemPrice;
	private int count;
	private int id;
	private int totalPrice;
	private int allTotal;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public int getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getAllTotal() {
		return allTotal;
	}
	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}
	
}
