package br.com.devdojo.demo.repository;

import br.com.devdojo.demo.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

    List<Student> findByNameIgnoreCaseContaining(String name);


}
