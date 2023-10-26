package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.GioHangModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GioHangDtoReponse {
    private String id;
    private String anh;
    private BigDecimal donGia;
    private String tensanPham;
    private Float size;
    private Integer soLuong;

    public GioHangDtoReponse(ChiTietSanPhamModel model, Integer sl) {
        id = model.getId();
        tensanPham = model.getSanPham() == null ? "" : model.getSanPham().getTen();
        donGia = model.getSanPham().getGiaNiemYet();
        size = model.getSize().getMa();
        anh = model.getSanPham().getImages().size() == 0 ? "default.png" : model.getSanPham().getImages().get(0).getTen();
        soLuong = sl;
    }

}
