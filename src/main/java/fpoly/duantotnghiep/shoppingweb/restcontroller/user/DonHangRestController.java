package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("don-hang-restCtrl-user")
@RequestMapping("don-hang")
public class DonHangRestController {
    @Autowired
    private IDonHangService donHangService;

    @GetMapping("{maDH}")
    public ResponseEntity<?> getDetail(@PathVariable("maDH")String maDh){
        if (!donHangService.existsByMa(maDh)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.findByMa(maDh));
    }
}
