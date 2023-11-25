package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Data
public class VoucherRequest {
    private String ma;

    @NotEmpty(message = "Vui lòng nhập dữ liệu")
    private String moTa;

    private String loaiMucGiam;

    @NotNull(message = "Vui lòng nhập dữ liệu")
    private Double mucGiam;

    @NotNull(message = "Vui lòng nhập dữ liệu")
    private Double giaTriDonHang;

    private Double mucGiamToiDa;

    @NotNull(message = "Vui lòng nhập dữ liệu")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayBatDau;

    @NotNull(message = "Vui lòng nhập dữ liệu")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayKetThuc;

    private int soLuong;

    private int trangThai;

    private int trangThaiHienThi;

    private int doiTuongSuDung;

    @NotNull(message = "Vui lòng nhập dữ liệu")
    private int hinhThucThanhToan;

    private List<KhachHangModel> khachHang;


    public VoucherModel maptoModel() {
        VoucherModel model = new VoucherModel();
        model.setMa(ma);
        model.setMota(moTa);
        model.setLoaiMucGiam(loaiMucGiam);
        model.setMucGiam(mucGiam);
        model.setGiaTriDonHang(giaTriDonHang);
        model.setNgayBatDau(ngayBatDau);
        model.setNgayKetThuc(ngayKetThuc);
        model.setSoLuong(soLuong);
        model.setMucGiamToiDa(mucGiamToiDa);
        model.setHinhThucThanhToan(hinhThucThanhToan);
        model.setTrangThai(trangThai);
        model.setTrangThaiHienThi(trangThaiHienThi);
        model.setKhachHang(khachHang);
        model.setDoiTuongSuDung(doiTuongSuDung);
        return model;
    }
}
