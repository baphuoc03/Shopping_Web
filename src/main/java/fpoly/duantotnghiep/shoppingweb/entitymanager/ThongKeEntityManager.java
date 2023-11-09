package fpoly.duantotnghiep.shoppingweb.entitymanager;

import fpoly.duantotnghiep.shoppingweb.dto.thongKe.ChiTietSanPhamThongKeDto;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ThongKeEntityManager {
    @Autowired
    private EntityManager entityManager;

    public Map<Integer,Long> getQuantityOrderByYear(String year){

        return entityManager.createQuery("""
                SELECT MONTH(d.ngayDatHang) AS month, COUNT(d)  AS quantity
                FROM DonHangModel d
                WHERE YEAR(d.ngayDatHang) = :year
                GROUP BY MONTH(d.ngayDatHang)
            """, Tuple.class)
                .setParameter("year",year)
                .getResultList()
                .stream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((Number) tuple.get("month")).intValue(),
                                tuple -> ((Number) tuple.get("quantity")).longValue()
                        )
                );
    }

    public Map<Integer, BigDecimal> getRevenueInOrderByYear(String year){
        return entityManager.createQuery("""
                SELECT MONTH(d.donHang.ngayDatHang) AS month, SUM(d.soLuong*d.donGiaSauGiam) - SUM(d.donHang.tienGiam)  AS revenue
                FROM ChiTietDonHangModel d
                WHERE YEAR(d.donHang.ngayDatHang) = :year
                GROUP BY MONTH(d.donHang.ngayDatHang)
            """, Tuple.class)
                .setParameter("year",year)
                .getResultList()
                .stream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((Number) tuple.get("month")).intValue(),
                                tuple -> BigDecimal.valueOf(((Number) tuple.get("revenue")).doubleValue())
                        )
                );
    }

    public Map<Integer, Long> getTotalProductsByYear(String year){
        return entityManager.createQuery("""
                SELECT MONTH(d.donHang.ngayDatHang) AS month, SUM(d.soLuong)  AS quantity
                FROM ChiTietDonHangModel d
                WHERE YEAR(d.donHang.ngayDatHang) = :year
                GROUP BY MONTH(d.donHang.ngayDatHang)
            """, Tuple.class)
                .setParameter("year",year)
                .getResultList()
                .stream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((Number) tuple.get("month")).intValue(),
                                tuple -> ((Number) tuple.get("quantity")).longValue()
                        )
                );
    }

    public List<ChiTietSanPhamThongKeDto> getChiTietSanPhamDaBan(String maSanPham){

        return entityManager.createQuery("""
                                SELECT s AS sanPham, SUM(cd.soLuong) AS soLuong
                                 FROM ChiTietSanPhamModel s LEFT JOIN ChiTietDonHangModel cd ON s.id = cd.chiTietSanPham.id
                                 WHERE s.sanPham.ma = :maSanPham 
                                 GROUP BY s
                                 
                                 """,Tuple.class)
                                .setParameter("maSanPham",maSanPham)
                                .getResultList().stream()
                                .map(r -> new ChiTietSanPhamThongKeDto(
                                        (ChiTietSanPhamModel) r.get("sanPham"),
                                        r.get("soLuong") == null ? 0L : (Long)  r.get("soLuong")
                                ))
                                .collect(Collectors.toList());


    }

    public Long getTotalQauntityInOrdersWithDate(String firstDate, String lastDate){
        StringBuilder jpql = new StringBuilder("SELECT SUM(c.soLuong) FROM ChiTietDonHangModel c ");
        if (firstDate!=null && lastDate!=null){
            jpql.append("WHERE c.donHang.ngayDatHang between "+firstDate+" and "+lastDate);
        }
        System.out.println(jpql);
        System.out.println((Long) entityManager.createQuery(jpql.toString()).getSingleResult());
        return (Long) entityManager.createQuery(jpql.toString()).getSingleResult();
    }
    public Long getQuantityOrdersWithDate(String firstDate, String lastDate){
        StringBuilder jpql = new StringBuilder("SELECT COUNT(d) FROM DonHangModel d  ");
        if (firstDate!=null && lastDate!=null){
            jpql.append("WHERE d.ngayDatHang between "+firstDate+" and "+lastDate);
        }
        return (Long) entityManager.createQuery(jpql.toString()).getSingleResult();
    }
    public BigDecimal getTotalPriceInOrdersWithDate(String firstDate, String lastDate){
        StringBuilder jpql = new StringBuilder("SELECT SUM(c.donGiaSauGiam*c.soLuong) - SUM(c.donHang.tienGiam) FROM ChiTietDonHangModel c  ");
        if (firstDate!=null && lastDate!=null){
            jpql.append("WHERE c.donHang.ngayDatHang between "+firstDate+" and "+lastDate);
        }
        return (BigDecimal) entityManager.createQuery(jpql.toString()).getSingleResult();
    }

}
