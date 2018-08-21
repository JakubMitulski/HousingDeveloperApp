package capgemini.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class CustomerEntity implements Serializable {

    @Version
    public int version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Long phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "apartments_customers",
            joinColumns = {@JoinColumn(name = "apartment_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "customer_id", nullable = false, updatable = false)}
    )
    private Set<CustomerEntity> apartments = new HashSet<>();

}