package com.sdxx.purchaser.controller;

import com.sdxx.commonutils.R;
import com.sdxx.purchaser.service.ContentMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 内容管理菜单Controller
 */
@RestController
@RequestMapping("/purchaser/guest/contextMenu")
public class ContentMenuController {
    @Autowired
    private ContentMenuService contentMenuService;

    /**
     * 查询内容管理菜单
     */
    @PostMapping("menu")
    public R selectAC(@RequestParam(defaultValue = "0") int id) {
        return null;
    }
}
