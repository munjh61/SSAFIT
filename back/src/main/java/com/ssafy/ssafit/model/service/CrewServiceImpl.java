package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.CrewDao;
import com.ssafy.ssafit.model.dao.GuildDao;
import com.ssafy.ssafit.model.dto.Crew;
import com.ssafy.ssafit.model.dto.Guild;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrewServiceImpl implements CrewService {

    private final CrewDao crewDao;
    private final GuildDao guildDao;
    private final GuildService guildService;

    @Transactional
    @Override
    public Map<String, Object> manageCrew(Crew crew, String manage, String userId) {
        Map<String, Object> map = new HashMap<>();
        boolean success =false;
        String msg;

        Guild guild = guildDao.select(crew.getGuildId());
        if (guild == null) {
            msg = "잘못된 guildId";
            map.put("success",success);
            map.put("msg", msg);
            return map;
        }

        String master = guild.getUserId();
        String crewId = crew.getUserId();



        switch (manage) {
            case "apply":
                if (!userId.equals(crewId)) {
                    msg = "신청한 사람이 본인이 아닙니다.";
                } else {
                    if (!apply(crew)) {
                        msg = "이미 신청, 초대하거나 멤버입니다.";
                    } else {
                        success = true;
                        msg = "신청되었습니다.";
                    }
                }
                break;
            case "invite":
                if (!userId.equals(master)) {
                    msg = "초대자가 모임장이 아님";
                } else {
                    if (!invite(crew)) {
                        msg = "이미 신청, 초대하거나 멤버입니다";
                    } else {
                        success = true;
                        msg = "초대 되었습니다.";
                    }
                }
                break;
            case "applyAccept":
                if (!userId.equals(master)) {
                    msg = "허락한 사람이 모임장이 아님.";
                } else {
                    if(!accept(crew)){
                        msg = "신청, 초대 된적이 없음";
                    } else {
                        success = true;
                        msg = "멤버가 되었습니다.";
                    }
                }
                break;
            case "applyRefuse":
                if (!userId.equals(master)) {
                    msg = "모임장이 아니므로 거부할 수 없습니다.";
                } else {
                    if(!refuse(crew)){
                        msg = "신청, 초대 된적이 없음";
                    } else {
                        success = true;
                        msg = "신청 거부되어 table에서 삭제하였습니다.";
                    }
                }
                break;
            case "inviteAccept":
                if (!userId.equals(crewId)) {
                    msg = "허락한 사람이 초대받은 사람이 아님.";
                } else {
                    if(!accept(crew)){
                        msg = "신청, 초대 된적이 없음";
                    } else {
                        success = true;
                        msg = "멤버가 되었습니다.";
                    }
                }
                break;
            case "inviteRefuse":
                if (!userId.equals(crewId)) {
                    msg = "거절한 사람이 초대받은 사람이 아님.";
                } else {
                    if(!refuse(crew)){
                        msg = "신청, 초대 된적이 없음";
                    } else {
                        success = true;
                        msg = "신청 거부되어 table에서 삭제하였습니다.";
                    }
                }
                break;
            default:
                msg = "알 수 없는 요청입니다."; // manage : apply, invite, applyAccept, applyRefuse, inviteAccept, inviteRefuse 중에서 선택해주세요.
        }
        map.put("success",success);
        map.put("msg", msg);
        return map;
    }

    public boolean apply(Crew crew) {
        List<Crew> tmp = crewDao.search(crew);
        if (!tmp.isEmpty()) {
            return false;
        }
        crew.setStatus(2);
        crewDao.insert(crew);
        return true;
    }

    public boolean invite(Crew crew) {
        List<Crew> tmp = crewDao.search(crew);
        if (!tmp.isEmpty()) {
            return false;
        }
        crew.setStatus(1);
        crewDao.insert(crew);
        return true;
    }

    public boolean accept(Crew crew) {
        List<Crew> tmp = crewDao.search(crew);
        if (tmp.isEmpty()) {
            return false;
        }
        crew.setCrewId(tmp.get(0).getCrewId());
        crew.setStatus(0);
        crewDao.update(crew);
        return true;
    }

    public boolean refuse(Crew crew) {
        List<Crew> tmp = crewDao.search(crew);
        if (tmp.isEmpty()) {
            return false;
        }
        crew.setCrewId(tmp.get(0).getCrewId());
        crewDao.delete(crew);
        return true;
    }

    @Transactional
    @Override
    public String quitCrew(Crew tmp, String loginUser) {
        Crew crew = crewDao.selectByGuildIdAndUserId(tmp);
        if (crew == null) {
            return "잘못된 crewId";
        }
        Guild guild = guildDao.select(crew.getGuildId());
        if (guild == null) {
            return "잘못된 guildId";
        }

        String quitter = crew.getUserId();
        String owner = guild.getUserId();

        // 1. 로그인한 사람이 탈퇴되는 사람과 다를 경우, 로그인한 사람이 모임장이 아니라면 탈락
        if (!quitter.equals(loginUser) && !loginUser.equals(owner))
            return "로그인한 사람이 모임장 또는 탈퇴자 본인이 아닙니다.";

        // 2. 로그인한 사람이 탈퇴하는 경우
        if (quitter.equals(loginUser)) {
            // 2.1 탈퇴자(=로그인한자)가 모임장이라면
            if (quitter.equals(owner)) {
                guildService.quitMasterGuild(guild, guild.getUserId());
                return "모임장이 본인을 탈퇴처리하였습니다.";
            }
            // 2.2 탈퇴자가 모임장이 아닌 경우
            // if (quitter.equals(loginUser)) {
            crewDao.delete(crew);
            guild.setHeadCount(guild.getHeadCount() - 1);
            guildDao.update(guild);
            return "탈퇴처리되었습니다.";
            // }
        }
        // 3. 모임장이 탈퇴시키는 경우
        // 여기까지 왔을 경우 항상 참
        // if (owner.equals(loginUser)) {
        crewDao.delete(crew);
        guild.setHeadCount(guild.getHeadCount() - 1);
        guildDao.update(guild);
        // }
        return "탈퇴 처리되었습니다.";

    }

    @Override
    public List<Crew> getCrews(String userId, long guildId) {
        Crew crew = Crew
                .builder()
                .guildId(guildId)
                .userId(userId)
                .build();
        if (crewDao.selectByGuildIdAndUserId(crew) == null) {
            return null;
        }
        crew.setUserId(null);
        return crewDao.search(crew);
    }

    public List<Crew> getCandidates(long guildId) {
        Guild tmp = guildDao.select(guildId);
        if (tmp == null) return null;
        return crewDao.candidates(guildId);
    }
}
