package com.project1.oauth.domain.kakao;

public class SelectedCardInfo {
	private String card_bin;
	private Integer install_mounth;
	private String install_type;
	private String card_corp_name;
	private String interest_free_install;

	public String getCard_bin() {
		return card_bin;
	}

	public void setCard_bin(String card_bin) {
		this.card_bin = card_bin;
	}

	public Integer getInstall_mounth() {
		return install_mounth;
	}

	public void setInstall_mounth(Integer install_mounth) {
		this.install_mounth = install_mounth;
	}

	public String getInstall_type() {
		return install_type;
	}

	public void setInstall_type(String install_type) {
		this.install_type = install_type;
	}

	public String getCard_corp_name() {
		return card_corp_name;
	}

	public void setCard_corp_name(String card_corp_name) {
		this.card_corp_name = card_corp_name;
	}

	public String getInterest_free_install() {
		return interest_free_install;
	}

	public void setInterest_free_install(String interest_free_install) {
		this.interest_free_install = interest_free_install;
	}

	@Override
	public String toString() {
		return "SelectedCardInfo [card_bin=" + card_bin + ", install_mounth=" + install_mounth + ", install_type="
				+ install_type + ", card_corp_name=" + card_corp_name + ", interest_free_install="
				+ interest_free_install + "]";
	}

}
