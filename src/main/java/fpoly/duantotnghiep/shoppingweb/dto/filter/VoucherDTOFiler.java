package fpoly.duantotnghiep.shoppingweb.dto.filter;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
public class VoucherDTOFiler {
    private String ma;
    private BigDecimal mucGiam;
    private BigDecimal mucGiamMax;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayBatDau;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayKetThuc;
    private Integer hinhThucThanhToan;
//    private String loaiMucGiam;
    private Integer trangThai;
    private Integer doiTuongSuDung;
    private Integer sort;
}
