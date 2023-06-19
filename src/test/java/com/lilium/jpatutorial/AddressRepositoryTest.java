package com.lilium.jpatutorial;

import com.lilium.jpatutorial.entity.Address;
import com.lilium.jpatutorial.repository.AddressRepository;
import com.lilium.jpatutorial.service.AddressService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(allCreatedSince)
                .isNotNull()
                .isEmpty();

        // Find all addresses created since 'beforeCreateSecondTimestamp' and verify that only second address is found
        allCreatedSince = repository.findAllCreatedSince(beforeCreateSecondAddressTimestamp);
        assertThat(allCreatedSince)
                .isNotNull()
                .extracting(Address::getId)
                .containsExactly(secondCreatedAddress.getId());

        // Find all addresses created since 'beforeAllCreateTimestamp' and verify that all addresses are found
        allCreatedSince = repository.findAllCreatedSince(beforeAllCreateTimestamp);
        assertThat(allCreatedSince)
                .isNotNull()
                .extracting(Address::getId)
                .containsExactlyInAnyOrder(firstCreatedAddress.getId(), secondCreatedAddress.getId());
    }

    @Test
    void test_findAddressByName() {
        final Address firstCreatedAddress = service.createAddress("742 Evergreen Terrace, Springfield, USA");
        final Address secondCreatedAddress = service.createAddress("Oxenthorge Road, Puddleby, Slopshire, England");

        //Search for address byName that does not exist
        List<Address> foundAddresses = repository.findAll(AddressRepository.Specs.byName("I.do.not.exist"));
        assertThat(foundAddresses)
                .isNotNull()
                .isEmpty();

        //Search for first address by its name
        foundAddresses = repository.findAll(AddressRepository.Specs.byName(firstCreatedAddress.getName()));
        assertThat(foundAddresses)
                .isNotNull()
                .extracting(Address::getId)
                .containsExactly(firstCreatedAddress.getId());

        //Search for second address by part of its name
        foundAddresses = repository.findAll(AddressRepository.Specs.byName("%ngland"));
        assertThat(foundAddresses)
                .isNotNull()
                .extracting(Address::getId)
                .containsExactly(secondCreatedAddress.getId());
    }
}
