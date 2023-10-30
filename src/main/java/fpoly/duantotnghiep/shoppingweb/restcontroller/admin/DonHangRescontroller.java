package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangResponsitory;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.util.List;

@RestController("don-hang-restCtrl-admin")
@RequestMapping("${admin.domain}/don-hang")
public class DonHangRescontroller {
    @Autowired
    private IDonHangService donHangService;

    @GetMapping("get-by-trangthai")
    public Page<DonHangDtoResponse> getChuaXacNhan(@RequestParam("trangThai")Integer trangThai,
                                                   @RequestParam(defaultValue = "0")Integer pageNumber,
                                                   @RequestParam(defaultValue = "10")Integer limit){
        return donHangService.getAllByTrangThai(trangThai,limit,pageNumber);
    }

    @GetMapping("/{ma}")
    public ResponseEntity<DonHangDtoResponse> getByMa(@PathVariable("ma")String ma){
        if(!donHangService.existsByMa(ma)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.findByMa(ma));
    }

    @GetMapping("update-trang-thai/{ma}")
    public ResponseEntity<Integer> updatTrangThai(@PathVariable("ma")String ma,@RequestParam("trangThai")Integer trangThai) throws MessagingException {
        if(!donHangService.existsByMa(ma)){
            return ResponseEntity.notFound().build();
        }
        donHangService.updateTrangThai(ma,trangThai);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/huy-don-hang/{ma}")
    public ResponseEntity<Integer> huyDonHang(@PathVariable("ma")String ma) throws MessagingException {
        if(!donHangService.existsByMa(ma)){
            return ResponseEntity.notFound().build();
        }
        donHangService.huyDonHang(ma);
        return ResponseEntity.ok().build();
    }

    @PutMapping("")
    public ResponseEntity<?> updateDonHang(@RequestBody DonHangDTORequest request){
        if(!donHangService.existsByMa(request.getMa())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.updateDonHang(request));
    }


}
