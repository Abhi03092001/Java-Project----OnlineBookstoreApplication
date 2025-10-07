package com.bookstore.util;

import com.bookstore.model.Book;
import com.bookstore.model.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ReceiptGenerator {

    private static final String RECEIPTS_DIR = "receipts";

    public static String generatePdf(int billNo, User user, Map<Book, Integer> cart,
                                     String address, String phone, BigDecimal total) throws IOException, DocumentException {
        ensureReceiptsDir();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = String.format("bill_%d_%s.pdf", billNo, timestamp);
        File out = new File(RECEIPTS_DIR, filename);

        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(out));
        doc.open();

        doc.add(new Paragraph("BOOKSTORE BILL"));
        doc.add(new Paragraph("========================================"));
        doc.add(new Paragraph("Bill No: " + billNo));
        doc.add(new Paragraph("Date: " + new Date().toString()));
        doc.add(new Paragraph("Customer: " + safe(user.getFullName())));
        doc.add(new Paragraph("Phone: " + safe(phone)));
        doc.add(new Paragraph("Address: " + safe(address)));
        doc.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell(new PdfPCell(new Paragraph("Title")));
        table.addCell(new PdfPCell(new Paragraph("Qty")));
        table.addCell(new PdfPCell(new Paragraph("Price")));
        table.addCell(new PdfPCell(new Paragraph("Subtotal")));

        for (Map.Entry<Book, Integer> e : cart.entrySet()) {
            Book book = e.getKey();
            int qty = e.getValue();
            BigDecimal price = book.getPrice();
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(qty));

            table.addCell(new PdfPCell(new Paragraph(book.getTitle())));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(qty))));
            table.addCell(new PdfPCell(new Paragraph(String.format("$%.2f", price))));
            table.addCell(new PdfPCell(new Paragraph(String.format("$%.2f", subtotal))));
        }

        doc.add(table);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("----------------------------------------"));
        doc.add(new Paragraph(String.format("TOTAL: $%.2f", total)));
        doc.add(new Paragraph("========================================"));
        doc.add(new Paragraph("Thank you for shopping with us!"));

        doc.close();
        return out.getAbsolutePath();
    }

    public static String generateText(int billNo, User user, Map<Book, Integer> cart,
                                      String address, String phone, BigDecimal total) throws IOException {
        ensureReceiptsDir();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = String.format("bill_%d_%s.txt", billNo, timestamp);
        File out = new File(RECEIPTS_DIR, filename);
        StringBuilder sb = new StringBuilder();
        sb.append("BOOKSTORE BILL\n");
        sb.append("========================================\n");
        sb.append("Bill No: ").append(billNo).append("\n");
        sb.append("Date: ").append(new Date().toString()).append("\n");
        sb.append("Customer: ").append(safe(user.getFullName())).append("\n");
        sb.append("Phone: ").append(safe(phone)).append("\n");
        sb.append("Address: ").append(safe(address)).append("\n");
        sb.append("----------------------------------------\n");
        for (Map.Entry<Book, Integer> e : cart.entrySet()) {
            Book book = e.getKey();
            int qty = e.getValue();
            BigDecimal price = book.getPrice();
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(qty));
            sb.append(String.format("%s x%d - $%.2f\n", book.getTitle(), qty, subtotal));
        }
        sb.append("----------------------------------------\n");
        sb.append(String.format("TOTAL: $%.2f\n", total));
        sb.append("========================================\n");
        sb.append("Thank you for shopping with us!\n");
        java.nio.file.Files.write(out.toPath(), sb.toString().getBytes());
        return out.getAbsolutePath();
    }

    private static void ensureReceiptsDir() throws IOException {
        File dir = new File(RECEIPTS_DIR);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create receipts directory at " + dir.getAbsolutePath());
        }
    }

    private static String safe(String s) { return s == null ? "" : s; }
}
