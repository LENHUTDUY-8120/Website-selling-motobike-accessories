package com.PhuTungXeMay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tinhthanhpho")
public class TinhThanhPho {

	@Id
	@Column
	private String matp;
	@Column
	private String name;
	@Column
	private String type;
	
	public String getMatp() {
		return matp;
	}
	public void setMatp(String matp) {
		this.matp = matp;
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
}
