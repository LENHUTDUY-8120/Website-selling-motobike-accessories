package com.PhuTungXeMay.api;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PhuTungXeMay.payload.request.OrderRequest;
import com.PhuTungXeMay.payload.response.ItemResponse;
import com.PhuTungXeMay.payload.response.OrderDetailResponse;
import com.PhuTungXeMay.payload.response.OrderItemResponse;
import com.PhuTungXeMay.payload.response.OrderResponse;
import com.PhuTungXeMay.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class OrderApi {

	@Autowired
	private OrderService orderService;
	@Autowired
    public JavaMailSender emailSender;
	
	@PostMapping("/orders")
	public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
		OrderResponse orderResponse = orderService.createOrder(orderRequest);
		return orderResponse;
	}
	
	@GetMapping("/admin/orders")
	public List<OrderDetailResponse> getListOrderAd(@RequestParam String status){
		return orderService.getListOrderAd(status);
	}
	
	@PutMapping("/admin/orders/{id}")
	public ResponseEntity<?> setStatus(@PathVariable Long id, @RequestParam(defaultValue = "DELIVERY") String status){
		orderService.setState(id, status);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/admin/orders/{id}")
	public OrderItemResponse getOrderItemsAd(@PathVariable Long id){
		OrderItemResponse orderItem = orderService.getOrderItemResponse(id);
		return orderItem;
	}
	
	@GetMapping("/order/{id}")
	public void sendMail(@PathVariable Long id) {
		try {
			emailSend(id);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GetMapping("/orders")
	public List<OrderDetailResponse> getListOrder(@RequestParam String phone){
		return orderService.getByPhone(phone);
	}
	
	@Async
	public void emailSend(Long orderId) throws MessagingException {
		
		OrderItemResponse order = orderService.getOrderItemResponse(orderId);
		String listP = "";
		for (ItemResponse item : order.getListItem()) {
			System.out.println("1");
			listP += "								<div class=\"product\">\r\n"
	        		+ "                                    <div class=\"product-productImage\">\r\n"
	        		+ "                                        <a href=\"#\"><img alt=\"image\" \r\n"
	        		+ "                                                src=\"http://localhost:8120/images/productImages/"+item.getImage()+"\"\r\n"
	        		+ "                                                width=\"160px\" class=\"CToWUd\"></a>\r\n"
	        		+ "                                    </div>\r\n"
	        		+ "                                    <div class=\"product-productInfo\">\r\n"
	        		+ "                                        <div class=\"product-productInfo-name\">\r\n"
	        		+ "                                            <a href=\"#\">"+item.getName()+"</a>\r\n"
	        		+ "                                        </div>\r\n"
	        		+ "                                        <div class=\"product-productInfo-price\">\r\n"
	        		+ "                                            VND "+item.getPrice()+"\r\n"
	        		+ "                                        </div>\r\n"
	        		+ "                                        <div class=\"product-productInfo-subInfo\">\r\n"
	        		+ "                                            Số lượng: "+item.getQuantity()+"\r\n"
	        		+ "                                        </div>\r\n"
	        		+ "                                    </div>\r\n"
	        		+ "                                </div>\r\n";
			System.out.println(listP);
		}
		
		System.out.println(listP);
		
		MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;
        
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart,"UTF-8");
        
        String htmlMsg = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org=\r\n"
        		+ "/TR/xhtml1/DTD/xhtml1-strict.dtd\">\r\n"
        		+ "<html lang=\"vi\">\r\n"
        		+ "\r\n"
        		+ "<head>\r\n"
        		+ "    <meta charset=\"UTF-8\">\r\n"
        		+ "    <meta name=\"viewport\" content=\"width= device-width, initial-scale= =1.0\">\r\n"
        		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie= edge\">\r\n"
        		+ "	   <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'/>"
        		+ "    <title>Document</title>\r\n"
        		+ "\r\n"
        		+ "    <style>\r\n"
        		+ "        ul {\r\n"
        		+ "            margin: 0;\r\n"
        		+ "            padding-left: 30px;\r\n"
        		+ "            list-style-type: square;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        li {\r\n"
        		+ "            padding-left: 10px;\r\n"
        		+ "            margin-top: 5px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        li:first-child {\r\n"
        		+ "            margin-top: 0;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        a {\r\n"
        		+ "            color: #33A2B2;\r\n"
        		+ "            text-decoration: none;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        a:visited {\r\n"
        		+ "            color: #33A2B2;\r\n"
        		+ "            text-decoration: none;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        a:hover {\r\n"
        		+ "            text-decoration: underline;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .main-content {\r\n"
        		+ "            margin: auto;\r\n"
        		+ "            max-width: 730px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        h2 {\r\n"
        		+ "            font-size: 18px;\r\n"
        		+ "            font-weight: normal;\r\n"
        		+ "            margin-top: 0;\r\n"
        		+ "            margin-bottom: 0;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        /* Helper */\r\n"
        		+ "        .hide {\r\n"
        		+ "            display: none;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .text-orange-normal {\r\n"
        		+ "            color: #f27c24;\r\n"
        		+ "            font-size: 16px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .text-red-normal {\r\n"
        		+ "            color: #DD3937;\r\n"
        		+ "            font-size: 16px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .text-blue-big {\r\n"
        		+ "            color: #173948;\r\n"
        		+ "            font-size: 22px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        /* Button */\r\n"
        		+ "        .button {\r\n"
        		+ "            background: #f36e20;\r\n"
        		+ "            color: #fff;\r\n"
        		+ "            padding: 12px 25px;\r\n"
        		+ "            display: block;\r\n"
        		+ "            text-align: center;\r\n"
        		+ "            text-transform: uppercase;\r\n"
        		+ "            min-width: 140px;\r\n"
        		+ "            cursor: pointer;\r\n"
        		+ "            border-bottom: 1px solid #8f8f8f;\r\n"
        		+ "            border-right: 1px solid #8f8f8f;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .button:visited {\r\n"
        		+ "            background: #f36e20;\r\n"
        		+ "            color: #fff;\r\n"
        		+ "            padding: 12px 25px;\r\n"
        		+ "            display: block;\r\n"
        		+ "            text-align: center;\r\n"
        		+ "            text-transform: uppercase;\r\n"
        		+ "            min-width: 140px;\r\n"
        		+ "            cursor: pointer;\r\n"
        		+ "            border-bottom: 1px solid #8f8f8f;\r\n"
        		+ "            border-right: 1px solid #8f8f8f;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .button:hover {\r\n"
        		+ "            background: #db5609;\r\n"
        		+ "            text-decoration: none;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .button--blue {\r\n"
        		+ "            background: #355f86;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .button--blue:visited {\r\n"
        		+ "            background: #355f86;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .button--blue:hover {\r\n"
        		+ "            background: #1b466c;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        /* Columns */\r\n"
        		+ "        .two_col {\r\n"
        		+ "            width: 100%;\r\n"
        		+ "            float: none;\r\n"
        		+ "            max-width: none;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .two-column-left {\r\n"
        		+ "            float: left;\r\n"
        		+ "            width: 49%;\r\n"
        		+ "            overflow-wrap: break-word;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .two-column-right {\r\n"
        		+ "            float: right;\r\n"
        		+ "            width: 49%;\r\n"
        		+ "            overflow-wrap: break-word;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        @media screen and (max-width:640px) {\r\n"
        		+ "\r\n"
        		+ "            .two-column-left,\r\n"
        		+ "            .two-column-right {\r\n"
        		+ "                width: 100%;\r\n"
        		+ "            }\r\n"
        		+ "\r\n"
        		+ "            .two-column-left {\r\n"
        		+ "                margin-bottom: 15px;\r\n"
        		+ "            }\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        /* Header */\r\n"
        		+ "        .header {\r\n"
        		+ "            margin-bottom: 25px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .header-title {\r\n"
        		+ "            padding: 10px 30px 30px 30px;\r\n"
        		+ "            font-size: 25px;\r\n"
        		+ "            font-weight: normal;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .header-progressBar img {\r\n"
        		+ "            max-height: 140px;\r\n"
        		+ "            width: auto;\r\n"
        		+ "            max-width: 95%;\r\n"
        		+ "            margin-left: auto;\r\n"
        		+ "            margin-right: auto;\r\n"
        		+ "            display: block;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .header-subText {\r\n"
        		+ "            text-align: center;\r\n"
        		+ "            font-style: italic;\r\n"
        		+ "            font-size: 14px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        /* Section */\r\n"
        		+ "        .section {\r\n"
        		+ "            padding: 30px;\r\n"
        		+ "            background: #fff;\r\n"
        		+ "            /* box-shadow: 1px 2px 5px #888888; */\r\n"
        		+ "            /* border-left: 1px solid #f0f0f0; */\r\n"
        		+ "            /* border-right: 1px solid #f0f0f0; */\r\n"
        		+ "            border-bottom: 10px solid #f0f0f0;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .section:last-child {\r\n"
        		+ "            border-bottom: none;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .section--dark {\r\n"
        		+ "            background: #f0f0f0;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .section-header {\r\n"
        		+ "            background-size: 30px;\r\n"
        		+ "            background-repeat: no-repeat;\r\n"
        		+ "            height: 30px;\r\n"
        		+ "            padding: 5px 0 15px 45px;\r\n"
        		+ "            font-size: 18px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .section-content {\r\n"
        		+ "            display: inline-block;\r\n"
        		+ "            width: 100%;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .section-content p:first-child {\r\n"
        		+ "            margin-top: 0;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .section-content p:last-child {\r\n"
        		+ "            margin-bottom: 0;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .section-content--justify {\r\n"
        		+ "            text-align: justify;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        /* Shipment Index */\r\n"
        		+ "        .shipmentIndex {\r\n"
        		+ "            width: 100%;\r\n"
        		+ "            background-color: #e6edfe;\r\n"
        		+ "            font-size: 15px;\r\n"
        		+ "            border-left: 5px solid #42537d;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .shipmentIndex p {\r\n"
        		+ "            padding: 10px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        /* Product */\r\n"
        		+ "        .product {\r\n"
        		+ "            border-bottom: 1px solid #D8D8D8;\r\n"
        		+ "            padding-top: 20px;\r\n"
        		+ "            padding-bottom: 20px;\r\n"
        		+ "            display: inline-block;\r\n"
        		+ "            width: 100%;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product:first-child {\r\n"
        		+ "            padding-top: 0;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product-productImage {\r\n"
        		+ "            width: 160px;\r\n"
        		+ "            vertical-align: top;\r\n"
        		+ "            float: left;\r\n"
        		+ "            margin-right: 40px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product-productImage img {\r\n"
        		+ "            max-width: 160px;\r\n"
        		+ "            height: auto;\r\n"
        		+ "            margin-left: auto;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product-productInfo {\r\n"
        		+ "            vertical-align: top;\r\n"
        		+ "            overflow-wrap: break-word;\r\n"
        		+ "            float: left;\r\n"
        		+ "            max-width: 400px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        @media screen and (max-width:440px) {\r\n"
        		+ "            .product-productInfo {\r\n"
        		+ "                width: 100%;\r\n"
        		+ "                margin-bottom: 15px;\r\n"
        		+ "            }\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product-productInfo-name {\r\n"
        		+ "            margin-bottom: 7px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product-productInfo-name a {\r\n"
        		+ "            color: #173948;\r\n"
        		+ "            font-size: 16px;\r\n"
        		+ "            text-decoration: none;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product-productInfo-price {\r\n"
        		+ "            color: #DD3937;\r\n"
        		+ "            font-size: 16px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product-productInfo-subInfo {\r\n"
        		+ "            color: #585858;\r\n"
        		+ "            font-size: 15px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product-productInfo-button {\r\n"
        		+ "            margin-top: 2px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product-productInfo-button a {\r\n"
        		+ "            background-size: 18px;\r\n"
        		+ "            background-repeat: no-repeat;\r\n"
        		+ "            background-position: bottom left;\r\n"
        		+ "            height: 20px;\r\n"
        		+ "            padding-left: 25px;\r\n"
        		+ "            padding-right: 15px;\r\n"
        		+ "            font-size: 15px;\r\n"
        		+ "            text-decoration: none;\r\n"
        		+ "            vertical-align: middle;\r\n"
        		+ "            display: inline-block;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .product-productInfo-button a:hover {\r\n"
        		+ "            text-decoration: underline;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "\r\n"
        		+ "        /* Check out */\r\n"
        		+ "        .checkout {\r\n"
        		+ "            display: inline-block;\r\n"
        		+ "            width: 100%;\r\n"
        		+ "            margin-top: 20px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .checkout-info {\r\n"
        		+ "            background-size: 30px;\r\n"
        		+ "            background-repeat: no-repeat;\r\n"
        		+ "            height: 30px;\r\n"
        		+ "            color: #9AA439;\r\n"
        		+ "            padding: 5px 0 5px 40px;\r\n"
        		+ "            overflow-wrap: normal;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .checkout-info:first-child {\r\n"
        		+ "            margin-top: 15px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "\r\n"
        		+ "        .checkout-amount {\r\n"
        		+ "            width: 100%;\r\n"
        		+ "            line-height: 24px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .checkout-amount td {\r\n"
        		+ "            height: 35px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .checkout-amount tr.total td {\r\n"
        		+ "            color: red;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .checkout-amount tr.total:last-child td {\r\n"
        		+ "            height: 24px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .checkout-amount-subtext {\r\n"
        		+ "            text-align: right;\r\n"
        		+ "            font-size: 12px;\r\n"
        		+ "        }\r\n"
        		+ "\r\n"
        		+ "        .footer {\r\n"
        		+ "            margin-top: 15px;\r\n"
        		+ "        }\r\n"
        		+ "    </style>\r\n"
        		+ "</head>\r\n"
        		+ "\r\n"
        		+ "<body\r\n"
        		+ "    style=\"margin:auto;max-width:800px;width:100%;padding:0px;color:rgb(32,32,32);font-size:16px;font-weight:normal;font-family:Roboto,RobotoDraft,Helvetica,Arial,sans-serif;line-height:150%\">\r\n"
        		+ "    <div class=\"content\" style=\"min-width:360px\">\r\n"
        		+ "        <table width=\"100%\" style=\"border:0\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
        		+ "            <tbody>\r\n"
        		+ "                <tr>\r\n"
        		+ "                    <td colspan=\"2\"></td>\r\n"
        		+ "                    <td colspan=\"3\" style=\"background-color:#E8E8E8\" height=\"1px\"></td>\r\n"
        		+ "                    <td colspan=\"3\"></td>\r\n"
        		+ "                </tr>\r\n"
        		+ "                <tr>\r\n"
        		+ "                    <td style=\"background-color:#F8F8F8\" width=\"1px\"></td>\r\n"
        		+ "                    <td style=\"background-color:#E8E8E8\" width=\"1px\"></td>\r\n"
        		+ "                    <td style=\"background-color:#D1D1D1\" width=\"1px\"></td>\r\n"
        		+ "                    <td>\r\n"
        		+ "                        <div class=\"section section--dark\">\r\n"
        		+ "                            <div class=\"section-content\">\r\n"
        		+ "                                <h2>"+order.getFullname()+" thân mến,</h2>\r\n"
        		+ "                                <div class=\"hide\">\r\n"
        		+ "                                </div>\r\n"
        		+ "                                <p>Yêu cầu đặt hàng cho đơn hàng <span\r\n"
        		+ "                                        class=\"text-orange-normal\">#"+order.getId()+"</span> của\r\n"
        		+ "                                    bạn đã được tiếp nhận và đang chờ xử lý, thời gian đặt hàng là ngày "+order.getCreatedDate()
        		+ " 									với hình thức thanh toán là <b>Thanh toán khi\r\n"
        		+ "                                        nhận hàng</p>\r\n"
        		+ "                            </div>\r\n"
        		+ "                        </div>\r\n"
        		+ "                        <div class=\"section\">\r\n"
        		+ "                            <div class=\"section-header section-header--yourPackage\">\r\n"
        		+ "                                Đơn hàng của bạn</div>\r\n"
        		+ "                            <div class=\"section-content\">\r\n"
        		+ "                                <div class=\"two-column-left\">\r\n"
        		+ "                                    Thời gian giao hàng dự kiến:<br>\r\n"
        		+ "                                    <p style=\"margin-top:5px;margin-bottom:5px\"> 5-7 ngày kể từ ngày đặt hàng<br>\r\n"
        		+ "                                    <span class=\"product-productInfo-subInfo\">(có thể kéo dài hơn do COVID-19)</span></p>\r\n"
        		+ "                                </div>\r\n"
        		+ "                                <div class=\"two-column-right\">\r\n"
        		+ "                                    <a class=\"button\"\r\n"
        		+ "                                        href=\"http://localhost:5500/review-order.html?id="+order.getId()+"\"\r\n"
        		+ "                                        target=\"_blank\"\r\n"
        		+ "                                        <p>TÌNH TRẠNG ĐƠN HÀNG</p>\r\n"
        		+ "                                    </a>\r\n"
        		+ "                                </div>\r\n"
        		+ "                            </div>\r\n"
        		+ "                        </div>\r\n"
        		+ "                        <div class=\"section\">\r\n"
        		+ "                            <div class=\"section-header section-header--whatsNext\">\r\n"
        		+ "                                Bước tiếp theo</div>\r\n"
        		+ "                            <div class=\"section-content section-content--justify\">\r\n"
        		+ "                                <ul>\r\n"
        		+ "                                    <li>Bạn vui lòng chuẩn bị sẵn số tiền mặt tương ứng để thuận tiện cho việc thanh\r\n"
        		+ "                                        toán.</li>\r\n"
        		+ "                                    <li>Trong trường hợp đơn hàng có dịch vụ kèm theo, chúng tôi sẽ liên\r\n"
        		+ "                                        hệ với bạn để xác nhận một số thông tin liên quan đến việc thực hiện dịch vụ\r\n"
        		+ "                                        (thời gian, địa điểm,...).</li>\r\n"
        		+ "                                    <li>Bạn không cần phải trả bất kỳ khoản tiền đặt cọc nào. Vui lòng liên hệ chúng\r\n"
        		+ "                                        tôi qua hotline nếu bạn có thắc mắc cần giải đáp.</li>\r\n"
        		+ "                                </ul>\r\n"
        		+ "                            </div>\r\n"
        		+ "                        </div>\r\n"
        		+ "                        <div class=\"section\">\r\n"
        		+ "                            <div class=\"section-header section-header section-header--deliveredTo\">\r\n"
        		+ "                                Đơn hàng được giao đến</div>\r\n"
        		+ "                            <div class=\"section-content\">\r\n"
        		+ "                                <div class=\"two-column-left\">\r\n"
        		+ "                                    <span class=\"text-orange-normal\">"+order.getFullname()+" </span> <br>\r\n"
        		+ "                                    "+order.getAddress()+" <br>\r\n"
        		+ "                                </div>\r\n"
        		+ "                                <div class=\"two-column-right\">\r\n"
        		+ "                                    Điện thoại: "+order.getPhoneNumber()+"<br>\r\n"
        		+ "                                    Email: <a href=\"mailto:+"+order.getEmail()+"\"\r\n"
        		+ "                                        target=\"_blank\">"+order.getEmail()+"</a>\r\n"
        		+ "                                </div>\r\n"
        		+ "                            </div>\r\n"
        		+ "                        </div>\r\n"
        		+ "                        <div class=\"section\">\r\n"
        		+ "                            <div class=\"section-header section-header--itemsDetails\">\r\n"
        		+ "                                Chi tiết đơn hàng</div>\r\n"
        		+ "                            <div class=\"section-content\">\r\n"
        		+ "                                <div class=\"shipmentIndex\">\r\n"
        		+ "                                    <p></p>\r\n"
        		+ "                                </div>\r\n"
        											+ listP
        		+ "                                <div class=\"checkout\">\r\n"
        		+ "                                    <div class=\"two-column-left\">\r\n"
        		+ "                                        <div class=\"checkout-info checkout-info--deliveryType\">\r\n"
        		+ "                                            Giao hàng Tiêu chuẩn</div>\r\n"
        		+ "                                        <div class=\"checkout-info checkout-info--paymentMethod\">\r\n"
        		+ "                                            Thanh toán khi nhận hàng</div>\r\n"
        		+ "                                    </div>\r\n"
        		+ "                                    <div class=\"two-column-right\">\r\n"
        		+ "                                        <table class=\"checkout-amount\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
        		+ "                                            <tbody>\r\n"
        		+ "                                                <tr class=\"total\">\r\n"
        		+ "                                                    <td valign=\"top\">Tổng cộng: </td>\r\n"
        		+ "                                                    <td valign=\"top\" style=\"text-align:right\">VND</td>\r\n"
        		+ "                                                    <td valign=\"top\" style=\"text-align:right\">"+order.getTotal()+"</td>\r\n"
        		+ "                                                </tr>\r\n"
        		+ "                                            </tbody>\r\n"
        		+ "                                        </table>\r\n"
        		+ "                                    </div>\r\n"
        		+ "                                </div>\r\n"
        		+ "                            </div>\r\n"
        		+ "                        </div>\r\n"
        		+ "                    </td>\r\n"
        		+ "\r\n"
        		+ "\r\n"
        		+ "                    <td style=\"background-color:#B3B3B3\" width=\"1px\"></td>\r\n"
        		+ "                    <td style=\"background-color:#D1D1D1\" width=\"1px\"></td>\r\n"
        		+ "                    <td style=\"background-color:#E8E8E8\" width=\"1px\"></td>\r\n"
        		+ "                    <td style=\"background-color:#F8F8F8\" width=\"1px\"></td>\r\n"
        		+ "                </tr>\r\n"
        		+ "\r\n"
        		+ "            </tbody>\r\n"
        		+ "        </table>\r\n"
        		+ "    </div>\r\n"
        		+ "</body>\r\n"
        		+ "\r\n"
        		+ "</html>";
        
        helper.setText(htmlMsg, true);
        
        helper.setTo(order.getEmail());
        
        helper.setSubject("81Xe đã nhận đơn hàng #"+order.getTotal());
        
    
        this.emailSender.send(message);
	}
}
