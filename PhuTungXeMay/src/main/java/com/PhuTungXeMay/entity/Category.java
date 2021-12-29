package com.PhuTungXeMay.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends BaseEntity{
	@Column
	private String title;
	@Column
	private String code;
	
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private List<SubCategory> listSubCategory = new ArrayList<>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<SubCategory> getListSubCategory() {
		return listSubCategory;
	}
	public void setListSubCategory(List<SubCategory> listSubCategory) {
		this.listSubCategory = listSubCategory;
	}
	
}
