package bank_app.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "banks")
public class Banks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banks")
    private Long bankId;


    @NotBlank(message = "Поле должно быть заполнено!")
    @Pattern(regexp = " ")
    @Column(name = "bic")
    private String bic;
    private Long id;

    public Banks(long l, String s) {
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
