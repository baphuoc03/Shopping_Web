package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("${admin.domain}/voucher")
public class VoucherController {
    @Autowired
    VoucherServiceImpl service;

    @GetMapping("")
    public String hienThi(Model model,
                          @RequestParam(name = "name", required = false) String name,
                          @RequestParam(defaultValue = "1", name = "pageNumber", required = false) Integer pageNumber) {
        List<VoucherReponse> listVC;
        int totalPage = 0;
        long totalItems = 0;
        if (name == null || name.isEmpty()) {
            Page<VoucherReponse> page = service.findAll(pageNumber - 1, 5);
            listVC = page.getContent();
            totalPage = page.getTotalPages();
        } else {
            Pageable pageable = PageRequest.of(pageNumber - 1, 8);
            Page<VoucherReponse> page = service.findByName("%" + name + "%", pageable);
            listVC = page.getContent();
            totalPage = page.getTotalPages();
            model.addAttribute("name", name);
        }
        model.addAttribute("listVoucher", listVC);
        model.addAttribute("totalPage", totalPage);
        return "/admin/Voucher";
    }
    @GetMapping("/chi-tiet-voucher/{id}")
    public String chiTietVoucher(@PathVariable String id) {
        return "/admin/formVoucher";
    }
    @GetMapping("/them-voucher")
    public String danhSa3() {
        return "/admin/fromVoucher";
    }
}
