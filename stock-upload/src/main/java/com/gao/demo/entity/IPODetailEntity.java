package com.gao.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ipo_detail")
public class IPODetailEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
//    @Column(name = "company_id")
//    private Long companyId;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "company_id",nullable = false)
    private CompanyEntity company; 
    
    @Column(name = "stock_exchange")
    private String stockExchange;
    
    @Column(name="price_per_share")
    private double pricePerShare;
    
    @Column(name="total_number_shares")
    private int totalNumberOfShares;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="open_datetime")
    private Date openDateTime;

    public IPODetailEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getCompanyId() {
//        return companyId;
//    }

//    public void setCompanyId(Long companyId) {
//        this.companyId = companyId;
//    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }
    
    public String getStockExchange() {
        return stockExchange;
    }
    
    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public int getTotalNumberOfShares() {
        return totalNumberOfShares;
    }

    public void setTotalNumberOfShares(int totalNumberOfShares) {
        this.totalNumberOfShares = totalNumberOfShares;
    }

    public Date getOpenDateTime() {
        return openDateTime;
    }

    public void setOpenDateTime(Date openDateTime) {
        this.openDateTime = openDateTime;
    }

}
