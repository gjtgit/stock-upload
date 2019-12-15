package com.gao.demo.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gao.demo.entity.StockPriceEntity;
import com.gao.demo.model.AvgStockPriceVo;

@Repository
public interface StockPriceRepo extends JpaRepository<StockPriceEntity, Long>{

//    @Transactional
//    @Query("delete from StockPriceEntity s where s.companyId=?1 and s.stockExchange=?2 and s.curDate>=?3 and s.curDate<=?4")
//    void deleteStockPriceByCondition(Long companyId, String stockExchange, Date fromDate, Date toDate);
    
    @Transactional
    void deleteByCompanyIdAndStockExchangeAndCurDateBetween(Long companyId, String stockExchange, Date fromDate, Date toDate);
    
    List<StockPriceEntity> findByCompanyIdAndStockExchangeAndCurDateBetween(Long companyId, String stockExchange, Date fromDate, Date toDate);
    
    @Query("select new com.gao.demo.model.AvgStockPriceVo(avg(s.curPrice), s.stockExchange, s.curDate) "
        +" from StockPriceEntity s "
        +" where s.companyId in ?1 and s.stockExchange=?2 and s.curDate>=?3 and s.curDate<=?4 "
        +" group by s.stockExchange,s.curDate ")
    List<AvgStockPriceVo> findByCompanyIdListAndStockExchangeAndCurDateBetween(List<Long> companyIdList, String stockExchange, Date fromDate, Date toDate);
    
}
