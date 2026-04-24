package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.response.FacultyTopStudentResponse;
import com.ume.studentsystem.dto.response.RankingResponse;
import com.ume.studentsystem.service.ReportService;
import com.ume.studentsystem.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/classrooms/{id}/students")
    public ResponseEntity<InputStreamResource> classroomStudentList(@PathVariable Long id) {
        ByteArrayInputStream pdf = reportService.classroomStudentList(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=classroom-student-list.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping("/classrooms/{id}/students/excel")
    public ResponseEntity<InputStreamResource> excel(@PathVariable Long id) {
        ByteArrayInputStream file = reportService.exportStudentsExcel(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(file));
    }

    @GetMapping("/sessions/{id}/attendance")
    public ResponseEntity<InputStreamResource> attendance(@PathVariable Long id) {

        ByteArrayInputStream pdf = reportService.attendanceSheet(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=attendance-sheet.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping("/exams/{examId}/seat-list")
    public ResponseEntity<InputStreamResource> generateSeatList(@PathVariable Long examId) {
        ByteArrayInputStream pdf = reportService.generateSeatList(examId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=exam-seat-list.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping("/students/{studentId}/transcript")
    public ResponseEntity<InputStreamResource> generate(@PathVariable Long studentId) {

        ByteArrayInputStream pdf = reportService.generateTranscript(studentId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=transcript.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping("/classrooms/{classroomId}")
    public ResponseEntity<APIResponse<List<RankingResponse>>> rankingByClassroom(@PathVariable Long classroomId) {
        return ResponseEntity.ok(APIResponse.ok(reportService.rankByClassroom(classroomId)));
    }

    @GetMapping("/dean-list/faculties/{facultyId}")
    public ResponseEntity<APIResponse<List<FacultyTopStudentResponse>>> getTopStudent(@PathVariable Long facultyId){
        return ResponseEntity.ok(APIResponse.ok(reportService.getTopStudentsByFaculty(facultyId)));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<InputStreamResource> download(@PathVariable Long paymentId) {

        ByteArrayInputStream pdf = reportService.generateReceipt(paymentId);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", "inline; filename=receipt.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

}
