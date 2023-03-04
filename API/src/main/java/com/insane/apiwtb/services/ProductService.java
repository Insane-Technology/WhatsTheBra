package com.insane.apiwtb.services;

import com.insane.apiwtb.dto.ProductInput;
import com.insane.apiwtb.exception.ProductNotFoundException;
import com.insane.apiwtb.exception.ProductTypeNotFoundException;
import com.insane.apiwtb.exception.ShopNotFoundException;
import com.insane.apiwtb.interfaces.*;
import com.insane.apiwtb.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private BraTypeRepository braTypeRepository;
    @Autowired
    private ImageService imageService;


    @Transactional
    public Product save(ProductInput productInput) {

        Integer shopId = productInput.getShop();
        Integer productTypeId = productInput.getProductType();

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));

        ProductType productType = productTypeRepository.findById(productTypeId)
                .orElseThrow(() -> new ProductTypeNotFoundException(productTypeId));

        ArrayList<Image> images = new ArrayList<>();
        for (Integer imageId: productInput.getImages()) {
            Image image = imageService.getById(imageId);
            images.add(image);
        }

        ArrayList<BraType> braTypes = new ArrayList<>();
        for (Integer braTypeId : productInput.getBraTypes()) {
            Optional<BraType> braType = braTypeRepository.findById(braTypeId);
            braType.ifPresent(braTypes::add);
        }

        ArrayList<Category> categories = new ArrayList<>();
        for (Integer categoryId : productInput.getCategories()) {
            Optional<Category> category = categoryRepository.findById(categoryId);
            category.ifPresent(categories::add);
        }

        Product product = mapper.map(productInput, Product.class);

        product.setBraTypes(braTypes);
        product.setCategories(categories);
        product.setImages(images);
        product.setShop(shop);
        product.setProductType(productType);

        return productRepository.save(product);
    }
    @Transactional
    public Product save(ProductInput productInput, List<MultipartFile> files) {

        Integer shopId = productInput.getShop();
        Integer productTypeId = productInput.getProductType();

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));

        ProductType productType = productTypeRepository.findById(productTypeId)
                .orElseThrow(() -> new ProductTypeNotFoundException(productTypeId));

        ArrayList<Image> images = new ArrayList<>();
        for (MultipartFile file: files) {
            images.add(mapper.map(imageService.save(file), Image.class));
        }

        ArrayList<BraType> braTypes = new ArrayList<>();
        for (Integer braTypeId : productInput.getBraTypes()) {
            Optional<BraType> braType = braTypeRepository.findById(braTypeId);
            braType.ifPresent(braTypes::add);
        }

        ArrayList<Category> categories = new ArrayList<>();
        for (Integer categoryId : productInput.getCategories()) {
            Optional<Category> category = categoryRepository.findById(categoryId);
            category.ifPresent(categories::add);
        }

        Product product = mapper.map(productInput, Product.class);

        product.setBraTypes(braTypes);
        product.setCategories(categories);
        product.setImages(images);
        product.setShop(shop);
        product.setProductType(productType);

        return productRepository.save(product);
    }

    public Product getById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

}
