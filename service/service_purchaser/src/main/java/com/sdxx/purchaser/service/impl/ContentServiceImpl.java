package com.sdxx.purchaser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdxx.purchaser.entity.Content;
import com.sdxx.purchaser.mapper.ContentMapper;
import com.sdxx.purchaser.service.ContentService;
import org.springframework.stereotype.Service;

/**
 * 内容管理正文ServiceImpl
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {
}
