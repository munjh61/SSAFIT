package com.ssafy.ssafit.controller.guild;

import com.ssafy.ssafit.model.dto.Crew;
import com.ssafy.ssafit.model.dto.Guild;
import com.ssafy.ssafit.model.service.GuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/guild")
public class GuildPublicController {

    private final GuildService guildService;

    @GetMapping
    public ResponseEntity<List<Guild>> guildList(@RequestParam(name = "q", required = false) String search) {
        List<Guild> list = guildService.guildList(search);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{guildId}")
    public ResponseEntity<Guild> getDetail(@PathVariable("guildId") Long guildId){
        Guild guild = guildService.getGuild(guildId);
        return new ResponseEntity<>(guild, HttpStatus.OK);
    }
}
