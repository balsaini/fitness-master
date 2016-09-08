package com.chyld.messaging;

import com.chyld.entities.Device;
import com.chyld.entities.Position;
import com.chyld.entities.Run;
import com.chyld.services.DeviceService;
import com.chyld.services.RunService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class PositionComputeService {
    @Autowired
    DeviceService deviceService;

    @Autowired
    RunService runService;

    @RabbitListener(queues = "fit.queue.pos")
    @Transactional
    public void receive(Message msg, HashMap<String, Object> data){
        String key = msg.getMessageProperties().getReceivedRoutingKey();
        String serialNumber = (String) data.get("serialNumber");
        Position position = (Position) data.get("position");

        Device device = deviceService.findDeviceBySerialNumber(serialNumber);

        if(!runService.isARunActive(device)) {
            return;
        }

        Run run = runService.findActiveRunByDevice(device);
        position.setRun(run);
        run.getPositions().add(position);

        runService.saveRun(run);
    }
}
