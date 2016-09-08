package com.chyld.controllers;

import com.chyld.entities.Device;
import com.chyld.entities.Position;
import com.chyld.entities.Run;
import com.chyld.repositories.IPositionRepository;
import com.chyld.services.DeviceService;
import com.chyld.services.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/positions")
public class PositionController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    RunService runService;

    @Autowired
    IPositionRepository positionRepository;

    @RequestMapping(value = "/{serialNumber}", method = RequestMethod.POST)
    public ResponseEntity<?> addPosition(@PathVariable String serialNumber, @RequestBody Position position) {

        Device device = deviceService.findDeviceBySerialNumber(serialNumber);

        if(!runService.isARunActive(device)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Run run = runService.findActiveRunByDevice(device);

        position.setRun(run);

        run.getPositions().add(position);

        runService.saveRun(run);
        return ResponseEntity.status(HttpStatus.CREATED).body(position);
    }

}