package com.chyld.services;

import com.chyld.entities.Device;
import com.chyld.repositories.IDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    IDeviceRepository repository;

    public Device saveDevice(Device device) {
        return repository.save(device);
    }

    public Device findDeviceById(Integer id) {
        return repository.findOne(id);
    }

    public Device findDeviceBySerialNumber(String serialNumber) { return repository.findOneBySerialNumber(serialNumber); }

}
