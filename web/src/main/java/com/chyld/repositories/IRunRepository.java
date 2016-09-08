package com.chyld.repositories;

import com.chyld.entities.Device;
import com.chyld.entities.Run;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IRunRepository extends PagingAndSortingRepository<Run, Integer> {
    List<Run> findByDeviceAndStopTimeIsNull(Device device);

    Run findOneByDeviceAndStopTimeIsNull(Device device);
}
