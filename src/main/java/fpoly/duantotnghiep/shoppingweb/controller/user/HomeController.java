package fpoly.duantotnghiep.shoppingweb.controller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.service.IGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller("san-pham-user-ctrl")
public class HomeController {
    @Autowired
    IGioHangService gioHangService;

    @GetMapping("trang-chu")
    public String trangChu() {
        return "/user/trangChu1";
    }

    @GetMapping("san-pham")
    public String sanPham() {
        return "/user/sanPhamNguoiDung";
    }

    @GetMapping("san-pham/thuong-hieu/{id}")
    public String sanPhamByThuongHieu() {
        return "/user/filterProduct";
    }

    @GetMapping("thanh-toan")
    public String thanhToan() {
        List<GioHangDtoReponse> giohang = gioHangService.laySpTrongGio();
        if (giohang.size() == 0) {
            return "/user/gioHang";
        }
        return "/user/thanhToan";
    }


    @GetMapping("lich-su-mua-hang1")
    public String licSu() {
        return "/user/hoaDonNguoiDung";
    }

    @GetMapping("danh-sach-yeu-thich")
    public String danhSachYeuThich() {
        return "/user/dsyt";
    }


    @GetMapping("1")
    public String danhSa() {
        return "/user/trangChu1";
    }

    @GetMapping("2")
    public String danhSa1() {
        return "/user/sanPhamNguoiDung";
    }

    @GetMapping("3")
    public String danhSa3() {
        return "/admin/fromVoucher";
    }

    @GetMapping("lien-he")
    public String viewLienHe() {
        return "/user/lienhe";
    }
}
