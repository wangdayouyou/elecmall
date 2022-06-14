package com.sdxx.purchaser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdxx.purchaser.entity.ContentMenu;
import com.sdxx.purchaser.mapper.ContentMenuMapper;
import com.sdxx.purchaser.service.ContentMenuService;
import org.springframework.stereotype.Service;

/**
 * 内容管理正文ServiceImpl
 */
@Service
public class ContentMenuServiceImpl extends ServiceImpl<ContentMenuMapper, ContentMenu> implements ContentMenuService {
}
