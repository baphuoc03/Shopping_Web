package fpoly.duantotnghiep.shoppingweb.model;

import fpoly.duantotnghiep.shoppingweb.enumtype.KhuyenMaiType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "voucher")
public class VoucherModel {
    @Id
    @Column(name = "ma")
    private String ma;

    @Column(name = "mota")
    private String mota;

    @Column(name = "loaimucgiam")
    private String loaiMucGiam;

    @Column(name = "mucgiam")
    private Double mucGiam;

    @Column(name = "giatridonhang")
    private Double giaTriDonHang;

    @Column(name = "mucgiamtoida")
    private Double mucGiamToiDa;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngaybatdau")
    private Date ngayBatDau;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngayketthuc")
    private Date ngayKetThuc;

    @Column(name = "soluong")
    private int soLuong;

    @Column(name = "trangthaihienthi")
    private Integer trangThaiHienThi;

    @Column(name = "hinhthucthanhtoan")
    private Integer hinhThucThanhToan;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "doituongsudung")
    private Integer doiTuongSuDung;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "voucherkhachhang",
            joinColumns = {@JoinColumn(name = "voucher")},
            inverseJoinColumns = {@JoinColumn(name = "khachhang")})
    private List<KhachHangModel> khachHang;

}
