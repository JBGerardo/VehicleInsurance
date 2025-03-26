package Java_Project.Vehicle_Insurance_Management.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@Service
public class ReportService {

    // 🔹 MEMBER REPORT: PDF
    public void generateMemberReportPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=member-report.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("📄 Member Report"));
        document.add(new Paragraph("ID: 1\nName: John Doe\nEmail: john@example.com"));
        document.add(new Paragraph("ID: 2\nName: Jane Smith\nEmail: jane@example.com"));

        document.close();
    }

    // 🔹 MEMBER REPORT: CSV
    public void generateMemberReportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=member-report.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,First Name,Last Name,Email,Phone");
        writer.println("1,John,Doe,john@example.com,123-4567");
        writer.println("2,Jane,Smith,jane@example.com,987-6543");
        writer.flush();
    }

    // 🔹 CLAIM REPORT: PDF
    public void generateClaimReportPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=claim-report.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("📄 Claim Report"));
        document.add(new Paragraph("Type: Accident\nStatus: Approved\nDate: 2025-03-25"));
        document.add(new Paragraph("Type: Theft\nStatus: Pending\nDate: 2025-03-26"));

        document.close();
    }

    // 🔹 CLAIM REPORT: CSV
    public void generateClaimReportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=claim-report.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Type,Status,Date,Vehicle,Vendor");
        writer.println("1,Accident,Approved,2025-03-25,CRV,AutoFix Center");
        writer.println("2,Theft,Pending,2025-03-26,Civic,DriveWell Repairs");
        writer.flush();
    }

    // 🔹 VENDOR REPORT: PDF
    public void generateVendorReportPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=vendor-report.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("🧾 Partner Vendor Report"));
        document.add(new Paragraph("Name: AutoFix Center\nStatus: Active"));
        document.add(new Paragraph("Name: Speedy Garage\nStatus: Pending"));

        document.close();
    }

    // 🔹 VENDOR REPORT: CSV
    public void generateVendorReportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=vendor-report.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Vendor Name,Status");
        writer.println("1,AutoFix Center,Active");
        writer.println("2,DriveWell Repairs,Pending");
        writer.flush();
    }

    // 🔹 CUSTOM DATE RANGE REPORT: PDF
    public void generateCustomReport(LocalDate start, LocalDate end, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=custom-report.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("📆 Custom Report"));
        document.add(new Paragraph("Start Date: " + start));
        document.add(new Paragraph("End Date: " + end));
        document.add(new Paragraph("• Include filtered logic here based on the date range."));

        document.close();
    }
}
