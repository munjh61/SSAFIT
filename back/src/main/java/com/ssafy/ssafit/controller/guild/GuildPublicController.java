package com.ssafy.ssafit.controller.guild;

import com.ssafy.ssafit.model.dto.Guild;
import com.ssafy.ssafit.model.service.GuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guild/public")
public class GuildPublicController {

    private final GuildService guildService;

    @GetMapping
    public ResponseEntity<List<Guild>> guildList(@RequestBody Map<String, String> map){
        String search = map.get("search");
        List<Guild> list = guildService.guildList(search);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
