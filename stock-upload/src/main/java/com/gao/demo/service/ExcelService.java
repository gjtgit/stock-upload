package com.gao.demo.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.demo.entity.CompanyEntity;
import com.gao.demo.entity.StockPriceEntity;
import com.gao.demo.repository.StockPriceRepo;

@Service
public class ExcelService {
    
    Logger logger = LoggerFactory.getLogger(ExcelService.class);
    
    @Autowired
    private StockPriceRepo stockPriceRepo;
    
    @Autowired
    private CompanyClient companyClient;
    
    public List<StockPriceEntity> getListByExcel(InputStream in, String fileName) throws Exception {
        List<StockPriceEntity> list = new ArrayList<StockPriceEntity>();
        
        //Create excel workbook
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            logger.info("[error]Excel workbook is null");
            throw new Exception("Excel workbook is null!");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            logger.info("[info]: sheet="+i+"  firstRow="+sheet.getFirstRowNum()+"  lastRow="+sheet.getLastRowNum());
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                //List<Object> li = new ArrayList<>();
                StockPriceEntity sp = new StockPriceEntity();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    //li.add(cell);
                    cell = row.getCell(y);
                    if (cell == null || cell.getCellType()==CellType.BLANK) {
                        logger.info("[error] Cell is blank at sheet.row.column["+i+","+j+","+y+"]");
                        throw new Exception("Cell is blank at sheet.row.column["+i+","+j+","+y+"]");
                    }
                    switch(y) {
                        case 0: sp.setCompanyId(Math.round(cell.getNumericCellValue())); break;
                        case 1: sp.setStockExchange(StringTrim(cell.getStringCellValue())); break; 
                        case 2: sp.setCurPrice(cell.getNumericCellValue()); break;
                        case 3: sp.setCurDate(cell.getDateCellValue()); break;
                        case 4: sp.setCurTime(StringTrim(cell.getStringCellValue())); break;
                    }
                }
                list.add(sp);
            }
        }
        work.close();
        return list;
    }

    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inStr);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inStr);
        } else {
            logger.info("[error]:Please upload an excel file");
            throw new Exception("Please upload an excel file!");
        }
        return workbook;
    }
    
    public static String StringTrim(String str){
        return str.replaceAll("[\\s\\u00A0]+","").trim();
    }
    
    public List<List<String>> importToDb(InputStream in, String fileName) throws Exception {
        List<StockPriceEntity> list = getListByExcel(in, fileName);
        Date fromDate = new Date();
        Date toDate = new Date();
        String companyId = "";
        String stockExchange = "";
        List<List<String>> sumList = new ArrayList<List<String>>();
        List<String> tmpList = new ArrayList<String>();
        int num = 0;
        for(int i=0;i<list.size();i++) {
            StockPriceEntity sp = list.get(i);
            String tmpId = String.valueOf(sp.getCompanyId());
            String tmpExchange = sp.getStockExchange();
            Date tmpDate = sp.getCurDate();
            if(i==0) {
                companyId = tmpId;
                stockExchange = tmpExchange;
                fromDate = tmpDate;
                toDate = tmpDate;
                num++;
            }else {
                if(tmpId.equals(companyId) && tmpExchange.equals(stockExchange)) {
                    if(tmpDate.after(toDate)) {
                        toDate = tmpDate;
                    }else if(tmpDate.before(fromDate)) {
                        fromDate = tmpDate;
                    }
                    num++;
                    //logger.info("--- num"+num);
                }else {
                    tmpList.add(companyId);
                    tmpList.add(stockExchange);
                    tmpList.add(num+"");
                    tmpList.add(new SimpleDateFormat("MM-dd-yyyy").format(fromDate));
                    tmpList.add(new SimpleDateFormat("MM-dd-yyyy").format(toDate));
                    sumList.add(tmpList);
                    
                    num = 1;
                    companyId = tmpId;
                    stockExchange = tmpExchange;
                    fromDate = tmpDate;
                    toDate = tmpDate;
                    tmpList = new ArrayList<String>();
                }
                
                if(i==list.size()-1) {
                    tmpList.add(companyId);
                    tmpList.add(stockExchange);
                    tmpList.add(num+"");
                    tmpList.add(new SimpleDateFormat("MM-dd-yyyy").format(fromDate));
                    tmpList.add(new SimpleDateFormat("MM-dd-yyyy").format(toDate));
                    sumList.add(tmpList);
                }
            }
        }
        
        for(int i=0;i<sumList.size();i++) {
            List<String> l = sumList.get(i);
            stockPriceRepo.deleteByCompanyIdAndStockExchangeAndCurDateBetween(
                    Long.parseLong(l.get(0)), l.get(1), 
                    new SimpleDateFormat("MM-dd-yyyy").parse(l.get(3)), 
                    new SimpleDateFormat("MM-dd-yyyy").parse(l.get(4)));
        }
        stockPriceRepo.saveAll(list);
        
        List<CompanyEntity> comList = this.companyClient.getCompanyList();
        for(int i=0;i<sumList.size();i++) {
            List<String> l = sumList.get(i);
            Long tempComId = Long.parseLong(l.get(0));
            if(comList!=null) {
                for(CompanyEntity com:comList) {
                    if(tempComId == com.getId()) {
                        l.add(com.getCompanyName());
                        break;
                    }
                }
            }
        }
        
        return sumList;
    }
    
}
