package fpoly.duantotnghiep.shoppingweb.model;

import fpoly.duantotnghiep.shoppingweb.enumtype.KhuyenMaiType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "khuyenmai")
public class KhuyenMaiModel {
    @Id
    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "loai")
    private String loai;

    @Column(name = "mucgiam")
    private Double mucGiam;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngaybatdau")
    private Date ngayBatDau;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngayketthuc")
    private Date ngayKetThuc;

    @Column(name = "trangthai")
    private Boolean trangThai;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    private Date ngayCapNhat;
    @ManyToMany
    @JoinTable(name = "khuyenmai_sanpham",
            joinColumns = {@JoinColumn(name = "khuyenmai")},
            inverseJoinColumns = {@JoinColumn(name = "sanpham")})
    private List<SanPhamModel> sanPham;

}
