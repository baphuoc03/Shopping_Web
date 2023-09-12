package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.SizeModel;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChiTietSanPhamDtoRequest {
    private String id;
    private String sanPham;
    private Float size;
    private Long soLuong;

    public ChiTietSanPhamModel mapToModel(){
        ChiTietSanPhamModel model = new ChiTietSanPhamModel();
        model.setId(id);

        SanPhamModel sanPhamModel = new SanPhamModel();
        sanPhamModel.setMa(sanPham);
        model.setSanPham(sanPhamModel);

        SizeModel sizeModel = new SizeModel();
        sizeModel.setMa(size);
        model.setSize(sizeModel);

        model.setSoLuong(soLuong);
        return model;
    }
}
