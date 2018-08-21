package capgemini.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentTo {

    private int version;
    private Long id;
    private Double area;
    private Integer roomsNumber;
    private Integer balconiesNumber;
    private Integer floor;
    private String address;
    private String status;
    private Long buildingId;

    public static ApartmentToBuilder builder() {
        return new ApartmentToBuilder();
    }

    public static class ApartmentToBuilder {

        private int version;
        private Long id;
        private Double area;
        private Integer roomsNumber;
        private Integer balconiesNumber;
        private Integer floor;
        private String address;
        private String status;
        private Long buildingId;

        public ApartmentToBuilder withVersion(int version) {
            this.version = version;
            return this;
        }

        public ApartmentToBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ApartmentToBuilder withArea(Double area) {
            this.area = area;
            return this;
        }

        public ApartmentToBuilder withRoomsNumber(Integer roomsNumber) {
            this.roomsNumber = roomsNumber;
            return this;
        }

        public ApartmentToBuilder withBalconiesNumber(Integer balconiesNumber) {
            this.balconiesNumber = balconiesNumber;
            return this;
        }

        public ApartmentToBuilder withfloor(Integer floor) {
            this.floor = floor;
            return this;
        }

        public ApartmentToBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public ApartmentToBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public ApartmentToBuilder withBuildingId(Long buildingId) {
            this.buildingId = buildingId;
            return this;
        }

        public ApartmentTo build() {
            checkBeforeBuild(area, roomsNumber, balconiesNumber, floor, address, status, buildingId);
            return new ApartmentTo(version, id, area, roomsNumber, balconiesNumber, floor, address, status, buildingId);
        }

        private void checkBeforeBuild(Double area, Integer roomsNumber,
                                      Integer balconiesNumber, Integer floor, String address,
                                      String status, Long buildingId) {
            if (area == null ||
                    roomsNumber == null ||
                    balconiesNumber == null ||
                    floor == null ||
                    address == null || address.isEmpty() ||
                    status == null || status.isEmpty() ||
                    buildingId == null) {
                throw new RuntimeException("Incorrect apartment_to be created");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentTo that = (ApartmentTo) o;
        return version == that.version &&
                Objects.equals(id, that.id) &&
                Objects.equals(area, that.area) &&
                Objects.equals(roomsNumber, that.roomsNumber) &&
                Objects.equals(balconiesNumber, that.balconiesNumber) &&
                Objects.equals(floor, that.floor) &&
                Objects.equals(address, that.address) &&
                Objects.equals(status, that.status) &&
                Objects.equals(buildingId, that.buildingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, area, roomsNumber, balconiesNumber, floor, address, status, buildingId);
    }
}
