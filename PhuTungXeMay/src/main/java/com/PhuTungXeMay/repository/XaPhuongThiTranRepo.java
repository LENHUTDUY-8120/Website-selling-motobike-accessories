package com.PhuTungXeMay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PhuTungXeMay.entity.XaPhuongThiTran;

public interface XaPhuongThiTranRepo extends JpaRepository<XaPhuongThiTran, String>{

	List<XaPhuongThiTran> findByMaqh(String maqh);
}
