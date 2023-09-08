package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.AnhModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SanPhamDtoResponse {
    private String ma;
    private String ten;
    private String mauSac;
    private String dongSanPham;
    private String kieuDang;
    private String chatLieu;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private String moTa;
    private Date ngayTao;
    private Date ngayCapNhat;
    private Boolean hienThi;
    private Set<AnhDtoResponse> anh;

    public SanPhamDtoResponse(SanPhamModel model){
        ma = model.getMa();
        ten = model.getTen();
        mauSac = model.getMauSac() == null ? "" : model.getMauSac().getTen();
        dongSanPham = model.getDongSanPham()== null ? "" : model.getDongSanPham().getTen();
        kieuDang = model.getKieuDang()== null ? "" : model.getKieuDang().getTen();
        chatLieu = model.getChatLieu()== null ? "" : model.getChatLieu().getTen();
        giaNhap = model.getGiaNhap();
        giaBan = model.getGiaBan();
        moTa = model.getMoTa();
        ngayTao = model.getNgayTao();
        ngayCapNhat = model.getNgayCapNhat();
        hienThi = model.getHienThi();

        if(model.getImages() != null)
        anh = model.getImages().stream()
                .sorted(Comparator.comparing(AnhModel::getViTriAnh))
                .map(img -> {
                    AnhDtoResponse anh = new AnhDtoResponse(img.getTen(),img.getViTriAnh());
                    return anh;
                })
                .collect(Collectors.toSet());
    }
}
