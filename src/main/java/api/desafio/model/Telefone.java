package api.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "telefone")
public class Telefone {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID idTelefone;

    private String numero;

    @ManyToOne
    @JoinColumn(name = "id_contato")
    @JsonIgnore
    private Contato contato;
}
