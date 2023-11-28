package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${admin.domain}/khach-hang")
public class KhachHangRestcontroller {

    @Autowired
    private IKhachHangService taiKhoanService;

    @GetMapping("/khach-hang-voucher")
    public List<KhachHangDtoResponse> findAllKhach() {
        return taiKhoanService.getAllFromVoucher();
    }
    @GetMapping("get-all-khach-hang")
    public ResponseEntity<Page<KhachHangDtoResponse>> getAllKhachHang(@RequestParam(defaultValue = "0")Integer page,
                                                                      @RequestParam(defaultValue = "8")Integer limit,
                                                                      @RequestParam(required = false) String keyWord){
        if(keyWord!=null){
            return ResponseEntity.ok(taiKhoanService.search(keyWord,page, limit));
        }
        return ResponseEntity.ok(taiKhoanService.getAll(page, limit));
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<KhachHangDtoResponse> getById(@PathVariable("id")String id){
        if(taiKhoanService.findById(id)==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taiKhoanService.findById(id));
    }

}
