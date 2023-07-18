package com.example.asm.controller;

import com.example.asm.entity.KhuyenMai;
import com.example.asm.entity.KhuyenMai_SanPham;
import com.example.asm.entity.SanPham;
import com.example.asm.repository.IKhuyenMaiRepo;
import com.example.asm.repository.IKhuyenMai_SanPhamRepo;
import com.example.asm.repository.ISanPhamRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/khuyen-mai")
public class KhuyenMaiCtrl {
    @Autowired
    private ISanPhamRepo sanPhamRepo;
    @Autowired
    private IKhuyenMaiRepo khuyenMaiRepo;
    @Autowired
    private IKhuyenMai_SanPhamRepo khuyenMai_sanPhamRepo;
    @Autowired
    private HttpServletRequest request;
    @PostMapping("add")
    public String add(@Valid @ModelAttribute("KhuyenMai")KhuyenMai khuyenMai,
                      BindingResult bindingResult,
                      @RequestParam(value = "products",required = false)Integer[] products
                      ){

        if(bindingResult.hasErrors()){
            request.setAttribute("deals",khuyenMaiRepo.findAll(Sort.by(Sort.Direction.DESC,"id")));
            return "thongKe";
        }

        khuyenMaiRepo.save(khuyenMai);
        for (Integer s: products){
            khuyenMai_sanPhamRepo.save(new KhuyenMai_SanPham(null,sanPhamRepo.getReferenceById(s),khuyenMai));
        }
        ProductCtrl.alert = "alert('Thêm khuyến mại thành công')";
        return "redirect:/sandal/thongke";
    }
    @GetMapping("update/{id}")
    public String viewUpdate(@PathVariable("id")Integer idKM){
        request.setAttribute("KhuyenMai",khuyenMaiRepo.getReferenceById(idKM));
        request.setAttribute("products",sanPhamRepo.findAll());
        request.setAttribute("productsOnDeal",khuyenMai_sanPhamRepo.getProductOnKhuyenMaiByMaKM(idKM));
        return "KhuyenMaiViewUpdate";
    }

    @PostMapping("update/{id}")
    @Transactional
    public String update(@PathVariable("id")Integer idKM,
                         @Valid @ModelAttribute("KhuyenMai")KhuyenMai khuyenMai,
                         BindingResult bindingResult,
                         @RequestParam(value = "products",required = false)Integer[] products){



        if(idKM == null || khuyenMaiRepo.getReferenceById(idKM) == null){
            ProductCtrl.alert = "alert('Cập nhật thất bại')";
            return "redirect:/sandal/thongke";
        }else {
            if(bindingResult.hasErrors()){

                request.setAttribute("productsOnDeal",khuyenMai_sanPhamRepo.getProductOnKhuyenMaiByMaKM(idKM));
                request.setAttribute("products",sanPhamRepo.findAll());

                return "KhuyenMaiViewUpdate";
            }
            khuyenMai.setId(idKM);
            khuyenMaiRepo.save(khuyenMai);
            khuyenMai_sanPhamRepo.deleteByKhuyenMai(khuyenMaiRepo.getReferenceById(idKM));
            for (Integer s : products) {
                khuyenMai_sanPhamRepo.save(new KhuyenMai_SanPham(null, sanPhamRepo.getReferenceById(s), khuyenMai));
            }
            ProductCtrl.alert = "alert('Cập nhật khuyến mại thành công')";
            return "redirect:/sandal/thongke";
        }
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id")Integer idKM){
        khuyenMaiRepo.deleteById(idKM);
        ProductCtrl.alert = "alert('Xóa thành công')";
        return "redirect:/sandal/thongke";
    }
}
