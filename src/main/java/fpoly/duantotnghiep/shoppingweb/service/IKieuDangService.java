package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KieuDangDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.MauSacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KieuDangDtoRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.MauSacDTORequest;

import java.util.List;

public interface IKieuDangService {
    public List<KieuDangDTOResponse> findAll();
    public KieuDangDTOResponse save( KieuDangDtoRequest kieuDangDtoRequest);
    public KieuDangDTOResponse findById(String s);
    public boolean existsById(String s);
    public void deleteById(String s);
    public void deleteByIds(List<String> s);

}
