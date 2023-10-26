package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IDonHangResponsitory extends JpaRepository<DonHangModel,String> {
    @Query("""
SELECT d FROM DonHangModel d WHERE d.trangThai = ?1 ORDER BY d.ngayDatHang DESC 
""")
    Page<DonHangModel> getAllByTrangThai(Integer trangThai,  Pageable pageable);

    @Transactional
    @Modifying
    @Query("""
            update DonHangModel d set d.trangThai = ?1 WHERE d.ma=?2
            """)
    Integer updateTrangThaiDonHang(int trangThai,String maDonHang);

}