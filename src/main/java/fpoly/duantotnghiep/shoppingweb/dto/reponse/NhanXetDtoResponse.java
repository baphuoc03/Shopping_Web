package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.NhanXetModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NhanXetDtoResponse {
    private String id;
    private ChiTietSanPhamDtoResponse chiTietSanPhamDtoResponse;
    private KhachHangDtoResponse khachHang;
    private Float rating;
    private String tieuDe;
    private String noiDung;
    private Date thoiGian;
    private Boolean pheDuyet;

    public NhanXetDtoResponse(NhanXetModel model) {
        this.id = model.getId();
        this.chiTietSanPhamDtoResponse = new ChiTietSanPhamDtoResponse(model.getChiTietDonHangModel().getChiTietSanPham());
        this.khachHang = new KhachHangDtoResponse(model.getChiTietDonHangModel().getDonHang().getNguoiSoHuu());
        this.rating = model.getRating();
        this.tieuDe = model.getTieuDe();
        this.noiDung = model.getNoiDung();
        this.thoiGian = model.getThoiGian();
        this.pheDuyet = model.getPheDuyet();
    }
}
