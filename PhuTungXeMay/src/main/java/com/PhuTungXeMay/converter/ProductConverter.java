package com.PhuTungXeMay.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.PhuTungXeMay.entity.Comments;
import com.PhuTungXeMay.entity.Products;
import com.PhuTungXeMay.payload.response.ProductsAdminResponse;
import com.PhuTungXeMay.payload.response.ProductsCusResponse;

public class ProductConverter {

	public static ProductsAdminResponse toAdminResponse(Products products) {
		ProductsAdminResponse productad = new ProductsAdminResponse();
		productad.setId(products.getId());
		productad.setTitle(products.getTitle());
		productad.setProductCode(products.getProductCode());
		productad.setSubCategoryName(products.getSubCategory().getTitle());
		productad.setDonVi(products.getDonVi());
		productad.setPrice(products.getPrice());
		productad.setQuantity(products.getQuantity());
		productad.setImages(products.getListImage().get(0).getFileName());
		return productad;
	}

	public static List<ProductsAdminResponse> toListAdminResponse(List<Products> products) {
		return products.stream().map(product -> toAdminResponse(product)).collect(Collectors.toList());
	}

	public static ProductsCusResponse toCusResponse(Products products) {
		ProductsCusResponse product = new ProductsCusResponse();
		product.setId(products.getId());
		product.setTitle(products.getTitle());
		product.setDonVi(products.getDonVi());
		product.setProductCode(products.getProductCode());
		product.setSubCategoryName(products.getSubCategory().getTitle());
		product.setPrice(products.getPrice());
		product.setQuantity(products.getQuantity());
		product.setDescribes(products.getDescribes());
		List<String> listImage = products.getListImage().stream().map(image -> image.getFileName())
				.collect(Collectors.toList());
		product.setImages(listImage);
		List<String> listXe = products.getXe().stream().map(xe -> xe.getTenXe())
				.collect(Collectors.toList());
		product.setXe(listXe);
		List<Comments> listCmt = products.getComments();
		if (listCmt.isEmpty()) {
			product.setRate(0);
		} else {
			int sum = 0;
			for (Comments comment : listCmt) {
				sum += comment.getRate();
			}
			product.setRate((int) Math.round(sum / listCmt.size()));
		}
		return product;
	};

	public static List<ProductsCusResponse> toListCusResponse(List<Products> products) {
		return products.stream().map(product -> toCusResponse(product)).collect(Collectors.toList());
	}
}
