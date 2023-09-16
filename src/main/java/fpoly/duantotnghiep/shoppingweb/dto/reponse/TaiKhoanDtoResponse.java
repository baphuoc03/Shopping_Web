package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import lombok.ToString;

import java.time.LocalDate;
public interface TaiKhoanDtoResponse {

    String getId();
    String getVaiTro();
    String getUsername();
    String getHoVaTen();
    Boolean getGioiTinh();
    LocalDate getNgaySinh();
    String getSoDienThoai();
    String getEmail();
    String getAnhDaiDien();
}
