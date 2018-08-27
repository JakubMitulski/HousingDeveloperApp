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
@Table(name = "buildings")
public class BuildingEntity extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, columnDefinition = "VARCHAR(45) DEFAULT ''")
    private String description;
    @Column(nullable = false, columnDefinition = "VARCHAR(45) DEFAULT ''")
    private String location;
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer floorsAmount;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean hasElevator;
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer apartmentsAmount;

    @OneToMany(mappedBy = "building", cascade = CascadeType.REMOVE)
    private List<ApartmentEntity> apartments = new ArrayList<>();
}
