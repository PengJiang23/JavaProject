package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    AddressBookMapper addressBookMapper;


    public void addAddressBook(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.insertAddress(addressBook);

    }


    public List<AddressBook> GetUersAllAddress(Long userId) {
        List<AddressBook> list = addressBookMapper.GetByUserId(userId);
        return list;
    }



    public AddressBook GetDefaultById(AddressBook addressBook) {

        AddressBook defaultAddress = addressBookMapper.GetDefaultById(addressBook);
        return defaultAddress;
    }


    public AddressBook GetById(Long id) {

        return addressBookMapper.GetById(id);
    }


    public void updateById(AddressBook addressBook) {
        addressBookMapper.updateById(addressBook);
    }


    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }

    public void SetDafaultById(AddressBook addressBook) {
        /**
         * 1.首先获取用户id对应的所有地址然后将所有地址重置为非默认地址
         * 2.对想要设置默认地址的数据进行设置
         */

        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());

        addressBookMapper.updateDefault(addressBook);

        addressBook.setIsDefault(1);
        addressBookMapper.updateById(addressBook);
    }
}
