package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IChiTietSanPhamRepository extends JpaRepository<ChiTietSanPhamModel,String> {
    List<ChiTietSanPhamModel> getAllBySanPhamMaAndTrangThai(String maSP,Boolean trangThai);

    @Query("""
UPDATE ChiTietSanPhamModel s SET s.soLuong = ?1 WHERE s.id = ?2
""")
    ChiTietSanPhamModel updateSoLuong(Long soLuong, String id);

    Boolean existsBySanPhamMaAndSizeMa(String maSP, Float size);

    @Query("""
UPDATE ChiTietSanPhamModel s SET s.trangThai = ?1 WHERE s.id = ?2
""")
    ChiTietSanPhamModel updateTrangThai(Boolean trangThai, String id);

    ChiTietSanPhamModel getBySanPhamMaAndSizeMa(String maSP, Float size);
}
