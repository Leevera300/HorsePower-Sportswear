package com.horsepower.checkout;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.horsepower.chekcout.bo.CheckoutBO;
import com.horsepower.chekcout.entity.CheckoutEntity;
import com.horsepower.chekcout.entity.CheckoutInfo;
import com.horsepower.product.bo.ProductBO;
import com.horsepower.product.bo.ProductDetailBO;
import com.horsepower.product.bo.ProductPicsBO;
import com.horsepower.product.domain.Product;
import com.horsepower.product.domain.ProductDetail;
import com.horsepower.product.domain.ProductPics;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/horsepower/checkout")
@Controller
public class CheckoutController {

	@Autowired
	private CheckoutBO checkoutBO;
	
	@Autowired
	private ProductBO productBO;

	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private ProductPicsBO productPicBO;
	
	@GetMapping("/check")
	public String check(HttpSession session, Model model) {

		String userEmail = (String) session.getAttribute("userEmail");

		List<CheckoutEntity> checkoutList = checkoutBO.getCheckoutListByUserEmail(userEmail);
		
		List<CheckoutInfo> checkoutinfoList = new ArrayList<>();

		for (CheckoutEntity checkout : checkoutList) {
			CheckoutInfo checkoutinfo = new CheckoutInfo();
			
			checkoutinfo.setCheckout(checkout);
			
			Product product = productBO.getProductById(checkout.getProductId());
			checkoutinfo.setProduct(product);
			
			ProductDetail productDetail = productDetailBO.getProductDetailById(checkout.getProductDetailId());
            checkoutinfo.setProductDetail(productDetail);
            
            ProductPics productPic = productPicBO.getProductPicByProductIdLimit1(product.getId());
            checkoutinfo.setProductPic(productPic);
            
            checkoutinfoList.add(checkoutinfo);
		}
		
		model.addAttribute("checkoutinfoList", checkoutinfoList);

		return "checkout/checkout-edit-page";
	}
}
