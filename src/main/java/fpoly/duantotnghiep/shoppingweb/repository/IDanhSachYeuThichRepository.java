package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.DanhSachYeuThichModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDanhSachYeuThichRepository extends JpaRepository<DanhSachYeuThichModel,String> {
    @Query("""
        delete from DanhSachYeuThichModel d where d.nguoiSoHuu = ?1 and d.sanPham = ?2
""")
    void deleteDanhSachYeuThichKKK(String nguoiSoHuu,String sanPham);

}
