package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class VoucherRequest {
    private String ma;

    private String ten;

    private String loai;

    private Double mucGiam;

    private BigDecimal giaTriToiThieu;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayBatDau;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayKetThuc;

    private Long soLuong;

    private Long mucGiamToiDa;


    public VoucherModel maptoModel() {
        VoucherModel model = new VoucherModel();
        model.setMa(ma);
        model.setTen(ten);
        model.setLoai(loai);
        model.setMucGiam(mucGiam);
        model.setGiaTriToiThieu(giaTriToiThieu);
        model.setNgayBatDau(ngayBatDau);
        model.setNgayKetThuc(ngayKetThuc);
        model.setSoLuong(soLuong);
        model.setMucGiamToiDa(mucGiamToiDa);
        return model;
    }
}
