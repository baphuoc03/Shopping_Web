package fpoly.duantotnghiep.shoppingweb.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Component;

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
}
