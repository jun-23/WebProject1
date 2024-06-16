package com.project1.oauth.domain.kakao;

import java.util.Date;

public class PaymentActionDetails {
	private String aid;
	private Date approved_at;
	private Integer amount;
	private Integer poinit_amount;
	private Integer discount_amount;
	private Integer green_deposit;
	private String payment_action_type;
	private String payload;

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public Date getApproved_at() {
		return approved_at;
	}

	public void setApproved_at(Date approved_at) {
		this.approved_at = approved_at;
	}

	

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPoinit_amount() {
		return poinit_amount;
	}

	public void setPoinit_amount(Integer poinit_amount) {
		this.poinit_amount = poinit_amount;
	}

	public Integer getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(Integer discount_amount) {
		this.discount_amount = discount_amount;
	}

	public Integer getGreen_deposit() {
		return green_deposit;
	}

	public void setGreen_deposit(Integer green_deposit) {
		this.green_deposit = green_deposit;
	}

	public String getPayment_action_type() {
		return payment_action_type;
	}

	public void setPayment_action_type(String payment_action_type) {
		this.payment_action_type = payment_action_type;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	@Override
	public String toString() {
		return "PaymentActionDetails [aid=" + aid + ", approved_at=" + approved_at + ", amount=" + amount
				+ ", poinit_amount=" + poinit_amount + ", discount_amount=" + discount_amount + ", green_deposit="
				+ green_deposit + ", payment_action_type=" + payment_action_type + ", payload=" + payload + "]";
	}
}
