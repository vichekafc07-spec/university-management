package com.ume.studentsystem.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.repository.StudentRepository;
import com.ume.studentsystem.service.StudentReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class StudentReportServiceImpl implements StudentReportService {

    private final StudentRepository studentRepository;

    @Override
    public ByteArrayInputStream exportByGeneration(Integer generation) {

        var students = studentRepository.findByGeneration(generation);

        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No students found");
        }

        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);

            Paragraph title = new Paragraph("Student Registration List - Generation " + generation, titleFont);

            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);

            document.add(title);

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);

            table.setWidths(new int[]{1,2,4,2,3,3});

            addHeader(table, "No");
            addHeader(table, "Code");
            addHeader(table, "Name");
            addHeader(table, "Gender");
            addHeader(table, "Major");
            addHeader(table, "Phone");

            int no = 1;

            for (var s : students) {

                table.addCell(String.valueOf(no++));
                table.addCell(s.getStudentCode());
                table.addCell(s.getFullName());
                table.addCell(s.getGender().name());
                table.addCell(s.getMajor());
                table.addCell(s.getPhone());
            }

            document.add(table);

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph(
                    "Total Students: " + students.size()
            ));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("PDF failed");
        }

        return new ByteArrayInputStream(
                out.toByteArray()
        );
    }

    private void addHeader(PdfPTable table, String title) {

        PdfPCell header = new PdfPCell();
        header.setPhrase(new Phrase(title));
        header.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        table.addCell(header);
    }
}
