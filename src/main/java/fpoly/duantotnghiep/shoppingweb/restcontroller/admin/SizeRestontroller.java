package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SizeDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SizeDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.ISizeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${admin.domain}/size")
public class SizeRestontroller {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISizeService service;

    @GetMapping("/hien-thi")
    public List<SizeDTOResponse> findAll() {
        return service.findAll();
    }

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody SizeDTORequest size) throws IOException {
        System.out.println(size.toString());
        return ResponseEntity.ok(service.save(size));
    }

    @DeleteMapping("/{ma}")
    public ResponseEntity<?> delete(@PathVariable Float ma) {
        service.deleteById(ma);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update/{ma}")
    public ResponseEntity<?> update(@RequestBody SizeDTORequest size, @PathVariable Float ma) {
        size.setMa(ma);
        return ResponseEntity.ok(service.save(size));
    }
}
