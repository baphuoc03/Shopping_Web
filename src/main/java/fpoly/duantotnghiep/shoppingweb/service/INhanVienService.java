package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.NhanVienDtoRequest;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface INhanVienService {
    List<NhanVienDtoResponse> getAll();

    NhanVienDtoResponse findById(String username);

    Boolean existsByUsername(String username);


    NhanVienDtoResponse add(NhanVienDtoRequest nhanVien) throws MessagingException;

    NhanVienDtoResponse update(NhanVienDtoRequest nhanVien) ;

    NhanVienDtoResponse update(NhanVienDtoRequest nhanVien, MultipartFile img) throws IOException;

    void deleteByUsername(String username);
}
