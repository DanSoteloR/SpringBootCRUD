package mx.strikes.crudDtoDemo.dto;

import jakarta.validation.constraints.*;

public class CreateStudentRequestDto {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Students must be at least 18 years old")
    private Integer age;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Student email must be valid")
    private String email;

    @NotNull(message = "RollNo is required")
    private Integer rollNo;

    @NotBlank(message = "Subject cannot be empty")
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
