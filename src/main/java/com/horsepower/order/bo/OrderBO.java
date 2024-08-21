package com.horsepower.order.bo;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.horsepower.chekcout.bo.CheckoutBO;
import com.horsepower.chekcout.entity.CheckoutEntity;
import com.horsepower.chekcout.entity.CheckoutItem;
import com.horsepower.delievery.bo.BillingBO;
import com.horsepower.delievery.bo.DelieveryBO;
import com.horsepower.order.entity.DelieveryInfo;
import com.horsepower.order.entity.OrderEntity;
import com.horsepower.order.repository.OrderRepository;

@Service
public class OrderBO {
	
	@Autowired
	private BillingBO billingBO;
	
	@Autowired
	private DelieveryBO delieveryBO;
	
	@Autowired
	private CheckoutBO checkoutBO;
	
	@Autowired
	private OrderRepository orderRepository;

	public OrderEntity getOrderEntityByOrderNumber(String orderNumber) {
		return orderRepository.findByOrderNumber(orderNumber);
	}

	@Transactional
	public void addOrder(Integer userId, String userEmail, DelieveryInfo delieveryInfo) {
		
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder(16);
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String orderNumber = sb.toString();
        
        OrderEntity order = null;
		for (int i = 0; i < 1000; i++) {
			order = orderRepository.findByOrderNumber(orderNumber);
			if (order != null) {
				for (int j = 0; j < 16; j++) {
		            char c = chars[random.nextInt(chars.length)];
		            sb.append(c);
		        }
		        orderNumber = sb.toString();
			} else if (order == null) {
                break;
            }
		}
		
		delieveryBO.addDelieveryInfo(delieveryInfo, orderNumber);
		
		billingBO.addBillingInfo(delieveryInfo, orderNumber);
		
		List<CheckoutItem> checkoutItems = delieveryInfo.getCheckoutItems();
		
		for (CheckoutItem checkoutItem : checkoutItems) {
			CheckoutEntity checkout = checkoutBO.getCheckoutEntityById(checkoutItem.getCheckoutId());
			
			OrderEntity orderInfo = orderRepository.save(OrderEntity.builder()
					.orderNumber(orderNumber)
					.userId(userId)
					.email(userEmail)
					.productId(checkout.getProductId())
					.productDetailId(checkout.getProductDetailId())
					.quantity(checkout.getQuantity())
					.orderStatus("Pending")
					.paymentType("Credit Card")
					.build());
			
			if (orderInfo != null) {
				checkoutBO.deleteCheckoutById(checkout.getId());
			}
		}
	}

	public List<OrderEntity> getOrderStatusListByUserId(int userId) {
        return orderRepository.findByUserId(userId);	}

	public List<OrderEntity> getOrderStatusList() {
		return orderRepository.findAll();
	}

	public void deleteOrderEntityById(int orderId) {
        orderRepository.deleteById(orderId);		
	}

	public OrderEntity getOrderEntityById(int id) {
		return orderRepository.findById(id).get();
	}
}
