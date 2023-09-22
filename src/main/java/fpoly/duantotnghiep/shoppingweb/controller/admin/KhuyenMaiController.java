package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhuyenMaiResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhuyenMaiRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.impl.KhuyenMaiServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.SanPhamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    ISanPhamRepository repository;
    @Autowired
    SanPhamServiceImpl sanPhamService;
    @Autowired
    KhuyenMaiServiceImpl khuyenMaiService;

    @GetMapping("")
    public String hienThi(Model model,
                          @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber) {
        Page<KhuyenMaiResponse> page = khuyenMaiService.findAll(pageNumber - 1, 5);
        List<KhuyenMaiResponse> list = page.getContent();
        String trangThai = "";
        model.addAttribute("khuyenMai", list);
        return "/admin/khuyenMai";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("khuyenMai", new KhuyenMaiRequest());
        model.addAttribute("sanPham", sanPhamService.findAll());
        model.addAttribute("action", "/admin/khuyen-mai/add");
        return "/admin/formKhuyenMai";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("khuyenMai") KhuyenMaiRequest khuyenMaiRequest,
                      @RequestParam(value = "ids", required = false) List<String> sanPham) {
        List<SanPhamModel> sanPhamModel = repository.findByMaIn(sanPham);
        khuyenMaiRequest.setMa(code());
        khuyenMaiRequest.setSanPham(sanPhamModel);
        System.out.println(khuyenMaiRequest.toString());
        khuyenMaiService.save(khuyenMaiRequest);
        return "redirect:/admin/khuyen-mai";

    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") String id, Model model) {
        model.addAttribute("khuyenMai", khuyenMaiService.findById(id));
        model.addAttribute("sanPham", sanPhamService.findAll());
        model.addAttribute("action", "/admin/khuyen-mai/update/" + id);
        khuyenMaiService.findById(id).getSanPham().forEach(System.out::println);
        model.addAttribute("sp1", khuyenMaiService.findById(id).getSanPham().stream()
                                                .map(SanPhamModel::getMa).collect(Collectors.toList()));

        return "/admin/formKhuyenMai";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("khuyenMai") KhuyenMaiRequest khuyenMaiRequest,
                         @RequestParam("ids") List<String> sanPham) {

        khuyenMaiRequest.setMa(id);
        List<SanPhamModel> sanPhamModel = repository.findByMaIn(sanPham);
        khuyenMaiRequest.setSanPham(sanPhamModel);
        khuyenMaiService.save(khuyenMaiRequest);
        return "redirect:/admin/khuyen-mai";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        khuyenMaiService.delete(id);
        return "redirect:/admin/khuyen-mai";
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
