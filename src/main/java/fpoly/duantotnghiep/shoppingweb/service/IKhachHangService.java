package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhachHangDTORequest;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IKhachHangService {

    List<KhachHangDtoResponse> getAll();

    KhachHangDtoResponse findById(String username);

    Boolean existsByUsername(String username);

    KhachHangDtoResponse add(KhachHangDTORequest khachHang) throws MessagingException;

    KhachHangDtoResponse update(KhachHangDTORequest khachHang);

    KhachHangDtoResponse update(KhachHangDTORequest khachHang, MultipartFile img) throws IOException;

    void deleteByUsername(String username);
}
