package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietDonHangDTORequest;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IDonHangService {
    List<DonHangDtoResponse> getAllByTrangThai(Integer trangThai);

    Page<DonHangDtoResponse> getAllByTrangThai(Integer trangThai, Integer limit, Integer pageNumber);
    DonHangDtoResponse checkOut(DonHangDTORequest donHangDTORequest);

    DonHangDtoResponse findByMa(String ma);

    Boolean existsByMa(String ma);

    void updateTrangThai(String maDonHang, Integer trangThai) throws MessagingException;

//    void huyDonHang(String maDonHang, String lyDo) throws MessagingException;

//    DonHangDtoResponse updateDonHang(DonHangDTORequest request);

    void huyDonHang(List<String> maDonHang, String lyDo) throws MessagingException;

    DonHangDtoResponse updateDonHang(DonHangDTORequest request, List<ChiTietDonHangDTORequest> products);

    @Query("""
    SELECT SUM(c.soLuong) FROM ChiTietDonHangModel c 
    WHERE c.donHang.ngayDatHang in (?1,?2)
""")
    Long getTotalQauntityInOrdersWithDate(Date firstDate, Date lastDate);

    Long getQuantityOrdersWithDate(Date firstDate, Date lastDate);

    BigDecimal getTotalPriceInOrdersWithDate(Date firstDate, Date lastDate);
}
