package Java_Project.Vehicle_Insurance_Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import Java_Project.Vehicle_Insurance_Management.model.Member;
import Java_Project.Vehicle_Insurance_Management.model.Claim;
import Java_Project.Vehicle_Insurance_Management.model.Vendor;
import Java_Project.Vehicle_Insurance_Management.repository.MemberRepository;
import Java_Project.Vehicle_Insurance_Management.repository.ClaimRepository;
import Java_Project.Vehicle_Insurance_Management.repository.VendorRepository;

@Service
public class ReportService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private VendorRepository vendorRepository;

    // 🔹 MEMBER REPORT: PDF
    public void generateMemberReportPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=member-report.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("📄 Member Report"));

        // Retrieve data dynamically from the MemberRepository
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            document.add(new Paragraph("ID: " + member.getId() + "\nName: " + member.getName() + "\nEmail: " + member.getEmail()));
        }

        document.close();
    }

    // 🔹 CLAIM REPORT: PDF
    public void generateClaimReportPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=claim-report.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("📄 Claim Report"));

        // Retrieve data dynamically from the ClaimRepository
        List<Claim> claims = claimRepository.findAll();
        for (Claim claim : claims) {
            document.add(new Paragraph("Type: " + claim.getType() + "\nStatus: " + claim.getStatus() + "\nDate: " + claim.getClaimDate()));
        }

        document.close();
    }

    // 🔹 VENDOR REPORT: PDF
    public void generateVendorReportPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=vendor-report.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("🧾 Partner Vendor Report"));

        // Retrieve data dynamically from the VendorRepository
        List<Vendor> vendors = vendorRepository.findAll();
        for (Vendor vendor : vendors) {
            document.add(new Paragraph("Name: " + vendor.getName() + "\nStatus: " + vendor.getStatus()));
        }

        document.close();
    }

    // 🔹 MEMBER REPORT: CSV
    public void generateMemberReportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=member-report.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,First Name,Last Name,Email,Phone");

        // Retrieve data dynamically from the MemberRepository
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            writer.println(member.getId() + "," + member.getFirstName() + "," + member.getLastName() + "," + member.getEmail() + "," + member.getPhone());
        }

        writer.flush();
    }

    // 🔹 CLAIM REPORT: CSV
    public void generateClaimReportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=claim-report.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Type,Status,Date,Vehicle,Vendor");

        // Retrieve data dynamically from the ClaimRepository
        List<Claim> claims = claimRepository.findAll();
        for (Claim claim : claims) {
            writer.println(claim.getId() + "," + claim.getType() + "," + claim.getStatus() + "," + claim.getClaimDate() + "," + claim.getVehicleDetails() + "," + claim.getVendor());
        }

        writer.flush();
    }

    // 🔹 VENDOR REPORT: CSV
    public void generateVendorReportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=vendor-report.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Vendor Name,Status");

        // Retrieve data dynamically from the VendorRepository
        List<Vendor> vendors = vendorRepository.findAll();
        for (Vendor vendor : vendors) {
            writer.println(vendor.getId() + "," + vendor.getName() + "," + vendor.getStatus());
        }

        writer.flush();
    }

//    // 🔹 CUSTOM DATE RANGE REPORT: PDF
//    public void generateCustomReport(LocalDate start, LocalDate end, HttpServletResponse response) throws IOException {
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=custom-report.pdf");
//
//        PdfWriter writer = new PdfWriter(response.getOutputStream());
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf);
//
//        document.add(new Paragraph("📆 Custom Report"));
//        document.add(new Paragraph("Start Date: " + start));
//        document.add(new Paragraph("End Date: " + end));
//        document.add(new Paragraph("• Include filtered logic here based on the date range."));
//
//        // Example: You can filter claims or policies based on the date range
//        List<Claim> claimsInRange = claimRepository.find(start, end);
//        for (Claim claim : claimsInRange) {
//            document.add(new Paragraph("Claim ID: " + claim.getId() + "\nDate: " + claim.getDate() + "\nType: " + claim.getType()));
//        }
//
//        document.close();
//    }
}
