package br.com.devdojo.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

public class Student {
    private int id;
    private String name;
    public static List<Student> studentList;

    static {
        studentRepository();
    }

    public Student() {

    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student(String name) {
        this.name = name;
    }

    /* Os métodos equal e hashCode tem que ser autogerados e sobrescrevidos pela pp´roprio IDE
    * pois o IDE vai gerar um código(lógica) eficiente no interior desses métodos dispensando
    * que desenvolvedor perca tempo na implemetação desses códigos*/

    /*
     * Antes inserir um objeto em um coleção precisa-se verificar se já existe um objeto
     * com a mesma hash. Está aí a importância do método equal
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    /*
     * Para gerar o código hash em um objeto, precisamos sobrescrever o método hashCode().
     * É necessário definir em cima de qual atributo do objeto será gerado o código hash.
      * Para o caso atual o melhor atributo a ser usado seria o "id" do objeto.
    * */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static void studentRepository(){
        studentList = new ArrayList<>(asList(new Student(1,"Deku"), new Student(2, "Todoroki"), new Student(3,"Yamaha")));
    }
}
