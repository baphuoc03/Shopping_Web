package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietDonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDonHangDTORequest {
    private String donHangID;
    private String sanPhamCT;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal donGiaSauGiam;

    public ChiTietDonHangModel mapModel() {
        ChiTietDonHangModel model = new ChiTietDonHangModel();
        ChiTietSanPhamModel chiTietSanPhamModel = new ChiTietSanPhamModel();
        DonHangModel donhang = new DonHangModel();
        donhang.setMa(donHangID);
        chiTietSanPhamModel.setId(sanPhamCT);
        model.setDonHang(donhang);
        model.setSoLuong(soLuong);
        model.setChiTietSanPham(chiTietSanPhamModel);
        model.setDonGia(donGia);
        model.setDonGiaSauGiam(donGiaSauGiam);
        return model;
    }
}
