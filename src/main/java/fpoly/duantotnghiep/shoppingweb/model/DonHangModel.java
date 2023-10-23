package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "donhang")
public class DonHangModel {

    @Id
    @Column(name = "ma")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "Khachhang")
    private KhachHangModel nguoiSoHuu;

    @Column(name = "tennguoinhan")
    private String tenNguoiNhan;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "thanhphoName")
    private String thanhphoName;

    @Column(name = "thanhphoCode")
    private Integer thanhPhoCode;

    @Column(name = "quanhuyenName")
    private String quanHuyenName;

    @Column(name = "quanhuyenCode")
    private Integer quanHuyenCode;

    @Column(name = "xaphuongName")
    private String xaPhuongName;

    @Column(name = "xaphuongCode")
    private String xaPhuongCode;

    @Column(name = "diachichitiet")
    private String diaChiChiTiet;

    @Column(name = "ngaydathang")
    private Date ngayDatHang;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "tiengiam")
    private BigDecimal tienGiam;

    @Column(name = "phigiaohang")
    private BigDecimal phiGiaoHang;

    @OneToMany(mappedBy = "donHang",fetch = FetchType.LAZY)
    private List<ChiTietDonHangModel> danhSachSanPham;

    public String trangThaiDetail(){
        if(trangThai==1){
            return "Đã xác nhận";
        }else if(trangThai == 2){
            return "Chưa xác nhận";
        }else{
            return "null";
        }
    }

}
