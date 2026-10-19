package mx.strikes.crudSpringBootDemo.controller;

import mx.strikes.crudSpringBootDemo.entity.Student;
import mx.strikes.crudSpringBootDemo.service.StudentService;
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

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    @GetMapping("/get")
    public ResponseEntity<Student> getStudent(@RequestParam long id){
        Student studentResp = studentService.getStudent(id);

        if(studentResp == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentResp);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> studentList = studentService.getAllStudent();

        if(studentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentList);
    }

    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestParam long id,
                                                 @RequestBody Student studetReq){
        Student studentResp = studentService.updateStudent(id, studetReq);

        if(studentResp == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentResp);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam long id){
        Boolean wasDeleted = studentService.deleteStudent(id);

        if(!wasDeleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Student deleted");
    }

    @PatchMapping("/delete-soft")
    public ResponseEntity<String> softDeleteStudent(@RequestParam long id){
        Boolean wasSoftDeleted = studentService.deleteStudentSoftly(id);

        if(!wasSoftDeleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Student deleted softly");
    }
}
