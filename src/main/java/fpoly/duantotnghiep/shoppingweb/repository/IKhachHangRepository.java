package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IKhachHangRepository extends JpaRepository<KhachHangModel,String> {
@Query("select  kh.danhSachDiaChi from KhachHangModel kh where  kh.username = :taiKhoan")
    List<DiaChiModel> findAllDiaChiByTaiKhoan(@Param("taiKhoan")String taiKhoan);


}
