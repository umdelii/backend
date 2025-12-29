package com.example.movietalk.common;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.movietalk.movie.dto.MovieImageDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/upload")
public class UploadController {

    @Value("${com.example.movietalk.upload.path}")
    private String uploadPath;

    @GetMapping("/upload")
    public void getUpload() {
        log.info("upload form 呼び出し");
    }

    @PostMapping("/upload")
    @ResponseBody
    public List<MovieImageDTO> postUpload(MultipartFile[] uploadFiles) {
        // フォルダに保存
        // 1. フォルダ生成
        String saveDirPath = makeDir();
        List<MovieImageDTO> upList = new ArrayList<>();

        for (MultipartFile file : uploadFiles) {
            log.info("getOriginalFilename : {}", file.getOriginalFilename());
            log.info("getSize : {}", file.getSize());
            log.info("getContentType : {}", file.getContentType());

            // uuid 生成
            String uuid = UUID.randomUUID().toString();
            // クライアントがアップロードしたファイル名
            String oriName = file.getOriginalFilename();

            // 保存情報を画面に送るための客体
            upList.add(MovieImageDTO.builder().path(saveDirPath).imgName(oriName).uuid(uuid).build());

            try {
                // file 保存
                // upload/2025/12/24/~~~_test0.jpg
                String saveName = uploadPath + File.separator + saveDirPath + File.separator + uuid + "_" + oriName;
                File saveFile = new File(saveName);
                file.transferTo(saveFile);

                // thumbnail 保存
                String thumbSaveName = uploadPath + File.separator + saveDirPath + File.separator + "s_" + uuid + "_"
                        + oriName;
                File thumbFile = new File(thumbSaveName);
                Thumbnailator.createThumbnail(saveFile, thumbFile, 100, 100);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return upList;

    }

    // 이미지 화면에 보여주기
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {
        log.info("display {}", fileName);

        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "utf-8");
            File file = new File(uploadPath + File.separator + srcFileName);

            HttpHeaders headers = new HttpHeaders();
            // image/png
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    // フォルダ生成メソッド
    private String makeDir() {

        // 日付を取得
        // new Date(); => SimpleDateFormat
        LocalDate today = LocalDate.now(); // 2025-12-24 => 2025/12/24
        String dateStr = today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        // directory生成
        File file = new File(uploadPath, dateStr);

        if (!file.exists()) {
            file.mkdirs();
        }
        return dateStr;
    }

    // ファイル削除
    @PostMapping("/remove")
    public ResponseEntity<String> removeFile(String fileName) {
        log.info("消すファイル名 {}", fileName);

        ResponseEntity<String> result = null;

        try {
            // %2F => /
            String srcFileName = URLDecoder.decode(fileName, "utf-8");
            File file = new File(uploadPath + File.separator + srcFileName);

            //
            file.delete();

            // サムネ削除
            File thumbFile = new File(file.getParent(), "s_" + file.getName());
            thumbFile.delete();

            result = new ResponseEntity<>("success", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return result;
    }

}
