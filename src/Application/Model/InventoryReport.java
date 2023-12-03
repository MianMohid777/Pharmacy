package Application.Model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class InventoryReport extends Report{

    private HashMap<String,List<Vector<Object>>> stockProdMap;
    private HashMap<String,Integer> stockQty;

    public void assignData(HashMap<String, List<Vector<Object>>> dataMap,HashMap<String,Integer> stockQty)
    {
        stockProdMap = dataMap;
        this.stockQty = stockQty;
    }

    @Override
    public void display() throws IOException {

        System.out.println("-----INVENTORY REPORT-----");

        Set<String> keys = stockProdMap.keySet();


       /* for(String name: keys)
        {
            List<Vector<Object>> productAtStock = stockProdMap.get(name);

            System.out.println("\nProduct Name: " + name + " Stock Batches Available in Inventory\n");
            System.out.println("Stock Id , Product Code,  Packs, Expiry Data\n");

            for(Vector<Object> row : productAtStock)
            {
                for(Object val : row)
                {
                      System.out.print(val + "\t");
                }
            }

            System.out.println("\n Total Stock Qty Available: " + stockQty.get(name));
        }*/



    String file = "/Users/FullStackMohid/IdeaProjects/PharamacyPos/Inventory Reports/Inventory_Report_" + LocalDate.now() +".xlsx";

    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("InventoryReport");

    // Create styles for formatting
    CellStyle headerStyle = createHeaderStyle(workbook);
    CellStyle productStyle = createProductStyle(workbook);
    CellStyle qtyStyle = createQtyStyle(workbook);


    int rowIndex = 0;

    for (String name : keys) {
        List<Vector<Object>> productAtStock = stockProdMap.get(name);

        // Create header row for each product
        Row headerRow = sheet.createRow(rowIndex++);
        createHeaderCell(headerRow, 0, "Product Name: " + name, headerStyle);

        // Create empty row for spacing
        Row emptyRow = sheet.createRow(rowIndex++);
        emptyRow.setHeightInPoints(10); // Adjust the height for spacing

        // Create header row for stock batches
        Row batchesHeaderRow = sheet.createRow(rowIndex++);
        createHeaderCell(batchesHeaderRow, 0, "Stock Id", headerStyle);
        createHeaderCell(batchesHeaderRow, 1, "Product Code", headerStyle);
        createHeaderCell(batchesHeaderRow, 2, "Packs", headerStyle);
        createHeaderCell(batchesHeaderRow, 3, "Expiry Date", headerStyle);

        for (int colNum = 0; colNum < 4; colNum++) {
            sheet.autoSizeColumn(colNum);
        }

        // Create rows for stock batches
        for (Vector<Object> row : productAtStock) {
            Row dataRow = sheet.createRow(rowIndex++);
            createProductCell(dataRow, 0, row.get(0), productStyle);
            createProductCell(dataRow, 1, row.get(1), productStyle);
            createProductCell(dataRow, 2, row.get(2), productStyle);
            createProductCell(dataRow, 3, row.get(3), productStyle);
        }

        for (int colNum = 0; colNum < 4; colNum++) {
            sheet.autoSizeColumn(colNum);
        }

        // Create empty row for spacing
        Row emptyRow2 = sheet.createRow(rowIndex++);
        emptyRow2.setHeightInPoints(10); // Adjust the height for spacing

        // Create row for Total Stock Qty Available
        Row totalQtyRow = sheet.createRow(rowIndex++);
        createHeaderCell(totalQtyRow, 0, "Total Stock Qty Available:", headerStyle);
        createHeaderCell(totalQtyRow, 1, stockQty.get(name), qtyStyle);

        // Create empty row for additional spacing
        Row emptyRow3 = sheet.createRow(rowIndex++);
        emptyRow3.setHeightInPoints(50); // Adjust the height for additional spacing
    }

    // Write the workbook to a file
    try (FileOutputStream fileOut = new FileOutputStream(file)) {
        workbook.write(fileOut);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

        workbook.close();
}

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createProductStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private CellStyle createQtyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private void createHeaderCell(Row row, int cellIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value.toString());
        cell.setCellStyle(style);
    }

    private void createProductCell(Row row, int cellIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value.toString());
        cell.setCellStyle(style);
    }

}
