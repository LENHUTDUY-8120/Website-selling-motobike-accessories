package com.PhuTungXeMay.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "xe")
public class Xe extends BaseEntity{
	@Column
	private String tenXe;
	@Column
	private String codeXe;
	
	@ManyToMany(mappedBy = "xe")
	private List<Products> products = new ArrayList<>();
	
	public String getTenXe() {
		return tenXe;
	}
	public void setTenXe(String tenXe) {
		this.tenXe = tenXe;
	}
	public String getCodeXe() {
		return codeXe;
	}
	public void setCodeXe(String codeXe) {
		this.codeXe = codeXe;
	}
	public List<Products> getProducts() {
		return products;
	}
	public void setProducts(List<Products> products) {
		this.products = products;
	}
}
