package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.TaiKhoanDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.TaiKhoanModel;
import fpoly.duantotnghiep.shoppingweb.repository.ITaiKhoanRepository;
import fpoly.duantotnghiep.shoppingweb.service.ITaiKhoanService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaiKhoanServiceImpl implements ITaiKhoanService {
    @Autowired
    ITaiKhoanRepository taiKhoanRepository;


    @Override
    public List<TaiKhoanDtoResponse> getByRoleAdmin() {
        return taiKhoanRepository.getByRoleAdmin();
    }

    @Override
    public TaiKhoanModel findById(String id) {
        return taiKhoanRepository.findById(id).get();
    }

    @Override
    public TaiKhoanDtoResponse getDtoResponseById(String id){
        TaiKhoanModel model = taiKhoanRepository.findById(id).get();
        return new TaiKhoanDtoResponse(model);
    }

    @Override
    public List<TaiKhoanDtoResponse> getTaiKhoanNhanVien() {
        return taiKhoanRepository.getTaiKhoanNhanVien();
    }

    @Override
    public List<TaiKhoanDtoResponse> getTaiKhoanKhachHang() {
        return taiKhoanRepository.getTaiKhoanKhachHang();
    }
}
