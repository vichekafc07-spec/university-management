package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.response.DashboardResponse;
import com.ume.studentsystem.repository.*;
import com.ume.studentsystem.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final StudentRepository studentRepository;
    private final InvoiceRepository invoiceRepository;
    private final AttendanceRepository attendanceRepository;
    private final FacultyRepository facultyRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public DashboardResponse getDashboard() {
        LocalDate now = LocalDate.now();
        Long totalStudents = studentRepository.countAllStudents();
        Long paidStudents = invoiceRepository.countPaidStudents();
        Long unpaidStudents = invoiceRepository.countUnpaidStudents();
        Double attendance = attendanceRepository.getAttendancePercent();
        String topFaculty = facultyRepository.getTopFaculty();
        Double revenue = paymentRepository.getRevenueThisMonth(now.getMonthValue(), now.getYear());

        return new DashboardResponse(
                totalStudents,
                paidStudents,
                unpaidStudents,
                attendance == null ? 0 : attendance,
                topFaculty,
                revenue
        );
    }
}
