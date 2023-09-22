package fpoly.duantotnghiep.shoppingweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("${admin.domain}/khuyen-mai")
public class KhuyenMaiController {

    @GetMapping("")
    public String hienThi() {
        return "/admin/khuyenMai";
    }

    @GetMapping("/add")
    public String form() {
        return "/admin/formKhuyenMai";
    }

    @PostMapping("/add")
    public String add(@RequestParam("ids") String ids) {
        System.out.println(ids);
        return "/admin/KhuyenMai";
    }
}
