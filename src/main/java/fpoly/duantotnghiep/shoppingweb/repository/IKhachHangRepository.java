package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface IKhachHangRepository extends JpaRepository<KhachHangModel,String> {



}
