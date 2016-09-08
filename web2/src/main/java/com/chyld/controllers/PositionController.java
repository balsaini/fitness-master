package com.chyld.controllers;

import com.chyld.entities.Device;
import com.chyld.entities.Position;
import com.chyld.entities.Run;
import com.chyld.repositories.IPositionRepository;
import com.chyld.services.DeviceService;
import com.chyld.services.RunService;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/positions")
public class PositionController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    RunService runService;

    @Autowired
    IPositionRepository positionRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    TopicExchange topicExchange;

    @RequestMapping(value = "/{serialNumber}", method = RequestMethod.POST)
    public ResponseEntity<?> addPosition(@PathVariable String serialNumber, @RequestBody Position position) {
//
//        Device device = deviceService.findDeviceBySerialNumber(serialNumber);
//
//        if(!runService.isARunActive(device)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        Run run = runService.findActiveRunByDevice(device);
//        position.setRun(run);
//        run.getPositions().add(position);
//
//        runService.saveRun(run);
//        return ResponseEntity.status(HttpStatus.CREATED).body(position);

        String topicName = topicExchange.getName();
        Map<String, Object> hm = new HashMap<>();
        hm.put("serialNumber", serialNumber);
        hm.put("position", position);
        rabbitTemplate.convertAndSend(topicName, "fit.topic.pos", hm);

        return null;
    }

}