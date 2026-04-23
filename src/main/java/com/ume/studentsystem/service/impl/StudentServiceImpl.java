package com.ume.studentsystem.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.ume.studentsystem.dto.request.StudentRequest;
import com.ume.studentsystem.dto.response.StudentResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.DuplicateResourceException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.model.Department;
import com.ume.studentsystem.model.Faculty;
import com.ume.studentsystem.spec.SpecificationBuilder;
import com.ume.studentsystem.mapper.StudentMapper;
import com.ume.studentsystem.model.Student;
import com.ume.studentsystem.repository.DepartmentRepository;
import com.ume.studentsystem.repository.FacultyRepository;
import com.ume.studentsystem.repository.StudentRepository;
import com.ume.studentsystem.service.StudentService;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponse create(StudentRequest request, MultipartFile photo) {
        var faculty = getFacultyId(request.facultyId());
        var department = getDepartmentId(request.departmentId());

        if (studentRepository.existsByFullName(request.fullName())){
            throw new DuplicateResourceException("this is name already exists");
        }

        var student = studentMapper.toEntity(request);
        student.setFaculty(faculty);
        student.setDepartment(department);

        if (photo !=null && !photo.isEmpty()){
            student.setPhotoUrl(savePhoto(photo));
        }

        var saved = studentRepository.save(student);
        return studentMapper.toResponse(saved);
    }

    @Override
    public StudentResponse update(Long id, StudentRequest request) {
        var student = getById(id);
        var faculty = getFacultyId(request.facultyId());
        var department = getDepartmentId(request.departmentId());
        studentMapper.updateStudent(request,student);
        student.setFaculty(faculty);
        student.setDepartment(department);
        var saved = studentRepository.save(student);
        return studentMapper.toResponse(saved);
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        var student = getById(id);
        return studentMapper.toResponse(student);
    }

    @Override
    public PageResponse<StudentResponse> getAll(Long id, String fullName, String studentCode, String faculty, String major, Integer generation, String payment, String programType, String status, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Student> spec = new SpecificationBuilder<Student>()
                .equal("id", id)
                .like("fullName", fullName)
                .like("studentCode", studentCode)
                .like("faculty", faculty)
                .like("major", major)
                .equal("generation", generation)
                .like("paymentType", payment)
                .like("programType", programType)
                .like("status", status)
                .build();
        List<String> allowSort = List.of("id", "fullName", "studentCode", "faculty", "major", "generation", "payment", "programType", "status");
        var sort = SortResponse.sortResponse(sortBy, sortAs, allowSort);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Student> studentPage = studentRepository.findAll(spec, pageable);
        return PageResponse.from(studentPage, studentMapper::toResponse);
    }

    @Override
    public void delete(Long id) {
        var student = getById(id);
        studentRepository.delete(student);
    }

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

            document.add(new Paragraph("Total Students: " + students.size()));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("PDF failed");
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public StudentResponse updatePhoto(Long id, MultipartFile photo) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("student not found with id " + id));
        if (photo == null || photo.isEmpty()){
            throw new BadRequestException("Photo is required");
        }
        deleteOldPhoto(student.getPhotoUrl());
        String newPath = savePhoto(photo);
        student.setPhotoUrl(newPath);
        var saved = studentRepository.save(student);
        return studentMapper.toResponse(saved);
    }

    @Override
    public ByteArrayInputStream generateIdCard(Long id) {

        var student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        try {

            Rectangle card =
                    new Rectangle(243, 153);

            Document document =
                    new Document(card, 10, 10, 10, 10);

            PdfWriter writer =
                    PdfWriter.getInstance(document, out);

            document.open();

            Font title =
                    new Font(Font.HELVETICA, 12, Font.BOLD);

            Font normal =
                    new Font(Font.HELVETICA, 8);

            PdfContentByte cb =
                    writer.getDirectContent();

            // FRONT PAGE
            document.add(new Paragraph(
                    "MY UNIVERSITY",
                    title
            ));

            document.add(new Paragraph(
                    "STUDENT ID CARD",
                    normal
            ));

            // photo
            if (student.getPhotoUrl() != null) {

                Image photo =
                        Image.getInstance(
                                student.getPhotoUrl()
                        );

                photo.scaleToFit(60, 70);
                photo.setAbsolutePosition(15, 45);
                document.add(photo);
            }

            ColumnText.showTextAligned(
                    cb, Element.ALIGN_LEFT,
                    new Phrase(
                            "Name: "
                                    + student.getFullName(),
                            normal
                    ),
                    85, 105, 0
            );

            ColumnText.showTextAligned(
                    cb, Element.ALIGN_LEFT,
                    new Phrase(
                            "ID: "
                                    + student.getStudentCode(),
                            normal
                    ),
                    85, 90, 0
            );

            ColumnText.showTextAligned(
                    cb, Element.ALIGN_LEFT,
                    new Phrase(
                            "Major: "
                                    + student.getMajor(),
                            normal
                    ),
                    85, 75, 0
            );

            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, new Phrase("Gen: " + student.getGeneration(), normal), 85, 60, 0);

            // QR Code
            Image qr = createQr("http://localhost:8080/api/v1/students/" + student.getId());

            qr.scaleToFit(40, 40);
            qr.setAbsolutePosition(185, 30);

            document.add(qr);

            // BACK PAGE
            document.newPage();

            document.add(new Paragraph(
                    "MY UNIVERSITY",
                    title
            ));

            document.add(new Paragraph(
                    "VALID UNTIL: 2030",
                    normal
            ));

            document.add(new Paragraph(
                    "Signature: __________",
                    normal
            ));

            document.add(new Paragraph(
                    "Emergency: "
                            + student.getPhone(),
                    normal
            ));

            document.add(new Paragraph(
                    "If found, return to university",
                    normal
            ));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
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

    private Student getById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    private Faculty getFacultyId(Byte id){
        return facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + id ));
    }

    private Department getDepartmentId(Integer id){
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

    private String savePhoto(MultipartFile file){
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads/students");
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
                    );
            return "uploads/students/" + fileName;
        }catch (Exception e){
            throw new RuntimeException("Failed to upload file");
        }
    }

    private void deleteOldPhoto(String oldPath) {

        try {

            if (oldPath == null || oldPath.isBlank()) {
                return;
            }

            Path path = Paths.get(oldPath);

            Files.deleteIfExists(path);

        } catch (Exception e) {
            throw new RuntimeException("Invalid");
        }
    }

    private Image createQr(String text) throws Exception {

        BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 150, 150);

        ByteArrayOutputStream png = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(matrix, "PNG", png);

        return Image.getInstance(png.toByteArray());
    }

}
