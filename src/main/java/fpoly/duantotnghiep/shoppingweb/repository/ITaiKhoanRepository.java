package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.TaiKhoanDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.TaiKhoanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ITaiKhoanRepository extends JpaRepository<TaiKhoanModel,String> {

    @Query(value = """
    SELECT new fpoly.duantotnghiep.shoppingweb.dto.reponse.TaiKhoanDtoResponse(t) FROM TaiKhoanModel t
    WHERE t.vaiTro.ma = 'ADMIN'
""")
    List<TaiKhoanDtoResponse> getByRoleAdmin();

    @Query(value = """
    SELECT new fpoly.duantotnghiep.shoppingweb.dto.reponse.TaiKhoanDtoResponse(t) FROM TaiKhoanModel t
    WHERE t.vaiTro.ma <> 'CUST'
""")
    List<TaiKhoanDtoResponse> getTaiKhoanNhanVien();

    @Query(value = """
    SELECT new fpoly.duantotnghiep.shoppingweb.dto.reponse.TaiKhoanDtoResponse(t) FROM TaiKhoanModel t
    WHERE t.vaiTro.ma = 'CUST'
""")
    List<TaiKhoanDtoResponse> getTaiKhoanKhachHang();

}
