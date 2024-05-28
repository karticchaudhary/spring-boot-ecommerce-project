package com.project.catalog_service.domain;

import com.project.catalog_service.ApplicationProperties;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ApplicationProperties applicationProperties;

    public PagedResult<Product> getProducts(int pageNo) {
        final Sort sort = Sort.by("name").ascending();
        pageNo = (pageNo <= 1) ? 0 : pageNo - 1;
        final Pageable pageable = PageRequest.of(pageNo, applicationProperties.pageSize(), sort);
        final Page<Product> productPage = productRepository.findAll(pageable)
                .map(ProductMapper::toProduct);

        return new PagedResult<>(
                productPage.getContent(),
                productPage.getTotalElements(),
                (productPage.getNumber() + 1),
                productPage.getTotalPages(),
                productPage.isFirst(),
                productPage.isLast(),
                productPage.hasNext(),
                productPage.hasPrevious()
        );
    }

    public Optional<Product> getProductByCode(String code){
        return productRepository.findByCode(code).map(ProductMapper::toProduct);
    }


}
