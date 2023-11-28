package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.VoucherRequest;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.repository.VoucherRepository;
import fpoly.duantotnghiep.shoppingweb.service.VoucherService;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository repository;
    @Autowired
    private IKhachHangRepository khachHangRepository;

    @Override
    public List<VoucherReponse> voucherInKhachHang(String username) {
//       List<VoucherReponse> m = repository.voucherInKhachHang(username).stream()
//                .map(c -> new VoucherReponse(c)).collect(Collectors.toList());
        return null;

    }

    @Override
    public List<VoucherReponse> findVoucherSort(String sort) {
//        if ("tenDes".equals(sort)) {
//            return repository.findAllByOrderByTenDesc().stream().map(c -> new VoucherReponse(c)).collect(Collectors.toList());
//        } else if ("tenAsc".equals(sort)) {
//            return repository.findAllByOrderByTenAsc().stream().map(c -> new VoucherReponse(c)).collect(Collectors.toList());
//        } else if ("mucGiamDes".equals(sort)) {
//            return repository.findAllByOrderByMucGiamDesc().stream().map(c -> new VoucherReponse(c)).collect(Collectors.toList());
//        } else if ("mucGiamAsc".equals(sort)) {
//            return repository.findAllByOrderByMucGiamAsc().stream().map(c -> new VoucherReponse(c)).collect(Collectors.toList());
//        } else if ("ngayBDDes".equals(sort)) {
//            return repository.findAllByOrderByNgayBatDauDesc().stream().map(c -> new VoucherReponse(c)).collect(Collectors.toList());
//        } else if ("ngayBDAsc".equals(sort)) {
//            return repository.findAllByOrderByNgayBatDauAsc().stream().map(c -> new VoucherReponse(c)).collect(Collectors.toList());
//        } else if ("ngayBDDes".equals(sort)) {
//            return repository.findAllByOrderByNgayBatDauDesc().stream().map(c -> new VoucherReponse(c)).collect(Collectors.toList());
//        } else if ("soLuongDes".equals(sort)) {
//            return repository.findAllByOrderBySoLuongDesc().stream().map(c -> new VoucherReponse(c)).collect(Collectors.toList());
//        } else if ("soLuongAsc".equals(sort)) {
//            return repository.findAllByOrderBySoLuongAsc().stream().map(c -> new VoucherReponse(c)).collect(Collectors.toList());
//        } else if("giamAsc".equals("")){
//
//        }
        return null;
    }

    @Override
    public List<VoucherReponse> voucherEligible() {
        return repository.findVoucherHienThi().stream()
                .map(c -> new VoucherReponse(c)).collect(Collectors.toList());
    }

    @Override
    public Page<VoucherReponse> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<VoucherModel> pageModel = repository.findAll(pageable);
        return pageModel.map(x -> new VoucherReponse(x));
    }

    @Override
    public List<VoucherReponse> findAll() {
        return repository.findAll().stream().map(x -> new VoucherReponse(x)).collect(Collectors.toList());
    }

    @Override
    public Page<VoucherReponse> findByName(String keyword, Pageable pageable) {
        Page<VoucherModel> page = repository.findByMotaLike(keyword, pageable);
        return page.map(x -> new VoucherReponse(x));
    }

    @Override
    public VoucherReponse findById(String id) {
        VoucherModel getById = repository.findById(id).get();
        return new VoucherReponse(getById);
    }

    @Override
    public void updateTrangThai(int trangThai, String id) {
        this.repository.updateTrangThai(trangThai, id);
    }

    @Override
    public VoucherModel findById1(String id) {
        return repository.findById(id).get();
    }

    @Override
    public VoucherReponse addVoucher(VoucherRequest voucher) {
        VoucherModel voucherModel = repository.save(voucher.maptoModel());
        if (voucher.getTrangThai() == 0) {
            if (voucherModel.getKhachHang() != null) {
                for (var mail : voucherModel.getKhachHang()) {
                    Context context = new Context();
                    context.setVariable("voucher", voucher);
                    new Thread(() -> {
                        try {
                            EmailUtil.sendEmailWithHtml(mail.getEmail(), "HYDRA SNEAKER tặng bạn voucher giảm giá", "email/voucherTang", context);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }).start();

                }
            }
        }
        return new VoucherReponse(voucherModel);
    }

    @Override
    public void deleteVoucher(String id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exitst(String id) {
        boolean exitst = repository.existsById(id);
        return exitst;
    }

    @Override
    public void deleteVouchers(List<String> ids) {
        for (String id : ids) {
            repository.deleteById(id);
        }
    }

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void updateTrangThai() {
        for (var vc : repository.findAll()) {
            if (vc.getNgayKetThuc().after(new Date())) {
                repository.updateTrangThai(2, vc.getMa());
            }
        }
        System.out.println("oke oke ");

    }

    @Override
    public void upddateSoLuong(int soLuong, String ma) {
        this.repository.updateSoLuong(soLuong, ma);
    }

    @Override
    public List<KhachHangModel> findByUserNameIn(List<String> maKhachHang) {
        return khachHangRepository.findByUsernameIn(maKhachHang);
    }
}
