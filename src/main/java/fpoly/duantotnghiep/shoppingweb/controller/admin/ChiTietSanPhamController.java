package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/san-pham/{maSP}")
public class ChiTietSanPhamController {

    @Autowired
    private ISanPhamService sanPhamService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public String getChiTietSanPhamView(@PathVariable("maSP")String maSP){
        request.setAttribute("sanPham",sanPhamService.findByMa(maSP).getTen());
        return "admin/chiTietSanPham";
    }

}
