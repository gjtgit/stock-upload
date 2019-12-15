package com.gao.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gao.demo.entity.StockPriceEntity;
import com.gao.demo.model.AvgStockPriceVo;
import com.gao.demo.service.StockPriceService;

@RestController
public class StockPriceController {
    Logger logger = LoggerFactory.getLogger(StockPriceController.class);
    
    @Autowired
    private StockPriceService stockPriceService ;
    
    @GetMapping(path="/stockprice")
    public List<StockPriceEntity> getStockPriceByComIdAndExAndPeriod(
            @RequestParam Long companyId, @RequestParam String stockExchange,
            @RequestParam String fromDate, @RequestParam String toDate) throws Exception{
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
        return this.stockPriceService.getStockPriceByComIdAndExAndPeriod(companyId, stockExchange, from, to);
    }
    
    @GetMapping(path="/getStockPriceByComIdListAndExAndPeriod")
    public List<AvgStockPriceVo> getStockPriceByComIdListAndExAndPeriod(
            @RequestParam List<Long> companyIdList, @RequestParam String stockExchange,
            @RequestParam String fromDate, @RequestParam String toDate) throws Exception{
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
        List<AvgStockPriceVo> l = this.stockPriceService.getStockPriceByComIdListAndExAndPeriod(companyIdList, stockExchange, from, to);
        
        return l;
    }
    
}

