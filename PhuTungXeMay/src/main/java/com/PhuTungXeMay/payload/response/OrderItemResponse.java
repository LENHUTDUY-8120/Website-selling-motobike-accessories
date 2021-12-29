package com.PhuTungXeMay.payload.response;

import java.util.List;

public class OrderItemResponse extends OrderDetailResponse{

	List<ItemResponse> listItem;

	public List<ItemResponse> getListItem() {
		return listItem;
	}

	public void setListItem(List<ItemResponse> listItem) {
		this.listItem = listItem;
	}
	
}
