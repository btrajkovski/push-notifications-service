package com.btrajkovski.push.notifications.controller;

import com.btrajkovski.push.notifications.controller.auth.utils.SecurityUtil;
import com.btrajkovski.push.notifications.model.Device;
import com.btrajkovski.push.notifications.model.base.BaseEntity;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import com.btrajkovski.push.notifications.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bojan on 13.5.16.
 */
@RestController
@RequestMapping(path = "/api/devices")
public class DeviceController {
    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/{appIdentifier}", method = RequestMethod.POST)
    public Device addDevice(@RequestBody Device device, @PathVariable String appIdentifier) throws UserNotAuthenticated {
        return deviceService.save(device, appIdentifier);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Device> listAllDevices() throws UserNotAuthenticated {
        return deviceService.findDevicesByUser(SecurityUtil.getUserDetails());
    }

    @RequestMapping(method = RequestMethod.PUT)
    public BaseEntity editDevice(@RequestBody Device device) throws UserNotAuthenticated {
        return deviceService.save(device);
    }
}
