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
public class CriteriaApartmentTo {

    private Double minArea;
    private Double maxArea;
    private Integer minRoomsNumber;
    private Integer maxRoomsNumber;
    private Integer minBalconiesNumber;
    private Integer maxBalconiesNumber;

    public static CriteriaApartmentTo builder() {
        return new CriteriaApartmentTo();
    }

    public static class CriteriaApartmentToBuilder {

        private Double minArea;
        private Double maxArea;
        private Integer minRoomsNumber;
        private Integer maxRoomsNumber;
        private Integer minBalconiesNumber;
        private Integer maxBalconiesNumber;

        public CriteriaApartmentToBuilder withMinArea(Double minArea) {
            this.minArea = minArea;
            return this;
        }

        public CriteriaApartmentToBuilder withMaxArea(Double maxArea) {
            this.maxArea = maxArea;
            return this;
        }

        public CriteriaApartmentToBuilder withMinRoomsNumber(Integer minRoomsNumber) {
            this.minRoomsNumber = minRoomsNumber;
            return this;
        }

        public CriteriaApartmentToBuilder withMaxRoomsNumber(Integer maxRoomsNumber) {
            this.maxRoomsNumber = maxRoomsNumber;
            return this;
        }

        public CriteriaApartmentToBuilder withMinBalconiesNumber(Integer minBalconiesNumber) {
            this.minBalconiesNumber = minBalconiesNumber;
            return this;
        }

        public CriteriaApartmentToBuilder withMaxBalconiesNumber(Integer maxBalconiesNumber) {
            this.maxBalconiesNumber = maxBalconiesNumber;
            return this;
        }

        public CriteriaApartmentTo build() {
            return new CriteriaApartmentTo(minArea, maxArea, minRoomsNumber, maxRoomsNumber, minBalconiesNumber, maxBalconiesNumber);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CriteriaApartmentTo that = (CriteriaApartmentTo) o;
        return Objects.equals(minArea, that.minArea) &&
                Objects.equals(maxArea, that.maxArea) &&
                Objects.equals(minRoomsNumber, that.minRoomsNumber) &&
                Objects.equals(maxRoomsNumber, that.maxRoomsNumber) &&
                Objects.equals(minBalconiesNumber, that.minBalconiesNumber) &&
                Objects.equals(maxBalconiesNumber, that.maxBalconiesNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minArea, maxArea, minRoomsNumber, maxRoomsNumber, minBalconiesNumber, maxBalconiesNumber);
    }
}
