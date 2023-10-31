package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DanhSachYeuThichResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DanhSachYeuThichRequest;

import java.util.List;

public interface IDanhSachYeuThichService {
    List<DanhSachYeuThichResponse> findAll();
    DanhSachYeuThichResponse save(DanhSachYeuThichRequest danhSachYeuThichRequest);
    DanhSachYeuThichResponse findById(String s);
    boolean existsById(String s);
    void deleteById(String s);
}
