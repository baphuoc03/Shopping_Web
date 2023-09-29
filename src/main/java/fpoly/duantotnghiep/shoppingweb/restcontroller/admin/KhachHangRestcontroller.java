package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${admin.domain}/khach-hang")
public class KhachHangRestcontroller {

    @Autowired
    private IKhachHangService taiKhoanService;


    @GetMapping("get-all-khach-hang")
    public ResponseEntity<List<KhachHangDtoResponse>> getAllKhachHang(){
        return ResponseEntity.ok(taiKhoanService.getAll());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<KhachHangDtoResponse> getById(@PathVariable("id")String id){
        if(taiKhoanService.findById(id)==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taiKhoanService.findById(id));
    }

}
