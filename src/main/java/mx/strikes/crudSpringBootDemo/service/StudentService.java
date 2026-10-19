package mx.strikes.crudSpringBootDemo.service;

import mx.strikes.crudSpringBootDemo.entity.Student;
import mx.strikes.crudSpringBootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student studentReq){
        //Bussiness logic like validation here
        studentReq.setDeleted(false);
        Student studentResp = studentRepository.save(studentReq);
        return studentResp;
    }


    public Student getStudent(long id) {
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);
        if(studentResp.isPresent()) {
            return studentResp.get();
        }
        return null;
    }

    public List<Student> getAllStudent() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();
        return studentList;
    }

    public Student updateStudent(long id, Student studentReq) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);

        if(existingStudent.isEmpty()) {
            return null;
        }

        Student studentToUpdate = existingStudent.get();

        studentToUpdate.setName(studentReq.getName());
        studentToUpdate.setAge(studentReq.getAge());
        studentToUpdate.setEmail(studentReq.getEmail());
        studentToUpdate.setRollNo(studentReq.getRollNo());
        studentToUpdate.setSubject(studentReq.getSubject());
        studentToUpdate.setDeleted(false);

        return studentRepository.save(studentToUpdate);
    }

    public Boolean deleteStudent(long id) {
        Boolean studentExists = studentRepository.existsById(id);

        if(!studentExists) return false;

        studentRepository.deleteById(id);
        return true;
    }

    public Boolean deleteStudentSoftly(long id) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);

        if(existingStudent.isEmpty()) {
            return false;
        }

        Student studentToSoftDelete = existingStudent.get();
        studentToSoftDelete.setDeleted(true);

        studentRepository.save(studentToSoftDelete);

        return true;
    }
}
