package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

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
    public String add(@Valid @ModelAttribute SanPhamDtoRequest sanPham, BindingResult result,
                      @RequestParam("img") List<MultipartFile> file) throws IOException {

        if(result.hasErrors()){
            return "test";
        }

        sanPham.setAnh(file);
        sanPhamService.save(sanPham);

        return "redirect:/view-all";
    }
}
