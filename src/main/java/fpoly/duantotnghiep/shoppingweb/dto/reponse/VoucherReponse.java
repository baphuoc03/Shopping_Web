package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class VoucherReponse {

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


    public VoucherReponse(VoucherModel model) {
        this.ma = model.getMa();
        this.ten = model.getTen();
        this.loai = model.getLoai();
        this.mucGiam = model.getMucGiam();
        this.giaTriToiThieu = model.getGiaTriToiThieu();
        this.ngayBatDau = model.getNgayBatDau();
        this.ngayKetThuc = model.getNgayKetThuc();
        this.soLuong = model.getSoLuong();
        this.mucGiamToiDa = model.getMucGiamToiDa();
    }
}
