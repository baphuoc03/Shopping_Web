package fpoly.duantotnghiep.shoppingweb.controller.user;

import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("securitycontroller-user")
public class SecurityController {
    @Autowired
    private IKhachHangService khachHangService;
    @Autowired
    private IKhachHangRepository khachHangRepository;

    @GetMapping("/dang-nhap")
    public String viewLogin(){
        return "/user/authen/LoginForm.html";
    }

}
