package com.ume.studentsystem.util;

import com.ume.studentsystem.dto.request.student.StudentRequest;
import com.ume.studentsystem.dto.response.student.StudentResponse;
import com.ume.studentsystem.model.enums.*;

public class StudentMockTest {

    public static StudentRequest studentRequest(){

        return new StudentRequest(
                "John Doe",
                "ST001",
                GenderStatus.MALE,
                null,
                "012345678",
                "john@gmail.com",
                ProgramType.BACHELOR,
                PaymentType.PAID,
                (byte) 1,
                1,
                "Computer Science",
                "2026",
                28,
                StudyTime.MORNING,
                StudentStatus.ACTIVE
        );
    }

    public static StudentResponse studentResponse(){
        return new StudentResponse(
                1L,
                "ST001",
                "John Doe",
                GenderStatus.MALE,
                "012345678",
                "WEEKEND",
                "john@gmail.com",
                null,
                ProgramType.BACHELOR,
                PaymentType.PAID,
                "CS",
                "IT",
                "Computer Science",
                "2026",
                28,
                "ACTIVE");
    }
}
