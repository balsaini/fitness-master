package com.chyld.messaging;

import com.chyld.entities.Device;
import com.chyld.entities.Run;
import com.chyld.services.DeviceService;
import com.chyld.services.RunService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RunComputeService {

    @Autowired
    DeviceService deviceService;

    @Autowired
    RunService runService;

    @RabbitListener(queues = "fit.queue.run")
    @Transactional
    public void receive(Message msg, String serialNumber){
        String key = msg.getMessageProperties().getReceivedRoutingKey();
        if(key.contains("start")) {
            Device device = deviceService.findDeviceBySerialNumber(serialNumber);

            if(runService.isARunActive(device)) {
                return;
            }
            Run run = new Run();
            run.setDevice(device);
            device.getRuns().add(run);

            deviceService.saveDevice(device);
        }
        else if(key.contains("stop")) {
            Device device = deviceService.findDeviceBySerialNumber(serialNumber);

            Run run = runService.findActiveRunByDevice(device);
            if(run == null) {
                return;
            }

            run.setStopTime(new Date());

            runService.saveRun(run);
        }
        return;
    }

}
