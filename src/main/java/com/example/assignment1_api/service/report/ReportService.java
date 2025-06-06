package com.example.assignment1_api.service.report;

import com.example.assignment1_api.dto.user.UserDto;
import com.example.assignment1_api.dto.user.UserReportDto;
import com.example.assignment1_api.entity.user.User;
import com.example.assignment1_api.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRRecordedValues;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportService {

    public byte[] generatePdfListUser(List<User> users) throws FileNotFoundException, JRException {

        try {
            List<UserDto> listUser = users.stream()
                    .map(user -> new UserDto(
                            user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getUsername()
                    ))
                    .collect(Collectors.toList());

            InputStream reportStream = getClass().getClassLoader().getResourceAsStream("reports/BookStoreJasperReport.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("Jasper report file not found in classpath");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listUser);
            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            return outputStream.toByteArray();
        }catch (JRException e) {
            System.out.println("JRException: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
