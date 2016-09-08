package com.chyld.services;

import com.chyld.entities.Device;
import com.chyld.entities.Run;
import com.chyld.repositories.IRunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunService {

    @Autowired
    IRunRepository repository;

    public Run saveRun(Run run) {
        return repository.save(run);
    }

    public Run findRunById(Integer id) {
        return repository.findOne(id);
    }

    public Boolean isARunActive(Device device) {
        List<Run> activeRuns = repository.findByDeviceAndStopTimeIsNull(device);
        if(activeRuns.isEmpty()) {
            return false;
        }
        return true;
    }

    public Run findActiveRunByDevice(Device device) {
       return repository.findOneByDeviceAndStopTimeIsNull(device);
    }

}
