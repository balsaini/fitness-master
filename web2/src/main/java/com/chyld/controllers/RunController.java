package com.chyld.controllers;

import com.chyld.entities.Device;
import com.chyld.entities.Run;
import com.chyld.services.DeviceService;
import com.chyld.services.RunService;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/runs")
public class RunController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    RunService runService;

    @Autowired
    TopicExchange topicExchange;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/{serialNumber}/start", method = RequestMethod.POST)
    public ResponseEntity<?> startRun(@PathVariable String serialNumber) {

//        Device device = deviceService.findDeviceBySerialNumber(serialNumber);
//
//        if(runService.isARunActive(device)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        Run run = new Run();
//        run.setDevice(device);
//
//        device.getRuns().add(run);
//
//        deviceService.saveDevice(device);
//        return ResponseEntity.status(HttpStatus.CREATED).body(null);
        String topicName = topicExchange.getName();
        rabbitTemplate.convertAndSend(topicName, "fit.topic.run.start", serialNumber);
        return null;
    }

    @RequestMapping(value = "/{serialNumber}/stop", method = RequestMethod.POST)
    public ResponseEntity<?> stopRun(@PathVariable String serialNumber) {
//
//        Device device = deviceService.findDeviceBySerialNumber(serialNumber);
//
//        Run run = runService.findActiveRunByDevice(device);
//        if(run == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        run.setStopTime(new Date());
//
//        runService.saveRun(run);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        String topicName = topicExchange.getName();
        rabbitTemplate.convertAndSend(topicName, "fit.topic.run.stop", serialNumber);
        return null;
    }
}
