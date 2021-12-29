package com.PhuTungXeMay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quanhuyen")
public class QuanHuyen {

	@Id
	@Column
	private String maqh;
	@Column
	private String name;
	@Column
	private String type;
	@Column
	private String matp;
	
	public String getMaqh() {
		return maqh;
	}
	public void setMaqh(String maqh) {
		this.maqh = maqh;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMatp() {
		return matp;
	}
	public void setMatp(String matp) {
		this.matp = matp;
	}
}
