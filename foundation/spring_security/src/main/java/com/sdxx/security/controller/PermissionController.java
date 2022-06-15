package com.sdxx.security.controller;

import com.sdxx.security.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionController {
    @Autowired
    PermissionService permissionService;
}
