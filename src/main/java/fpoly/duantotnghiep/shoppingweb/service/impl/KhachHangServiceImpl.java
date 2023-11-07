package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhachHangServiceImpl implements IKhachHangService {
    @Autowired
    IKhachHangRepository khachHangRepository;

    @Override
    public Page<KhachHangDtoResponse> getAll(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<KhachHangModel> pageModel = khachHangRepository.findAll(pageable);

        return new PageImpl<>(pageModel.getContent().stream().map(k -> new KhachHangDtoResponse(k)).collect(Collectors.toList()),
                pageable, pageModel.getTotalElements());
    }

    @Override
    public KhachHangDtoResponse findById(String username) {
        return new KhachHangDtoResponse(khachHangRepository.findById(username).get());
    }

    @Override
    public List<DiaChiModel> diaChiByTaiKhoan(String taiKhoan) {
        return khachHangRepository.findAllDiaChiByTaiKhoan(taiKhoan);
    }

}
