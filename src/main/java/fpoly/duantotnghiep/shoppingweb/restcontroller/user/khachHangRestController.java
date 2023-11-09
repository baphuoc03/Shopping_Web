package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhachHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.security.ResetPasswordDto;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/khach-hang")
public class khachHangRestController {

    @Autowired
    private IKhachHangService khachHangService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<KhachHangDtoResponse>> getAllKhachHang(@RequestParam(defaultValue = "0")Integer page,
                                                                      @RequestParam(defaultValue = "8")Integer limit){
        return ResponseEntity.ok(khachHangService.getAll(page,limit));

    }

    @PostMapping("")
    public ResponseEntity<?> add(@Valid @RequestBody KhachHangDTORequest khachHang,
                                 BindingResult result) throws MessagingException{
        if (khachHang.getUsername()!=null && !khachHang.getUsername().isBlank()){
            if (khachHangService.exsistsByUsername(khachHang.getUsername())){
                result.addError(new FieldError("username", "username", "Username đã tồn tại"));
                if (!result.hasErrors())
                    return ValidateUtil.getErrors(result);
            }
        }
//        if(resetPasswordDto.getNewPass()!=null&&resetPasswordDto.getVerifyNewPass()!=null){
//            if(!resetPasswordDto.checkVerifyPassword()){
//                result.addError(new FieldError("verifyNewPass","verifyNewPass","Nhập lại mật khẩu không chính xác"));
//                if(!result.hasErrors()) return ValidateUtil.getErrors(result);
//            }
//        }
        if (result.hasErrors())
            return ValidateUtil.getErrors(result);
        return ResponseEntity.ok(khachHangService.add(khachHang));
    }
}