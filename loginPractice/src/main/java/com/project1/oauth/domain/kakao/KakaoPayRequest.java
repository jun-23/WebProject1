package com.project1.oauth.domain.kakao;

public class KakaoPayRequest {
	private String item_name;
	
	private String partner_user_id;
	
	private Integer quantity;
	
	private Integer total_amount;
	
	private Integer tier;

	public Integer getTier() {
		return tier;
	}

	public void setTier(Integer tier) {
		this.tier = tier;
	}

	public String getPartner_user_id() {
		return partner_user_id;
	}

	public void setPartner_user_id(String partner_user_id) {
		this.partner_user_id = partner_user_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Integer total_amount) {
		this.total_amount = total_amount;
	}

	@Override
	public String toString() {
		return "KakaoPayRequest [item_name=" + item_name + ", partner_user_id=" + partner_user_id + ", quantity="
				+ quantity + ", total_amount=" + total_amount + ", tier=" + tier + "]";
	}


	
	
}
