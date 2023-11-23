package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.VoucherRequest;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import fpoly.duantotnghiep.shoppingweb.repository.VoucherRepository;
import fpoly.duantotnghiep.shoppingweb.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository repository;


    @Override
    public List<VoucherReponse> disabledVoucher(Double sumTotalBill) {
        return repository.disabledVoucher(sumTotalBill).stream()
                .map(c -> new VoucherReponse(c)).collect(Collectors.toList());
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
        return repository.findVoucherEligible().stream()
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
    public VoucherReponse addVoucher(VoucherRequest voucher) {
        VoucherModel voucherModel = repository.save(voucher.maptoModel());
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
    public void upddateSoLuong(int soLuong, String ma) {
        this.repository.updateSoLuong(soLuong, ma);
    }
}
