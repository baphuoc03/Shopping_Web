package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ISanPhamRepository extends JpaRepository<SanPhamModel, String> {

    @Transactional
    @Modifying
    @Query("""
            update SanPhamModel s SET s.hienThi = ?1 where s.ma = ?2
            """)
    Integer updateTrangThaiHIenThi(Boolean trangThai, String ma);

    @Query("""
            update SanPhamModel s SET s.giaBan = ?1 where s.ma = ?2
            """)
    Integer updateGiaBan(BigDecimal giaBan, String ma);

    List<SanPhamModel> findByMaIn(List<String> ma);

    @Query("SELECT km.sanPham  FROM KhuyenMaiModel km")
    List<SanPhamModel> findAllSanPhamWithKhuyenMai();

    @Query("SELECT km.sanPham  FROM KhuyenMaiModel km where km.ngayBatDau > current_date")
    List<SanPhamModel> findAllSanPhamWithKmWhereNgayBatDau();

}
