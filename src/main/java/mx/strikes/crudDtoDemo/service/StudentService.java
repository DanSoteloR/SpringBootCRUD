package mx.strikes.crudDtoDemo.service;

import mx.strikes.crudDtoDemo.dto.CreateStudentRequestDto;
import mx.strikes.crudDtoDemo.dto.CreateStudentResponseDto;
import mx.strikes.crudDtoDemo.dto.UpdateStudentRequestDto;
import mx.strikes.crudDtoDemo.dto.UpdateStudentResponseDto;
import mx.strikes.crudDtoDemo.entity.Student;
import mx.strikes.crudDtoDemo.exception.DuplicateResourceException;
import mx.strikes.crudDtoDemo.exception.ResourceNotFoundException;
import mx.strikes.crudDtoDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentReqDto){
        //Business logic like validation here
        Student student = mapToEntity(studentReqDto);

        if(emailExists(student.getEmail())){
            throw new DuplicateResourceException("Student with email "+
                                                    student.getEmail() +
                                                " already exists.");
        }

        Student studentResp = studentRepository.save(student);

        return mapToDto(studentResp);
    }


    public CreateStudentResponseDto getStudent(long id) {
        Student studentResp = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));
//
        return mapToDto(studentResp);
    }

    public List<CreateStudentResponseDto> getAllStudent() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToDto)
                .toList();
    }

    public UpdateStudentResponseDto updateStudent(long id, UpdateStudentRequestDto studentReqDto) {
        Student existingStudent = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));

        existingStudent.setName(studentReqDto.getName());
        existingStudent.setAge(studentReqDto.getAge());
        existingStudent.setRollNo(studentReqDto.getRollNo());
        existingStudent.setSubject(studentReqDto.getSubject());
        existingStudent.setUpdatedAt(LocalDateTime.now());

        Student updatedStudent = studentRepository.save(existingStudent);

        return mapToUpdateDto(updatedStudent);
    }

    public void deleteStudent(long id) {
        Student studentToBeDeleted = studentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));

        studentRepository.delete(studentToBeDeleted);
    }

    public void deleteStudentSoftly(long id) {
        Student studentToSoftDelete = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));

        studentToSoftDelete.setDeleted(true);

        studentRepository.save(studentToSoftDelete);
    }

    private Student mapToEntity(CreateStudentRequestDto studentReqDto) {
        Student student = new Student();

        student.setName(studentReqDto.getName());
        student.setAge(studentReqDto.getAge());
        student.setEmail(studentReqDto.getEmail());
        student.setRollNo(studentReqDto.getRollNo());
        student.setSubject(studentReqDto.getSubject());

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        student.setDeleted(false);

        return student;
    }

    private CreateStudentResponseDto mapToDto(Student studentResp) {
        CreateStudentResponseDto studentResponseDto = new CreateStudentResponseDto();

        studentResponseDto.setId(studentResp.getId());
        studentResponseDto.setName(studentResp.getName());
        studentResponseDto.setAge(studentResp.getAge());
        studentResponseDto.setEmail(studentResp.getEmail());
        studentResponseDto.setRollNo(studentResp.getRollNo());
        studentResponseDto.setSubject(studentResp.getSubject());
        studentResponseDto.setMessage("Student saved successfully");
        studentResponseDto.setCreatedAt(studentResp.getCreatedAt());
        studentResponseDto.setUpdatedAt(studentResp.getUpdatedAt());

        return studentResponseDto;
    }

    private UpdateStudentResponseDto mapToUpdateDto(Student studentResp) {
        UpdateStudentResponseDto studentResponseDto = new UpdateStudentResponseDto();

        studentResponseDto.setId(studentResp.getId());
        studentResponseDto.setName(studentResp.getName());
        studentResponseDto.setAge(studentResp.getAge());
        studentResponseDto.setEmail(studentResp.getEmail());
        studentResponseDto.setRollNo(studentResp.getRollNo());
        studentResponseDto.setSubject(studentResp.getSubject());
        studentResponseDto.setMessage("Student updated successfully");
        studentResponseDto.setUpdatedAt(studentResp.getUpdatedAt());

        return studentResponseDto;
    }

    private boolean emailExists(String email){
        return studentRepository.existsByEmail(email);
    }
}
