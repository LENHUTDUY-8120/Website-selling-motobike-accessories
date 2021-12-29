package com.PhuTungXeMay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "xaphuongthitran")
public class XaPhuongThiTran {

	@Id
	@Column
	private String xaid;
	@Column
	private String name;
	@Column
	private String type;
	@Column
	private String maqh;
	
	public String getXaid() {
		return xaid;
	}
	public void setXaid(String xaid) {
		this.xaid = xaid;
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
	public String getMaqh() {
		return maqh;
	}
	public void setMaqh(String maqh) {
		this.maqh = maqh;
	}
}
