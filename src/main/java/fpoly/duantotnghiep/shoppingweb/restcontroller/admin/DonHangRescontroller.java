package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangResponsitory;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

@RestController("don-hang-restCtrl-admin")
@RequestMapping("${admin.domain}/don-hang")
public class DonHangRescontroller {
    @Autowired
    private IDonHangService donHangService;

    @GetMapping("get-by-trangthai")
    public Page<DonHangDtoResponse> getChuaXacNhan(@RequestParam("trangThai") Integer trangThai,
                                                   @RequestParam(defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(defaultValue = "10") Integer limit) {
        return donHangService.getAllByTrangThai(trangThai, limit, pageNumber);
    }

    @GetMapping("get-by-trangThai-khachHang")
    public ResponseEntity<List<DonHangDtoResponse>> getByKhachHangAndTrangThai(@RequestParam("trangThai") Integer trangThai,
                                                                               Authentication authentication) {
        if (authentication != null) {
            String khachHang = authentication.getName();
            return ResponseEntity.ok(donHangService.getAllByKhachHangAndTrangThai(khachHang, trangThai));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{ma}")
    public ResponseEntity<DonHangDtoResponse> getByMa(@PathVariable("ma") String ma) {
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.findByMa(ma));
    }

    @GetMapping("update-trang-thai/{ma}")
    public ResponseEntity<Integer> updatTrangThai(@PathVariable("ma") String ma, @RequestParam("trangThai") Integer trangThai) throws MessagingException {
        if (!donHangService.existsByMa(ma)) {
            return ResponseEntity.notFound().build();
        }
        donHangService.updateTrangThai(ma, trangThai);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update-trang-thai")
    public ResponseEntity<Integer> updatTrangThaiAll(@RequestBody List<String> ma, @RequestParam("trangThai") Integer trangThai) throws MessagingException {
        ma.forEach(m -> {
            try {
                donHangService.updateTrangThai(m, trangThai);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.ok().build();
    }

    @PutMapping("/huy-don-hang")
    public ResponseEntity<Integer> huyDonHang(@RequestBody List<String> ma, @RequestParam("lyDo") String lyDo) throws MessagingException {
        donHangService.huyDonHang(ma, lyDo);
        return ResponseEntity.ok().build();
    }

    @PutMapping("")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> updateDonHang(@RequestPart("donHang") DonHangDTORequest request,
                                           @RequestPart("chiTietDonHang") List<ChiTietDonHangDTORequest> products) {
        if (!donHangService.existsByMa(request.getMa())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHangService.updateDonHang(request, products));
    }


}
