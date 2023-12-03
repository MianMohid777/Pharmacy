package Application.Model;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Set;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;


public class Order extends ItemContainer{
   private String customerName;

   private String name;

   private LocalDateTime timeStamp;

   private float totalPrice;



    public Order(String customerName, String name, LocalDateTime timeStamp, float totalPrice) {
        super();
        this.customerName = customerName;
        this.name = name;
        this.timeStamp = timeStamp;
        this.totalPrice = totalPrice;
    }

    public Order() {
        super();
        this.customerName = "";
        this.timeStamp = null;
        totalPrice = 0.0F;
    }

    public void cancel()
    {
        this.getItemMap().clear();
        customerName = "";
        timeStamp = null;
        totalPrice = 0.0F;
    }

    public void generateInvoice() {
        Set<String> keys = super.getItemMap().keySet();

        totalPrice = super.total();

        System.out.println("Receipt Id: " + id);
        System.out.println("User: " + name);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Order DateTime: " + timeStamp);

        System.out.println("\n Item     \t" + "Qty     \t" + "Price     \t" + "Total Price  ");
        for (String s : keys) {
            Item i = super.getItemMap().get(s);

            System.out.println(s + " \t     " + i.getQtyOrdered() + "     \t" + i.getP().getPrice() + "     \t" + i.totalPrice());
        }

        System.out.println(" \n\t\t   Grand Total : \t" + totalPrice);


        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("/Users/FullStackMohid/IdeaProjects/PharamacyPos/Invoices/POS-Invoice-" + id + ".pdf"));
            document.open();

            Image logo = Image.getInstance("/Users/FullStackMohid/IdeaProjects/PharamacyPos/src/Resources.PharmacyLogo.jpeg");
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
            document.add(Chunk.NEWLINE);

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Paragraph title = new Paragraph("Pharmacy Bill", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);


            Font detailsFont = new Font(Font.FontFamily.COURIER, 12, Font.BOLD, BaseColor.BLACK);
            document.add(new Paragraph("Receipt Id: " + id, detailsFont));
            document.add(new Paragraph("User: " + name, detailsFont));
            document.add(new Paragraph("Customer Name: " + customerName, detailsFont));
            document.add(new Paragraph("Order DateTime: " + timeStamp, detailsFont));

            PdfPTable table = new PdfPTable(4);

            table.setWidthPercentage(100);


            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            document.add(Chunk.NEWLINE);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
            PdfPCell headerCell1 = new PdfPCell(new Phrase("Item", headerFont));
            PdfPCell headerCell2 = new PdfPCell(new Phrase("Qty", headerFont));
            PdfPCell headerCell3 = new PdfPCell(new Phrase("Price", headerFont));
            PdfPCell headerCell4 = new PdfPCell(new Phrase("Total Price", headerFont));
            headerCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell1);

            headerCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell2);

            headerCell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell3);

            headerCell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell4);

            Font cellFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.DARK_GRAY);

            for (String s : keys) {
                Item i = super.getItemMap().get(s);

                PdfPCell cell1 = new PdfPCell(new Phrase(s, cellFont));
                table.addCell(cell1);
                PdfPCell cell2 = new PdfPCell(new Phrase(String.valueOf(i.getQtyOrdered()), cellFont));
                table.addCell(cell2);
                PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(i.getP().getPrice()), cellFont));
                table.addCell(cell3);
                PdfPCell cell4 = new PdfPCell(new Phrase(String.valueOf(i.totalPrice()), cellFont));
                table.addCell(cell4);
            }

            document.add(table);

            Font totalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.RED);
            Paragraph total = new Paragraph("Grand Total: " + totalPrice, totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();

            System.out.println("PDF generated successfully.");

            File pdfFile = new File("/Users/FullStackMohid/IdeaProjects/PharamacyPos/Invoices/POS-Invoice-" + id + ".pdf");
            Desktop desktop = Desktop.getDesktop();

            if (pdfFile.exists()) {
                desktop.print(pdfFile);
            } else {
                System.out.println("PDF File Not Found.");
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getTotalPriceFromContainer()
    {
        return super.total();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
