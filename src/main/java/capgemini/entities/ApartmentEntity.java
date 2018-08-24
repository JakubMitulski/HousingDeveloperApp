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
    @Column(nullable = false)
    private Double area;
    @Column(nullable = false)
    private Integer roomsAmount;
    @Column(nullable = false)
    private Integer balconiesAmount;
    @Column(nullable = false)
    private Integer floorNumber;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String status;
    @Column
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building")
    private BuildingEntity building;

}