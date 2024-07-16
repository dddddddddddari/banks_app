package bank_app.models;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "opf")
public class opf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opf_name")
    private String name;
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
