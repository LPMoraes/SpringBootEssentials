package br.com.devdojo.demo.model;




import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;

@Entity
public class Student extends AbstractEntity{

    @NotEmpty(message = "campo nome é obrigatório")
    private String name;

    @NotEmpty
    @Email
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
