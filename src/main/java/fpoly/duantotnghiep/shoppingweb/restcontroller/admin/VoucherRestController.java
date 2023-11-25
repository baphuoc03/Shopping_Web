package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.ResponseEntity.ResponseObject;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.VoucherRequest;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.service.impl.KhachHangServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("${admin.domain}/voucher")
@CrossOrigin(origins = "*")
public class VoucherRestController {
    @Autowired
    VoucherServiceImpl service;
    KhachHangServiceImpl khachHangService;

    @GetMapping("/a")
    public List<VoucherReponse> findAll(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber) {
        Page<VoucherReponse> page = service.findAll(pageNumber - 1, 1);
        List<VoucherReponse> listVC = page.getContent();
        return listVC;
    }


    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestPart("voucher") VoucherRequest voucherRequest,
//                                  @RequestPart(value = "idKhach", required = false) List<String> idKhachHang,
                                  BindingResult result) {

        System.out.println(voucherRequest);
//        voucherRequest.setMa(codeVoucher());
//        validateNhap(result, voucherRequest);
//        if (result.hasErrors()) {
//            return ValidateUtil.getErrors(result);
//        }
//        if (idKhachHang != null) {
//            List<KhachHangModel> khachHang = khachHangService.findByUserNameIn(idKhachHang);
//            voucherRequest.setKhachHang(khachHang);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("Oke", "Thêm thành công", service.addVoucher(voucherRequest))
//        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        System.out.println(id);
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") String id) {
        boolean exitst = service.exitst(id);
        if (exitst) {
            service.deleteVoucher(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Oke", "Xóa thành công", "")
            );
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("found", "Xóa thất bài", "")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@Valid @RequestBody VoucherRequest voucherRequest,
                                                 BindingResult result,
                                                 @PathVariable("id") String id) {
        boolean exitst = service.exitst(id);
        validateNhap(result, voucherRequest);
        if (exitst) {
            voucherRequest.setMa(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Oke", "Sửa thành công", service.addVoucher(voucherRequest))
            );
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("Found", "Không tìm thấy", "")
        );
    }

    private static String codeVoucher() {
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

    private void validateNhap(BindingResult result, VoucherRequest voucherRequest) {
        if (voucherRequest.getDoiTuongSuDung() == 0) {
            if (voucherRequest.getSoLuong() == 0) {
                result.rejectValue("soLuong", "erSoLuong", "Vui lòng nhập số lượng");
            }
        }

        if (voucherRequest.getLoaiMucGiam().equals("TIEN")) {
            voucherRequest.setMucGiamToiDa(voucherRequest.getMucGiam());
        }
        if (voucherRequest.getLoaiMucGiam().equals("TIEN")) {
            if (voucherRequest.getMucGiam() < 1000) {
                result.rejectValue("mucGiam", "erMucGiam", "Mức giảm phải lớn hơn 1000");
            }

        } else {
            if (voucherRequest.getMucGiamToiDa() == null) {
                result.rejectValue("mucGiamToiDa", "erMucGiamToiDa", "Vui lòng nhập dữ liệu");

            }
            if (voucherRequest.getMucGiam() < 1 || voucherRequest.getMucGiam() >= 99) {
                result.rejectValue("mucGiam", "erMucGiam", "Mức giảm phải trong khoảng 1-99");
            }
        }

        if (voucherRequest.getNgayBatDau().after(voucherRequest.getNgayKetThuc())) {
            result.rejectValue("ngayBatDau", "", "Ngày bắt đầu lớn hơn ngày kết ");
        } else if (voucherRequest.getNgayBatDau().before(new Date())) {
            result.rejectValue("ngayBatDau", "", "Ngày bắt đầu phải lớn hơn hoặc bằng ngày hiện tại ");
        }
    }
}

