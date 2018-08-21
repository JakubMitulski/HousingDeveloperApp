package capgemini.entities;

import capgemini.entities.listeners.CreateListener;
import capgemini.entities.listeners.UpdateListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({CreateListener.class, UpdateListener.class})
@Table(name = "buildings")
public class BuildingEntity extends AbstractEntity implements Serializable {

    @Version
    public int version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Integer floorsNumber;
    @Column(nullable = false)
    private Boolean hasElevator;
    @Column(nullable = false)
    private Integer apartmentsNumber;

    @OneToMany(mappedBy = "building", cascade = CascadeType.REMOVE)
    private Collection<ApartmentEntity> apartments = new HashSet<>();

}