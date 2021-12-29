package com.PhuTungXeMay.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PhuTungXeMay.payload.response.QuanHuyenResponse;
import com.PhuTungXeMay.payload.response.TinhThanhPhoResponse;
import com.PhuTungXeMay.payload.response.XaPhuongThiTranResponse;
import com.PhuTungXeMay.service.AddressService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/address")
public class AddressApi {
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/TTP")
	public List<TinhThanhPhoResponse> getAllTTP() {
		return addressService.getAllTinhThanhPho();
	}
	
	@GetMapping("/QH")
	public List<QuanHuyenResponse> getQH(@RequestParam(name = "matp") String matp) {
		return addressService.getQH(matp);
	}
	
	@GetMapping("/XPTT")
	public List<XaPhuongThiTranResponse> getXPTT(@RequestParam(name = "maqh") String maqh) {
		return addressService.getXPTT(maqh);
	}
}
