package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public interface ISanPhamService {
    List<SanPhamDtoResponse> findAll();

    SanPhamDtoResponse findByMa(String ma);

    SanPhamDtoRequest findDtoRequetsByMa(String ma);

    List<SanPhamDtoResponse> saveAll(List<SanPhamDtoRequest> sanPham);

    List<SanPhamModel> findListById(List<String> ma);

    List<SanPhamModel> findByAllSanPhamWithKM();

    List<SanPhamModel> findAllWithKmWhereNgayBD();

    SanPhamDtoResponse save(SanPhamDtoRequest entity);

    SanPhamModel save1(SanPhamModel entity);

    SanPhamDtoResponse update(SanPhamDtoRequest entity) throws IOException;

    boolean existsById(String s);

    void deleteById(String s) throws IOException;

    Integer updateTrangThaiHIenThi(Boolean trangThai, String ma);

    Integer updateGiaBan(BigDecimal giaBan, String ma);

//    public void updateGiaGiam(SanPhamModel sanPhamModel);


}
