package MainProject.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDTO {

    private Integer orderId;


    private List<Integer> productIds;

    private double totalPrice;


    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", productIds=" + productIds +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
