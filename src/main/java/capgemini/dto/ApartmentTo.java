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
    private Integer roomsAmount;
    private Integer balconiesAmount;
    private Integer floorNumber;
    private String address;
    private String status;
    private Long buildingId;
    private Double price;

    public static ApartmentToBuilder builder() {
        return new ApartmentToBuilder();
    }

    public static class ApartmentToBuilder {

        private int version;
        private Long id;
        private Double area;
        private Integer roomsAmount;
        private Integer balconiesAmount;
        private Integer floorNumber;
        private String address;
        private String status;
        private Long buildingId;
        private Double price;

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

        public ApartmentToBuilder withRoomsAmount(Integer roomsAmount) {
            this.roomsAmount = roomsAmount;
            return this;
        }

        public ApartmentToBuilder withBalconiesAmount(Integer balconiesAmount) {
            this.balconiesAmount = balconiesAmount;
            return this;
        }

        public ApartmentToBuilder withFloorNumber(Integer floorNumber) {
            this.floorNumber = floorNumber;
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

        public ApartmentToBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public ApartmentTo build() {
            checkBeforeBuild(area, roomsAmount, balconiesAmount, floorNumber, address, status, buildingId);
            return new ApartmentTo(version, id, area, roomsAmount, balconiesAmount, floorNumber, address, status, buildingId, price);
        }

        private void checkBeforeBuild(Double area, Integer roomsAmount,
                                      Integer balconiesAmount, Integer floorNumber, String address,
                                      String status, Long buildingId) {
            if (area == null ||
                    roomsAmount == null ||
                    balconiesAmount == null ||
                    floorNumber == null ||
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
                Objects.equals(roomsAmount, that.roomsAmount) &&
                Objects.equals(balconiesAmount, that.balconiesAmount) &&
                Objects.equals(floorNumber, that.floorNumber) &&
                Objects.equals(address, that.address) &&
                Objects.equals(status, that.status) &&
                Objects.equals(buildingId, that.buildingId) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, area, roomsAmount, balconiesAmount, floorNumber, address, status, buildingId, price);
    }
}
