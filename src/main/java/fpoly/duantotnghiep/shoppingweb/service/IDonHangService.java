package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;

import java.util.List;

public interface IDonHangService {
    List<DonHangDtoResponse> getAllByTrangThai(Integer trangThai);

    DonHangDtoResponse findByMa(String ma);

    Boolean existsByMa(String ma);
}
