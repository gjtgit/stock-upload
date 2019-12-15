package com.gao.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.demo.entity.StockPriceEntity;
import com.gao.demo.model.AvgStockPriceVo;
import com.gao.demo.repository.StockPriceRepo;

@Service
public class StockPriceService {
Logger logger = LoggerFactory.getLogger(StockPriceService.class);
    
    @Autowired
    private StockPriceRepo stockPriceRepo;
    
    public List<StockPriceEntity> getStockPriceByComIdAndExAndPeriod(Long companyId, 
           String stockExchange, Date fromDate, Date toDate) throws Exception{
        return stockPriceRepo.findByCompanyIdAndStockExchangeAndCurDateBetween(companyId, stockExchange, fromDate, toDate);
    }
    
    public List<AvgStockPriceVo> getStockPriceByComIdListAndExAndPeriod(List<Long> companyIdList, 
            String stockExchange, Date fromDate, Date toDate) throws Exception{
        //companyIdList = "1,2";
        //String[] idArray = companyIdList.split(",");
//        List<Long> comIdList = new ArrayList<Long>();
//        for(int i=0;i<idArray.length;i++) {
//            Long id = Long.parseLong(idArray[i].trim());
//            comIdList.add(id);
//        }
        List<AvgStockPriceVo> l = new ArrayList<AvgStockPriceVo>();
        l = stockPriceRepo.findByCompanyIdListAndStockExchangeAndCurDateBetween(companyIdList, stockExchange, fromDate, toDate);
        return l;
    }
}
