package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IChiTietSanPhamRepository extends JpaRepository<ChiTietSanPhamModel,String> {
    List<ChiTietSanPhamModel> getAllBySanPhamMa(String maSP);
}
