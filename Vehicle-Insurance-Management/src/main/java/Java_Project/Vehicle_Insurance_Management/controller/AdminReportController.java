package Java_Project.Vehicle_Insurance_Management.controller;

import Java_Project.Vehicle_Insurance_Management.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/generate-report")
    public String showReportPage() {
        return "Administrator/ManageReport/manage-report"; // or "Administrator/Report/generate-report" depending on your file path
    }

    // === MEMBER REPORTS ===
    @GetMapping("/reports/members/pdf")
    public void downloadMemberPDF(HttpServletResponse response) throws IOException {
        reportService.generateMemberReportPDF(response);
    }

    @GetMapping("/reports/members/csv")
    public void downloadMemberCSV(HttpServletResponse response) throws IOException {
        reportService.generateMemberReportCSV(response);
    }

    // === CLAIM REPORTS ===
    @GetMapping("/reports/claims/pdf")
    public void downloadClaimsPDF(HttpServletResponse response) throws IOException {
        reportService.generateClaimReportPDF(response);
    }

    @GetMapping("/reports/claims/csv")
    public void downloadClaimsCSV(HttpServletResponse response) throws IOException {
        reportService.generateClaimReportCSV(response);
    }

    // === VENDOR REPORTS ===
    @GetMapping("/reports/vendors/pdf")
    public void downloadVendorsPDF(HttpServletResponse response) throws IOException {
        reportService.generateVendorReportPDF(response);
    }

    @GetMapping("/reports/vendors/csv")
    public void downloadVendorsCSV(HttpServletResponse response) throws IOException {
        reportService.generateVendorReportCSV(response);
    }

    // === CUSTOM REPORT ===
//    @GetMapping("/reports/custom")
//    public void generateCustomReport(
//            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//            HttpServletResponse response
//    ) throws IOException {
//        reportService.generateCustomReport(startDate, endDate, response);
//    }
}
