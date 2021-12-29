package com.PhuTungXeMay.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.PhuTungXeMay.config.FileStorageException;
import com.PhuTungXeMay.config.FileStorageProperties;
import com.PhuTungXeMay.config.MapperUtil;
import com.PhuTungXeMay.config.VnCharacterUtils;
import com.PhuTungXeMay.converter.ProductConverter;
import com.PhuTungXeMay.entity.Images;
import com.PhuTungXeMay.entity.Products;
import com.PhuTungXeMay.entity.SubCategory;
import com.PhuTungXeMay.entity.Xe;
import com.PhuTungXeMay.payload.request.ProductsRequest;
import com.PhuTungXeMay.payload.response.ProductsCusResponse;
import com.PhuTungXeMay.payload.response.ProductsResponse;
import com.PhuTungXeMay.repository.ImagesRepo;
import com.PhuTungXeMay.repository.ProductsRepo;
import com.PhuTungXeMay.repository.SubCategoryRepo;
import com.PhuTungXeMay.repository.XeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductsService {

	private final Path fileStorageLocation;

	@Autowired
	private ProductsRepo productsRepo;
	@Autowired
	private ImagesRepo imagesRepo;
	@Autowired
	private XeRepo xeRepo;
	@Autowired
	private SubCategoryRepo subCategoryRepo;

	@Autowired
	public ProductsService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getProductImages()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public ProductsRequest jsonToProduct(String P) {
		ProductsRequest productsRequest = new ProductsRequest();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			productsRequest = objectMapper.readValue(P, ProductsRequest.class);
		} catch (Exception e) {
			System.out.println(e);
		}
		return productsRequest;
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public ProductsResponse saveProduct(MultipartFile[] files, String P) {
		ProductsRequest productsRequest = jsonToProduct(P);
		Products products = new Products();
		products.setTitle(productsRequest.getTitle());
		products.setSearchKey(VnCharacterUtils.removeAccent(productsRequest.getTitle()));
		products.setProductCode(productsRequest.getProductCode());
		products.setDonVi(productsRequest.getDonVi());
		products.setPrice(productsRequest.getPrice());
		products.setDescribes(productsRequest.getDescribes());
		SubCategory subCategory = subCategoryRepo.findOneByCode(productsRequest.getSubCategoryCode());
		products.setSubCategory(subCategory);
		List<Xe> listXe = new ArrayList<>();
		for (String code : productsRequest.getListXe()) {
			listXe.add(xeRepo.findOneByCodeXe(code));
		}
		products.setXe(listXe);
		Products product = productsRepo.save(products);
		List<Images> listImage = new ArrayList<>();
		for (MultipartFile file : files) {
			String fileName = storeFile(file);
			Images image = new Images();
			image.setFileName(fileName);
			image.setProduct(product);
			listImage.add(imagesRepo.save(image));
		}
		product.setListImage(listImage);
		ProductsResponse productsResponse = MapperUtil.map(product, ProductsResponse.class);
		productsResponse.setImages(
				product.getListImage().stream().map(image -> image.getFileName()).collect(Collectors.toList()));
		productsResponse.setListXe(
				product.getXe().stream().map(xe -> xe.getTenXe()).collect(Collectors.toList()));
		return productsResponse;
	}

	public Page<Products> findProducts(String title, int page, int size, String stt) {
		Pageable paging = PageRequest.of(page, size,Sort.by("id").descending());
		Page<Products> pageP;
		if (title == null) {
			pageP = productsRepo.findByStatus(stt, paging);
		} else {
			String searchKey = VnCharacterUtils.removeAccent(title);
			pageP = productsRepo.findBySearchKeyContainingAndStatus(searchKey, paging, stt);
		}
		return pageP;
	}

	public ProductsResponse update(String P, MultipartFile[] files) {
		ProductsRequest productsRequest = jsonToProduct(P);
		Products products = productsRepo.findById(productsRequest.getId()).orElseThrow();
		products.setTitle(productsRequest.getTitle());
		products.setProductCode(productsRequest.getProductCode());
		products.setDonVi(productsRequest.getDonVi());
		products.setPrice(productsRequest.getPrice());
		products.setDescribes(productsRequest.getDescribes());
		SubCategory subCategory = subCategoryRepo.findOneByCode(productsRequest.getSubCategoryCode());
		products.setSubCategory(subCategory);
		List<Xe> listXe = new ArrayList<>();
		for (String code : productsRequest.getListXe()) {
			listXe.add(xeRepo.findOneByCodeXe(code));
		}
		products.setXe(listXe);
		if (files != null) {
			List<Images> listImage1 = products.getListImage();
			imagesRepo.deleteAll(listImage1);
			List<Images> listImage = new ArrayList<>();
			for (MultipartFile file : files) {
				String fileName = storeFile(file);
				Images image = new Images();
				image.setFileName(fileName);
				image.setProduct(products);
				listImage.add(imagesRepo.save(image));
			}
			products.setListImage(listImage);
		}
		Products product = productsRepo.save(products);
		ProductsResponse productsResponse = MapperUtil.map(product, ProductsResponse.class);
		productsResponse.setImages(
				product.getListImage().stream().map(image -> image.getFileName()).collect(Collectors.toList()));
		return productsResponse;
	}

	public ProductsResponse getProductByIdAdmin(Long id) {
		Products product = productsRepo.findByIdAndStatus(id, "active");
		ProductsResponse productsResponse = MapperUtil.map(product, ProductsResponse.class);
		productsResponse.setCategoryCode(product.getSubCategory().getCategory().getCode());
		productsResponse.setSubCategoryCode(product.getSubCategory().getCode());
		productsResponse.setImages(
				product.getListImage().stream().map(image -> image.getFileName()).collect(Collectors.toList()));
		productsResponse.setListXe(
				product.getXe().stream().map(xe -> xe.getCodeXe()).collect(Collectors.toList()));
		return productsResponse;
	}

	public void delete(Long id) {
		Products products = productsRepo.findById(id).orElseThrow();
		products.setStatus("deactive");
		productsRepo.save(products);
	}

	public Page<Products> getAllProducts(String title, int page, int size, Sort sort) {
		Pageable paging = PageRequest.of(page, size,sort);
		Page<Products> pageP;
		if (title == null) {
			pageP = productsRepo.findByStatus("active", paging);
		} else {
			String searchKey = VnCharacterUtils.removeAccent(title);
			pageP = productsRepo.findBySearchKeyContainingAndStatus(searchKey, paging, "active");
		}
		return pageP;
	}

	public Page<Products> getShopByCar( int page, int size,String code, Sort sort) {
		Pageable paging = PageRequest.of(page, size,sort);
		Page<Products> pageP;
		pageP = productsRepo.findByCodeXe(code, paging);
		return pageP;
	}
	
	public Page<Products> getShopByCT( int page, int size,String code, Sort sort) {
		Pageable paging = PageRequest.of(page, size,sort);
		Page<Products> pageP;
		pageP = productsRepo.findByCategory(code, paging);
		return pageP;
	}
	
	public Page<Products> getShopBySCT( int page, int size,String code, Sort sort) {
		Pageable paging = PageRequest.of(page, size,sort);
		Page<Products> pageP;
		pageP = productsRepo.findBySubCategory(code, paging);
		return pageP;
	}
	
	public ProductsCusResponse getProductById(Long id) {
		Products product = productsRepo.findByIdAndStatus(id, "active");
		ProductsCusResponse productsCusResponse = ProductConverter.toCusResponse(productsRepo.save(product));
		return productsCusResponse;
	}
	
	public void setStock(Long id,int quantity) {
		Products products = productsRepo.findById(id).orElseThrow();
		products.setQuantity(quantity);
		productsRepo.save(products);
	}
}
