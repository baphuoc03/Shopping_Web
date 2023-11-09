package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDonHangRepository extends JpaRepository<DonHangModel, String> {
    @Query("SELECT dh FROM DonHangModel dh where dh.nguoiSoHuu.username = ?1 and dh.trangThai = ?2")
    List<SanPhamModel> findAllByKhachHangAndTrangThai(String nguoiSoHuu, Integer trangThai);
}
