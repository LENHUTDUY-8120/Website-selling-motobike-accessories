package com.PhuTungXeMay;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import com.PhuTungXeMay.config.FileStorageProperties;
import com.PhuTungXeMay.entity.Category;
import com.PhuTungXeMay.entity.Role;
import com.PhuTungXeMay.entity.SubCategory;
import com.PhuTungXeMay.entity.User;
import com.PhuTungXeMay.repository.CategoryRepo;
import com.PhuTungXeMay.repository.RoleRepo;
import com.PhuTungXeMay.repository.SubCategoryRepo;
import com.PhuTungXeMay.repository.UserRepo;
import com.PhuTungXeMay.service.UserService;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableConfigurationProperties({
	FileStorageProperties.class
})
@EnableAsync
public class Application {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private SubCategoryRepo subCategoryRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@PostConstruct
	public void init() {
		
		List<User> users = (List<User>) userRepo.findAll();
		List<Role> roleList =  roleRepo.findAll();
		List<Category> listCategories = categoryRepo.findAll();
		List<SubCategory> listSubCategories = subCategoryRepo.findAll();
		
		if (roleList.isEmpty()) {
			Role roleAdmin = new Role();
			roleAdmin.setName("ROLE_ADMIN");
			roleRepo.save(roleAdmin);
			Role Customer = new Role();
			Customer.setName("ROLE_CUSTOMER");
			roleRepo.save(Customer);
		}
		Role roleadmin = roleRepo.findByName("ROLE_ADMIN");
		List<Role> listRole = new ArrayList<>();
		listRole.add(roleadmin);
		if (users.isEmpty()) {
			User useradmin = new User();
			useradmin.setUsername("admin");
			useradmin.setPassword("admin");
			useradmin.setRoles(listRole);
			userService.save(useradmin);
		}
		if (listCategories.isEmpty()) {
			Category category1 = new Category();
			category1.setTitle("Ph??? ki???n cho Biker");
			category1.setCode("PKB");
			Category category2 = new Category();
			category2.setTitle("Ph??? t??ng thay th???");
			category2.setCode("PTTT");
			Category category3 = new Category();
			category3.setTitle("V??? xe m??y");
			category3.setCode("VXM");
			Category category4 = new Category();
			category4.setTitle("????? ch??i xe m??y");
			category4.setCode("DCXM");
			Category category5 = new Category();
			category5.setTitle("Nh???t xe m??y");
			category5.setCode("NXM");
			
			categoryRepo.save(category1);
			categoryRepo.save(category2);
			categoryRepo.save(category3);
			categoryRepo.save(category4);
			categoryRepo.save(category5);
		}
		
		if (listSubCategories.isEmpty()) {
			List<Category> listCategories1 = categoryRepo.findAll();
			
			/*
			 * phu kien cho biker
			 */
			SubCategory subCategory1 = new SubCategory();
			subCategory1.setTitle("????? ph?????t");
			subCategory1.setCode("DP");
			subCategory1.setCategory(listCategories1.get(0));
			SubCategory subCategory2 = new SubCategory();
			subCategory2.setTitle("??o gi??p b???o h???");
			subCategory2.setCode("AG");
			subCategory2.setCategory(listCategories1.get(0));
			SubCategory subCategory3 = new SubCategory();
			subCategory3.setTitle("G??ng tay xe m??y");
			subCategory3.setCode("GT");
			subCategory3.setCategory(listCategories1.get(0));
			SubCategory subCategory4 = new SubCategory();
			subCategory4.setTitle("B?? g??i b???o v???");
			subCategory4.setCode("BG");
			subCategory4.setCategory(listCategories1.get(0));
			SubCategory subCategory5 = new SubCategory();
			subCategory5.setTitle("N??n b???o hi???m");
			subCategory5.setCode("NBH");
			subCategory5.setCategory(listCategories1.get(0));
			SubCategory subCategory6 = new SubCategory();
			subCategory6.setTitle("Balo + T??i h??t");
			subCategory6.setCode("BLTK");
			subCategory6.setCategory(listCategories1.get(0));
			
			/*
			 * phu tung thay the
			 */
			SubCategory subCategory7 = new SubCategory();
			subCategory7.setTitle("Nh??ng s??n d??a");
			subCategory7.setCode("NSD");
			subCategory7.setCategory(listCategories1.get(1));
			SubCategory subCategory8 = new SubCategory();
			subCategory8.setTitle("B??? th???ng");
			subCategory8.setCode("BT");
			subCategory8.setCategory(listCategories1.get(1));
			SubCategory subCategory9 = new SubCategory();
			subCategory9.setTitle("Phu???c xe m??y");
			subCategory9.setCode("PHXM");
			subCategory9.setCategory(listCategories1.get(1));
			SubCategory subCategory10 = new SubCategory();
			subCategory10.setTitle("P?? xe m??y");
			subCategory10.setCode("PXM");
			subCategory10.setCategory(listCategories1.get(1));
			SubCategory subCategory11 = new SubCategory();
			subCategory11.setTitle("B??? n???i xe m??y");
			subCategory11.setCode("BNXM");
			subCategory11.setCategory(listCategories1.get(1));
			SubCategory subCategory12 = new SubCategory();
			subCategory12.setTitle("Bugi + IC xe m??y");
			subCategory12.setCode("BGIC");
			subCategory12.setCategory(listCategories1.get(1));
			SubCategory subCategory13 = new SubCategory();
			subCategory13.setTitle("Heo d???u xe m??y");
			subCategory13.setCode("HDXM");
			subCategory13.setCategory(listCategories1.get(1));
			SubCategory subCategory14 = new SubCategory();
			subCategory14.setTitle("L???c nh???t xe m??y");
			subCategory14.setCode("LNXM");
			subCategory14.setCategory(listCategories1.get(1));
			SubCategory subCategory15 = new SubCategory();
			subCategory15.setTitle("Kh??a xe m??y");
			subCategory15.setCode("KXM");
			subCategory15.setCategory(listCategories1.get(1));
			SubCategory subCategory16 = new SubCategory();
			subCategory16.setTitle("L???c gi?? xe m??y");
			subCategory16.setCode("LGXM");
			subCategory16.setCategory(listCategories1.get(1));
			
			/*
			 * V??? xe m??y
			 */
			SubCategory subCategory17 = new SubCategory();
			subCategory17.setTitle("V??? xe Michelin");
			subCategory17.setCode("Michelin");
			subCategory17.setCategory(listCategories1.get(2));
			SubCategory subCategory18 = new SubCategory();
			subCategory18.setTitle("V??? xe Dunlop");
			subCategory18.setCode("Dunlop");
			subCategory18.setCategory(listCategories1.get(2));
			SubCategory subCategory19 = new SubCategory();
			subCategory19.setTitle("V??? xe Maxxis");
			subCategory19.setCode("Maxxis");
			subCategory19.setCategory(listCategories1.get(2));
			SubCategory subCategory20 = new SubCategory();
			subCategory20.setTitle("V??? xe Pirelli");
			subCategory20.setCode("Pirelli");
			subCategory20.setCategory(listCategories1.get(2));
			SubCategory subCategory21 = new SubCategory();
			subCategory21.setTitle("V??? xe Metzeler");
			subCategory21.setCode("Metzeler");
			subCategory21.setCategory(listCategories1.get(2));
			SubCategory subCategory22 = new SubCategory();
			subCategory22.setTitle("V??? xe Aspira");
			subCategory22.setCode("Aspira");
			subCategory22.setCategory(listCategories1.get(2));
			SubCategory subCategory23 = new SubCategory();
			subCategory23.setTitle("V??? xe Quick");
			subCategory23.setCode("Quick");
			subCategory23.setCategory(listCategories1.get(2));
			SubCategory subCategory24 = new SubCategory();
			subCategory24.setTitle("V??? xe IRC");
			subCategory24.setCode("IRC");
			subCategory24.setCategory(listCategories1.get(2));
			SubCategory subCategory25 = new SubCategory();
			subCategory25.setTitle("V??? xe ch???ng ??inh");
			subCategory25.setCode("CD");
			subCategory25.setCategory(listCategories1.get(2));
			SubCategory subCategory26 = new SubCategory();
			subCategory26.setTitle("V??? xe Camel");
			subCategory26.setCode("Camel");
			subCategory26.setCategory(listCategories1.get(2));
			
			/*
			 * Do choi xe may
			 */
			SubCategory subCategory27 = new SubCategory();
			subCategory27.setTitle("Tay th???ng + ph??? ki???n");
			subCategory27.setCode("TTPK");
			subCategory27.setCategory(listCategories1.get(3));
			SubCategory subCategory28 = new SubCategory();
			subCategory28.setTitle("Bao tay + G??");
			subCategory28.setCode("BTG");
			subCategory28.setCategory(listCategories1.get(3));
			SubCategory subCategory29 = new SubCategory();
			subCategory29.setTitle("K??nh chi???u h???u + ???c ki???ng");
			subCategory29.setCode("CHOK");
			subCategory29.setCategory(listCategories1.get(3));
			SubCategory subCategory30 = new SubCategory();
			subCategory30.setTitle("????n led xe m??y");
			subCategory30.setCode("DLED");
			subCategory30.setCategory(listCategories1.get(3));
			SubCategory subCategory31 = new SubCategory();
			subCategory31.setTitle("????a xe m??y");
			subCategory31.setCode("DXM");
			subCategory31.setCategory(listCategories1.get(3));
			SubCategory subCategory32 = new SubCategory();
			subCategory32.setTitle("????? ki???ng xe m??y kh??c");
			subCategory32.setCode("DKO");
			subCategory32.setCategory(listCategories1.get(3));
			
			/*
			 * Nhot xe may
			 */
			SubCategory subCategory33 = new SubCategory();
			subCategory33.setTitle("Motul");
			subCategory33.setCode("Motul");
			subCategory33.setCategory(listCategories1.get(4));
			SubCategory subCategory34 = new SubCategory();
			subCategory34.setTitle("Repsol");
			subCategory34.setCode("Repsol");
			subCategory34.setCategory(listCategories1.get(4));
			SubCategory subCategory35 = new SubCategory();
			subCategory35.setTitle("Shell Advance");
			subCategory35.setCode("SAdvance");
			subCategory35.setCategory(listCategories1.get(4));
			SubCategory subCategory36 = new SubCategory();
			subCategory36.setTitle("Fuchs");
			subCategory36.setCode("Fuchs");
			subCategory36.setCategory(listCategories1.get(4));
			SubCategory subCategory37 = new SubCategory();
			subCategory37.setTitle("Liqui Moly");
			subCategory37.setCode("LMoly");
			subCategory37.setCategory(listCategories1.get(4));
			SubCategory subCategory38 = new SubCategory();
			subCategory38.setTitle("Gulf Western");
			subCategory38.setCode("GWestern");
			subCategory38.setCategory(listCategories1.get(4));
			
			subCategoryRepo.save(subCategory1);
			subCategoryRepo.save(subCategory2);
			subCategoryRepo.save(subCategory3);
			subCategoryRepo.save(subCategory4);
			subCategoryRepo.save(subCategory5);
			subCategoryRepo.save(subCategory6);
			subCategoryRepo.save(subCategory7);
			subCategoryRepo.save(subCategory8);
			subCategoryRepo.save(subCategory9);
			subCategoryRepo.save(subCategory10);
			subCategoryRepo.save(subCategory11);
			subCategoryRepo.save(subCategory12);
			subCategoryRepo.save(subCategory13);
			subCategoryRepo.save(subCategory14);
			subCategoryRepo.save(subCategory15);
			subCategoryRepo.save(subCategory16);
			subCategoryRepo.save(subCategory17);
			subCategoryRepo.save(subCategory18);
			subCategoryRepo.save(subCategory19);
			subCategoryRepo.save(subCategory20);
			subCategoryRepo.save(subCategory21);
			subCategoryRepo.save(subCategory22);
			subCategoryRepo.save(subCategory23);
			subCategoryRepo.save(subCategory24);
			subCategoryRepo.save(subCategory25);
			subCategoryRepo.save(subCategory26);
			subCategoryRepo.save(subCategory27);
			subCategoryRepo.save(subCategory28);
			subCategoryRepo.save(subCategory29);
			subCategoryRepo.save(subCategory30);
			subCategoryRepo.save(subCategory31);
			subCategoryRepo.save(subCategory32);
			subCategoryRepo.save(subCategory33);
			subCategoryRepo.save(subCategory34);
			subCategoryRepo.save(subCategory35);
			subCategoryRepo.save(subCategory36);
			subCategoryRepo.save(subCategory37);
			subCategoryRepo.save(subCategory38);
		}
		
	}
}
