package fpoly.duantotnghiep.shoppingweb.entitymanager;

import fpoly.duantotnghiep.shoppingweb.dto.filter.SanPhamDtoFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SanPhamEntityManager {
    @Autowired
    private EntityManager entityManager;

    public Page<SanPhamDtoResponse> filterMultipleProperties(SanPhamDtoFilter sanPham, Integer pageNumber, Integer limit){
        StringBuilder jpql = new StringBuilder("select s FROM SanPhamModel s WHERE s.trangThai = true ");

        if(sanPham.getTen() != null){
            jpql.append(" And (s.ten like '%"+sanPham.getTen()+"%' Or s.ma like '%"+sanPham.getTen()+"%')");
//            queryBuider.append("And s.ten like '%"+sanPham.getTen()+"%'");
        }

        if(sanPham.getMauSac()!=null) jpql.append(" And s.mauSac.ma = '"+sanPham.getMauSac()+"'");
        if(sanPham.getDongSanPham()!=null) jpql.append(" And s.dongSanPham.id = '"+ sanPham.getDongSanPham()+"'");
        if(sanPham.getKieuDang()!=null) jpql.append(" And s.kieuDang.id = '" + sanPham.getKieuDang()+"'");
        if(sanPham.getXuatXu()!=null) jpql.append(" And s.xuatXu.id = '" + sanPham.getXuatXu()+"'");
        if(sanPham.getChatLieu()!=null) jpql.append(" And s.chatLieu.id = '" + sanPham.getChatLieu()+"'");
        if(sanPham.getGiaBan()!=null) jpql.append(" And s.giaBan >= " + sanPham.getGiaBan());
        if(sanPham.getGiaMax()!=null) jpql.append(" And s.giaBan <= " + sanPham.getGiaMax());

        if(sanPham.getSort()!=null){
            if(sanPham.getSort()==1) jpql.append( "ORDER BY s.giaBan DESC");
            else if(sanPham.getSort()==2) jpql.append(" ORDER BY s.giaBan");
            else if(sanPham.getSort()==3) jpql.append(" ORDER BY s.ten DESC");
            else if(sanPham.getSort()==4) jpql.append(" ORDER BY s.ten");
            else if(sanPham.getSort()==5) jpql.append("ORDER BY s.soLuong DESC");
            else if(sanPham.getSort()==6) jpql.append("ORDER BY s.soLuong");
            else if(sanPham.getSort()==7) jpql.append("ORDER BY s.ngayTao DESC");
            else if(sanPham.getSort()==8) jpql.append("ORDER BY s.ngayTao");
            else if(sanPham.getSort()==9) jpql.append("ORDER BY s.ngayCapNhat DESC");
            else if(sanPham.getSort()==10) jpql.append("ORDER BY s.ngayCapNhat");
        }

        Query query = entityManager.createQuery(String.valueOf(jpql));
        List<SanPhamModel> listContent = query.getResultList();
        Pageable pageable = PageRequest.of(pageNumber,limit);

        return new PageImpl<>(listContent.stream().skip(pageable.getOffset()).limit(limit).map(m -> new SanPhamDtoResponse(m)).collect(Collectors.toList())
                ,pageable,listContent.size());
    }
}
