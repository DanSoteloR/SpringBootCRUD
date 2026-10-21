package mx.strikes.crudDtoDemo.repository;

import mx.strikes.crudDtoDemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByIdAndDeletedIsFalse(long id);

    List<Student> findByDeletedIsFalse();

    Boolean existsByEmail(String email);
}


