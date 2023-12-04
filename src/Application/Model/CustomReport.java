package Application.Model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

public class CustomReport extends Report  implements SalesReport{

    public void assignData(HashMap<String, Vector<Object>> reportData)
    {
        super.setReportData(reportData);
    }

    @Override
    public void assignDate(LocalDate start, LocalDate end) {

        super.setStart(start);
        super.setEnd(end);
    }


    @Override
    public void createSalesReport() throws IOException {

        System.out.println("\t\t -----------Custom Sales Report--------");

        Set<String> categoryName = super.getReportData().keySet();

        Float total = 0.0F;
        float categTotal = 0.0F;

        for(String name : categoryName)
        {
            if(!Objects.equals(name, "TOTAL"))
            {
                Vector<Object> data = super.getReportData().get(name);

                System.out.println("Category Name: "+ name + "\n\n");

                for(int i = 0,j=1; i < data.size();i++)
                {
                    Product p = (Product) data.get(i);
                    int soldStock = (int) data.get(++i);

                    System.out.println("\t " + j++ + ") " + p.getName() + " Quantity Sold = " + soldStock);

                    categTotal+= p.getPrice()*soldStock;
                }

                System.out.println("\nCategory Total Sale: "+ categTotal + "\n\n");
                categTotal = 0.0F;
            }
            else {
                total = (Float) super.getReportData().get(name).get(0);

            }
        }

        System.out.println("\n\t Total Sales: " + total );

        String file = "/Users/FullStackMohid/IdeaProjects/PharamacyPos/Sales Report/Custom_Sales_Report_( " + super.getStart() + " - " + super.getEnd() + " ).xlsx";

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("CustomSalesReport");


        int rowIndex = 0;


        for (String name : categoryName) {
            if (!Objects.equals(name, "TOTAL")) {
                Vector<Object> data = super.getReportData().get(name);

                // Create a header row for each category
                Row headerRow = sheet.createRow(rowIndex++);
                CellStyle headerCellStyle = workbook.createCellStyle();
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerCellStyle.setFont(headerFont);
                headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); // Set cell background color
                headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                headerRow.createCell(0).setCellValue("Category Name");
                headerRow.getCell(0).setCellStyle(headerCellStyle);

                headerRow.createCell(1).setCellValue("Product Name");
                headerRow.getCell(1).setCellStyle(headerCellStyle);

                headerRow.createCell(2).setCellValue("Qty Sold");
                headerRow.getCell(2).setCellStyle(headerCellStyle);

                // Set column width for better visibility
                sheet.setColumnWidth(0, 4000); // Adjust the width as needed
                sheet.setColumnWidth(1, 6000); // Adjust the width as needed
                sheet.setColumnWidth(2, 3000); // Adjust the width as needed


                // Create a data row for each product in the category
                for (int i = 0, j = 1; i < data.size(); i++) {
                    Product p = (Product) data.get(i);
                    int soldStock = (int) data.get(++i);

                    Row productRow = sheet.createRow(rowIndex++);
                    productRow.createCell(0).setCellValue(name); // Category Name
                    productRow.createCell(1).setCellValue(p.getName()); // Product Name
                    productRow.createCell(2).setCellValue(soldStock); // Qty Sold

                    categTotal += p.getPrice() * soldStock;
                }

                // Create a total row for the category
                Row totalRow = sheet.createRow(rowIndex++);
                CellStyle totalCellStyle = workbook.createCellStyle();
                totalCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex()); // Set cell background color for total
                totalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                totalRow.createCell(0).setCellValue("Category Total Sale:");
                totalRow.createCell(1).setCellValue(categTotal);
                totalRow.getCell(0).setCellStyle(totalCellStyle);
                totalRow.getCell(1).setCellStyle(totalCellStyle);

                categTotal = 0.0F;
            } else {
                total = (Float) super.getReportData().get(name).get(0);
            }
        }


        Row space = sheet.createRow(rowIndex++);
        Row totalSalesRow = sheet.createRow(rowIndex);

        CellStyle totalCellStyle = workbook.createCellStyle();
        totalCellStyle.setFillForegroundColor(IndexedColors.CORAL.getIndex()); // Set cell background color for total
        totalCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        totalSalesRow.createCell(0).setCellValue("Total Sales:");
        totalSalesRow.createCell(1).setCellValue(total);

        totalSalesRow.getCell(0).setCellStyle(totalCellStyle);
        totalSalesRow.getCell(1).setCellStyle(totalCellStyle);

        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        workbook.close();
    }



    @Override
    public void display() {

    }
}
