package com.chyld.controllers;

import com.chyld.dtos.AuthDto;
import com.chyld.dtos.UserDto;
import com.chyld.entities.Device;
import com.chyld.entities.Role;
import com.chyld.entities.Run;
import com.chyld.entities.User;
import com.chyld.enums.RoleEnum;
import com.chyld.security.JwtToken;
import com.chyld.services.DeviceService;
import com.chyld.services.RoleService;
import com.chyld.services.RunService;
import com.chyld.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/runs")
public class RunController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    RunService runService;

    @RequestMapping(value = "/{serialNumber}/start", method = RequestMethod.POST)
    public ResponseEntity<?> startRun(@PathVariable String serialNumber) {

        Device device = deviceService.findDeviceBySerialNumber(serialNumber);

        if(runService.isARunActive(device)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Run run = new Run();
        run.setDevice(device);

        device.getRuns().add(run);

        deviceService.saveDevice(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "/{serialNumber}/stop", method = RequestMethod.POST)
    public ResponseEntity<?> stopRun(@PathVariable String serialNumber) {

        Device device = deviceService.findDeviceBySerialNumber(serialNumber);

        Run run = runService.findActiveRunByDevice(device);
        if(run == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        run.setStopTime(new Date());

        runService.saveRun(run);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
