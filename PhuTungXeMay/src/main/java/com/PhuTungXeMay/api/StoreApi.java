package com.PhuTungXeMay.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhuTungXeMay.payload.request.StoreRequest;
import com.PhuTungXeMay.payload.response.StoreResponse;
import com.PhuTungXeMay.service.StoreService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class StoreApi {

	@Autowired
	private StoreService storeService;
	
	@GetMapping("/store")
	public List<StoreResponse> getAll() {
		return storeService.getAll();
	}
	
	@DeleteMapping("/store/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		storeService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/store")
	public StoreResponse addStore(@RequestBody StoreRequest storeRequest) {
		return storeService.addStore(storeRequest);
	}
	
	@GetMapping("/store/{id}")
	public StoreResponse getById(@PathVariable Long id) {
		return storeService.findById(id);
	}
	
	@PutMapping("/store/{id}")
	public StoreResponse update(@PathVariable Long id, @RequestBody StoreRequest storeRequest) {
		return storeService.update(storeRequest, id);
	}
}
