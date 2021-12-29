package com.PhuTungXeMay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.PhuTungXeMay.config.MapperUtil;
import com.PhuTungXeMay.entity.Store;
import com.PhuTungXeMay.payload.request.StoreRequest;
import com.PhuTungXeMay.payload.response.StoreResponse;
import com.PhuTungXeMay.repository.StoreRepo;

@Service
public class StoreService {

	@Autowired
	private StoreRepo storeRepo;
	
	public StoreResponse addStore(StoreRequest storeRequest) {
		
		Store store = MapperUtil.map(storeRequest, Store.class);
		return MapperUtil.map(storeRepo.save(store), StoreResponse.class);
	}
	
	public List<StoreResponse> getAll(){
		return MapperUtil.mapAll(storeRepo.findAll(Sort.by("id").descending()), StoreResponse.class);
	}
	
	public void delete(Long id) {
		Store store = storeRepo.findById(id).orElseThrow();
		storeRepo.delete(store);
	}
	
	public StoreResponse findById(Long id) {
		return MapperUtil.map(storeRepo.findById(id).orElseThrow(), StoreResponse.class);
	}
	
	public StoreResponse update(StoreRequest storeRequest, Long id) {
		Store store = storeRepo.findById(id).orElseThrow();
		store.setAddress(storeRequest.getAddress());
		store.setHotline(storeRequest.getHotline());
		store.setMap(storeRequest.getMap());
		return MapperUtil.map(storeRepo.save(store), StoreResponse.class);
	}
}
