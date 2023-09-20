package fpoly.duantotnghiep.shoppingweb.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/tai-khoan")
public class TaiKhoanController {
    @GetMapping("nhan-vien")
    public String getTaiKhoanNhanVienView(){
        return "admin/TaiKhoanNhanVien";
    }

    @GetMapping("nguoi-dung")
    public String getTaiKhoanNguoiDungView(){
        return "admin/TaiKhoanNguoiDung";
    }
}
