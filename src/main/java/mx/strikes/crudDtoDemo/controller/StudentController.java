package mx.strikes.crudDtoDemo.controller;

import jakarta.validation.Valid;
import mx.strikes.crudDtoDemo.dto.CreateStudentRequestDto;
import mx.strikes.crudDtoDemo.dto.CreateStudentResponseDto;
import mx.strikes.crudDtoDemo.dto.UpdateStudentRequestDto;
import mx.strikes.crudDtoDemo.dto.UpdateStudentResponseDto;
import mx.strikes.crudDtoDemo.entity.Student;
import mx.strikes.crudDtoDemo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponseDto> createStudent(
            @Valid @RequestBody CreateStudentRequestDto studentRequestDTO){

        CreateStudentResponseDto createdStudent =
                studentService.createStudent(studentRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@PathVariable long id){
        CreateStudentResponseDto studentResp = studentService.getStudent(id);

        return ResponseEntity.ok(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudent(){
        List<CreateStudentResponseDto> studentList = studentService.getAllStudent();

        return ResponseEntity.ok(studentList);
    }

    @PutMapping
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@RequestParam long id,
                                     @RequestBody UpdateStudentRequestDto studentRequestDto){
        UpdateStudentResponseDto studentResp =
                studentService.updateStudent(id, studentRequestDto);

        return ResponseEntity.ok(studentResp);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam long id){
        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/delete-soft")
    public ResponseEntity<String> softDeleteStudent(@RequestParam long id){
        studentService.deleteStudentSoftly(id);

        return ResponseEntity.noContent().build();
    }
}
