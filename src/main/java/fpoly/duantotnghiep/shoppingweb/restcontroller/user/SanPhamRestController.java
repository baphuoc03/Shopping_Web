package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.filter.SanPhamDtoFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController("san-pham-user")
@RequestMapping("/san-pham")
public class SanPhamRestController {

    @Autowired
    private ISanPhamService sanPhamService;

    private Page<SanPhamDtoResponse> page = null;

    @GetMapping("get-all")
    public ResponseEntity<Page<SanPhamDtoResponse>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(value = "limit", defaultValue = "6") Integer limit) {

        return ResponseEntity.ok(sanPhamService.pagination(pageNumber, limit));
    }

    @PostMapping("filter")
    public ResponseEntity<Page<SanPhamDtoResponse>> filter(@RequestBody SanPhamDtoFilter sanPhamDtoFilter,
                                                           @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        return ResponseEntity.ok(sanPhamService.filter(sanPhamDtoFilter,pageNumber,limit));
    }

    @GetMapping("{ma}")
    public ResponseEntity<?> detail(@PathVariable("ma")String ma){
        if(!sanPhamService.existsById(ma)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sanPhamService.findByMa(ma));
    }

}
