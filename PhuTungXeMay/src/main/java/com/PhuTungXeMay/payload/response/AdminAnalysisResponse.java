package com.PhuTungXeMay.payload.response;

public class AdminAnalysisResponse {

	private Long totalProduct;
	private Long totalBrand;
	private Long totalOrder;
	public Long getTotalProduct() {
		return totalProduct;
	}
	public void setTotalProduct(Long totalProduct) {
		this.totalProduct = totalProduct;
	}
	public Long getTotalBrand() {
		return totalBrand;
	}
	public void setTotalBrand(Long totalBrand) {
		this.totalBrand = totalBrand;
	}
	public Long getTotalOrder() {
		return totalOrder;
	}
	public void setTotalOrder(Long totalOrder) {
		this.totalOrder = totalOrder;
	}
}
