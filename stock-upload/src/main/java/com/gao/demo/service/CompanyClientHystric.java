package com.gao.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gao.demo.entity.CompanyEntity;

@Component
public class CompanyClientHystric implements CompanyClient {
    
    Logger logger = LoggerFactory.getLogger(CompanyClientHystric.class);
 
    @Override
    public List<CompanyEntity>  getCompanyList() {
        logger.info("[error]:getCompanyList --- Call service-company failed.");
        return null;
    }

}
