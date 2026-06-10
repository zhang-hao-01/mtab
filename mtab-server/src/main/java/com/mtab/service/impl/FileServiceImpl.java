package com.mtab.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtab.entity.FileEntity;
import com.mtab.mapper.FileMapper;
import com.mtab.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 文件服务实现
 *
 * @author zhanghao
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {

    @Value("${mtab.upload.path:./uploads}")
    private String uploadPath;

    @Override
    public Page<FileEntity> list(Integer userId, Integer page, Integer size) {
        Page<FileEntity> pageParam = new Page<>(page, size);
        return this.page(pageParam, new LambdaQueryWrapper<FileEntity>()
                .eq(FileEntity::getUserId, userId)
                .orderByDesc(FileEntity::getCreateTime));
    }

    @Override
    public void del(Long id, Integer userId) {
        FileEntity fileEntity = this.getOne(new LambdaQueryWrapper<FileEntity>()
                .eq(FileEntity::getId, id)
                .eq(FileEntity::getUserId, userId));
        if (fileEntity != null) {
            // 删除物理文件
            FileUtil.del(fileEntity.getPath());
            this.removeById(id);
        }
    }

    @Override
    public void scanLocal() {
        // 扫描本地文件目录
        File dir = new File(uploadPath);
        if (dir.exists() && dir.isDirectory()) {
            scanDirectory(dir);
        }
    }

    private void scanDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file);
            } else {
                String path = file.getAbsolutePath();
                long count = this.count(new LambdaQueryWrapper<FileEntity>().eq(FileEntity::getPath, path));
                if (count == 0) {
                    FileEntity fileEntity = FileEntity.builder()
                            .path(path)
                            .userId(0)
                            .createTime(LocalDateTime.now())
                            .size((double) file.length())
                            .mimeType(FileUtil.getMimeType(path))
                            .hash(DigestUtil.md5Hex(file))
                            .build();
                    this.save(fileEntity);
                }
            }
        }
    }

    @Override
    public FileEntity addFile(MultipartFile file, Integer userId) {
        try {
            String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String dirPath = uploadPath + File.separator + datePath;
            FileUtil.mkdir(dirPath);

            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";
            String fileName = System.currentTimeMillis() + ext;
            String filePath = dirPath + File.separator + fileName;

            file.transferTo(new File(filePath));

            String hash = DigestUtil.md5Hex(file.getBytes());

            FileEntity fileEntity = FileEntity.builder()
                    .path(filePath)
                    .userId(userId)
                    .createTime(LocalDateTime.now())
                    .size((double) file.getSize())
                    .mimeType(file.getContentType())
                    .hash(hash)
                    .build();
            this.save(fileEntity);
            return fileEntity;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }
}
