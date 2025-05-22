package com.ssafy.ssafit.controller.guild;

import com.ssafy.ssafit.model.dto.Guild;
import com.ssafy.ssafit.model.service.GuildService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guild")
public class GuildController {

    private final GuildService guildService;

    // --- 모임 ---

    @PostMapping
    public ResponseEntity<String> createGuild(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Guild guild) {
        guild.setUserId(userDetails.getUsername());
        if (guild.getGuildName() == null || guild.getGuildName().isEmpty()) {
            return new ResponseEntity<>("모임명을 입력하지 않았습니다.", HttpStatus.BAD_REQUEST);
        }
        if (!guildService.createGuild(guild)) {
            return new ResponseEntity<>("중복된 모임명입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("모임이 생성되었습니다.", HttpStatus.OK);
    }

    @PutMapping("/{guildId}")
    public ResponseEntity<String> updateGuild(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("guildId") long guildId, @RequestBody Guild guild) {
        guild.setGuildId(guildId);
        String msg = guildService.updateGuild(guild, userDetails.getUsername());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @DeleteMapping("/{guildId}")
    public ResponseEntity<String> deleteGuild(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("guildId") long guildId) {
        Guild guild = Guild.builder()
                .guildId(guildId)
                .userId(userDetails.getUsername())
                .build();
        String msg = guildService.deleteGuild(guild);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PutMapping("/qgm/{guildId}")
    public ResponseEntity<String> quitGuildMaster(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("guildId") long guildId, @RequestBody Guild guild){
        String msg = guildService.quitMasterGuild(guild, userDetails.getUsername());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
