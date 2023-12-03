package Application.Model;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
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

                Row categoryRow =  sheet.createRow(rowIndex++);
                categoryRow.createCell(0).setCellValue("Category Name: " + name);

                for (int i = 0, j = 1; i < data.size(); i++) {
                    Product p = (Product) data.get(i);
                    int soldStock = (int) data.get(++i);

                    Row productRow =  sheet.createRow(rowIndex++);
                    productRow.createCell(0).setCellValue(j++);
                    productRow.createCell(1).setCellValue(p.getName());
                    productRow.createCell(2).setCellValue("Quantity Sold = " + soldStock);

                    categTotal += p.getPrice() * soldStock;
                }

                Row totalRow = sheet.createRow(rowIndex++);
                totalRow.createCell(0).setCellValue("Category Total Sale:");
                totalRow.createCell(1).setCellValue(categTotal);

                categTotal = 0.0F;
            } else {
                total = (Float) super.getReportData().get(name).get(0);
            }
        }

        Row totalSalesRow = sheet.createRow(rowIndex);
        totalSalesRow.createCell(0).setCellValue("Total Sales:");
        totalSalesRow.createCell(1).setCellValue(total);

        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        workbook.close();
    }



    @Override
    public void display() {

    }
}
