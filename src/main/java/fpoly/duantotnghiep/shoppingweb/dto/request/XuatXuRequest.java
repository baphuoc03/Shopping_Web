package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.XuatXuModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class XuatXuRequest {
    private String id;
    @NotBlank(message = "Không được để trống tên!!!")
    private String ten;
    private Date ngayTao;
    private Date ngayCapNhat;

    public XuatXuModel mapXuatXuToModel(){
        XuatXuModel model = new XuatXuModel();
        model.setId(id);
        model.setTen(ten);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        return model;
    }
}
