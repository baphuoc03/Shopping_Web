package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ThuongHieuDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDtoRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.ThuongHieuDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.ThuongHieuModel;
import fpoly.duantotnghiep.shoppingweb.service.IKieuDangService;
import fpoly.duantotnghiep.shoppingweb.service.IThuongHieuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("thuong-hieu")
public class ThuongHieuRestController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IThuongHieuService service;
    @GetMapping("find-all")
    public List<ThuongHieuDtoResponse> findAll(){
        return service.findAll();
    }
    @GetMapping("view-all")
    public String viewAll(@ModelAttribute("thuongHieu")ThuongHieuDtoRequest thuonghieu){
        request.setAttribute("method", "add");
        return "test";
    }
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody ThuongHieuDtoRequest thuonghieu ){
        return ResponseEntity.ok(service.save(thuonghieu));
    }
    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@RequestBody ThuongHieuDtoRequest thuonghieu, @PathVariable("id") String id){
        service.findById(id);
        ThuongHieuDtoResponse model = service.save(thuonghieu);
        return ResponseEntity.ok(model);
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestBody List<String> id){
        service.deleteByIds(id);
        return ResponseEntity.ok().build();
    }
}
