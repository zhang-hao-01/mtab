package com.mtab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mtab.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口
 *
 * @author zhanghao
 */
public interface FileService extends IService<FileEntity> {

    /**
     * 文件列表
     */
    Page<FileEntity> list(Integer userId, Integer page, Integer size);

    /**
     * 删除文件
     */
    void del(Long id, Integer userId);

    /**
     * 扫描本地文件
     */
    void scanLocal();

    /**
     * 添加文件
     */
    FileEntity addFile(MultipartFile file, Integer userId);
}
