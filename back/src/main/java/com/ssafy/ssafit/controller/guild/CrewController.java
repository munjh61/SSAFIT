package com.ssafy.ssafit.controller.guild;

import com.ssafy.ssafit.model.dto.Crew;
import com.ssafy.ssafit.model.service.CrewService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crew")
public class CrewController {

    private final CrewService crewService;

    // --- 인원 ---

    // 신청
    @PostMapping("/{manage}")
    public ResponseEntity<String> apply(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Crew crew, @PathVariable("manage")String manage){
        // manage : apply, invite, applyAccept, applyRefuse, inviteAccept, inviteRefuse
        String msg = crewService.manageCrew(crew, manage, userDetails.getUsername());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping("/{crewId}")
    public ResponseEntity<String> delete(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("crewId") long crewId){
        String msg = crewService.quitCrew(crewId, userDetails.getUsername());
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    // 인원 확인
    @GetMapping("/{guildId}")
    public ResponseEntity<List<Crew>> crewList(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("guildId") long guildId){
        String userId = userDetails.getUsername();
        List<Crew> list = crewService.getCrews(userId,guildId);
        if(list.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
