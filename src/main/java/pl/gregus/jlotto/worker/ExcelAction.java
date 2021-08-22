/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gregus.jlotto.worker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Grzegorz
 */
public class ExcelAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelAction.class);

    private final Map<Integer, String> results;

    public ExcelAction(Map<Integer, String> results) {
        this.results = results;
    }

    public void save(String fileName) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("wyniki");

        int rowNum = 0;

        for (Map.Entry<Integer, String> entry : this.results.entrySet()) {

            XSSFRow row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (String field : entry.getValue().split(";")) {
                XSSFCell cell = row.createCell(colNum++);
                if (entry.getKey() > 0) {
                    switch (colNum) {
                        case 1, 3, 4 ->
                            cell.setCellValue((Integer) Integer.parseInt(field));
                        case 2 ->
                            cell.setCellValue(Double.parseDouble(field));
                    }
                } else {
                    cell.setCellValue(field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.info("File " + fileName + " created");
    }

}
