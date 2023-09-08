package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SanPhamRestController {

    @Autowired
    private ISanPhamService sanPhamService;

    @DeleteMapping("delete/{id}")
    @Transactional(rollbackFor =  {Exception.class, Throwable.class})//Khi có lỗi sẽ rollback
    public ResponseEntity<?> delete(@PathVariable("id")String ma){

        if(!sanPhamService.existsById(ma)){//Kiểm tra xem mã tồn tại ko
            return ResponseEntity.status(404).body("Mã sản phẩm không hợp lệ");
        }

        sanPhamService.deleteById(ma);

        //Tạo và gửi thông báo
        ThongBaoModel thongBao = new ThongBaoModel(null,null, ThongBaoType.Add.name(),"Thêm mới sản phẩm",new Date(),null);
        SocketUtil.sendNotification(thongBao);

        return ResponseEntity.ok("Xóa thành công");
    }

}
