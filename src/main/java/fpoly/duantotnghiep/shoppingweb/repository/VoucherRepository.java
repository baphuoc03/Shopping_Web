package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoucherRepository extends JpaRepository<VoucherModel, String> {
    Page<VoucherModel> findByTenLike(String ten, Pageable pageable);

    @Query("SELECT vc  FROM VoucherModel vc where vc.giaTriToiThieu >= :sumTotalBill")
    List<VoucherModel> disabledVoucher(@Param("sumTotalBill") Double sumTotalBill);
}
