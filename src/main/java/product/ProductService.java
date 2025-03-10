package product;

import org.springframework.data.domain.Page;
import product.modul.ProductDto;

import java.util.List;

public interface ProductService {
    Page <ProductDto> getAllProducts(int page, int size);

    ProductDto getProduct(Long id);

    ProductDto createProduct(ProductDto productDTO);

    ProductDto updateProduct(ProductDto productDto, Long id);

    void deleteProduct(Long id);

}
