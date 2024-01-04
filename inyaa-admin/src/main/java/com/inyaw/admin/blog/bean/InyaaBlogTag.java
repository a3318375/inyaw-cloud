package com.inyaw.admin.blog.bean;import com.fasterxml.jackson.annotation.JsonIgnoreProperties;import jakarta.persistence.Entity;import jakarta.persistence.GeneratedValue;import jakarta.persistence.GenerationType;import jakarta.persistence.Id;import lombok.Data;import java.time.LocalDateTime;@Entity@Data@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})public class InyaaBlogTag {    @Id    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略    private Integer id;    /**     * 标签名称     */    private String name;    /**     * 创建时间     */    private LocalDateTime createTime;    private String remark;}