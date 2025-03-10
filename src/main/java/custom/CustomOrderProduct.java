package custom;

import lombok.Data;
import product.Product;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CustomOrderProduct extends Product {
    private boolean inWork;
    private String comment;
}
