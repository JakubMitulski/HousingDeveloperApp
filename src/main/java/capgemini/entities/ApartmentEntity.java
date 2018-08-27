package capgemini.entities;

import capgemini.entities.listeners.CreateListener;
import capgemini.entities.listeners.UpdateListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({CreateListener.class, UpdateListener.class})
@Table(name = "apartments")
public class ApartmentEntity extends AbstractEntity implements Serializable {

    @Version
    private int version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,  columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double area;
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer roomsAmount;
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer balconiesAmount;
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer floorNumber;
    @Column(nullable = false, columnDefinition = "VARCHAR(45) DEFAULT ''")
    private String address;
    @Column(nullable = false, columnDefinition = "VARCHAR(45) DEFAULT ''")
    private String status;
    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building")
    private BuildingEntity building;
}
