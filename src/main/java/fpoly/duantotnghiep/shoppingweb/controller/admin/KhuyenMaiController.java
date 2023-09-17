package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhuyenMaiRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.service.impl.KhuyenMaiServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.SanPhamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${admin.domain}/khuyen-mai")
public class KhuyenMaiController {
    @Autowired
    SanPhamServiceImpl sanPhamService;
    @Autowired
    KhuyenMaiServiceImpl khuyenMaiService;

    @GetMapping("")
    public String hienThi() {
        return "/admin/khuyenMai";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("khuyenMai", new KhuyenMaiRequest());
        model.addAttribute("sanPham", sanPhamService.findAll());
        return "/admin/formKhuyenMai";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("khuyenMai") KhuyenMaiRequest khuyenMaiRequest,
                      @RequestParam("ids") List<String> sanPham) {

        List<SanPhamModel> a = new ArrayList<>();
        for (String ids : sanPham
        ) {
            SanPhamDtoRequest sp = sanPhamService.findDtoRequetsByMa(ids);
            a.add(sp.mapToModel());
        }

        khuyenMaiRequest.setMa(code());
        khuyenMaiRequest.setSanPham(a);
        System.out.println(khuyenMaiRequest.toString());
        khuyenMaiService.save(khuyenMaiRequest);

        return "/admin/KhuyenMai";
    }

    private static String code() {
        final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        final int CODE_LENGTH = 8;

        StringBuilder code = new StringBuilder();

        Random random = new Random();
        int maxIndex = ALLOWED_CHARACTERS.length();

        // Sinh ngẫu nhiên các ký tự cho mã
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(maxIndex);
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }

}
