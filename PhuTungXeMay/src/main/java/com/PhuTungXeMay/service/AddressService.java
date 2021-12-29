package com.PhuTungXeMay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PhuTungXeMay.config.MapperUtil;
import com.PhuTungXeMay.entity.TinhThanhPho;
import com.PhuTungXeMay.payload.response.QuanHuyenResponse;
import com.PhuTungXeMay.payload.response.TinhThanhPhoResponse;
import com.PhuTungXeMay.payload.response.XaPhuongThiTranResponse;
import com.PhuTungXeMay.repository.QuanHuyenRepo;
import com.PhuTungXeMay.repository.TinhThanhPhoRepo;
import com.PhuTungXeMay.repository.XaPhuongThiTranRepo;

@Service
public class AddressService {

	@Autowired
	private TinhThanhPhoRepo tinhThanhPhoRepo;
	@Autowired
	private QuanHuyenRepo quanHuyenRepo;
	@Autowired
	private XaPhuongThiTranRepo xaPhuongThiTranRepo;
	
	public List<TinhThanhPhoResponse> getAllTinhThanhPho() {
		List<TinhThanhPho> tinhThanhPho = tinhThanhPhoRepo.findAll();
		return MapperUtil.mapAll(tinhThanhPho, TinhThanhPhoResponse.class);
	}
	
	public List<QuanHuyenResponse> getQH(String matp){
		return MapperUtil.mapAll(quanHuyenRepo.findByMatp(matp), QuanHuyenResponse.class);
	}
	
	public List<XaPhuongThiTranResponse> getXPTT(String maqh){
		return MapperUtil.mapAll(xaPhuongThiTranRepo.findByMaqh(maqh), XaPhuongThiTranResponse.class);
	}
}
