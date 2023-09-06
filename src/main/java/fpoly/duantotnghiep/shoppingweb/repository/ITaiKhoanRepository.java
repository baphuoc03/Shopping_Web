package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.TaiKhoanDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.TaiKhoanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ITaiKhoanRepository extends JpaRepository<TaiKhoanModel,String> {

    @Query(value = """
    SELECT t.id,v.Ten,t.Username,t.HoVaTen,t.NgaySinh,t.SoDienThoai,t.Email,t.AnhDaiDien FROM shopping_web.TaiKhoan t
    join VaiTro v on t.VaiTro = v.Ma
    WHERE v.Ma = 'ADMIN';
""",nativeQuery = true)
    List<TaiKhoanDtoResponse> getByRoleAdmin();

}
