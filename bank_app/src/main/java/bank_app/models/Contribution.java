package bank_app.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import bank_app.util.CustomLocalDateDeserializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contribution")
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositId;

    @Column(name = "id_banks")
    @Min(value = 1, message = "Id > нуля!")
    @NotNull(message = "Поле должно быть заполнено!")
    private Long customersId;

    @NotNull(message = "Поле должно быть заполнено!")
    @Min(value = 1, message = "Id > нуля!")
    @Column(name = "id_customers")
    private Long bankId;

    @NotNull(message = "Поле должно быть заполнено!")
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @Column(name = "date_opening")
    private LocalDate openDate;

    @NotNull(message = "Поле должно быть заполнено!")
    @Min(value = 0, message = "Процент должен быть не меньше нуля!")
    @Column(name = "percent")
    private Double percent;

    @NotNull(message = "Поле должно быть заполнено!")
    @Column(name = "term_month")
    private LocalDate termDate;
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
