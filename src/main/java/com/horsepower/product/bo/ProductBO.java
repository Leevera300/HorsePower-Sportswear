package com.horsepower.product.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.horsepower.product.domain.Product;
import com.horsepower.product.domain.ProductDetail;
import com.horsepower.product.domain.ProductInfo;
import com.horsepower.product.domain.ProductPics;
import com.horsepower.product.mapper.ProductMapper;

@Service
public class ProductBO {

	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private ProductPicsBO productPicsBO;
	
	@Autowired
	private ProductMapper productMapper;
	
	private static final int POST_MAX_SIZE = 4;
	
	private static final int LIST_MAX_SIZE = 10;
	
	@Transactional
	public void addProduct(String name, String category,  String description, MultipartFile[] imgFiles,
			List<ProductDetail> productDetail) {
		
		Product product = new Product();
		product.setName(name);
		product.setCategory(category);
		product.setDescription(description);
		productMapper.insertProduct(product);
		
		productDetailBO.addProductDetail(product.getId(), productDetail);
		
		productPicsBO.addProdcutPics(product.getId(), imgFiles);
	}
	
	public List<ProductInfo> getProductInfo(Integer prevId, Integer nextId) {
		List<ProductInfo> productInfoList = new ArrayList<>();
		
		List<Product> productList = new ArrayList<>();
		Integer standardId = null; // 기준 postId;
		String direction = null; // 방향
		if (prevId != null) { // 2) 이전
			standardId = prevId;
			direction = "prev";
			productList = productMapper.selectProduct(standardId, direction, LIST_MAX_SIZE);
			Collections.reverse(productList); // void = 뒤집고 저장까지 해준다
		} else if (nextId != null) { // 1) 다음
			standardId = nextId;
			direction = "next";
			productList = productMapper.selectProduct(standardId, direction, LIST_MAX_SIZE);
		} else {
			productList = productMapper.selectProduct(standardId, direction, LIST_MAX_SIZE);
		}
		

		for (Product product : productList) {
			ProductInfo productInfo = new ProductInfo();
			
			productInfo.setProduct(product);
			
			List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(product.getId());
			productInfo.setProductDetailList(productDetailList);
			
			List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(product.getId());
			productInfo.setProductPics(productPics);
			
			productInfoList.add(productInfo);
		}
		
		
		return productInfoList;
	}
	
	public List<ProductInfo> getProductInfoOrderByCreatedAtDESC(Integer prevIndexNewParam, Integer nextIndexNewParam) {
		int prevIndexNew = (prevIndexNewParam != null) ? prevIndexNewParam : 0;
		int nextIndexNew = (nextIndexNewParam != null) ? nextIndexNewParam : 0;
		List<ProductInfo> newProductInfoList = new ArrayList<>();
		
		List<Product> newProductList = productMapper.selectProductOrderByCreatedAtDESC();
		
		if (prevIndexNew != 0) {
			prevIndexNew = prevIndexNew - 1;
			nextIndexNew = POST_MAX_SIZE + prevIndexNew - 1;
			for (int i = prevIndexNew; i <= nextIndexNew; i++) {
				ProductInfo productInfo = new ProductInfo();
				productInfo.setPrevIndexNew(prevIndexNew);
				productInfo.setNextIndexNew(prevIndexNew);
				
				productInfo.setProduct(newProductList.get(i));
				
				List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(newProductList.get(i).getId());
				productInfo.setProductDetailList(productDetailList);
				
				List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(newProductList.get(i).getId());
				productInfo.setProductPics(productPics);
				
				newProductInfoList.add(productInfo);
		}
			
			return newProductInfoList;
		} else if (nextIndexNew != 0) {
			nextIndexNew = nextIndexNew + 1;
			prevIndexNew = nextIndexNew - POST_MAX_SIZE + 1;
			for (int i = prevIndexNew; i <= nextIndexNew; i++) {
				ProductInfo productInfo = new ProductInfo();
				productInfo.setPrevIndexNew(prevIndexNew);
				productInfo.setNextIndexNew(nextIndexNew);
				
				productInfo.setProduct(newProductList.get(i));
				
				List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(newProductList.get(i).getId());
				productInfo.setProductDetailList(productDetailList);
				
				List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(newProductList.get(i).getId());
				productInfo.setProductPics(productPics);
				
				newProductInfoList.add(productInfo);
			}
			return newProductInfoList;
		} else {
			prevIndexNew = 0;
			nextIndexNew = POST_MAX_SIZE - 1;
			for (int i = 0; i <= POST_MAX_SIZE - 1; i++) {
				ProductInfo productInfo = new ProductInfo();
				productInfo.setPrevIndexNew(prevIndexNew);
				productInfo.setNextIndexNew(nextIndexNew);
				
				productInfo.setProduct(newProductList.get(i));
				
				List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(newProductList.get(i).getId());
				productInfo.setProductDetailList(productDetailList);
				
				List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(newProductList.get(i).getId());
				productInfo.setProductPics(productPics);
				
				newProductInfoList.add(productInfo);
			}
			
			return newProductInfoList;
		}

		
	}
	
