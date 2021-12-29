package com.PhuTungXeMay.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.PhuTungXeMay.config.MapperUtil;
import com.PhuTungXeMay.entity.Products;
import com.PhuTungXeMay.entity.Xe;
import com.PhuTungXeMay.payload.request.XeRequest;
import com.PhuTungXeMay.payload.response.XeNavResponse;
import com.PhuTungXeMay.payload.response.XeResponse;
import com.PhuTungXeMay.repository.XeRepo;

@Service
public class XeService {

	@Autowired
	private XeRepo xeRepo;
	
	public XeResponse addXe(XeRequest xeRequest) {
		Xe xe = MapperUtil.map(xeRequest, Xe.class);
		return MapperUtil.map(xeRepo.save(xe), XeResponse.class);
	}
	
	public List<XeResponse> getAll(){
		return MapperUtil.mapAll(xeRepo.findAll(Sort.by("id").descending()), XeResponse.class);
	}
	
	public void remove(Long id) {
		Xe xe = xeRepo.findById(id).orElseThrow();
		xeRepo.delete(xe);
	}
	
	public List<XeNavResponse> getNavXe() {
		List<Xe> listXe= xeRepo.findAll(Sort.by("tenXe").descending());
		return listXe.stream().map(xe->toXeNav(xe)).collect(Collectors.toList());
	}
	
	public XeNavResponse toXeNav(Xe xe) {
		XeNavResponse xeNavResponse = new XeNavResponse();
		xeNavResponse.setId(xe.getId());
		xeNavResponse.setCodeXe(xe.getCodeXe());
		xeNavResponse.setTenXe(xe.getTenXe());
		int numP = 0;
		for (Products product : xe.getProducts()) {
			if ("active".equals(product.getStatus())) {
				numP += 1;
			}
		}
		xeNavResponse.setNumP(numP);
		return xeNavResponse;
	}
}
