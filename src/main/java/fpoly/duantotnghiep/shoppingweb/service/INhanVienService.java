package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;

import java.util.List;

public interface INhanVienService {
    List<NhanVienDtoResponse> getAll();

    NhanVienDtoResponse findById(String username);

    Boolean existsByUsername(String username);
}
