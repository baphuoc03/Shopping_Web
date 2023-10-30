package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.VoucherRequest;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoucherService {
    public List<VoucherReponse> disabledVoucher(Double sumTotalBill);

    public Page<VoucherReponse> findAll(int pageNumber, int pageSize);

    public List<VoucherReponse> findAll();

    public Page<VoucherReponse> findByName(String keyword, Pageable pageable);

    public VoucherReponse findById(String id);

    public VoucherReponse addVoucher(VoucherRequest voucher);

    public void deleteVoucher(String id);

    public boolean exitst(String id);

    public void deleteVouchers(List<String> ids);

}
