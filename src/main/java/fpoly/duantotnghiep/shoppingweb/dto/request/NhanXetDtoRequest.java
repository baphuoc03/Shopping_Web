package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.NhanXetModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NhanXetDtoRequest {
    private String id;
    private String khachHang;
    private String sanPham;
    private Float rating;
    private String tieuDe;
    private String noiDung;
    private Date thoiGian;

    public NhanXetModel mapToModel(){
        NhanXetModel model = new NhanXetModel();
        if(id!=null) model.setId(this.id);

        KhachHangModel khachHangModel = new KhachHangModel();
        khachHangModel.setUsername(khachHang);
        model.setKhachHang(khachHangModel);

        SanPhamModel sanPhamModel = new SanPhamModel();
        sanPhamModel.setMa(sanPham);
        model.setSanPham(sanPhamModel);

        model.setRating(this.rating);
        model.setTieuDe(this.tieuDe);
        model.setNoiDung(this.noiDung);
        model.setThoiGian(this.thoiGian);

        return model;
    }
}
