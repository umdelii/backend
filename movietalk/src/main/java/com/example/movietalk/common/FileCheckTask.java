package com.example.movietalk.common;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.movietalk.movie.dto.MovieImageDTO;
import com.example.movietalk.movie.entity.MovieImage;
import com.example.movietalk.movie.repository.MovieImageRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class FileCheckTask {

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Value("${com.example.movietalk.upload.path}")
    private String uploadPath;

    // 昨日のフォルダ取得
    private String getFolderYesterday() {
        // now + minus day
        LocalDate yesterday = LocalDate.now().minusDays(1);
        // localdate => string
        String str = yesterday.toString(); // "2026-01-05"
        // - => / 記号変更
        return str.replace("-", File.separator);
    }

    @Scheduled(cron = "0 30 18 * * *")
    public void checkFile() {
        log.info("file check test");

        // 昨日のmovieimage db 取得
        String path = LocalDate.now()
                .minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        List<MovieImage> oldImages = movieImageRepository.getOldFileImages(path);

        // entity => dto
        List<MovieImageDTO> movieImageDTOs = oldImages.stream().map(e -> {
            return MovieImageDTO.builder()
                    .inum(e.getInum())
                    .imgName(e.getImgName())
                    .uuid(e.getUuid())
                    .path(e.getPath())
                    .build();
        }).collect(Collectors.toList());

        // db에서 가져온 파일 내용을 Path객체로 수집 - ファイル削除する時サムネファイルも(s_)
        List<Path> fileList = movieImageDTOs.stream()
                // Paths.get c:\\upload\\2026\\01\\05\\uuid_imagename
                .map(dto -> Paths.get(uploadPath, dto.getPath(), dto.getUuid() + "_" + dto.getImgName()))
                .collect(Collectors.toList());

        movieImageDTOs.stream()
                .map(dto -> Paths.get(uploadPath, dto.getPath(), "s_" + dto.getUuid() + "_" + dto.getImgName()))
                .forEach(fileList::add);

        log.info("DBのファイルlist {}", fileList);

        // db 파일과 폴더 파일 비교
        File tarFile = Paths.get(uploadPath, getFolderYesterday()).toFile();
        // 지울 파일과 db 파일이 일치하지 않는지 확인
        File[] removFiles = tarFile.listFiles(f -> fileList.contains(f.toPath()) == false);

        if (removFiles != null) {
            for (File file : removFiles) {
                log.warn(file.getAbsolutePath());
                file.delete();
            }
        }
    }
}
