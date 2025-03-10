package product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import product.modul.ProductDto;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "category", source = "category.name")
    ProductDto productToProductDto(Product product);

    @Mapping(target = "category", source = "category", qualifiedByName = "categoryFromString")
    Product productDtoToProduct(ProductDto productDTO);

    @Named("categoryFromString")
    default ProductCategory categoryFromString(String categoryName) {
        return categoryName != null ? ProductCategory.valueOf(categoryName.toUpperCase()) : null;
    }
    @Named("categoryToString")
    default String categoryToString(ProductCategory category) {
        return category != null ? category.name() : null;
    }
}
