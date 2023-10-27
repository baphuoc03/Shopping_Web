package fpoly.duantotnghiep.shoppingweb.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckOutController {
    @GetMapping("/check-out")
    public String checkOut(){
        return "/user/Checkout";
    }
}
