package br.com.devdojo.demo.repository;

import br.com.devdojo.demo.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> { //CrudRepository já possuí todos os métodos básicos para fazer consulta no bando de dados

    List<Student> findByNameIgnoreCaseContaining(String name);


}
