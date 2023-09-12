package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface sizerepo extends JpaRepository<SizeModel,Float> {
    @Query("""
SELECT c.size FROM ChiTietSanPhamModel c 
WHERE c.sanPham.ma <> ?1
""")
    List<SizeModel> getAllNotInSanPham(String maSP);
}
