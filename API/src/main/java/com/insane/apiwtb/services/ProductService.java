package com.insane.apiwtb.services;

import com.insane.apiwtb.dto.ProductInput;
import com.insane.apiwtb.exception.BraTypeNotFoundException;
import com.insane.apiwtb.exception.ProductNotFoundException;
import com.insane.apiwtb.exception.ProductTypeNotFoundException;
import com.insane.apiwtb.exception.ShopNotFoundException;
import com.insane.apiwtb.interfaces.BraTypeRepository;
import com.insane.apiwtb.interfaces.ProductRepository;
import com.insane.apiwtb.interfaces.ProductTypeRepository;
import com.insane.apiwtb.interfaces.ShopRepository;
import com.insane.apiwtb.model.BraType;
import com.insane.apiwtb.model.Product;
import com.insane.apiwtb.model.ProductType;
import com.insane.apiwtb.model.Shop;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;


    @Transactional
    public Product save(ProductInput productInput) {

        Integer shopId = productInput.getShop();
        Integer productTypeId = productInput.getProductType();

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));

        ProductType productType = productTypeRepository.findById(productTypeId)
                .orElseThrow(() -> new ProductTypeNotFoundException(productTypeId));

        // TODO Iterar sobre a lista productInput.braTypes recuperar cada tipo e adicionar na lista do produto de BraTypes
        // TODO Fazer o mesmo com as categorias e as imagens

        Product product = mapper.map(productInput, Product.class);

        product.setShop(shop);
        product.setProductType(productType);

        return productRepository.save(product);
    }

    public Product getById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

}
