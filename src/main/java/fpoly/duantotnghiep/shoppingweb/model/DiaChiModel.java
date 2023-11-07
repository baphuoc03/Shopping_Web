package fpoly.duantotnghiep.shoppingweb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diachi")
public class DiaChiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @ManyToOne
    @JoinColumn(name = "Khachhang")
    @JsonBackReference
    private KhachHangModel taiKhoan;


}
