package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/doi-mat-khau")
public class ResetPasswordController {

    @GetMapping
    public String viewFormDoiMatKhau(){
        return "/admin/resetPassword";
    }

}
