package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.TaiKhoanModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanDtoResponse {

    private String id;
    private String vaiTro;
    private String username;
    private String hoVaTen;
    private String gioiTinh;
    private LocalDate ngaySinh;
    private String soDienThoai;
    private String email;
    private String anhDaiDien;

    public TaiKhoanDtoResponse(TaiKhoanModel model) {
        this.id = model.getId();
        this.vaiTro = model.getVaiTro().getTen();
        this.username = model.getUsername();
        this.hoVaTen = model.getHoVaTen();
        this.gioiTinh = model.getGioiTinh() == true ? "Nam" : "Nữ";
        this.ngaySinh = model.getNgaySinh();
        this.soDienThoai = model.getSoDienThoai();
        this.email = model.getEmail();
        this.anhDaiDien = model.getAnhDaiDien();
    }
}
