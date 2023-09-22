package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import lombok.Data;

import java.util.Date;
@Data
public class SizeDTORequest {
    private Float ma;
    private Float chieuDai;
    private Date  ngayTao;
    private Date  ngayCapNhat;

    public SizeModel mapToModel(){
        SizeModel model = new SizeModel();
        model.setMa(ma);
        model.setChieuDai(chieuDai);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        return model;
    }
}
