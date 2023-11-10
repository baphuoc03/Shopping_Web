package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DiaChiDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DiaChiDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.IDiaChiService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("dia-chi")
public class DiaChiController {
    @Autowired
    private IDiaChiService diaChiService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<DiaChiDTOResponse>> getDiaChi(@RequestParam(defaultValue = "0")Integer page,
                                                             @RequestParam(defaultValue = "8")Integer limit){
        return ResponseEntity.ok(diaChiService.getAll(page, limit));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<DiaChiDTOResponse> getById(@PathVariable("id")Long id){
        if (diaChiService.exsistsById(id)==false){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diaChiService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody DiaChiDTORequest diaChi,
                                 BindingResult result,
                                 Authentication authentication) throws IOException {
       if (result.hasErrors()){
           return ValidateUtil.getErrors(result);
       }
       diaChi.setTaiKhoan(authentication.getName());
        System.out.println(diaChi.toString());
       return ResponseEntity.ok(diaChiService.add(diaChi));
    }

}
