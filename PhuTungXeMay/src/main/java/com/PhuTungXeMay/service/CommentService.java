package com.PhuTungXeMay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PhuTungXeMay.config.MapperUtil;
import com.PhuTungXeMay.entity.Comments;
import com.PhuTungXeMay.payload.request.CommentResquest;
import com.PhuTungXeMay.payload.response.CommentResponse;
import com.PhuTungXeMay.repository.CommentsRepo;
import com.PhuTungXeMay.repository.ProductsRepo;

@Service
public class CommentService {

	@Autowired
	private CommentsRepo commentsRepo;
	@Autowired
	private ProductsRepo productsRepo;
	
	public CommentResponse addNewCmt(CommentResquest commentResquest, Long productId) {
		Comments comment = MapperUtil.map(commentResquest, Comments.class);
		comment.setProduct(productsRepo.findById(productId).orElseThrow());
		return MapperUtil.map(commentsRepo.save(comment), CommentResponse.class);
	}
	
	public List<CommentResponse> listComment (Long productId) {
		List<Comments> listCmt = productsRepo.findById(productId)
				.orElseThrow().getComments();
		return MapperUtil.mapAll(listCmt, CommentResponse.class);
	}
}
