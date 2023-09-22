package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.TaiKhoanDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.ITaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${admin.domain}/tai-khoan")
public class TaiKhoanRestcontroller {

    @Autowired
    private ITaiKhoanService taiKhoanService;

    @GetMapping("get-all-nhan-vien")
    public ResponseEntity<List<TaiKhoanDtoResponse>> getAllNhanVien(){
        return ResponseEntity.ok(taiKhoanService.getTaiKhoanNhanVien());
    }

    @GetMapping("get-all-khach-hang")
    public ResponseEntity<List<TaiKhoanDtoResponse>> getAllKhachHang(){
        return ResponseEntity.ok(taiKhoanService.getTaiKhoanKhachHang());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<TaiKhoanDtoResponse> getById(@PathVariable("id")String id){
        if(taiKhoanService.findById(id)==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taiKhoanService.getDtoResponseById(id));
    }

}
