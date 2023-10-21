package fpoly.duantotnghiep.shoppingweb.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @GetMapping("/cart")
    private String show(){
        return "/user/cart";
    }
}
