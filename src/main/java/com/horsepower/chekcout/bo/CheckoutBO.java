package com.horsepower.chekcout.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horsepower.chekcout.entity.CheckoutEntity;
import com.horsepower.chekcout.repository.CheckoutRepository;
import com.horsepower.product.bo.ProductBO;
import com.horsepower.product.bo.ProductDetailBO;
import com.horsepower.product.domain.Product;
import com.horsepower.product.domain.ProductDetail;

@Service
public class CheckoutBO {

	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private CheckoutRepository checkoutRepository;
	
	public void addCheckOutByProductIdAndUserId(int productId, int quantity, String color, String size, String UserEmail) {
		
		Product product = productBO.getProductByProductId(productId);
		
		ProductDetail productDetail = productDetailBO.getProductDetailByProductIdAndColorAndSize(productId, color, size);
		
		// add checkout
		checkoutRepository
				.save(CheckoutEntity.builder()
						.productId(product.getId())
						.productDetailId(productDetail.getId())
						.userEmail(UserEmail)
						.quantity(quantity)
						.build());
	}
	
	public List<CheckoutEntity> getCheckoutListByUserEmail(String UserEmail) {
		List<CheckoutEntity> checkoutList = checkoutRepository.findByUserEmail(UserEmail);
		
		return checkoutList;
	}
	
	public CheckoutEntity getCheckoutEntityById(int id) {
		return checkoutRepository.findById(id).orElse(null);
	}

	public void updateCheckoutByIdAndQuantity(List<Integer> checkoutId, List<Integer> quantity) {
		for (int i = 0; i < checkoutId.size(); i++) {
			CheckoutEntity checkout = checkoutRepository.findById(checkoutId.get(i)).orElse(null);
			checkout.setQuantity(quantity.get(i));
			checkoutRepository.save(checkout);
		}
	}
	
	public void deleteCheckoutById(int checkoutId) {
        checkoutRepository.deleteById(checkoutId);		
	}


}
