package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${admin.domain}/product")
public class SanPhamController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISanPhamService sanPhamService;

    @GetMapping("view-add")
    public String viewAdd(@ModelAttribute("sanPham")SanPhamDtoRequest sanPham){
//        request.setAttribute("sanPham", new SanPhamDtoRequest());
        request.setAttribute("method", "add");
        return "test";
    }
    @PostMapping("add")
    public String add(@Valid @ModelAttribute SanPhamDtoRequest sanPham, BindingResult result){

        if(result.hasErrors()){
            return "test";
        }

        sanPhamService.save(sanPham);

        return "redirect:/view-all";
    }
}
