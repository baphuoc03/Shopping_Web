package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IKhachHangService {


    Page<KhachHangDtoResponse> getAll(Integer page, Integer limit);

    KhachHangDtoResponse findById(String username);
}
