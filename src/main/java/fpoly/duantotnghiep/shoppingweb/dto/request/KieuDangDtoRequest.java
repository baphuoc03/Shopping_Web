package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.KieuDangModel;
import fpoly.duantotnghiep.shoppingweb.model.MauSacModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KieuDangDtoRequest {
    private String id;
    @NotBlank(message = "Vui Lòng nhập Tên")
    private String ten;
    private Date ngayTao;
    private Date ngayCapNhat;

    public KieuDangModel mapToModel() {
        KieuDangModel model = new KieuDangModel();
        model.setId(id);
        model.setTen(ten);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        return model;
    }
}
