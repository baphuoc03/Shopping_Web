package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;

import java.util.List;

public interface IDonHangService {
    List<DonHangDtoResponse> getAllByTrangThai(Integer trangThai);

    Page<DonHangDtoResponse> getAllByTrangThai(Integer trangThai, Integer limit, Integer pageNumber);

    void checkOut(DonHangDTORequest donHangDTORequest);

    DonHangDtoResponse findByMa(String ma);

    Boolean existsByMa(String ma);

    void updateTrangThai(String maDonHang, Integer trangThai) throws MessagingException;

//    void huyDonHang(String maDonHang, String lyDo) throws MessagingException;

//    DonHangDtoResponse updateDonHang(DonHangDTORequest request);

    void huyDonHang(List<String> maDonHang, String lyDo) throws MessagingException;

    DonHangDtoResponse updateDonHang(DonHangDTORequest request, List<ChiTietDonHangDTORequest> products);
}
