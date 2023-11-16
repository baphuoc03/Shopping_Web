package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.request.NhanXetDtoRequest;
import fpoly.duantotnghiep.shoppingweb.entitymanager.NhanXetEntityManager;
import fpoly.duantotnghiep.shoppingweb.service.INhanXetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("nhanXet-rest-user")
@RequestMapping("nhan-xet")
public class NhanXetRestController {
    @Autowired
    private INhanXetService nhanXetService;
    @Autowired
    private NhanXetEntityManager nhanXetEntityManager;

    @GetMapping()
    public ResponseEntity<?> getBySanPham(@RequestParam String maSP,
                                          @RequestParam(defaultValue = "0")Integer page,
                                          @RequestParam(defaultValue = "5")Integer limit,
                                          @RequestParam(required = false)Float rate){

        if(rate != null){
            return ResponseEntity.ok(nhanXetService.getNhanXetBySanPhamAndRate(page,limit,maSP,rate));
        }

        return ResponseEntity.ok(nhanXetService.getNhanXetBySanPham(page,limit,maSP));
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody NhanXetDtoRequest nhanXetDtoRequest){
        return ResponseEntity.ok(nhanXetService.add(nhanXetDtoRequest));
    }

    @GetMapping("avg-by-sanpham")
    public ResponseEntity<?> getAvgBySanPham(@RequestParam String maSP){
        return ResponseEntity.ok(nhanXetService.getAvgRatingBySanPham(maSP));
    }

    @GetMapping("avg-rates-by-sanpham")
    public ResponseEntity<?> getAvgRatesByMaSP(@RequestParam String maSP){
        return ResponseEntity.ok(nhanXetEntityManager.getAvgRatesByMaSP(maSP));
    }
}