	public List<ProductInfo> getProductInfoOrderByUpdatedAt(Integer prevIndexHotParam, Integer nextIndexHotParam) {
		int prevIndexHot = (prevIndexHotParam != null) ? prevIndexHotParam : 0;
		int nextIndexHot = (nextIndexHotParam != null) ? nextIndexHotParam : 0;
		
		List<ProductInfo> hotProductInfoList = new ArrayList<>();
		
		List<Product> hotProductList = productMapper.selectProductOrderByUpdatedAt();

			if (prevIndexHot != 0) {
				prevIndexHot = prevIndexHot - 1;
				nextIndexHot = POST_MAX_SIZE + prevIndexHot - 1;
				for (int i = prevIndexHot; i <= nextIndexHot; i++) {
					ProductInfo productInfo = new ProductInfo();
					productInfo.setPrevIndexNew(prevIndexHot);
					productInfo.setNextIndexNew(nextIndexHot);
					
					productInfo.setProduct(hotProductList.get(i));
					
					List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(hotProductList.get(i).getId());
					productInfo.setProductDetailList(productDetailList);
					
					List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(hotProductList.get(i).getId());
					productInfo.setProductPics(productPics);
					
					hotProductInfoList.add(productInfo);
			}
				
				return hotProductInfoList;
				
		} else if (nextIndexHot != 0) {
			nextIndexHot = nextIndexHot + 1;
			prevIndexHot = nextIndexHot - POST_MAX_SIZE + 1;
			for (int i = prevIndexHot; i <= nextIndexHot; i++) {
				ProductInfo productInfo = new ProductInfo();
				productInfo.setPrevIndexNew(prevIndexHot);
				productInfo.setNextIndexNew(nextIndexHot);
				
				productInfo.setProduct(hotProductList.get(i));
				
				List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(hotProductList.get(i).getId());
				productInfo.setProductDetailList(productDetailList);
				
				List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(hotProductList.get(i).getId());
				productInfo.setProductPics(productPics);
				
				hotProductInfoList.add(productInfo);
			}
			return hotProductInfoList;
		} else {
			prevIndexHot = 0;
			nextIndexHot = POST_MAX_SIZE - 1;
			for (int i = 0; i <= POST_MAX_SIZE - 1; i++) {
				ProductInfo productInfo = new ProductInfo();
				productInfo.setPrevIndexNew(prevIndexHot);
				productInfo.setNextIndexNew(nextIndexHot);
				
				productInfo.setProduct(hotProductList.get(i));
				
				List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(hotProductList.get(i).getId());
				productInfo.setProductDetailList(productDetailList);
				
				List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(hotProductList.get(i).getId());
				productInfo.setProductPics(productPics);
				
				hotProductInfoList.add(productInfo);
			}
			
			return hotProductInfoList;
		}
		
	}
	
	public ProductInfo getProductInfoByProductId(int productId) {
		ProductInfo productInfo = new ProductInfo();
		
		Product product = productMapper.selectProductById(productId);
		productInfo.setProduct(product);
		
		List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(productId);
		productInfo.setProductDetailList(productDetailList);
		
		List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(productId);
		productInfo.setProductPics(productPics);
		
		return productInfo;
	}
	
	public ProductInfo getProductInfoByProductIdAndProductDetailId(int productId, int productDetailId) {
		ProductInfo productInfo = new ProductInfo();
		
		Product product = productMapper.selectProductById(productId);
		productInfo.setProduct(product);
		
		List<ProductDetail> productDetailList = productDetailBO.getProductDetailListById(productDetailId);
		productInfo.setProductDetailList(productDetailList);
		
		List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(productId);
		productInfo.setProductPics(productPics);
		
		return productInfo;
	}
	
