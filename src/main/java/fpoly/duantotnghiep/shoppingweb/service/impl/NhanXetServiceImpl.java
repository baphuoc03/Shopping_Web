package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanXetDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.NhanXetDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.NhanXetModel;
import fpoly.duantotnghiep.shoppingweb.repository.INhanXetRepository;
import fpoly.duantotnghiep.shoppingweb.service.INhanXetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class NhanXetServiceImpl implements INhanXetService{
    @Autowired
    private INhanXetRepository nhanXetRepository;

    @Override
    public Page<NhanXetDtoResponse> getNhanXetBySanPham(Integer page, Integer limit, String maSP){

        Page<NhanXetModel> pageModel = nhanXetRepository.getBySanPhamMa(maSP, PageRequest.of(page,limit));

        return new PageImpl<>(pageModel.getContent().stream().map(n -> new NhanXetDtoResponse(n)).collect(Collectors.toList()),
                pageModel.getPageable(),pageModel.getTotalElements());
    }

    @Override
    public Page<NhanXetDtoResponse> getNhanXetBySanPhamAndRate(Integer page, Integer limit, String maSP, Float rate){

        Page<NhanXetModel> pageModel = nhanXetRepository.getBySanPhamMaAndRate(maSP, rate, PageRequest.of(page,limit));

        return new PageImpl<>(pageModel.getContent().stream().map(n -> new NhanXetDtoResponse(n)).collect(Collectors.toList()),
                pageModel.getPageable(),pageModel.getTotalElements());
    }

    @Override
    public NhanXetDtoResponse add(NhanXetDtoRequest nhanXetDtoRequest){
        NhanXetModel model = nhanXetRepository.save(nhanXetDtoRequest.mapToModel());
        model.setThoiGian(new Date());
        return new NhanXetDtoResponse(model);
    }

    @Override
    public Float getAvgRatingBySanPham(String maSP){
        return nhanXetRepository.getAvgRatingBySanPham(maSP);
    }
}
