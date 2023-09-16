package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KhuyenMaiRequest {
    private String ma;

    private String ten;

    private String loai;

    private Double mucGiam;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private Boolean trangThai;

    private Date ngayTao;

    private Date ngayCapNhat;

    private List<SanPhamModel> sanPham;

    public KhuyenMaiModel mapToModel() {
        KhuyenMaiModel model = new KhuyenMaiModel();
        model.setMa(ma);
        model.setTen(ten);
        model.setLoai(loai);
        model.setMucGiam(mucGiam);
        model.setNgayBatDau(ngayBatDau);
        model.setNgayKetThuc(ngayKetThuc);
        model.setTrangThai(trangThai);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        model.setSanPham(sanPham);
        return model;
    }
}
