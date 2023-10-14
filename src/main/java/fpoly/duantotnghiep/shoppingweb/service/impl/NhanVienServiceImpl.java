package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.repository.INhanVienRepository;
import fpoly.duantotnghiep.shoppingweb.service.INhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhanVienServiceImpl implements INhanVienService {

    @Autowired
    private INhanVienRepository nhanVienRepository;

    @Override
    public List<NhanVienDtoResponse> getAll() {
        return nhanVienRepository.findAll().stream().map(n -> new NhanVienDtoResponse(n)).collect(Collectors.toList());
    }

    @Override
    public NhanVienDtoResponse findById(String username) {
        return new NhanVienDtoResponse(nhanVienRepository.findById(username).get());
    }

    @Override
    public Boolean existsByUsername(String username){
        return nhanVienRepository.existsById(username);
    }

}
