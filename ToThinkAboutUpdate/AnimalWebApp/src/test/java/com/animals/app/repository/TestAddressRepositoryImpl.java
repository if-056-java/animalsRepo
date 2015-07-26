package com.animals.app.repository;

import com.animals.app.domain.Address;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by oleg on 24.07.2015.
 */

public class TestAddressRepositoryImpl {

    final int ID = 30;

    private static AddressRepositoryImpl addressRepository;

    @Before
    public void runBeforeClass() {
        addressRepository = new AddressRepositoryImpl();
    }

    @After
    public void runAfterClass() {
        addressRepository = null;
    }

    @Test
    public void testInsert(){
        Address address = addressRepository.getAll().get(0);
        addressRepository.insert(address);

        List<Address> addresses = addressRepository.getAll();
        Address insertedAddress = addresses.get(addresses.size() - 1);

        //test if object inserted
        assertNotNull(insertedAddress);

        //test if autoincrement work
        assertEquals((long) address.getId(), (long) insertedAddress.getId());

        //test if correct inserted
        assertEquals(address.getCountry(), insertedAddress.getCountry());
    }

    @Test
    public void testUpdate(){
        Address address = addressRepository.getAll().get(0);
        address.setCountry("Country");

        addressRepository.update(address);

        Address updatedAddress = addressRepository.getAll().get(0);

        //test if correct updated
        assertEquals(address.getCountry(), updatedAddress.getCountry());
    }

    @Test
    public void testDelete(){
        Address address = addressRepository.getAll().get(0);

        //test if object not null
        assertNotNull(address);

        addressRepository.delete(address);

        //test if object is deleted from db
        assertNotEquals(address, addressRepository.getAll().get(0));
    }

    @Test
    public void testGetAll(){
        List<Address> addressList = addressRepository.getAll();

        assertNotNull(addressList);
    }

    @Test
    public void testGetById(){
        Address address = addressRepository.getById(ID);

        assertNotNull(address);
    }
}
