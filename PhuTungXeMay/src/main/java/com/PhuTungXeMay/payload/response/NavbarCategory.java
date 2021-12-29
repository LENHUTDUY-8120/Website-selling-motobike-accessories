package com.PhuTungXeMay.payload.response;

import java.util.List;

public class NavbarCategory extends CategoryResponse{

	List<SubCategoryResponse> listSubCategory;

	public List<SubCategoryResponse> getListSubCategory() {
		return listSubCategory;
	}

	public void setListSubCategory(List<SubCategoryResponse> listSubCategory) {
		this.listSubCategory = listSubCategory;
	}
	
}
