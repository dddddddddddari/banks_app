package bank_app.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Long id_customer;

    @NotBlank(message = "Поле должно быть заполнено!")
    @Size(max = 255, message = "Имя не должно превышать 255 символов")
    @Column(name = "name_customer")
    private String nameCustomer;

    @NotBlank(message = "Поле должно быть заполнено!")
    @Size(max = 255, message = "Короткое имя не должно превышать 255 символов")
    @Column(name = "full_name_customer")
    private String fullNameCustomer;

    @NotBlank(message = "Поле должно быть заполнено!")
    @Size(max = 255, message = "Короткое имя не должно превышать 255 символов")
    @Column(name = "address")
    private String address;

    @NotNull(message = "Поле должно быть заполнено!")
    @Column(name = "opf_id")
    @Min(value = 1, message = "Id > нуля!")
    private Long opf;
    private Long id;


    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
