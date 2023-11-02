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

    @Column(name = "thanhphocode")
    private Integer thanhPhoCode;

    @Column(name = "quanhuyencode")
    private Integer quanHuyenCode;

    @Column(name = "xaphuongcode")
    private String xaPhuongCode;

    @Column(name = "thanhphoname")
    private Integer thanhPhoName;

    @Column(name = "quanhuyenname")
    private Integer quanHuyenName;

    @Column(name = "xaphuongname")
    private String xaPhuongName;

    @Column(name = "diachichitiet")
    private String diaChiChiTiet;

    @ManyToOne
    @JoinColumn(name = "Khachhang")
    @JsonBackReference
    private KhachHangModel taiKhoan;


}
