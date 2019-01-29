package br.com.devdojo.demo.repository;

import br.com.devdojo.demo.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> { //CrudRepository já possuí todos os métodos básicos para fazer consulta no bando de dados

    List<Student> findByNameIgnoreCaseContaining(String name); // como name é um parâmetro da classe Student, o próprio Spring fara o método de busca para esse parâmetro
}
