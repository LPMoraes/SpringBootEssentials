package br.com.devdojo.demo.endpoint;

import br.com.devdojo.demo.error.ResourceNotFoundException;
import br.com.devdojo.demo.model.Student;
import br.com.devdojo.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

import java.util.Optional;


@RestController // todos os métodos presente nesta classe, uma vez solicitados, irão retorna um JSON
@RequestMapping("v1")
public class StudentEndpoint {

    private final StudentRepository studentDAO;


    @Autowired
    public StudentEndpoint(StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    // Buscar todos
    @GetMapping(path = "protected/students/")
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping(path = "protected/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        verifyIfStudentExist(id);
        Optional<Student> student = studentDAO.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    //Buscar por nome
    @GetMapping(path = "protected/students/findByName/{name}")
    public ResponseEntity<?> findStudentByName(@PathVariable String name){
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    // Adicionar
    @PostMapping(path = "admin/students/")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);//passa o obejto que foi adicionado como resposta
    }

    // Atualizar
    // @RequestMapping (method = RequestMethod.PUT)
    @PutMapping(path = "admin/students/")
    public ResponseEntity<?> update(@RequestBody Student student) {
        verifyIfStudentExist(student.getId());
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Remover
    @DeleteMapping(path = "admin/students/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        verifyIfStudentExist(id);
        studentDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    private  void verifyIfStudentExist(Long id){
        if (studentDAO.existsById(id) == false)
            throw new ResourceNotFoundException("Student not found od ID: "+id);
    }



}
