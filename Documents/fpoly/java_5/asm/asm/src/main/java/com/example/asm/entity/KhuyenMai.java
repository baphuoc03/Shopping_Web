package com.example.asm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "khuyenmai")
public class KhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkhuyenmai")
    private Integer id;

    @Column(name = "ten")
    @NotBlank(message = "Không để trống tên khuyến mại")
    private String ten;
    @Column(name = "trangthai")
    private Integer trangThai;
    @Column(name = "mucgiamgia")
//    @Size(min = 0,max = 100,message = "Mức giảm giá chỉ từ 0% - 100%")
    @Min(value = 0,message = "Mức giảm giá chỉ từ 0% - 100%")
    @Max(value = 100,message = "Mức giảm giá chỉ từ 0% - 100%")
    @NotNull(message = "không để trống mức giảm ")
    private Integer mucGiam;

    @OneToMany(mappedBy = "khuyenMai",fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonBackReference
    private List<KhuyenMai_SanPham> khuyenMai_sanPhams;

    public Integer getTotalProductOnDeal(){
        return khuyenMai_sanPhams.size();
    }
}
