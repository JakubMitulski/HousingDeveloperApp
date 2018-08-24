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
    private Integer minRoomsAmount;
    private Integer maxRoomsAmount;
    private Integer minBalconiesAmount;
    private Integer maxBalconiesAmount;

    public static CriteriaApartmentTo builder() {
        return new CriteriaApartmentTo();
    }

    public static class CriteriaApartmentToBuilder {

        private Double minArea;
        private Double maxArea;
        private Integer minRoomsAmount;
        private Integer maxRoomsAmount;
        private Integer minBalconiesAmount;
        private Integer maxBalconiesAmount;

        public CriteriaApartmentToBuilder withMinArea(Double minArea) {
            this.minArea = minArea;
            return this;
        }

        public CriteriaApartmentToBuilder withMaxArea(Double maxArea) {
            this.maxArea = maxArea;
            return this;
        }

        public CriteriaApartmentToBuilder withMinRoomsAmount(Integer minRoomsAmount) {
            this.minRoomsAmount = minRoomsAmount;
            return this;
        }

        public CriteriaApartmentToBuilder withMaxRoomsAmount(Integer maxRoomsAmount) {
            this.maxRoomsAmount = maxRoomsAmount;
            return this;
        }

        public CriteriaApartmentToBuilder withMinBalconiesAmount(Integer minBalconiesAmount) {
            this.minBalconiesAmount = minBalconiesAmount;
            return this;
        }

        public CriteriaApartmentToBuilder withMaxBalconiesAmount(Integer maxBalconiesAmount) {
            this.maxBalconiesAmount = maxBalconiesAmount;
            return this;
        }

        public CriteriaApartmentTo build() {
            return new CriteriaApartmentTo(minArea, maxArea, minRoomsAmount, maxRoomsAmount, minBalconiesAmount, maxBalconiesAmount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CriteriaApartmentTo that = (CriteriaApartmentTo) o;
        return Objects.equals(minArea, that.minArea) &&
                Objects.equals(maxArea, that.maxArea) &&
                Objects.equals(minRoomsAmount, that.minRoomsAmount) &&
                Objects.equals(maxRoomsAmount, that.maxRoomsAmount) &&
                Objects.equals(minBalconiesAmount, that.minBalconiesAmount) &&
                Objects.equals(maxBalconiesAmount, that.maxBalconiesAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minArea, maxArea, minRoomsAmount, maxRoomsAmount, minBalconiesAmount, maxBalconiesAmount);
    }
}
