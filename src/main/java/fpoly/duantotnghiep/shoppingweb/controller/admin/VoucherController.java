package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.service.impl.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("${admin.domain}/voucher")
public class VoucherController {
    @Autowired
    VoucherServiceImpl service;

    @GetMapping("")
    public String hienThi(Model model,
                          @RequestParam(defaultValue = "1", name = "pageNumber", required = false) Integer pageNumber)
    {
        Page<VoucherReponse> page = service.findAll(pageNumber-1, 8);
        List<VoucherReponse> listVC = page.getContent();
        model.addAttribute("listVoucher", listVC);
        model.addAttribute("totalPage", page.getTotalPages());
        return "/admin/Voucher";
    }
}
