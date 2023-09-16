package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.enumtype.ThongBaoType;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.TaiKhoanModel;
import fpoly.duantotnghiep.shoppingweb.model.ThongBaoModel;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.ITaiKhoanService;
import fpoly.duantotnghiep.shoppingweb.util.SocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("${admin.domain}/san-pham")
public class SanPhamRestController {

    @Autowired
    private ISanPhamService sanPhamService;

    @GetMapping("get-all")
    public ResponseEntity<List<SanPhamDtoResponse>> getAll(){
        return ResponseEntity.ok(sanPhamService.findAll());
    }

    @DeleteMapping("delete/{id}")
    @Transactional(rollbackFor =  {Exception.class, Throwable.class})//Khi có lỗi sẽ rollback
    public ResponseEntity<?> delete(@PathVariable("id")String ma) throws IOException {

        if(!sanPhamService.existsById(ma)){//Kiểm tra xem mã tồn tại ko
            return ResponseEntity.status(404).body("Mã sản phẩm không hợp lệ");
        }

        String tenSanPham = sanPhamService.findByMa(ma).getMa() + " - " + sanPhamService.findByMa(ma).getTen();

        sanPhamService.deleteById(ma);

        //Tạo và gửi thông báo
        ThongBaoModel thongBao = new ThongBaoModel(null,null, ThongBaoType.Delete.name(),"Xóa sản phẩm: "+tenSanPham,new Date(),null);
        SocketUtil.sendNotification(thongBao);

        return ResponseEntity.ok().build();
    }

    @PutMapping("update-TrangThai-HienThi/{id}")
    public ResponseEntity<?> updateTrangThaiHienThi(@PathVariable("id")String ma,@RequestBody Boolean trangThai){
        if(!sanPhamService.existsById(ma)){//Kiểm tra xem mã tồn tại ko
            return ResponseEntity.status(404).body("Mã sản phẩm không hợp lệ");
        }
        return ResponseEntity.ok(sanPhamService.updateTrangThaiHIenThi(trangThai,ma));
    }

}
