package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class StudentFacadeTest extends Specification {

    @Autowired
    StudentRepository studentRepository
    @Autowired
    StudentFacade facade

    def "get all students"() {
        given: "2 students exist in db"
        studentRepository.save(StudentUtils.studentToAdd())
        studentRepository.save(StudentUtils.alternativeStudentToAdd())
        def expectedStudents = List.of(StudentUtils.studentDto(), StudentUtils.alternativeStudentDto())

        when: "user tries to retrieve them"
        def students = facade.getAllStudents()

        then: "gets exactly these students and no more"
        students.size() == 2
        students.eachWithIndex { StudentDto student, int i -> student == expectedStudents.get(i) }
    }

    def "add new student"() {
        given: "there is a completely new student data to be added to db"
        def expectedStudent = StudentUtils.studentDto()

        when: "user tries to add a new student"
        def student = facade.addStudent(StudentUtils.addStudentCommand())

        then: "this student should be successfully added"
        student == expectedStudent
    }

    def "get student"() {
        given: "a student exists in db"
        studentRepository.save(StudentUtils.studentToAdd())
        def expectedStudent = StudentUtils.studentDto()

        when: "user tries to retrieve student's data by student's id"
        def student = facade.getStudent(StudentUtils.ID)

        then: "gets his correct data"
        student == expectedStudent
    }

    def "edit student"() {
        given: "a student exists in db"
        studentRepository.save(StudentUtils.studentToAdd())
        def expectedStudent = StudentUtils.studentDto()

        when: "user tries to edit student's data"
        def student = facade.editStudentData(StudentUtils.ID, StudentUtils.editStudentDataCommand())

        then: "data is successfully edited"
        student == expectedStudent
    }

    def "update password"() {
        given: "a student exists in db"
        studentRepository.save(StudentUtils.studentToAdd())

        when: "user tries to update student's password"
        def updated = facade.updatePassword(StudentUtils.ID, StudentUtils.PASSWORD)

        then: "password is successfully updated"
        updated
    }

    def "delete student"() {
        given: "a student exists in db"
        studentRepository.save(StudentUtils.studentToAdd())

        when: "user tries to delete student's account"
        def deleted = facade.deleteStudent(StudentUtils.ID)

        then: "account is successfully deleted"
        deleted
    }
}
