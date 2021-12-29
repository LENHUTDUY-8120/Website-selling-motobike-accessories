package com.PhuTungXeMay.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PhuTungXeMay.entity.Products;

public interface ProductsRepo extends JpaRepository<Products, Long>{

	public Page<Products> findBySearchKeyContainingAndStatus(String title, Pageable pageable,String stt);
	
	public Page<Products> findByStatus(String status,Pageable pageable);
	
	public Products findByIdAndStatus(Long id, String stt);
	
	public Long countByStatus(String stt);
	
	@Query(value = "SELECT * FROM products p inner JOIN product_xe px on p.id=px.product_id inner join xe x on px.xe_id=x.id WHERE p.status='active' and x.code_xe = ?1",
		    countQuery = "SELECT count(*) FROM products p inner JOIN product_xe px on p.id=px.product_id inner join xe x on px.xe_id=x.id WHERE p.status='active' and x.code_xe = ?1",
		    nativeQuery = true)
	Page<Products> findByCodeXe(String codeXe, Pageable pageable);
	
	@Query(value = "SELECT * FROM products p inner JOIN sub_category sc on p.subcategory_id=sc.id where p.status='active' and sc.code = ?1",
		    countQuery = "SELECT count(*) FROM products p inner JOIN sub_category sc on p.subcategory_id=sc.id where p.status='active' and sc.code = ?1",
		    nativeQuery = true)
	Page<Products> findBySubCategory(String subCode, Pageable pageable);
	
	@Query(value = "SELECT * FROM products p inner JOIN sub_category sc on p.subcategory_id=sc.id inner join category ct on sc.category_id=ct.id where p.status='active' and ct.code = ?1",
		    countQuery = "SELECT count(*) FROM products p inner JOIN sub_category sc on p.subcategory_id=sc.id inner join category ct on sc.category_id=ct.id where p.status='active' and ct.code = ?1",
		    nativeQuery = true)
	Page<Products> findByCategory(String Code, Pageable pageable);
}
