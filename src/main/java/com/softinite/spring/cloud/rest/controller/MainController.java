package com.softinite.spring.cloud.rest.controller;

import com.softinite.spring.cloud.rest.pojo.FileResult;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sergiu Ivasenco on 5/13/18.
 */
@RestController
public class MainController {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(100);

    @RequestMapping("/file")
    public DeferredResult<FileResult> readFile() {
        DeferredResult<FileResult> dr = new DeferredResult<>();

        EXECUTOR_SERVICE.submit(() -> {
            FileResult fileResult = new FileResult();
            File file = new File("static.html");
            try {
                fileResult.setContent(FileUtils.readFileToString(file));
                dr.setResult(fileResult);
            } catch (IOException e) {
                dr.setErrorResult(e);
                e.printStackTrace();
            }
        });

        return dr;
    }

}
