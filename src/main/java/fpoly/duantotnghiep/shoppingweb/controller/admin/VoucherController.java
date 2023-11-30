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
        List<VoucherReponse> listVC0;
        int totalPage0 = 0;
//        long totalItems0 = 0;
        if (name == null || name.isEmpty()) {
            Page<VoucherReponse> page0 = service.findAll(pageNumber - 1, 5, 0);
            listVC0 = page0.getContent();
            totalPage0 = page0.getTotalPages();
        } else {
            Pageable pageable0 = PageRequest.of(pageNumber - 1, 8);
            Page<VoucherReponse> page0 = service.findByName("%" + name + "%", pageable0);
            listVC0 = page0.getContent();
            totalPage0 = page0.getTotalPages();
            model.addAttribute("name", name);
        }
//        trangj thasi 1
        List<VoucherReponse> listVC1;
        int totalPage1 = 0;
        if (name == null || name.isEmpty()) {
            Page<VoucherReponse> page1 = service.findAll(pageNumber - 1, 5, 1);
            listVC1 = page1.getContent();
            totalPage1 = page1.getTotalPages();
        } else {
            Pageable pageable1 = PageRequest.of(pageNumber - 1, 8);
            Page<VoucherReponse> page1 = service.findByName("%" + name + "%", pageable1);
            listVC1 = page1.getContent();
            totalPage1 = page1.getTotalPages();
            model.addAttribute("name", name);
        }
        model.addAttribute("listVoucher1", listVC1);
        model.addAttribute("totalPage1", totalPage1);
        model.addAttribute("listVoucher0", listVC0);
        model.addAttribute("totalPage0", totalPage0);
        return "/admin/Voucher";
    }

    @GetMapping("/chi-tiet-voucher/{id}")
    public String chiTietVoucher() {
        return "/admin/fromVoucher";
    }

    @GetMapping("/them-voucher")
    public String danhSa3() {
        return "/admin/fromVoucher";
    }
}
