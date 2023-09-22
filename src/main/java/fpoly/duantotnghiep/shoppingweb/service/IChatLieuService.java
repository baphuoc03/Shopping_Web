package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChatLieuDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.MauSacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChatLieuDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.MauSacDTORequest;

import java.util.List;

public interface IChatLieuService {
    public List<ChatLieuDTOResponse> findAll();
    public ChatLieuDTOResponse save(ChatLieuDTORequest chatLieuDTORequest);
    public ChatLieuDTOResponse findById(String s);
    public boolean existsById(String s);
    public void  deleteById(String s);
}
