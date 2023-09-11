package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${admin.domain}/san-pham/{maSP}")
public class ChiTietSanPhamRestController {

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("get-all")
    public ResponseEntity<List<ChiTietSanPhamDtoResponse>> getAll(@PathVariable("maSP")String maSP){

        return ResponseEntity.ok(chiTietSanPhamService.getAllBySanPhamMa(maSP));

    }
}
