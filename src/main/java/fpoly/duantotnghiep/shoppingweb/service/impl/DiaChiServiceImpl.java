package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDiaChiRepository;
import fpoly.duantotnghiep.shoppingweb.service.IDiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaChiServiceImpl implements IDiaChiService {
    @Autowired
    IDiaChiRepository repository;

    @Override
    public DiaChiModel findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void addDiaChi(DiaChiModel diaChiModel) {
        this. repository.save(diaChiModel);
    }
}
