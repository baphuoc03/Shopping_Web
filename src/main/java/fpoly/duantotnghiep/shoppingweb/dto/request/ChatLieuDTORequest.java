package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.ChatLieuModel;
import fpoly.duantotnghiep.shoppingweb.model.MauSacModel;
import lombok.Data;

import java.util.Date;

@Data
public class ChatLieuDTORequest {
    private String id;
    private String ten;
    private Date ngayTao;
    private Date ngayCapNhat;

    public ChatLieuModel mapToModel() {
        ChatLieuModel model = new ChatLieuModel();
        model.setId(id);
        model.setTen(ten);
        model.setNgayTao(ngayTao);
        model.setNgayCapNhat(ngayCapNhat);
        return model;
    }
}
