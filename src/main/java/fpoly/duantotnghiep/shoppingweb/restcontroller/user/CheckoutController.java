package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.impl.SanPhamServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CheckoutController {
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

}
