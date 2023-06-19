package com.lilium.jpatutorial;

import com.lilium.jpatutorial.entity.Address;
import com.lilium.jpatutorial.repository.AddressRepository;
import com.lilium.jpatutorial.service.AddressService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AddressRepositoryTest {
    @Autowired
    private AddressService service;
    @Autowired
    private AddressRepository repository;

    @Test
    void test_findCreatedSince() {
        //Cache timestamp before any address is created
        final long beforeAllCreateTimestamp = System.currentTimeMillis();

        //Create first address
        final Address firstCreatedAddress = service.createAddress("New Avenue 184");
        //Cache timestamp before second address is created
        final long beforeCreateSecondAddressTimestamp = System.currentTimeMillis();
        //Create second address
        final Address secondCreatedAddress = service.createAddress("Otakring 15");

        // Find all address with current timestamp and verify that none are found
        List<Address> allCreatedSince = repository.findAllCreatedSince(System.currentTimeMillis());
        Assertions.assertThat(allCreatedSince)
                .isNotNull()
                .isEmpty();

        // Find all addresses created since 'beforeCreateSecondTimestamp' and verify that only second address is found
        allCreatedSince = repository.findAllCreatedSince(beforeCreateSecondAddressTimestamp);
        Assertions.assertThat(allCreatedSince)
                .isNotNull()
                .extracting(Address::getId)
                .containsExactly(secondCreatedAddress.getId());

        // Find all addresses created since 'beforeAllCreateTimestamp' and verify that all addresses are found
        allCreatedSince = repository.findAllCreatedSince(beforeAllCreateTimestamp);
        Assertions.assertThat(allCreatedSince)
                .isNotNull()
                .extracting(Address::getId)
                .containsExactlyInAnyOrder(firstCreatedAddress.getId(), secondCreatedAddress.getId());
    }
}
