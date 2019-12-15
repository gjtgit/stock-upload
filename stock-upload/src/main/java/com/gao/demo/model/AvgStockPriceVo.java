package com.gao.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AvgStockPriceVo {
    private double curPrice;
    private String stockExchagne;
    private Date curDate;
    
    public AvgStockPriceVo() {
    }
    
    public AvgStockPriceVo(double curPrice, String stockExchagne, Date curDate) {
        super();
        this.curPrice = curPrice;
        this.stockExchagne = stockExchagne;
        this.curDate = curDate;
    }


    public double getCurPrice() {
        return curPrice;
    }
    public void setCurPrice(double curPrice) {
        this.curPrice = curPrice;
    }
    public String getStockExchagne() {
        return stockExchagne;
    }
    public void setStockExchagne(String stockExchagne) {
        this.stockExchagne = stockExchagne;
    }
    public Date getCurDate() {
        return curDate;
    }
    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }
    
}
