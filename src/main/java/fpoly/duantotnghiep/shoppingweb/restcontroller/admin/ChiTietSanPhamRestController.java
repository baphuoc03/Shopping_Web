package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietSanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import fpoly.duantotnghiep.shoppingweb.repository.sizerepo;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${admin.domain}/san-pham/{maSP}")
public class ChiTietSanPhamRestController {

    @Autowired
    private sizerepo sizerepo;
    @Autowired
    private IChiTietSanPhamService sanPhamService;

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("get-all")
    public ResponseEntity<List<ChiTietSanPhamDtoResponse>> getAll(@PathVariable("maSP")String maSP){

        return ResponseEntity.ok(chiTietSanPhamService.getAllBySanPhamMa(maSP));

    }

    @GetMapping("test")
    public List<SizeModel> test(@PathVariable("maSP")String maSP){
        return sizerepo.getAllNotInSanPham(maSP);
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestParam("soLuong")Long soLuong, @RequestBody List<ChiTietSanPhamDtoRequest> models){
        if(soLuong<0){
            Map<String, String> body = new HashMap<>();
            body.put("message", "Số lượng phải >= 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.valueOf("application/json")).body(body);
        }
        return ResponseEntity.ok(chiTietSanPhamService.saveAll(models));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")String id){
        if(!chiTietSanPhamService.existsById(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        chiTietSanPhamService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody ChiTietSanPhamDtoRequest model,
                                    BindingResult result){

        if(result.hasErrors()){
            return ValidateUtil.getErrors(result);
        }
        return ResponseEntity.ok(chiTietSanPhamService.update(model));
    }


}
