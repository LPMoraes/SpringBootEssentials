package br.com.devdojo.demo.endpoint;

import br.com.devdojo.demo.error.CustomErrorType;
import br.com.devdojo.demo.model.Student;
import br.com.devdojo.demo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;

@RestController // todos os métodos presente nesta classe, uma vez solicitados, irão retorna um JSON
@RequestMapping("students")
public class StudentEndpoint {

   private final DateUtil dateUtil;

    @Autowired
    public StudentEndpoint(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    // Buscar todos
    // @RequestMapping(method = RequestMethod.GET)

    @GetMapping
    public ResponseEntity<?> listAll() {

        System.out.println(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(Student.studentList, HttpStatus.OK);

    }

    // Buscar por ID
    // @RequestMapping (method = RequestMethod.GET, path = "/{id}")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") int id){

        Student student = new Student();
        student.setId(id);
        int index = Student.studentList.indexOf(student);

        if(index == -1)
            return new ResponseEntity<>(new CustomErrorType("student not found"),HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(Student.studentList.get(index), HttpStatus.OK);
    }

    // Adicionar
    // @RequestMapping (method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student){

        Student.studentList.add(student);
        return new ResponseEntity<>(student, HttpStatus.OK);//passa o obejto que foi adicionado como resposta

    }

    // Remover
    // @RequestMapping (method = RequestMethod.DELETE)
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Student student){

        Student.studentList.remove(student);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    // Atualizar
    // @RequestMapping (method = RequestMethod.PUT)
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student){

        Student.studentList.remove(student);
        Student.studentList.add(student);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
