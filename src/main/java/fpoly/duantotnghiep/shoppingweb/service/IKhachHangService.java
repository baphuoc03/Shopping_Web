package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;

import java.util.List;

public interface IKhachHangService {

    List<KhachHangDtoResponse> getAll();

    KhachHangDtoResponse findById(String username);
}
