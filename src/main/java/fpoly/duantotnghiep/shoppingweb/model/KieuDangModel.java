package fpoly.duantotnghiep.shoppingweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "kieudang")
public class KieuDangModel {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    private Date ngayCapNhat;

    public KieuDangModel(String id) {
        this.id = id;
    }
}
