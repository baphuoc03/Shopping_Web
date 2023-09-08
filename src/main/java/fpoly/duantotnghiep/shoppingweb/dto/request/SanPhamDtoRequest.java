package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SanPhamDtoRequest {
    @NotBlank(message = "Không để trống mã")
    @Size(max = 50, message = "Mã không quá 50 ký tự")
    private String ma;
    @NotBlank(message = "Không để trống tên")
    @Size(max = 50, message = "Tên không quá 50 ký tự")
    private String ten;
    private String mauSac;
    private String dongSanPham;
    private String kieuDang;
    private String chatLieu;
    private BigDecimal giaNhap;
    @NotNull(message = "Không để trống giá bán")
    private BigDecimal giaBan;
    private String moTa;
    private Date ngayTao;
    private Date ngayCapNhat;
    private Boolean hienThi;
    private Set<AnhDtoRequest> anh;

    public SanPhamModel mapToModel(){
        SanPhamModel model = new SanPhamModel();
        model.setMa(ma);
        model.setTen(ten);
        if(mauSac != null && !mauSac.isBlank()) model.setMauSac(new MauSacModel(mauSac));
        if(dongSanPham != null && !dongSanPham.isBlank()) model.setDongSanPham(new DongSanPhamModel(dongSanPham));
        if(kieuDang != null && !kieuDang.isBlank()) model.setKieuDang(new KieuDangModel(kieuDang));
        if(chatLieu != null && !chatLieu.isBlank()) model.setChatLieu(new ChatLieuModel(chatLieu));
        model.setGiaNhap(giaNhap);
        model.setGiaBan(giaBan);
        model.setMoTa(moTa);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        model.setHienThi(hienThi);
        if(anh!=null) {
            Set<AnhModel> images = anh.stream().map(anh -> {
                AnhModel img = new AnhModel();
                img.setTen(anh.getTen());
                img.setViTriAnh(anh.getViTriAnh());
                return img;
            }).collect(Collectors.toSet());
        }

        return model;
    }

    public void setAnh(List<MultipartFile> file) throws IOException {
        Set<AnhDtoRequest> setAnh = new HashSet<>();

        int i=0;
        for (MultipartFile f: file) {
            byte[] bytes = f.getBytes();
            String imgName = "imgProduct"+this.ma+i;
            String typeImg = f.getContentType().split("/")[f.getContentType().split("/").length-1];
            Path path = Paths.get("src/main/resources/static/images/" + imgName+"."+typeImg);
            Files.write(path, bytes);
            setAnh.add(new AnhDtoRequest(imgName,i));
            i++;
        }

        this.anh = setAnh;

    }


}
