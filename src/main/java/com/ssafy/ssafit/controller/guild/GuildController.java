package com.ssafy.ssafit.controller.guild;

import com.ssafy.ssafit.model.dto.Guild;
import com.ssafy.ssafit.model.service.GuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guild")
public class GuildController {

    private final GuildService guildService;

    @PostMapping
    public ResponseEntity<String> createGuild(@RequestBody Guild guild){
        if(!guildService.createGuild(guild)){
            return new ResponseEntity<>("중복된 모임명입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("모임이 생성되었습니다.", HttpStatus.OK);
    }
}
