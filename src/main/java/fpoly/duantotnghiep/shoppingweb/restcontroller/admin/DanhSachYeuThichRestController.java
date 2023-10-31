package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.ResponseEntity.ResponseObject;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DanhSachYeuThichResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DanhSachYeuThichRequest;
import fpoly.duantotnghiep.shoppingweb.service.IDanhSachYeuThichService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/danh-sach-yeu-thich")
public class DanhSachYeuThichRestController {
    @Autowired
    private IDanhSachYeuThichService service;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("find-all")
    public List<DanhSachYeuThichResponse> findAll(){return service.findAll();}

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody DanhSachYeuThichRequest danhSachYeuThichRequest, BindingResult result) throws IOException {
        return ResponseEntity.ok(service.save(danhSachYeuThichRequest));
    }

    @DeleteMapping("delete/{ma}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("ma") String ma){
        Boolean exit = service.existsById(ma);
        if(exit){
            service.deleteById(ma);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ook","Xóa thành công",""));
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseObject("found", "Xóa thất bại", "")
        );
    }
}
