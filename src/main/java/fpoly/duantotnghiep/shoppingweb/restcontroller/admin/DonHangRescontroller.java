package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("don-hang-restCtrl-admin")
@RequestMapping("${admin.domain}/don-hang")
public class DonHangRescontroller {
    @Autowired
    private IDonHangService donHangService;

    @GetMapping("chua-xac-nhan")
    public List<DonHangDtoResponse> getChuaXacNhan(){
        return donHangService.getAllByTrangThai(0);
    }

    @GetMapping("xac-nhan")
    public List<DonHangDtoResponse> getXacNhan(){
        return donHangService.getAllByTrangThai(1);
    }

    @GetMapping("/{ma}")
    public ResponseEntity<DonHangDtoResponse> getByMa(@PathVariable("ma")String ma){
        if(!donHangService.existsByMa(ma)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.findByMa(ma));
    }
}
