package com.ume.studentsystem.service.impl;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ume.studentsystem.dto.response.TranscriptResponse;
import com.ume.studentsystem.service.TranscriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class TranscriptPdfService {

        private final TranscriptService transcriptService;

        public ByteArrayInputStream export(Long studentId) {

            TranscriptResponse data = transcriptService.getTranscript(studentId);

            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try {
                PdfWriter.getInstance(document, out);
                document.open();

                Font titleFont =
                        new Font(Font.HELVETICA, 18, Font.BOLD);

                Font normal =
                        new Font(Font.HELVETICA, 11);

                Font bold =
                        new Font(Font.HELVETICA, 11, Font.BOLD);

                Paragraph title = new Paragraph("UNIVERSITY OF MANAGEMENT AND ECONOMICS", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);

                document.add(new Paragraph(
                        "Student Name: " + data.studentName(), normal));

                document.add(new Paragraph(
                        "Student Code: " + data.studentCode(), normal));

                document.add(new Paragraph(
                        "Faculty: " + data.faculty(), normal));

                document.add(new Paragraph(
                        "Department: " + data.department(), normal));

                document.add(new Paragraph(
                        "Major: " + data.major(), normal));

                document.add(Chunk.NEWLINE);

                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100);

                table.setWidths(new int[]{2,4,4,2,1,1});

                addHeader(table, "Code");
                addHeader(table, "Subject");
                addHeader(table, "Credit");
                addHeader(table, "Score");
                addHeader(table, "Grade");
                addHeader(table, "GPA");

                for (var item : data.subjects()) {

                    table.addCell(item.subjectCode());
                    table.addCell(item.subjectTitle());
                    table.addCell(String.valueOf(item.credit()));
                    table.addCell(String.valueOf(item.totalScore()));
                    table.addCell(item.grade().name());
                    table.addCell(String.valueOf(item.gpa()));
                }

                document.add(table);

                document.add(Chunk.NEWLINE);

                document.add(new Paragraph(
                        "Total Credits: " + data.totalCredits(), bold));

                document.add(new Paragraph(
                        "CGPA: " + data.cgpa(), bold));

                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);

                document.add(new Paragraph(
                        "Registrar Signature: __________________",
                        normal));

                document.close();

            } catch (Exception e) {
                throw new RuntimeException("PDF generation failed");
            }

            return new ByteArrayInputStream(out.toByteArray());
        }

        private void addHeader(PdfPTable table, String title) {

            PdfPCell header = new PdfPCell();
            header.setPhrase(new Phrase(title));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        }
    }