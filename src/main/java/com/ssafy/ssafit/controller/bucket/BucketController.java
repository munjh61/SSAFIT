package com.ssafy.ssafit.controller.bucket;

import com.ssafy.ssafit.model.service.BucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/bucket")
@RequiredArgsConstructor
public class BucketController {
     private final BucketService bucketService;


}
