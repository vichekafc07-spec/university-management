package com.ume.studentsystem.service;

import java.io.ByteArrayInputStream;

public interface StudentReportService {
    ByteArrayInputStream exportByGeneration(Integer generation);
}
