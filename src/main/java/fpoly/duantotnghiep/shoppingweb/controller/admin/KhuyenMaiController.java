package fpoly.duantotnghiep.shoppingweb.controller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhuyenMaiResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhuyenMaiRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.KhuyenMaiRepository;
import fpoly.duantotnghiep.shoppingweb.service.impl.KhuyenMaiServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.SanPhamServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
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

    @Autowired
    KhuyenMaiRepository kmTs;


    @GetMapping("")
    public String hienThi(Model model,
                          @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber) {

        Page<KhuyenMaiResponse> page = khuyenMaiService.findAll(pageNumber - 1, 1);

        List<KhuyenMaiResponse> list = page.getContent();

        model.addAttribute("khuyenMai", list);

        model.addAttribute("totalPage", page.getTotalPages());

        return "/admin/khuyenMai";
    }

    @GetMapping("/form-add")
    public String form(Model model) {
        getFormAdd(model,new KhuyenMaiRequest());

        return "/admin/formKhuyenMai";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("khuyenMai") KhuyenMaiRequest khuyenMaiRequest,
                      BindingResult result,
                      Model model,
                      RedirectAttributes redirectAttributes,
                      @RequestParam(value = "ids", required = false) List<String> sanPham) {
        Date date = new Date();


        if (khuyenMaiRequest.getLoai().equals("TIEN")) {
            if (khuyenMaiRequest.getMucGiam().compareTo(new BigDecimal("1000")) < 0) {
                redirectAttributes.addFlashAttribute("mess", "Vui lòng nhập giá tiền lớn hơn 1000");
                System.out.println("check tien");
                result.addError(new FieldError("loai1", "loai1", "tiền phải > "));
                model.addAttribute("loai1","tieefn phari ? 1000");
            }
        }
        if (khuyenMaiRequest.getLoai().equals("PHAN TRAM")) {
            if (khuyenMaiRequest.getMucGiam().compareTo(new BigDecimal("1")) < 0 || khuyenMaiRequest.getMucGiam().compareTo(new BigDecimal("99")) > 0) {
                System.out.println("check phan tra,");
                redirectAttributes.addFlashAttribute("mess", "Vui lòng nhập đúng tỉ lệ phần trăm 1-99 %");
                return "redirect:/admin/khuyen-mai/form-add";
            }
        }

        if (khuyenMaiRequest.getNgayBatDau().after(khuyenMaiRequest.getNgayKetThuc())) {
            redirectAttributes.addFlashAttribute("mess", "Ngày bắt đầu không được lớn hơn ngày thúc kết ");
            System.out.println("ngayBatDau > ngayKetThuc");
            return "redirect:/admin/khuyen-mai/form-add";
//        } else if (khuyenMaiRequest.getNgayBatDau().before(date)) {
//            System.out.println("ngayBat dau trc ngay hiwn tai");
//            return "redirect:/admin/khuyen-mai/form-add";
        }
        if (result.hasErrors()) {
            getFormAdd(model,khuyenMaiRequest);
            System.out.println(result.getFieldErrors().toString());
            return "/admin/formKhuyenMai";
        }
        List<SanPhamModel> sanPhamModel = repository.findByMaIn(sanPham);

        khuyenMaiRequest.setMa(code());

        khuyenMaiRequest.setSanPham(sanPhamModel);

        khuyenMaiService.save(khuyenMaiRequest);

        KhuyenMaiResponse khuyenMai = khuyenMaiService.findById(khuyenMaiRequest.getMa());
        List<SanPhamModel> sanPhamUpdate = khuyenMaiService.findById(khuyenMaiRequest.getMa()).getSanPham();
        updateGiaKhiGiam(khuyenMai, sanPhamUpdate);
        return "redirect:/admin/khuyen-mai";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") String id, Model model) {

        List<String> sanPhamOn = khuyenMaiService.findById(id).getSanPham().stream()
                .map(SanPhamModel::getMa).collect(Collectors.toList());

        List<String> sanPhamKhuyenMai = sanPhamService.findByAllSanPhamWithKM().stream()
                .map(SanPhamModel::getMa).collect(Collectors.toList());

        model.addAttribute("khuyenMai", khuyenMaiService.findById(id));

        model.addAttribute("sanPham", sanPhamService.findAll());

        model.addAttribute("action", "/admin/khuyen-mai/update/" + id);

        model.addAttribute("sanPhamOn", sanPhamOn);

        model.addAttribute("sanPhamKhuyenMai",
                listSanPhamKhuyenMaiById(sanPhamKhuyenMai, sanPhamOn));

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

        KhuyenMaiResponse khuyenMai = khuyenMaiService.findById(khuyenMaiRequest.getMa());

        List<SanPhamModel> sanPhamUpdate = khuyenMaiService.findById(khuyenMaiRequest.getMa()).getSanPham();

        updateGiaKhiGiam(khuyenMai, sanPhamUpdate);

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

    private static List<String> listSanPhamKhuyenMaiById(List<String> list1, List<String> list2) {
        List<String> list = new ArrayList<>();
        HashSet<String> hashSet2 = new HashSet<>(list2);
        for (String element : list1) {
            if (!hashSet2.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    private static BigDecimal giaGiam(String loai, SanPhamModel sanPham, KhuyenMaiResponse khuyenMai) {
        Date ngayHienTai = new Date();
        BigDecimal giaGiam = null;

        if (khuyenMai.getNgayBatDau().after(ngayHienTai)) {
            giaGiam = new BigDecimal("2000000");
        } else {
            if (loai.equals("TIEN")) {
                giaGiam = (sanPham.getGiaBan().subtract(khuyenMai.getMucGiam()));
            } else {
                giaGiam = sanPham.getGiaBan().subtract(
                        sanPham.getGiaBan().multiply(khuyenMai.getMucGiam().divide(new BigDecimal("100"))));
            }
        }
        return giaGiam;
    }

    private void updateGiaKhiGiam(KhuyenMaiResponse khuyenMai, List<SanPhamModel> sanPhamUpdate) {
        for (SanPhamModel sanPhamModel1 : sanPhamUpdate) {
            BigDecimal giaGiam = giaGiam(khuyenMai.getLoai(), sanPhamModel1, khuyenMai);
            sanPhamModel1.setGiaBan(giaGiam);
            sanPhamModel1.setMa(sanPhamModel1.getMa());
            sanPhamService.save1(sanPhamModel1);
        }
    }

    private void getFormAdd(Model model,KhuyenMaiRequest khuyenMaiRequest) {
        model.addAttribute("khuyenMai", khuyenMaiRequest);

        model.addAttribute("sanPham", sanPhamService.findAll());

        model.addAttribute("sanPhamKhuyenMai", sanPhamService.findByAllSanPhamWithKM().stream()
                .map(SanPhamModel::getMa).collect(Collectors.toList()));

        model.addAttribute("action", "/admin/khuyen-mai/add");

        model.addAttribute("sanPhamOn", new ArrayList<>());
    }
}
