package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VoucherRepository extends JpaRepository<VoucherModel, String> {
    Page<VoucherModel> findByMotaLike(String ten, Pageable pageable);

//    @Query("SELECT kh  FROM KhachHangModel kh where kh.username = ?1")
//    List<VoucherModel> voucherInKhachHang(String khachHang);

    @Query("SELECT vc FROM VoucherModel vc WHERE vc.trangThai = 0 AND vc.doiTuongSuDung = 0 AND (CASE WHEN vc.doiTuongSuDung = 0 THEN vc.soLuong > 0 ELSE true END)")
    List<VoucherModel> findVoucherHienThi();


    @Query("SELECT vc  FROM VoucherModel vc where current_date not between vc.ngayBatDau  and vc.ngayKetThuc")
    List<VoucherModel> findVoucherUpdateTrangThai();


    @Transactional
    @Modifying
    @Query("""
            UPDATE VoucherModel v SET v.soLuong = ?1 WHERE v.ma = ?2
            """)
    int updateSoLuong(int soLuong, String id);

    @Transactional
    @Modifying
    @Query("""
            UPDATE VoucherModel v SET v.trangThai = ?1 WHERE v.ma = ?2
            """)
    int updateTrangThai(int trangThai, String id);


    List<VoucherModel> findAllByOrderByMucGiamAsc();

    List<VoucherModel> findAllByOrderByMucGiamDesc();

    List<VoucherModel> findAllByOrderByNgayBatDauAsc();

    List<VoucherModel> findAllByOrderByNgayBatDauDesc();

    List<VoucherModel> findAllByOrderBySoLuongDesc();

    List<VoucherModel> findAllByOrderBySoLuongAsc();


}
