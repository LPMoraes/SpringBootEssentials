package br.com.devdojo.demo;


import br.com.devdojo.demo.model.Student;
import br.com.devdojo.demo.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

}
