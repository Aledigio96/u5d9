package digiovannialessandro.u5d9.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Il nome è obbligatorio")
    @Size(min = 2, max = 40, message = "Il nome deve essere di lunghezza compresa tra 2 e 40")
    private String name;
    @NotEmpty(message = "Il cognome è obbligatorio")
    @Size(min = 2, max = 40, message = "Il nome deve essere di lunghezza compresa tra 2 e 40")
    private String surname;
    @NotEmpty(message = "L'indirizzo email è obbligatorio")
    @Email(message = "L'indirizzo email inserito non è nel formato giusto")
    private String email;
    @NotEmpty(message = "La data di nascita è obbligatoria")
    @Past(message = "La data deve essere in un giorno|anno passato")
    private String dateOfBirth;
    private String avatar;


}
