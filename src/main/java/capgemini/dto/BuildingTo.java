package capgemini.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingTo {

    private int version;
    private Long id;
    private String description;
    private String location;
    private Integer floorsNumber;
    private Boolean hasElevator;
    private Integer apartmentsNumber;
    private Collection<Long> apartmentIds = new HashSet<>();

    public static BuildingToBuilder builder() {
        return new BuildingToBuilder();
    }

    public static class BuildingToBuilder {

        private int version;
        private Long id;
        private String description;
        private String location;
        private Integer floorsNumber;
        private Boolean hasElevator;
        private Integer apartmentsNumber;
        private Collection<Long> apartmentIds;

        public BuildingToBuilder withVersion(int version) {
            this.version = version;
            return this;
        }

        public BuildingToBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public BuildingToBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public BuildingToBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public BuildingToBuilder withFloorsNumber(Integer floorsNumber) {
            this.floorsNumber = floorsNumber;
            return this;
        }

        public BuildingToBuilder withElevator(Boolean hasElevator) {
            this.hasElevator = hasElevator;
            return this;
        }

        public BuildingToBuilder withApartmentsNumber(Integer apartmentsNumber) {
            this.apartmentsNumber = apartmentsNumber;
            return this;
        }

        public BuildingToBuilder withApartmentIds(Collection<Long> apartmentIds) {
            this.apartmentIds = apartmentIds;
            return this;
        }

        public BuildingTo build() {
            checkBeforeBuild(description, location, floorsNumber, hasElevator,
                    apartmentsNumber, apartmentIds);
            return new BuildingTo(version, id, description, location, floorsNumber,
                    hasElevator, apartmentsNumber, apartmentIds);
        }

        private void checkBeforeBuild(String description, String location, Integer floorsNumber,
                                      Boolean hasElevator, Integer apartmentsNumber,
                                      Collection<Long> apartmentIds) {
            if (description == null || description.isEmpty() ||
                    location == null || location.isEmpty() ||
                    floorsNumber == null ||
                    hasElevator == null ||
                    apartmentsNumber == null ||
                    apartmentIds == null) {
                throw new RuntimeException("Incorrect building_to be created");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingTo that = (BuildingTo) o;
        return version == that.version &&
                Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(location, that.location) &&
                Objects.equals(floorsNumber, that.floorsNumber) &&
                Objects.equals(hasElevator, that.hasElevator) &&
                Objects.equals(apartmentsNumber, that.apartmentsNumber) &&
                Objects.equals(apartmentIds, that.apartmentIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, description, location, floorsNumber, hasElevator, apartmentsNumber, apartmentIds);
    }
}
