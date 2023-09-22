package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.ResponseEntity.ResponseObject;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DongSanPhamResponese;
import fpoly.duantotnghiep.shoppingweb.dto.request.DongSanPhamRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.MauSacDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.IDongSanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${admin.domain}/dong-san-pham")
public class DongSanPhamRestController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IDongSanPhamService service;

    @GetMapping("find-all")
    public List<DongSanPhamResponese> findall(){
        return service.findAll();
    }

    @PostMapping("add")
    public ResponseEntity<ResponseObject> add(@RequestBody DongSanPhamRequest dongSanPhamRequest) throws IOException {
        System.out.println(dongSanPhamRequest.maptomodel().toString());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ooke", "Thêm thành công", service.save(dongSanPhamRequest))
        );
    }

    @PutMapping("update/{ma}")
    public ResponseEntity<?> update(@PathVariable("ma") String id,@RequestBody DongSanPhamRequest dongSanPhamRequest){
        Boolean exit = service.existsById(id);
        if(exit){
            dongSanPhamRequest.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ook","Sửa thành công",service.save(dongSanPhamRequest)));
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("found", "Sửa thất bại", "")
        );
    }

    @DeleteMapping("delete/{ma}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("ma") String id){
        Boolean exit = service.existsById(id);
        if(exit){
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ook","Xóa thành công",""));
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("found", "Xóa thất bại", "")
        );
    }
}
