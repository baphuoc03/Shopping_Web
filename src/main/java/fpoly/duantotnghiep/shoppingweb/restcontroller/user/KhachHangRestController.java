package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhachHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController("khach-hang-user")
@RequestMapping("khach-hang")
public class KhachHangRestController {

    @Autowired
    private IKhachHangService khachHangService;

    @GetMapping("get-all")
    public ResponseEntity<List<KhachHangDtoResponse>> getAllKhachHang() {
        return ResponseEntity.ok(khachHangService.getAll());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<KhachHangDtoResponse> getById(@PathVariable("id") String id) {
        if (khachHangService.existsByUsername(id) == false) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(khachHangService.findById(id));
    }
    @PostMapping("")
    public ResponseEntity<?> add(@Valid @RequestBody KhachHangDTORequest khachHang,
                                 BindingResult result) throws MessagingException {
        if (khachHang.getUsername()!=null && !khachHang.getUsername().isBlank()){
            if (khachHangService.existsByUsername(khachHang.getUsername())) {
                result.addError(new FieldError("username", "username","Username đã tồn tại"));
                if (!result.hasErrors()) return ValidateUtil.getErrors(result);
            }
        }
        if (result.hasErrors()) return ValidateUtil.getErrors(result);
        return ResponseEntity.ok(khachHangService.add(khachHang));
    }

    @PutMapping(value = "")
    public ResponseEntity<?> updateUser(@Valid @RequestPart("khachHang") KhachHangDTORequest khachHang,
                                        BindingResult result,
                                        @RequestPart(value = "img", required = false)MultipartFile img) throws IOException {
        if (khachHang.getUsername()!=null && !khachHang.getUsername().isBlank()){
            if (!khachHangService.existsByUsername(khachHang.getUsername())){
                result.addError(new FieldError("username", "username", "Username không tồn tại"));
                if (!result.hasErrors()) return ValidateUtil.getErrors(result);
            }
        }
        if (result.hasErrors()) return ValidateUtil.getErrors(result);
        return ResponseEntity.ok(khachHangService.update(khachHang,img));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateKhachHang(@Valid @RequestBody KhachHangDTORequest khachHang,
                                             BindingResult result){
        if(khachHang.getUsername() !=null && !khachHang.getUsername().isBlank()){
            result.addError(new FieldError("username", "username", "Username không tồn tại"));
            if(!result.hasErrors()) return ValidateUtil.getErrors(result);

        }
        if (result.hasErrors()) return ValidateUtil.getErrors(result);
        return ResponseEntity.ok(khachHangService.update(khachHang));
    }

    @GetMapping("getUser")
    public ResponseEntity<?> getUser(@PathVariable("username")String username){
        if (!khachHangService.existsByUsername(username)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteByUsername(@PathVariable("username")String username){
        if (!khachHangService.existsByUsername(username)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
