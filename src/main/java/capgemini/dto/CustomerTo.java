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
public class CustomerTo {

    private int version;
    private Long id;
    private String firstName;
    private String lastName;
    private Long phone;
    private Collection<Long> apartmentIds = new HashSet<>();

    public static CustomerToBuilder builder() {
        return new CustomerToBuilder();
    }

    public static class CustomerToBuilder {

        private int version;
        private Long id;
        private String firstName;
        private String lastName;
        private Long phone;
        private Collection<Long> apartmentIds;

        public CustomerToBuilder withVersion(int version) {
            this.version = version;
            return this;
        }

        public CustomerToBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CustomerToBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CustomerToBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerToBuilder withPhone(Long phone) {
            this.phone = phone;
            return this;
        }

        public CustomerToBuilder withApartmentIds(Collection<Long> apartmentIds) {
            this.apartmentIds = apartmentIds;
            return this;
        }

        public CustomerTo build() {
            checkBeforeBuild(firstName, lastName, phone, apartmentIds);
            return new CustomerTo(version, id, firstName, lastName, phone, apartmentIds);
        }

        private void checkBeforeBuild(String firstName, String lastName, Long phone,
                                      Collection<Long> apartmentIds) {
            if (firstName == null || firstName.isEmpty() ||
                    lastName == null || lastName.isEmpty() ||
                    phone == null ||
                    apartmentIds == null) {
                throw new RuntimeException("Incorrect customer_to be created");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerTo that = (CustomerTo) o;
        return version == that.version &&
                Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(apartmentIds, that.apartmentIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, firstName, lastName, phone, apartmentIds);
    }
}
