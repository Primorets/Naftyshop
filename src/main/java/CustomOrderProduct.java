import lombok.Data;
import product.Product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class CustomOrderProduct extends Product {
    private boolean inWork;
    private String comment;
}
