package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    void addAddressBook(AddressBook addressBook);

    List<AddressBook> GetUersAllAddress(Long currentId);

    AddressBook GetDefaultById(AddressBook addressBook);

    AddressBook GetById(Long id);

    void updateById(AddressBook addressBook);

    void deleteById(Long id);

    void SetDafaultById(AddressBook addressBook);
}
