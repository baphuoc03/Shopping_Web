package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
