package fpoly.duantotnghiep.shoppingweb.model;

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

    @Column(name = "thanhpho")
    private Integer thanhPho;

    @Column(name = "quanhuyen")
    private Integer quanHuyen;

    @Column(name = "xaphuong")
    private String xaPhuong;

    @Column(name = "diachichitiet")
    private String diaChiChiTiet;

    @ManyToOne
    @JoinColumn(name = "taikhoan")
    private TaiKhoanModel taiKhoan;


}
