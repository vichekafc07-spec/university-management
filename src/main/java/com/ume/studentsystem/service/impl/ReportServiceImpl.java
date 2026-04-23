package com.ume.studentsystem.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.repository.ExamRepository;
import com.ume.studentsystem.repository.SessionRepository;
import com.ume.studentsystem.repository.StudentClassroomRepository;
import com.ume.studentsystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final StudentClassroomRepository studentClassroomRepository;
    private final SessionRepository sessionRepository;
    private final ExamRepository examRepository;

    @Override
    public ByteArrayInputStream classroomStudentList(Long classroomId) {

        var list = studentClassroomRepository.findAllByClassroomId(classroomId);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No students found");
        }

        var classroom = list.getFirst().getClassroom();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            Document document = new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(document, out);

            document.open();

            Font title = new Font(Font.HELVETICA, 16, Font.BOLD);

            Font text = new Font(Font.HELVETICA, 10);

            Paragraph header = new Paragraph("MY UNIVERSITY\nCLASSROOM STUDENT LIST", title);

            header.setAlignment(Element.ALIGN_CENTER);

            document.add(header);
            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Classroom: "
                            + classroom.getName()
                            + "    Year: "
                            + classroom.getYear()
                            + "    Semester: "
                            + classroom.getSemester(),
                    text
            ));

            document.add(new Paragraph("Generation: " + classroom.getGeneration(), text));

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(8);

            table.setWidthPercentage(100);

            table.setWidths(new float[]{1, 2, 4, 1.5f,2 , 4 , 3, 2});

            addHeader(table, "No");
            addHeader(table, "Code");
            addHeader(table, "Full Name");
            addHeader(table, "Gender");
            addHeader(table, "Phone");
            addHeader(table, "Faculty");
            addHeader(table, "Department");
            addHeader(table, "Sign");

            int no = 1;

            for (var row : list) {

                var s = row.getStudent();

                table.addCell(String.valueOf(no++));
                table.addCell(s.getStudentCode());
                table.addCell(s.getFullName());
                table.addCell(String.valueOf(s.getGender()));
                table.addCell(s.getPhone());
                table.addCell(s.getFaculty().getName());
                table.addCell(s.getDepartment().getName());
                table.addCell(" ");
            }

            document.add(table);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed");
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream exportStudentsExcel(Long classroomId) {

        var list = studentClassroomRepository.findAllByClassroomId(classroomId);

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Students");

            Row header = sheet.createRow(0);

            String[] cols = {
                    "ល.រ",
                    "Code",
                    "Name",
                    "Gender",
                    "Phone",
                    "Faculty",
                    "Department"
            };

            for (int i = 0; i < cols.length; i++) {
                header.createCell(i).setCellValue(cols[i]);
            }

            int rowNum = 1;

            for (var sc : list) {

                var s = sc.getStudent();

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(rowNum - 1);
                row.createCell(1).setCellValue(s.getStudentCode());
                row.createCell(2).setCellValue(s.getFullName());
                row.createCell(3).setCellValue(s.getGender().name());
                row.createCell(4).setCellValue(s.getPhone());
                row.createCell(5).setCellValue(s.getFaculty().getName());
                row.createCell(6).setCellValue(s.getDepartment().getName());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Excel export failed");
        }
    }

    @Override
    public ByteArrayInputStream attendanceSheet(Long sessionId) {

        var session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        var students = studentClassroomRepository.findByClassroomId(session.getLecturerAssignment().getClassroom().getId());

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);

            document.open();

            Font title = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font text = new Font(Font.HELVETICA, 10);

            // Header
            document.add(new Paragraph("MY UNIVERSITY", title));
            document.add(new Paragraph("ATTENDANCE SHEET", text));

            document.add(new Paragraph(" "));

            // Session Info
            document.add(new Paragraph("Subject: " + session.getLecturerAssignment().getSubject().getTitle(), text));

            document.add(new Paragraph("Classroom: " + session.getLecturerAssignment().getClassroom().getName(), text));

            document.add(new Paragraph("Date: " + session.getDay(), text));

            document.add(new Paragraph(
                    "Time: " + session.getStartTime()
                            + " - " + session.getEndTime(),
                    text));

            document.add(new Paragraph("Room: " + session.getRoom().getName(), text));

            document.add(new Paragraph(" "));

            // Table
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            table.setWidths(new float[]{1, 3, 5, 2, 2, 2, 3});

            addHeader(table, "No");
            addHeader(table, "Code");
            addHeader(table, "Full Name");
            addHeader(table, "Present");
            addHeader(table, "Absent");
            addHeader(table, "Late");
            addHeader(table, "Signature");

            int no = 1;

            for (var sc : students) {

                var s = sc.getStudent();

                table.addCell(String.valueOf(no++));
                table.addCell(s.getStudentCode());
                table.addCell(s.getFullName());

                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
                table.addCell(" ");
            }

            document.add(table);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Attendance sheet generation failed");
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream generateSeatList(Long examId) {

        var exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        var students = studentClassroomRepository.findStudents(exam.getClassroom().getId());

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, out);

            document.open();

            Font title = new Font(Font.HELVETICA, 16, Font.BOLD);

            Font text = new Font(Font.HELVETICA, 10);
            // Header
            document.add(new Paragraph("MY UNIVERSITY", title));
            document.add(new Paragraph("EXAM SEATING LIST", text));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Subject: " + exam.getSubject().getTitle(), text));
            document.add(new Paragraph("Classroom: " + exam.getClassroom().getName(), text));
            document.add(new Paragraph("Room: " + exam.getRoom().getName(), text));
            document.add(new Paragraph("Date: " + exam.getExamDate(), text));
            document.add(new Paragraph("Time: " + exam.getStartTime() + " - " + exam.getEndTime(), text));
            document.add(new Paragraph(" "));

            // Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            table.setWidths(new float[]{1, 3, 5, 3});

            addHeader(table, "Seat No");
            addHeader(table, "Student Code");
            addHeader(table, "Full Name");
            addHeader(table, "Signature");

            int seat = 1;

            for (var sc : students) {

                var s = sc.getStudent();

                table.addCell(String.valueOf(seat++));
                table.addCell(s.getStudentCode());
                table.addCell(s.getFullName());
                table.addCell(" ");
            }

            document.add(table);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Exam seat list generation failed");
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addHeader(PdfPTable table, String title) {

        PdfPCell cell = new PdfPCell(new Phrase(title));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPadding(5);

        table.addCell(cell);
    }
}
