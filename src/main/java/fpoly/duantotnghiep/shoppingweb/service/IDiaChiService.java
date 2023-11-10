package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;

public interface IDiaChiService {
    DiaChiModel findById(Long id);

    void addDiaChi(DiaChiModel diaChiModel);
}
// chó tuấn ăn cức Phốc