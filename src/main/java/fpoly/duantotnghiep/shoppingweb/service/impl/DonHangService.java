package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietDonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietSanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietDonHangRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangResponsitory;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietDonHangService;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonHangService implements IDonHangService {

    @Autowired
    private IDonHangResponsitory donHangResponsitory;
    @Autowired
    private IChiTietDonHangService chiTietDonHangService;
    @Autowired
    private IChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    private IChiTietDonHangRepository chiTietDonHangRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public List<DonHangDtoResponse> getAllByTrangThai(Integer trangThai) {
        return null;
    }

    @Override
    public Page<DonHangDtoResponse> getAllByTrangThai(Integer trangThai, Integer limit, Integer pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber,limit);

        Page<DonHangModel> pageModel = donHangResponsitory.getAllByTrangThai(trangThai, pageable);

        return new PageImpl<>(pageModel.getContent().stream().map(d -> new DonHangDtoResponse(d)).collect(Collectors.toList()),
                               pageable, pageModel.getTotalElements());
    }

    @Override
    public DonHangDtoResponse findByMa(String ma) {
        return new DonHangDtoResponse(donHangResponsitory.findById(ma).orElse(new DonHangModel()));
    }

    @Override
    public Boolean existsByMa(String ma){
        return donHangResponsitory.existsById(ma);
    }

    @Override
    public void updateTrangThai(String maDonHang, Integer trangThai) throws MessagingException {
//         donHangResponsitory.updateTrangThaiDonHang(trangThai,maDonHang);
        DonHangModel model = donHangResponsitory.findById(maDonHang).get();
        model.setTrangThai(trangThai);

        String subject = "";
        String messeger = "";
        if(trangThai==2) {
            subject = "Tạo đơn hàng!";
            messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã được tạo. Cám ơn bạn đã mua hàng";
        } else if(trangThai==1){
            subject = "Xác nhận đơn hàng!";
            messeger = "Xin chào " + model.getTenNguoiNhan() + ", đơn hàng của bạn đã được xác nhận. Cám ơn bạn đã mua hàng. Đơn hàng sẽ đến tay bạn trong vài ngày tới";
        }

        List<ChiTietDonHangDtoResponse> lstSanPham = chiTietDonHangService.getByDonHang(maDonHang);
        BigDecimal tongTien = BigDecimal.valueOf(0);
        for (ChiTietDonHangDtoResponse d: lstSanPham) {
            tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
        }

        Context context = new Context();
        context.setVariable("donHang",new DonHangDtoResponse(model));
        context.setVariable("products",lstSanPham);
        context.setVariable("totalPrice",tongTien);
        context.setVariable("mess",messeger);
        String finalSubject = subject;
        new Thread(()->{
            try {
                sendEmailDonHang(model.getEmail(), finalSubject,"email/capNhatTrangThaiDonHang",context,lstSanPham);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();


        donHangResponsitory.saveAndFlush(model);
    }

    @Override
    public void huyDonHang(String maDonHang) throws MessagingException {
//         donHangResponsitory.updateTrangThaiDonHang(trangThai,maDonHang);
        DonHangModel model = donHangResponsitory.findById(maDonHang).get();
        model.setTrangThai(0);

        String subject = "Hủy đơn hàng!";
        String messeger = "Xin chào "+model.getTenNguoiNhan()+", đơn hàng của bạn đã hủy. Cám ơn bạn đã ghé qua cửa hàng";

        List<ChiTietDonHangModel> ctdhModel = chiTietDonHangRepository.findAllByDonHang(model);
        ctdhModel.forEach(c -> {
            int soLuongInDonHang = c.getSoLuong();
            ChiTietSanPhamModel sanPhamInDonHang = chiTietSanPhamRepository.findById(c.getChiTietSanPham().getId()).get();
            sanPhamInDonHang.setSoLuong(soLuongInDonHang+sanPhamInDonHang.getSoLuong());
            chiTietSanPhamRepository.save(sanPhamInDonHang);
        });


        List<ChiTietDonHangDtoResponse> lstSanPham = ctdhModel.stream().map(m->new ChiTietDonHangDtoResponse(m)).collect(Collectors.toList());
        BigDecimal tongTien = BigDecimal.valueOf(0);
        for (ChiTietDonHangDtoResponse d: lstSanPham) {
            tongTien = tongTien.add(d.getDonGiaSauGiam().multiply(BigDecimal.valueOf(d.getSoLuong())));
        }

        Context context = new Context();
        context.setVariable("donHang",new DonHangDtoResponse(model));
        context.setVariable("products",lstSanPham);
        context.setVariable("totalPrice",tongTien);
        context.setVariable("mess",messeger);
        new Thread(()->{
            try {
                sendEmailDonHang(model.getEmail(), subject,"email/capNhatTrangThaiDonHang",context,lstSanPham);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();

        donHangResponsitory.saveAndFlush(model);
    }

    public void sendEmailDonHang(String email, String subject, String tempalteHtml, Context context, List<ChiTietDonHangDtoResponse> lstSanPham) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        helper.setTo(email);
        helper.setSubject(subject);
        String htmlContent = templateEngine.process(tempalteHtml,context);
        helper.setText(htmlContent,true);

        ClassPathResource resource = new ClassPathResource("./images/product/default.png");
        helper.addInline("logo",resource);

        lstSanPham.forEach(s -> {
            ClassPathResource img = new ClassPathResource("./images/product/"+s.getAnh());
            try {
                helper.addInline(s.getAnh()+"",img);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        javaMailSender.send(mimeMessage);
    }
}