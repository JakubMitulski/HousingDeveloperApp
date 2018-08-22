package capgemini.entities;

import capgemini.entities.listeners.CreateListener;
import capgemini.entities.listeners.UpdateListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({CreateListener.class, UpdateListener.class})
@Table(name = "customers")
public class CustomerEntity extends AbstractEntity implements Serializable {

    @Version
    private int version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Long phone;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "apartments_customers",
            joinColumns = {@JoinColumn(name = "apartment_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "customer_id", nullable = false, updatable = false)}
    )
    private List<ApartmentEntity> apartments = new ArrayList<>();

    public void addApartmentToCustomer(ApartmentEntity apartmentEntity) {
        this.apartments.add(apartmentEntity);
    }

}