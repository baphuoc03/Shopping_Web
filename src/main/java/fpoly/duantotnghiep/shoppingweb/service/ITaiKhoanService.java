package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.TaiKhoanDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.TaiKhoanModel;

import java.util.List;

public interface ITaiKhoanService {
    List<TaiKhoanDtoResponse> getByRoleAdmin();

    TaiKhoanModel findById(String id);

    TaiKhoanDtoResponse getDtoResponseById(String id);

    List<TaiKhoanDtoResponse> getTaiKhoanNhanVien();

    List<TaiKhoanDtoResponse> getTaiKhoanKhachHang();
}
