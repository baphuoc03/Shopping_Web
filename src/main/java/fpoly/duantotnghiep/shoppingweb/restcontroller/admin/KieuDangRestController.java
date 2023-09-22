package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KieuDangDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.MauSacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDtoRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.MauSacDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.KieuDangModel;
import fpoly.duantotnghiep.shoppingweb.service.IKieuDangService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("kieu-dang")
public class KieuDangRestController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private IKieuDangService service;
    @GetMapping("find-all")
    public List<KieuDangDTOResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("view-all")
    public String viewAdd(@ModelAttribute("k") KieuDangDtoRequest kieudang){
        request.setAttribute("method","add");
        return "test";
    }
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody KieuDangDtoRequest kieudang){
        System.out.println(kieudang.mapToModel().toString());
        return  ResponseEntity.ok(service.save(kieudang));
    }
    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@RequestBody KieuDangDtoRequest kieudang, @PathVariable("id") String id){
        service.findById(id);
        KieuDangDTOResponse kieuDangModel = service.save(kieudang);
        return ResponseEntity.ok(kieuDangModel);
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestBody List<String> id){
        service.deleteByIds(id);
        return ResponseEntity.ok().build();
    }
//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<?> delete(@RequestBody )

}
