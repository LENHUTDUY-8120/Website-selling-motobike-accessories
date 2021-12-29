package com.PhuTungXeMay.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Products extends BaseEntity{

	@Column
	private String title;
	@Column
	private String searchKey;
	@Column
	private String productCode;
	@Column
	private String donVi;
	@Column
	private int price;
	@Column
	private int quantity = 1;
	@Column(columnDefinition = "TEXT")
	private String describes;
	@Column
	private String status = "active";

	@OneToMany(mappedBy = "product")
	private List<Images> listImage = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<Comments> comments = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "product_xe",
				joinColumns = @JoinColumn(name = "product_id"),
				inverseJoinColumns = @JoinColumn(name = "xe_id"))
	private List<Xe> xe = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subcategory_id", foreignKey = @ForeignKey(name = "Subcategory_ID_FK"))
	private SubCategory subCategory;
	
	public SubCategory getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public List<Comments> getComments() {
		return comments;
	}
	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	public List<Images> getListImage() {
		return listImage;
	}
	public void setListImage(List<Images> listImage) {
		this.listImage = listImage;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public List<Xe> getXe() {
		return xe;
	}
	public void setXe(List<Xe> xe) {
		this.xe = xe;
	}
}
