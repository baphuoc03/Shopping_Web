package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.IGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class GioHangRestController {
    @Autowired
    private IGioHangService service;

    @Autowired
    private IChiTietSanPhamRepository serviceCtsp;
    @GetMapping("/find-all")
    public ResponseEntity<List<GioHangDtoReponse>> getCartContents() {
        List<GioHangDtoReponse> cartContents = service.laySpTrongGio();
        return new ResponseEntity<>(cartContents, HttpStatus.OK);
    }

    @PostMapping("add-to-cart/{idCTSP}")
    public List<GioHangDtoReponse> addToCart(@RequestParam("idCTSP")String idCTSP, @RequestParam("sl")Integer sl){
        service.addOrUpdateToCart(idCTSP,sl);
        return service.laySpTrongGio();
    }

}
