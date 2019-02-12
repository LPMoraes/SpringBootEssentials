package br.com.devdojo.demo;


import br.com.devdojo.demo.model.Student;
import br.com.devdojo.demo.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

        /*
        * TESTANDO MÉTODOS DO REPOSITORY DO STUDENT
        *
        *
        *
        */
            @Test
            public void createShouldPersistData(){
                Student student =  new Student("william", "william@devdojo.com.br");
                this.studentRepository.save(student);
                Assertions.assertThat(student.getId()).isNotNull();
                Assertions.assertThat(student.getName()).isEqualTo("william");
                Assertions.assertThat(student.getEmail()).isEqualTo("william@devdojo.com.br");
            }

            @Test
            public void deleteShouldRemoveData(){
                Student student = new Student("william", "william@devdojo.com.br");
                this.studentRepository.save(student);
                studentRepository.delete(student);
                Assertions.assertThat(studentRepository.findById(student.getId())).isEmpty();
            }

            @Test
            public void updateShouldChangeAndPersistData(){
                Student student =  new Student("william", "william@devdojo.com.br");
                this.studentRepository.save(student);
                student.setName("william2");
                student.setEmail("william2@devdojo.com.br");
                this.studentRepository.save(student);

                Student studentUpdate = this.studentRepository.findById(student.getId()).orElse(new Student());
                Assertions.assertThat(studentUpdate.getName()).isEqualTo("william2");
                Assertions.assertThat(studentUpdate.getEmail()).isEqualTo("william2@devdojo.com.br");
            }

            @Test
            public void findByNameIgnoreCaseContainingShouldIgnoreCase(){
                Student student =  new Student("william", "william@devdojo.com.br");
                Student student2 =  new Student("WILLIAM", "william@devdojo.com.br");
                this.studentRepository.save(student);
                this.studentRepository.save(student2);

                List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("william");
                Assertions.assertThat(studentList.size()).isEqualTo(2);
            }


    /*
     * TESTANDO OS ATRIBUTOS - COM SUAS RESPECTIVAS VALIDAÇÕES - DA CLASSE STUDENT
     *
     *
     *
     */

            @Test
            public void createWhenNameIsNullShouldThrowConstraintViolationException(){
                thrown.expect(ConstraintViolationException.class);
                thrown.expectMessage("campo nome é obrigatório");
                this.studentRepository.save(new Student());
            }

            @Test
            public void createWhenEmailIsNullShouldThrowConstraintViolationException(){
                thrown.expect(ConstraintViolationException.class);
                Student student =  new Student();
                student.setName("william");
                this.studentRepository.save(student);
            }
}
