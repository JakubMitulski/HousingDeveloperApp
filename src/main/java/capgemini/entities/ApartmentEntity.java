package capgemini.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "apartments")
public class ApartmentEntity implements Serializable {

    @Version
    public int version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Double area;
    @Column(nullable = false)
    private Integer roomsNumber;
    @Column(nullable = false)
    private Integer balconiesNumber;
    @Column(nullable = false)
    private Integer floor;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building")
    private BuildingEntity building;

}