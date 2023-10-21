package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("chi-tiet-san-pham-user-restCtrl")
@RequestMapping("chi-tiet-san-pham/{maSP}")
public class ChiTietSanPhamRestController {

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("get-all")
    public ResponseEntity<List<ChiTietSanPhamDtoResponse>> getAll(@PathVariable("maSP")String maSP){

        return ResponseEntity.ok(chiTietSanPhamService.getAllBySanPhamMa(maSP));
    }
}