	public Product getProductByProductId(int productId) {
		Product product = productMapper.selectProductById(productId);
		
		return product;
	}
	
	public Product getProductById(int productId) {
		return productMapper.selectProductById(productId);
	}
	
	public void deleteProductById(int id) {
		
		Product product = productMapper.selectProductById(id);
		
		productMapper.deleteProductById(id);
		
		productDetailBO.deleteProductDetailByProductId(product.getId());
		
		productPicsBO.deleteProductPicsByProductId(product.getId());
	}
	
	@Transactional
	public void updateProductById(int id, String name, String category,  String description, MultipartFile[] imgFiles,
			List<ProductDetail> productDetail) {
		
		Product product = productMapper.selectProductById(id);
		
		productMapper.updateProductById(id, name, category, description);
		
		productDetailBO.updateProductDetailByProductId(product.getId(), productDetail);
		
		if (imgFiles != null) {
			productPicsBO.addProdcutPics(product.getId(), imgFiles);
		}
	}

	public void deleteProductDetailById(int productDetailId) {
		productDetailBO.deleteProductDetailById(productDetailId);
    }

	public boolean isPrevLastPageByUserId(int prevId) {
		int maxPostId = productMapper.selectProudcutIdAsSort("DESC");
		return maxPostId == prevId;
	}

	public boolean isNextLastPageByUserId(int nextId) {
		int minPostId = productMapper.selectProudcutIdAsSort("ASC");
		return minPostId == nextId;
	}

	public List<ProductInfo> getProductInfoByCategory(String category, Integer prevIndexParam, Integer nextIndexParam) {
		int prevIndex = (prevIndexParam != null) ? prevIndexParam : 0;
		int nextIndex = (nextIndexParam != null) ? nextIndexParam : 0;
		List<ProductInfo> catProductInfoList = new ArrayList<>();
		
		List<Product> catProductList = productMapper.selectProductByCategory(category);
		if (prevIndex != 0) {
			prevIndex = prevIndex - 1;
			nextIndex = POST_MAX_SIZE + prevIndex - 1;
			for (int i = prevIndex; i <= nextIndex; i++) {
				ProductInfo productInfo = new ProductInfo();
				productInfo.setPrevIndexNew(prevIndex);
				productInfo.setNextIndexNew(prevIndex);
				
				productInfo.setProduct(catProductList.get(i));
				
				List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(catProductList.get(i).getId());
				productInfo.setProductDetailList(productDetailList);
				
				List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(catProductList.get(i).getId());
				productInfo.setProductPics(productPics);
				
				catProductInfoList.add(productInfo);
		}
			
			return catProductInfoList;
		} else if (nextIndex != 0) {
			nextIndex = nextIndex + 1;
			prevIndex = nextIndex - POST_MAX_SIZE + 1;
			for (int i = prevIndex; i <= nextIndex; i++) {
				ProductInfo productInfo = new ProductInfo();
				productInfo.setPrevIndexNew(prevIndex);
				productInfo.setNextIndexNew(nextIndex);
				
				productInfo.setProduct(catProductList.get(i));
				
				List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(catProductList.get(i).getId());
				productInfo.setProductDetailList(productDetailList);
				
				List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(catProductList.get(i).getId());
				productInfo.setProductPics(productPics);
				
				catProductInfoList.add(productInfo);
			}
			return catProductInfoList;
		} else {
			prevIndex = 0;
			nextIndex = POST_MAX_SIZE - 1;
			for (int i = 0; i <= POST_MAX_SIZE - 1; i++) {
				ProductInfo productInfo = new ProductInfo();
				productInfo.setPrevIndexNew(prevIndex);
				productInfo.setNextIndexNew(nextIndex);
				
				productInfo.setProduct(catProductList.get(i));
				
				List<ProductDetail> productDetailList = productDetailBO.getProductDetailListByProductId(catProductList.get(i).getId());
				productInfo.setProductDetailList(productDetailList);
				
				List<ProductPics> productPics = productPicsBO.getProductPicsByProductId(catProductList.get(i).getId());
				productInfo.setProductPics(productPics);
				
				catProductInfoList.add(productInfo);
			}
		
		return catProductInfoList;
	}
		

	}

	public void deleteProductPicById(int productPicId) {
		productPicsBO.deleteProductPicById(productPicId);
		
	}

	
}
