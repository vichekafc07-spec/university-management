package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.StaffRequest;
import com.ume.studentsystem.dto.request.StaffUpdateRequest;
import com.ume.studentsystem.dto.response.StaffResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.spec.SpecificationBuilder;
import com.ume.studentsystem.mapper.StaffMapper;
import com.ume.studentsystem.model.Staff;
import com.ume.studentsystem.model.enums.StaffPosition;
import com.ume.studentsystem.repository.DepartmentRepository;
import com.ume.studentsystem.repository.FacultyRepository;
import com.ume.studentsystem.repository.StaffRepository;
import com.ume.studentsystem.repository.UserRepository;
import com.ume.studentsystem.service.StaffService;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final StaffMapper staffMapper;

    @Override
    public StaffResponse addStaff(StaffRequest req) {

        var faculty = facultyRepository.findById(req.facultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

        var dept = departmentRepository.findById(req.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        var user = userRepository.findById(req.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (staffRepository.existsByUserId(req.userId())){
            throw new BadRequestException("This user is already assigned to another staff");
        }

        var staff = staffMapper.toEntity(req);
        staff.setFaculty(faculty);
        staff.setDepartment(dept);
        staff.setUser(user);
        staff.setStaffCode(generateStaffCode());
        staffRepository.save(staff);

        return staffMapper.toResponse(staff);
    }

    @Override
    public StaffResponse updateStaff(StaffUpdateRequest request, Long id) {
        var staff = getStaffById(id);

        var faculty = facultyRepository.findById(request.facultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found " + request.facultyId()));

        var dept = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found "));

        staffMapper.updateStaff(request, staff);
        staff.setFaculty(faculty);
        staff.setDepartment(dept);

        var saved = staffRepository.save(staff);
        return staffMapper.toResponse(saved);
    }

    @Override
    public String deactivate(Long id) {
        var staff = getStaffById(id);
        staff.setActive(false);
        staffRepository.save(staff);
        return "Staff deactivate successfully";
    }

    @Override
    public String activate(Long id) {
        var staff = getStaffById(id);
        staff.setActive(true);
        staffRepository.save(staff);
        return "Staff activate successfully";
    }

    @Override
    public PageResponse<StaffResponse> getAllStaff(Long id, String fullName, String position, String faculty, Boolean active, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Staff> spec = new SpecificationBuilder<Staff>()
                .equal("id", id)
                .like("fullName", fullName)
                .like("position", position)
                .like("faculty", faculty)
                .equal("active", active)
                .build();

        List<String> allowSort = List.of("id","fullName","position","faculty","active");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1 ,size,sort);
        Page<Staff> staffPage = staffRepository.findAll(spec,pageable);
        return PageResponse.from(staffPage,staffMapper::toResponse);
    }

    @Override
    public List<StaffResponse> getByDepartment(Integer id) {
        return staffRepository.findByDepartmentId(id)
                .stream()
                .map(staffMapper::toResponse)
                .toList();
    }

    @Override
    public List<StaffResponse> getByFaculty(Byte id) {
        return staffRepository.findByFacultyId(id)
                .stream()
                .map(staffMapper::toResponse)
                .toList();
    }

    @Override
    public List<StaffResponse> getLecturers() {
        return staffRepository.findByPosition(StaffPosition.LECTURER)
                .stream()
                .map(staffMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        var staff = getStaffById(id);
        staffRepository.delete(staff);
    }

    @Override
    public String restore(Long id) {
        var staff = staffRepository.findByIdIncludingDeleted(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Staff not found " + id));
        staff.setDeleted(false);
        staff.setDeletedAt(null);
        staff.setActive(true);
        staffRepository.save(staff);
        return "Staff restored successfully";
    }

    private Staff getStaffById(Long id) {
        return staffRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found " + id));
    }



    private String generateStaffCode() {
        String prefix = "UME";
        String lastCode = staffRepository.findLastStaffCode();
        if (lastCode == null) {
            return prefix + "-001";
        }
        int number = Integer.parseInt(lastCode.split("-")[1]);
        number++;
        return prefix + "-" + String.format("%03d", number);
    }
}
