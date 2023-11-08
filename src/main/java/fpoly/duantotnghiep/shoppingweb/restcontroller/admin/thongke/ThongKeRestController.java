package fpoly.duantotnghiep.shoppingweb.restcontroller.admin.thongke;

import fpoly.duantotnghiep.shoppingweb.entitymanager.SanPhamEntityManager;
import fpoly.duantotnghiep.shoppingweb.entitymanager.ThongKeEntityManager;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${admin.domain}/thong-ke")
public class ThongKeRestController {

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private IDonHangService donHangService;
    @Autowired
    private ThongKeEntityManager thongKeEntityManager;
    @Autowired
    private SanPhamEntityManager sanPhamEntityManager;

    @GetMapping("tong-quat")
    public ResponseEntity<?> getTotalQauntityInOrdersWithDate(@RequestParam(defaultValue = "#{new java.util.Date()}")
                                                              @DateTimeFormat(pattern = "yyyy-MM-dd")Date firstDate,
                                                              @RequestParam(defaultValue = "#{new java.util.Date()}")
                                                              @DateTimeFormat(pattern = "yyyy-MM-dd")Date lastDate){

        lastDate.setHours(23); lastDate.setMinutes(59); lastDate.setSeconds(59);
        firstDate.setHours(00); firstDate.setMinutes(00); firstDate.setSeconds(00);


        Map<String,String> result = new HashMap<>();
        result.put("quantityProducts",donHangService.getTotalQauntityInOrdersWithDate(firstDate,lastDate).toString());
        result.put("quantityOrders",donHangService.getQuantityOrdersWithDate(firstDate,lastDate).toString());
        result.put("totalPrice",donHangService.getTotalPriceInOrdersWithDate(firstDate,lastDate).toString());

        return ResponseEntity.ok(result);
    }

    @GetMapping("tong-quat-nam")
    public ResponseEntity<?> getQuantityOrderByYear(@RequestParam(required = false)String year){
        if(year==null){
            year = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear()+"";
        }

        Map<String,List> result = new HashMap<>();
        result.put("revenue",thongKeEntityManager.getRevenueInOrderByYear(year).entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList()));
        result.put("totalProducts",thongKeEntityManager.getTotalProductsByYear(year).entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList()));
        result.put("quantityOrders",thongKeEntityManager.getQuantityOrderByYear(year).entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList()));
        result.put("months",thongKeEntityManager.getQuantityOrderByYear(year).entrySet().stream().map(m -> "Tháng " + m.getKey()).collect(Collectors.toList()));


        return ResponseEntity.ok(result);
    }

    @GetMapping("san-pham-ban-chay")
    public ResponseEntity<?> getSanPhamBanChay(){
        return ResponseEntity.ok(sanPhamEntityManager.getSanPhamBanChay());
    }

    @GetMapping("san-pham-ton")
    public ResponseEntity<?> getSanPhamTon(){
        return ResponseEntity.ok(sanPhamEntityManager.getSanPhamTon());
    }

}
