package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/nhan-xet")
public class NhanXetController {
    @GetMapping("{maSP}")
    public String show(){
        return "admin/nhanXet";
    }
}
