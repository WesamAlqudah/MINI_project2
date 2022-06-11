package MainProject.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int productId;
    private String productName;
    private String vendor;
    private String category;
    private int quantity;
    private String price;

}