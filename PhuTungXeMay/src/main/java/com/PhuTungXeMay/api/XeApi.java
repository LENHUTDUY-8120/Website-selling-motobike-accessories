package com.PhuTungXeMay.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PhuTungXeMay.payload.request.XeRequest;
import com.PhuTungXeMay.payload.response.XeNavResponse;
import com.PhuTungXeMay.payload.response.XeResponse;
import com.PhuTungXeMay.service.XeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class XeApi {

	@Autowired
	private XeService xeService;
	
	@GetMapping("/xe")
	public List<XeResponse> getAll(){
		return xeService.getAll();
	}
	
	@DeleteMapping("/xe/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		xeService.remove(id);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/xe")
	public XeResponse addXe(@RequestBody XeRequest xeRequest) {
		return xeService.addXe(xeRequest);
	}
	
	@GetMapping("/nav-xe")
	public List<XeNavResponse> getNavXe(){
		return xeService.getNavXe();
	}
}
