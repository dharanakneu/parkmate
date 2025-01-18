package com.neu.csye6220.parkmate.view;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.neu.csye6220.parkmate.model.Booking;
import com.neu.csye6220.parkmate.model.ParkingSpot;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class BookingReceiptPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
                                    PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Set up the title
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, new Color(64, 64, 64));
        Paragraph title = new Paragraph("Booking Receipt", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Retrieve booking data
        Booking booking = (Booking) model.get("booking");
        ParkingSpot spot = booking.getParkingSpot();
        String spotNumber = spot.getSpotNumber();
        String address = spot.getParkingLocation().getStreet() + " " + spot.getParkingLocation().getCity() + " " + spot.getParkingLocation().getState();
        String startTime = booking.getStartTime().toString();
        String endTime = booking.getEndTime().toString();
        String totalCharges = "$" + booking.getPayment().getAmount();

        // Create a table for the details
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        table.setSpacingBefore(20);

        Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        Font contentFont = new Font(Font.HELVETICA, 12);

        addTableRow(table, "Spot Number", spotNumber, headerFont, contentFont);
        addTableRow(table, "Address", address, headerFont, contentFont);
        addTableRow(table, "Start Time", startTime, headerFont, contentFont);
        addTableRow(table, "End Time", endTime, headerFont, contentFont);
        addTableRow(table, "Total Charges", totalCharges, headerFont, contentFont);

        // Add the table to the document
        document.add(table);

        // Add a footer
        Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY);
        Paragraph footer = new Paragraph("Thank you for using ParkMate. Drive safe!", footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(30);
        document.add(footer);
    }


    private void addTableRow(PdfPTable table, String key, String value, Font headerFont, Font contentFont) {
        PdfPCell keyCell = new PdfPCell(new Phrase(key, headerFont));
        PdfPCell valueCell = new PdfPCell(new Phrase(value, contentFont));

        keyCell.setBackgroundColor(new Color(240, 240, 240));
        keyCell.setPadding(8);
        valueCell.setPadding(8);

        table.addCell(keyCell);
        table.addCell(valueCell);
    }

    public void generateReceiptPdf(HttpServletResponse response, Booking booking) throws Exception {
        // Prepare the document and writer
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        // Prepare the model
        Map<String, Object> model = new HashMap<>();
        model.put("booking", booking);

        // Start the document
        document.open();

        // Call the original buildPdfDocument method (protected)
        buildPdfDocument(model, document, writer, null, response);

        // Close the document
        document.close();
    }
}

