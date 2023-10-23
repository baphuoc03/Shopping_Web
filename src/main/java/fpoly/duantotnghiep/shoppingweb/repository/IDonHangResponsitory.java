package fpoly.duantotnghiep.shoppingweb.repository;

import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IDonHangResponsitory extends JpaRepository<DonHangModel,String> {
    List<DonHangModel> getAllByTrangThai(Integer trangThai);
}
