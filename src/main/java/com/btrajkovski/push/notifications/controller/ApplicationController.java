package com.btrajkovski.push.notifications.controller;

import com.btrajkovski.push.notifications.controller.auth.utils.SecurityUtil;
import com.btrajkovski.push.notifications.model.Application;
import com.btrajkovski.push.notifications.model.exception.UserNotAuthenticated;
import com.btrajkovski.push.notifications.service.ApplicationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by bojan on 2.4.16.
 */
@RestController
@RequestMapping(path = "/api/applications")
public class ApplicationController {
    private ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @RequestMapping
    public List<Application> listAllApplications() throws UserNotAuthenticated {
        return applicationService.findApplicationsByUser(SecurityUtil.getUserDetails());
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, headers = "content-type=multipart/*")
    public Application addApplication(@RequestParam String application, @RequestParam(required = false) MultipartFile file) throws UserNotAuthenticated, IOException, SQLException {
        Application applicationObject = getApplication(application, file);
        return (Application) applicationService.save(applicationObject);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public Application deleteApplication(@PathVariable Long id) throws UserNotAuthenticated {
        return (Application) applicationService.delete(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, headers = "content-type=multipart/*", path = "/edit")
    public Application editApplication(@RequestParam String application, @RequestParam(required = false) MultipartFile file) throws UserNotAuthenticated, IOException, SQLException {
        Application applicationObject = getApplication(application, file);
        return (Application) applicationService.edit(applicationObject);
    }

    private Application getApplication(String application, MultipartFile file) throws IOException, SQLException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(application);

        Application applicationObject = mapper.convertValue(node, Application.class);
        if (file != null) {
            applicationObject.getAppleCertificate().setFile(new SerialBlob(file.getBytes()));
        }
        return applicationObject;
    }
}