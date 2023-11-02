package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.GioHangDtoReponse;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.IGioHangService;
import fpoly.duantotnghiep.shoppingweb.util.ValidateUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class GioHangRestController {
    @Autowired
    private IGioHangService service;

    @Autowired
    private IChiTietSanPhamRepository chiTietSanPhamRepository;
    @GetMapping("/find-all")
    public ResponseEntity<List<GioHangDtoReponse>> getCartContents() {
        List<GioHangDtoReponse> cartContents = service.laySpTrongGio();
        return new ResponseEntity<>(cartContents, HttpStatus.OK);
    }

    @PostMapping("add-to-cart")
    public List<GioHangDtoReponse> addToCart(@Valid @RequestParam("idCTSP")String idCTSP,
                                             @RequestParam("sl")Integer sl){
//        if(size.size()==0){
//            result.addError(new FieldError("eSize","eSize","Vui lòng chọn size"));
//            if(!result.hasErrors()) ValidateUtil.getErrors(result);
//        }
        service.addOrUpdateToCart(idCTSP,sl);
        return service.laySpTrongGio();
    }
    @PutMapping("update-sl")
    public List<GioHangDtoReponse> updateSL(@RequestParam("idCTSP")String idCTSP,@RequestParam("sl")Integer sl){
        Long slSanPham = chiTietSanPhamRepository.getReferenceById(idCTSP).getSoLuong();
        System.out.println("sl SP: "+slSanPham+" - SL: "+sl);
        if(slSanPham < sl || sl <= 0) return null;
        service.updateSoLuong(idCTSP,sl);
        return service.laySpTrongGio();
    }
    @DeleteMapping("/remove/{key}")
    public  List<GioHangDtoReponse>removeProductInCart(@PathVariable("key")String idCTSP){
        service.removeProductInCart(idCTSP);
        return service.laySpTrongGio();
    }


}
