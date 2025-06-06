package com.example.assignment1_api.controller;

import com.example.assignment1_api.entity.user.User;
import com.example.assignment1_api.repository.UserRepository;
import com.example.assignment1_api.service.report.ReportService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/jasper")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportController {
    ReportService reportService;

    UserRepository userRepository;

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf() {
        try{
            List<User> users = userRepository.findAll();
            byte[] pdfContent = reportService.generatePdfListUser(users);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=report.pdf");
            headers.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new byte[0]);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new byte[0]);
        }
    }
}
