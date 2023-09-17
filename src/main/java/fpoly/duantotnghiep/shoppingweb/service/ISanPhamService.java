package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ISanPhamService {
    List<SanPhamDtoResponse> findAll();

    SanPhamDtoResponse findByMa(String ma);

    SanPhamDtoRequest findDtoRequetsByMa(String ma);

    List<SanPhamDtoResponse> saveAll(List<SanPhamDtoRequest> sanPham);

    List<SanPhamModel> findListById(List<String> ma);

    SanPhamDtoResponse save(SanPhamDtoRequest entity);

    SanPhamDtoResponse update(SanPhamDtoRequest entity) throws IOException;

    boolean existsById(String s);

    void deleteById(String s) throws IOException;

    Integer updateTrangThaiHIenThi(Boolean trangThai, String ma);
}
