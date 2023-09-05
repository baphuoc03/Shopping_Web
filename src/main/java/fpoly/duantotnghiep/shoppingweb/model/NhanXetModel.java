package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "nhanxet")
public class NhanXetModel {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "taikhoan")
    private TaiKhoanModel taiKhoan;

    @ManyToOne
    @JoinColumn(name = "SanPham")
    private SanPhamModel sanPham;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "tieude")
    private String tieuDe;

    @Column(name = "NoiDung")
    private String noiDung;

    @Column(name = "thoigian")
    private Date thoiGian;

}
