package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DiaChiDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DiaChiDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDiaChiRepository;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IDiaChiService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DiaChiServiceImpl implements IDiaChiService {
    @Autowired
    IDiaChiRepository diaChiRepository;
    @Override
    public Page<DiaChiDTOResponse> getAll(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<DiaChiModel> pageModel = diaChiRepository.findAll(pageable);

        return new PageImpl<>(pageModel.getContent().stream().map(k -> new DiaChiDTOResponse(k)).collect(Collectors.toList()),
        pageable, pageModel.getTotalElements());

    }

    @Override
    public DiaChiDTOResponse findById(Long id) {
        return new DiaChiDTOResponse(diaChiRepository.findById(id).get());
    }

    @Override
    public Boolean exsistsById(Long id) {
        return diaChiRepository.existsById(id);
    }

    @Override
    public DiaChiDTOResponse add(DiaChiDTORequest diaChi) throws MessagingException {
       DiaChiModel diaChiModel = diaChiRepository.save(diaChi.mapToModel());
        return new DiaChiDTOResponse(diaChiModel);
    }

    @Override
    public DiaChiDTOResponse update(DiaChiDTORequest diaChi) {
        DiaChiModel diaChiModel = diaChiRepository.save(diaChi.mapToModel());
        return new DiaChiDTOResponse(diaChiModel);
    }

    @Override
    public void deleteById(Long id) {
        diaChiRepository.deleteById(id);

    }


}
