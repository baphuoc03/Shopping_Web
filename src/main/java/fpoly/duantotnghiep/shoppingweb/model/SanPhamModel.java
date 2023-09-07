package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "sanpham")
public class SanPhamModel {
    @Id
    @Column(name = "ma")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "mausac")
    private MauSacModel mauSac;

    @ManyToOne
    @JoinColumn(name = "dongsp")
    private DongSanPhamModel dongSanPham;

    @ManyToOne
    @JoinColumn(name = "kieudang")
    private KieuDangModel kieuDang;

    @ManyToOne
    @JoinColumn(name = "chatlieu")
    private ChatLieuModel chatLieu;

    @Column(name = "ten")
    private String ten;

    @Column(name = "gianhap")
    private BigDecimal giaNhap;

    @Column(name = "giaban")
    private BigDecimal giaBan;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    private Date ngayCapNhat;

    @Column(name = "hienthi")
    private Boolean hienThi;

    @Column(name = "trangthai")
    private Boolean trangThai;

    @OneToMany(mappedBy = "sanPham",fetch = FetchType.EAGER)
    private Set<AnhModel> Images;

    @OneToMany(mappedBy = "sanPham",fetch = FetchType.EAGER)
    private List<NhanXetModel> nhanXet;

}
