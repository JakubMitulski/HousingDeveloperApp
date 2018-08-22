package capgemini.mappers;

import capgemini.dto.CustomerTo;
import capgemini.entities.ApartmentEntity;
import capgemini.entities.CustomerEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static CustomerTo toCustomerTo(CustomerEntity customerEntity) {
        if (customerEntity == null)
            return null;

        return new CustomerTo.CustomerToBuilder()
                .withVersion(customerEntity.getVersion())
                .withId(customerEntity.getId())
                .withFirstName(customerEntity.getFirstName())
                .withLastName(customerEntity.getLastName())
                .withPhone(customerEntity.getPhone())
                .withApartmentIds(customerEntity.getApartments()
                        .stream()
                        .map(ApartmentEntity::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static CustomerEntity toCustomerEntity(CustomerTo customerTo) {
        if (customerTo == null)
            return null;

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setVersion(customerTo.getVersion());
        customerEntity.setId(customerTo.getId());
        customerEntity.setFirstName(customerTo.getFirstName());
        customerEntity.setLastName(customerTo.getLastName());
        customerEntity.setPhone(customerTo.getPhone());

        return customerEntity;
    }

    public static List<CustomerTo> map2Tos(List<CustomerEntity> customerEntities) {
        return customerEntities.stream().map(CustomerMapper::toCustomerTo).collect(Collectors.toList());
    }

    public static List<CustomerEntity> map2Entities(List<CustomerTo> customerTos) {
        return customerTos.stream().map(CustomerMapper::toCustomerEntity).collect(Collectors.toList());
    }
}
