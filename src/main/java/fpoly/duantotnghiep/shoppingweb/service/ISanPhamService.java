package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;

import java.util.List;

public interface ISanPhamService {
    List<SanPhamDtoResponse> findAll();

    SanPhamDtoResponse findByMa(String ma);

    List<SanPhamDtoResponse> saveAll(List<SanPhamDtoRequest> sanPham);


    SanPhamDtoResponse save(SanPhamDtoRequest entity);

    boolean existsById(String s);

    void deleteById(String s);
}
