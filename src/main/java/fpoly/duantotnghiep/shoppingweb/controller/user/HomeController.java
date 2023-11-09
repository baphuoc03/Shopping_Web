package fpoly.duantotnghiep.shoppingweb.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("san-pham-user-ctrl")
public class HomeController {
    @GetMapping("trang-chu")
    public String trangChu() {
        return "/user/trangChu";
    }

    @GetMapping("san-pham")
    public String sanPham() {
        return "/user/sanPhamUser";
    }

    @GetMapping("thanh-toan")
    public String thanhToan() {
        return "/user/thanhToan";
    }
    @GetMapping("lich-su-mua-hang")
    public String licSu() {
        return "/user/HoaDonUser";
    }
}
