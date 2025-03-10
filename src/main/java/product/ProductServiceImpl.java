package product;

import exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pageable.Pagination;
import product.modul.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  ProductMapper productMapper;

    @Override
    public Page <ProductDto> getAllProducts(int from, int size) {
        Pageable pageable = Pagination.makePageRequest(from, size);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::productToProductDto);
    }
    @Override
    public ProductDto getProduct(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductDto)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        return productMapper.productToProductDto(productRepository.save(product));
    }
    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        return productRepository.findById(productDto.getId())
                .map(product -> {
                    product.setName(productDto.getName());
                    product.setCategory(productDto.getCategory());
                    product.setPrice(productDto.getPrice());
                    return productMapper.productToProductDto(productRepository.save(product));
                })
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
