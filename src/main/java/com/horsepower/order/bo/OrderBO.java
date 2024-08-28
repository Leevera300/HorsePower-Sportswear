package com.horsepower.order.bo;

import java.util.ArrayList;
import java.util.Collections;
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
	
	private static final int LIST_MAX_SIZE = 10;

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

	public List<OrderEntity> getOrderStatusList(Integer prevId, Integer nextId) {
		List<OrderEntity> orderList = new ArrayList<>();
		Integer standardId = null; // 기준 postId;
		if (prevId != null) { // 2) 이전
			standardId = prevId;
			orderList = orderRepository.findOrderIdLessOrderByIdASC(standardId, LIST_MAX_SIZE);
			Collections.reverse(orderList); // void = 뒤집고 저장까지 해준다
		} else if (nextId != null) { // 1) 다음
			standardId = nextId;
			orderList = orderRepository.findOrderIdGreaterOrderByIdDESC(standardId, LIST_MAX_SIZE);
		} else {
			orderList = orderRepository.findOrderOrderByIdDESCLimit(LIST_MAX_SIZE);
		}
		
		return orderList;
	}

	public void deleteOrderEntityById(int orderId) {
        orderRepository.deleteById(orderId);		
	}

	public OrderEntity getOrderEntityById(int id) {
		return orderRepository.findById(id).get();
	}

	public boolean isPrevLastPageById(int prevId) {
		int maxPostId = orderRepository.findOrderOrderByIdDESC().get(0).getId();
		return maxPostId == prevId;
	}

	public boolean isNextLastPageById(int nextId) {
		int minPostId = orderRepository.findOrderOrderByIdASC().get(0).getId();
		return minPostId == nextId;
	}
}
