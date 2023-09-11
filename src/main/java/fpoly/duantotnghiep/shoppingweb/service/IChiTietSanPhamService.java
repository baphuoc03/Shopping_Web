package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;

import java.util.List;

public interface IChiTietSanPhamService {
    List<ChiTietSanPhamDtoResponse> getAllBySanPhamMa(String maSP);
}
