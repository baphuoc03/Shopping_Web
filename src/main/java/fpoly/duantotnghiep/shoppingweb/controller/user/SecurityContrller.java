package fpoly.duantotnghiep.shoppingweb.controller.user;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("Security-user")
public class SecurityContrller {
    @GetMapping("dang-nhap")
    public String viewLogin(){
        return "/user/authen/LoginForm.html";
    }

    @GetMapping("dang-nhap/error")
    public String viewLoginError(Model model, Authentication authentication){
        model.addAttribute("mess","Tài khoản hoặc mật khẩu không chính xác!");
        return "/user/authen/LoginForm.html";
    }
}
