package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.AcademicTermRequest;
import com.ume.studentsystem.dto.response.AcademicTermResponse;
import com.ume.studentsystem.exceptions.DuplicateResourceException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.AcademicMapper;
import com.ume.studentsystem.model.AcademicTerm;
import com.ume.studentsystem.repository.AcademicTermRepository;
import com.ume.studentsystem.service.AcademicService;
import com.ume.studentsystem.spec.SpecificationBuilder;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicServiceImpl implements AcademicService {

    private final AcademicTermRepository academicTermRepository;
    private final AcademicMapper academicMapper;

    @Override
    public AcademicTermResponse create(AcademicTermRequest request) {
        academicTermRepository.findAcademicTermByYearAndSemester(request.year(), request.semester()).ifPresent(e ->{
            throw new DuplicateResourceException("Academic Year " + request.year() + " is already exist");
        });
        var academic = academicMapper.toEntity(request);
        var saved = academicTermRepository.save(academic);
        return academicMapper.toResponse(saved);
    }

    @Override
    public PageResponse<AcademicTermResponse> getAll(Long id, Integer year, Integer semester, LocalDate startDate, LocalDate endDate, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<AcademicTerm> spec = new SpecificationBuilder<AcademicTerm>()
                .equal("id",id)
                .equal("year",year)
                .equal("semester",semester)
                .between("startDate",startDate,endDate)
                .build();
        List<String> allowSort = List.of("id","year","semester","startDate","endDate");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1,size,sort);
        Page<AcademicTerm> termPage = academicTermRepository.findAll(spec,pageable);
        return PageResponse.from(termPage,academicMapper::toResponse);
    }

    @Override
    public AcademicTermResponse updateTerm(Long id, AcademicTermRequest request) {
        var academic = getById(id);
        academicMapper.updateTerm(request,academic);
        var saved = academicTermRepository.save(academic);
        return academicMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        var academic = getById(id);
        academicTermRepository.delete(academic);
    }

    @Override
    public String restore(Long id) {
        var academic = academicTermRepository.findByIdIncludeDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("academic not found " + id));
        academic.setDeleted(false);
        academic.setCreatedAt(null);
        academicTermRepository.save(academic);
        return "Academic restored successfully";
    }

    private AcademicTerm getById(Long id){
        return academicTermRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Academic not found with id " + id));
    }

}
