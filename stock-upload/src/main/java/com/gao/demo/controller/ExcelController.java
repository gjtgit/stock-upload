package com.gao.demo.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gao.demo.service.ExcelService;

@Controller
public class ExcelController {
    
    Logger logger = LoggerFactory.getLogger(ExcelController.class);
    
    @Autowired
    private ExcelService excelService;
    
    @PostMapping(value = "/upload")
    @ResponseBody
    public List<List<String>> uploadExcel(HttpServletRequest request) throws Exception {
        logger.info("[info] uploadExcel start --- ");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("excelFile");
        if (file.isEmpty()) {
            throw new Exception("The excel file can not be empty!");
        }
        InputStream inputStream = file.getInputStream();
        List<List<String>> sumList = excelService.importToDb(inputStream, file.getOriginalFilename());
        inputStream.close();

        logger.info("[info] uploadExcel end --- ");
        return sumList;
    }
    
    @GetMapping(path="/test")
    public String test() {
        return "test from stock-upload";
    }
}
