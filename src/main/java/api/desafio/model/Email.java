package api.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID idemail;

    private String email;

    @ManyToOne
    @JoinColumn(name = "id_contato")
    @JsonIgnore
    private Contato contato;
}
