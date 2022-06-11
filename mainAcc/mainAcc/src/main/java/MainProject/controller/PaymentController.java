package MainProject.controller;

import MainProject.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import MainProject.payload.OrderWithProductVO;
import MainProject.payload.request.LoginRequest;
import MainProject.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    private Map<String, LoginRequest> users = new HashMap<String, LoginRequest>();
    private String orderUrl = "http://ORDER-SERVICE/order/";//chan url

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RestTemplate restTemplate;
    OrderWithProductVO order;


    @GetMapping("/payment/placeorder/{orderId}")
    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> placeOrder(@PathVariable Integer orderId) {
        System.out.println("-------______________-------------"+orderUrl + orderId);
         order = restTemplate.getForObject(orderUrl + orderId, OrderWithProductVO.class);
        System.out.println("ORDER PLACED " + order);
        return new ResponseEntity<String>("ORDER PLACED ",HttpStatus.OK);
    }

    @GetMapping("/payment/doPayment")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> transaction() {
        String msg="";
        //Order order = restTemplate.getForObject(serverUrl, Order.class);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Account account = accountRepository.findByUsername(username).get();
        double balance = account.getBalance();

        System.out.println("username is "+ username + " Balance is " + balance);
        System.out.println("doPayment");

        if(balance > order.getOrder().getTotalPrice()) {
            account.setBalance(balance - order.getOrder().getTotalPrice() );
            accountRepository.save(account);
            msg = "Transaction Completed";
        }else{
            msg = "insufficient fund";

        }

        System.out.println(msg);
        return new ResponseEntity<String>(msg,HttpStatus.OK);
    }


    @GetMapping("/payment/shiporder")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> shipOrder() {
        //Order order = restTemplate.getForObject(serverUrl, Books.class);
        System.out.println("Order shipped");
        return new ResponseEntity<String>("Order shipped",HttpStatus.OK);
    }


}
