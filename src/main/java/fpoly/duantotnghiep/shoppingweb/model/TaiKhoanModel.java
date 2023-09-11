package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;
import java.util.List;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "taikhoan")
public class TaiKhoanModel {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "vaitro")
    private VaiTroModel vaiTro;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "hovaten")
    private String hoVaTen;

    @Column(name = "gioitinh")
    private Boolean gioiTinh;

    @Column(name = "ngaysinh")
    private LocalDate ngaySinh;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "anhdaidien")
    private String anhDaiDien;

    @OneToMany(mappedBy = "taiKhoan",fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DiaChiModel> danhSachDiaChi;

    @OneToMany(mappedBy = "nguoiSoHuu",fetch = FetchType.LAZY)
    private List<DonHangModel> danhSachDonHang;

    @ManyToMany
    @JoinTable(name = "voucher_taikhoan",
            joinColumns = { @JoinColumn(name = "voucher") },
            inverseJoinColumns = { @JoinColumn(name = "taikhoan") })
    private Set<VoucherModel> vouchers;


}