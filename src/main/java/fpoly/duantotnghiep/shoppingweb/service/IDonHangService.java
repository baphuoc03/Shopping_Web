package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;

import java.util.List;

public interface IDonHangService {
    List<DonHangDtoResponse> getAllByTrangThai(Integer trangThai);

    void checkOut(DonHangDTORequest donHangDTORequest);

    DonHangDtoResponse findByMa(String ma);

    Boolean existsByMa(String ma);
}
