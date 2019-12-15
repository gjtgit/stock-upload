package com.gao.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @Column(name="company_name")
    private String companyName;
    
    @Column(name="turnover")
    private float turnover;
    
    @Column(name="ceo")
    private String ceo;
    
    @Column(name="board_members")
    private String boardMembers;
    
    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY) 
    private List<IPODetailEntity> ipoList;
    
    @Column(name="listed_exchanges")
    private String listedExchanges;
    
    @Column(name="sector_id")
    private Long sectorId;
    
    @Column(name="brief")
    private String brief;
    
    @Column(name="stock_code")
    private String stockCodeInExchange;

    public CompanyEntity() {
        
    }
    
    public CompanyEntity(String companyName, float turnover, String ceo, String boardMembers, String listedExchanges,
            Long sectorId, String brief, String stockCodeInExchange) {
        super();
        this.companyName = companyName;
        this.turnover = turnover;
        this.ceo = ceo;
        this.boardMembers = boardMembers;
        this.listedExchanges = listedExchanges;
        this.sectorId = sectorId;
        this.brief = brief;
        this.stockCodeInExchange = stockCodeInExchange;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public float getTurnover() {
        return turnover;
    }

    public void setTurnover(float turnover) {
        this.turnover = turnover;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getBoardMembers() {
        return boardMembers;
    }

    public void setBoardMembers(String boardMembers) {
        this.boardMembers = boardMembers;
    }

    public String getListedExchanges() {
        return listedExchanges;
    }

    public void setListedExchanges(String listedExchanges) {
        this.listedExchanges = listedExchanges;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getStockCodeInExchange() {
        return stockCodeInExchange;
    }

    public void setStockCodeInExchange(String stockCodeInExchange) {
        this.stockCodeInExchange = stockCodeInExchange;
    }

    public List<IPODetailEntity> getIpoList() {
        return ipoList;
    }

    public void setIpoList(List<IPODetailEntity> ipoList) {
        this.ipoList = ipoList;
    }
    
}
