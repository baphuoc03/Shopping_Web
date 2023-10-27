package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.impl.SanPhamServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class CheckoutController {
    @Autowired
    IDonHangService donHangService;
    @Autowired
    IChiTietSanPhamService sanPhamServic;
    @Autowired
    VoucherServiceImpl voucherService;


    @GetMapping("/check-out/cart")
    public ResponseEntity<List<ChiTietSanPhamDtoResponse>> cart() {
        return ResponseEntity.ok(sanPhamServic.fillAllChiTietSP());
    }

    @GetMapping("/check-out/voucher")
    public ResponseEntity<List<VoucherReponse>> checkOutVoucher() {
        return ResponseEntity.ok(voucherService.findAll());
    }

    @GetMapping("/check-out/san-pham")
    public ResponseEntity<List<ChiTietSanPhamDtoResponse>> checkOutSanPham(@RequestBody List<String> maSanPhamCheckOut) {
        List<ChiTietSanPhamDtoResponse> list = new ArrayList<>();
        list.clear();
        for (String ma : maSanPhamCheckOut
        ) {
            ChiTietSanPhamDtoResponse sp = sanPhamServic.finById(ma);
            list.add(sp);
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/check-out")
    public ResponseEntity<?> addHoaDon(@RequestBody DonHangDTORequest donHangDTORequest) {
        donHangDTORequest.setNgayDatHang(new Date());
        donHangDTORequest.setMa(codeVoucher());
        donHangService.checkOut(donHangDTORequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/using-voucher")
    public Double giaGiam(@RequestBody Map<String, Object> request) {
        VoucherReponse voucherResponse = voucherService.findById(request.get("voucher").toString());
        Double tongThanhToan = Double.parseDouble(request.get("tongThanhToan").toString());
        Double giaGiam = null;
        if (voucherResponse != null) {
                giaGiam = voucherResponse.getMucGiam();
        }
        return giaGiam;
    }


    private  String codeVoucher() {
        final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        final int CODE_LENGTH = 8;

        StringBuilder code = new StringBuilder();

        Random random = new Random();
        int maxIndex = ALLOWED_CHARACTERS.length();

        // Sinh ngẫu nhiên các ký tự cho mã
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(maxIndex);
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }

}
