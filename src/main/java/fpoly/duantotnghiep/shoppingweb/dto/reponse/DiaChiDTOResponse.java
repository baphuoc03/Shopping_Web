package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowSysOut;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaChiDTOResponse {

    private Long id;
    private Integer thanhPhoCode;
    private Integer quanHuyenCode;
    private String xaPhuongCode;
    private Integer thanhPhoName;
    private Integer quanHuyenName;
    private String xaPhuongName;
    private String diaChiChiTiet;

    public DiaChiDTOResponse(DiaChiModel model) {
        this.id = model.getId();
        this.thanhPhoCode= model.getThanhPhoCode();
        this.xaPhuongCode = model.getXaPhuongCode();
        this.thanhPhoName = model.getThanhPhoName();
        this.quanHuyenName = model.getQuanHuyenName();
        this.xaPhuongName = model.getXaPhuongName();
        this.diaChiChiTiet = model.getDiaChiChiTiet();
    }

}
