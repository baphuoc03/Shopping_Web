package fpoly.duantotnghiep.shoppingweb.controller.admin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.security.ResetPasswordDto;
import fpoly.duantotnghiep.shoppingweb.model.NhanVienModel;
import fpoly.duantotnghiep.shoppingweb.repository.INhanVienRepository;
import fpoly.duantotnghiep.shoppingweb.service.INhanVienService;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

@Controller
public class SecurityController {
    @Autowired
    private INhanVienService nhanVienService;
    @Autowired
    private INhanVienRepository nhanVienRepository;
    private Date dateCheck = new Date();
    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/admin/trang-chu"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    @GetMapping("${admin.domain}/login")
    public String viewLogin(){
        return "/admin/authen/LoginForm.html";
    }

    @GetMapping("${admin.domain}/quen-mat-khau")
    public String viewForgotPass(){
        return "/admin/authen/ForgotPass.html";
    }

    @PostMapping("${admin.domain}/quen-mat-khau")
    @ResponseBody
    public ResponseEntity<?> forgotPass(@RequestBody String username) throws MessagingException {
        HashMap<String, String> map = new HashMap<>();
        System.out.println(username);
        if(username==null||username.isBlank()){
            map.put("er","Vui lòng nhập username");
            return ResponseEntity.status(400).body(map);
        }else if(!nhanVienService.existsByUsername(username)){
            map.put("er","Username không tồn tại");
            return ResponseEntity.status(400).body(map);
        }
        NhanVienDtoResponse nhanVienDtoResponse = nhanVienService.findById(username);

        dateCheck = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Context context = new Context();
        context.setVariable("username",nhanVienDtoResponse.getUsername());
        context.setVariable("date",simpleDateFormat.format(new Date()));
        EmailUtil.sendEmailWithHtml(nhanVienDtoResponse.getEmail(), "Đặt Lại Mật Khẩu","/admin/authen/ConfirmResetPassword",context);
        map.put("success","Thành công! Vui lòng kiểm tra mail của tài khoản để đặt lại mật khẩu");
        return ResponseEntity.ok(map);

    }

    @GetMapping("${admin.domain}/dat-lai-mat-khau/{username}/{date}")
    public String viewDatLaiMK(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  Date date){
        Date dateNow = new Date();

        if(Duration.between(date.toInstant(),dateCheck.toInstant()).getSeconds()!=0){
            return "/admin/authen/404";
        }

        Long dffSeconds = Duration.between(dateNow.toInstant(),date.toInstant()).getSeconds();
        if(dffSeconds < -30 || dffSeconds>0){
            return "/admin/authen/404";
        }else{
            return "/admin/authen/datLaiMK";
        }
    }

    @GetMapping("${admin.domain}/dat-lai-mat-khau/thanh-cong")
    public String success(){
        return "admin/authen/Success";
    }

    @PutMapping("${admin.domain}/dat-lai-mat-khau/{username}/{date}")
    @ResponseBody
    public ResponseEntity<?> datLaiMK(@Valid @RequestBody ResetPasswordDto resetPasswordDto,
                                      BindingResult result,
                                      @PathVariable("username")String username,
                                      @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  Date date){

        if(Duration.between(date.toInstant(),dateCheck.toInstant()).getSeconds()!=0){
//            result.addError(new FieldError("newPass", "newPass", ""));
            result.addError(new FieldError("erDate","erDate","Đặt lại mật khẩu không có hiệu lực!"));
            return ValidateUtil.getErrors(result);
        }
        Date dateNow = new Date();
        Long dffSeconds = Duration.between(dateNow.toInstant(),date.toInstant()).getSeconds();
        if(dffSeconds < -30 || dffSeconds > 0){
//            result.addError(new FieldError("newPass", "newPass", ""));
            result.addError(new FieldError("erDate","erDate","Đặt lại mật khẩu không có hiệu lực!"));
            return ValidateUtil.getErrors(result);
        }
            if (resetPasswordDto.getNewPass() != null) {
                if (resetPasswordDto.getVerifyNewPass() == null || resetPasswordDto.getVerifyNewPass().isBlank()) {
                    result.addError(new FieldError("verifyNewPass", "verifyNewPass", "Vui lòng nhập lại mật khẩu"));
                }
            }
            if (resetPasswordDto.getNewPass() != null && resetPasswordDto.getVerifyNewPass() != null && !resetPasswordDto.getVerifyNewPass().isBlank()) {
                if (!resetPasswordDto.checkVerifyPassword()) {
                    result.addError(new FieldError("verifyNewPass", "verifyNewPass", "Nhập lại mật khẩu không chính xác"));
                }
            }
        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }

        NhanVienModel nhanVienModel = nhanVienRepository.findById(username).get();
        nhanVienModel.setPassword(resetPasswordDto.getNewPass());
        nhanVienRepository.save(nhanVienModel);

        return ResponseEntity.ok().build();


    }
}
