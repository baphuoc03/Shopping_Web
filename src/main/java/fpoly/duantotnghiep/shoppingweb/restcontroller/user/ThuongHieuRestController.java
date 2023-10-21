package fpoly.duantotnghiep.shoppingweb.restcontroller.user;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ThuongHieuDtoResponse;
import fpoly.duantotnghiep.shoppingweb.service.IThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("thuong-hieu-user")
@RequestMapping("thuong-hieu")
public class ThuongHieuRestController {
    @Autowired
    private IThuongHieuService service;
    @GetMapping("/find-all")
    public List<ThuongHieuDtoResponse> findAll(){
        return service.findAll();
    }
}
