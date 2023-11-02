package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@SessionScope
public interface IChiTietSanPhamRepository extends JpaRepository<ChiTietSanPhamModel, String> {
    List<ChiTietSanPhamModel> getAllBySanPhamMaAndTrangThaiOrderBySizeMa(String maSP, Boolean trangThai);

    @Transactional
    @Modifying
    @Query("""
            UPDATE ChiTietSanPhamModel s SET s.soLuong = ?1 WHERE s.id = ?2
            """)
    int updateSoLuong(Long soLuong, String id);

    Boolean existsBySanPhamMaAndSizeMa(String maSP, Float size);

    @Transactional
    @Modifying
    @Query("""
            UPDATE ChiTietSanPhamModel s SET s.trangThai = ?1 WHERE s.id = ?2
            """)
    int updateTrangThai(Boolean trangThai, String id);

    ChiTietSanPhamModel getBySanPhamMaAndSizeMa(String maSP, Float size);
}
