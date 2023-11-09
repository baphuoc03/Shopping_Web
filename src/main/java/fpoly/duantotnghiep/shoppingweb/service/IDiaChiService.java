package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DiaChiDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DiaChiDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhachHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDiaChiService {
    
    Page<DiaChiDTOResponse> getAll(Integer page, Integer limit);
    DiaChiDTOResponse findById(Long id);
    Boolean exsistsById(Long id);
    DiaChiDTOResponse add(DiaChiDTORequest diaChi) throws MessagingException;
    DiaChiDTOResponse update(DiaChiDTORequest diaChi);
    void deleteById(Long id);
}
