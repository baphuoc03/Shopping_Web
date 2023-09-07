package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SanPhamDtoRequest {
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
    private Set<String> anh;

    public SanPhamModel mapToModel(){
        SanPhamModel model = new SanPhamModel();
        model.setMa(ma);
        model.setTen(ten);
        model.setMauSac(new MauSacModel(mauSac));
        model.setDongSanPham(new DongSanPhamModel(dongSanPham));
        model.setKieuDang(new KieuDangModel(kieuDang));
        model.setChatLieu(new ChatLieuModel(chatLieu));
        model.setGiaNhap(giaNhap);
        model.setGiaBan(giaBan);
        model.setMoTa(moTa);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        model.setHienThi(hienThi);
        Set<AnhModel> images = anh.stream().map(anh -> {
            AnhModel img = new AnhModel();
            img.setTen(anh);
            return img;
        }).collect(Collectors.toSet());

        return model;
    }
}
