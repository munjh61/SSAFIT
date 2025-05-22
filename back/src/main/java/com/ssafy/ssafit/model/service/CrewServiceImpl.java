package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.CrewDao;
import com.ssafy.ssafit.model.dao.GuildDao;
import com.ssafy.ssafit.model.dto.Crew;
import com.ssafy.ssafit.model.dto.Guild;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewServiceImpl implements CrewService {

    private final CrewDao crewDao;
    private final GuildDao guildDao;
    private final GuildService guildService;

    @Transactional
    @Override
    public String manageCrew(Crew crew, String manage, String userId) {

        Guild guild = guildDao.select(crew.getGuildId());
        if (guild == null) {
            return "잘못된 guildId";
        }

        String master = guild.getUserId();
        String crewId = crew.getUserId();
        String msg;

        switch (manage) {
            case "apply":
                if (!userId.equals(crewId)) {
                    msg = "신청한 사람이 본인이 아닙니다.";
                } else {
                    msg = apply(crew);
                }
                break;
            case "invite":
                if (!userId.equals(master)) {
                    msg = "초대자가 모임장이 아님";
                } else {
                    msg = invite(crew);
                }
                break;
            case "applyAccept":
                if (!userId.equals(master)) {
                    msg = "허락한 사람이 모임장이 아님.";
                } else {
                    msg = accept(crew);
                }
                break;
            case "applyRefuse":
                if (!userId.equals(master)) {
                    msg = "모임장이 아니므로 거부할 수 없습니다.";
                } else {
                    msg = refuse(crew);
                }
                break;
            case "inviteAccept":
                if (!userId.equals(crewId)) {
                    msg = "허락한 사람이 초대받은 사람이 아님.";
                } else {
                    msg = accept(crew);
                }
                break;
            case "inviteRefuse":
                if (!userId.equals(crewId)) {
                    msg = "거절한 사람이 초대받은 사람이 아님.";
                } else {
                    msg = refuse(crew);
                }
                break;
            default:
                msg = "알 수 없는 manage입니다. manage : apply, invite, applyAccept, applyRefuse, inviteAccept, inviteRefuse 중에서 선택해주세요.";
        }

        return msg;
    }

    public String apply(Crew crew) {
        List<Crew> tmp = crewDao.search(crew);
        if (!tmp.isEmpty()) {
            return "이미 신청, 초대하거나 멤버임";
        }
        crew.setStatus(2);
        crewDao.insert(crew);
        return "신청되었습니다.";
    }

    public String invite(Crew crew) {
        List<Crew> tmp = crewDao.search(crew);
        if (!tmp.isEmpty()) {
            return "이미 신청, 초대하거나 멤버임";
        }
        crew.setStatus(1);
        crewDao.insert(crew);
        return "초대되었습니다.";
    }

    public String accept(Crew crew) {
        List<Crew> tmp = crewDao.search(crew);
        if (tmp.isEmpty()) {
            return "신청, 초대 된적이 없음";
        }
        crew.setCrewId(tmp.get(0).getCrewId());
        crew.setStatus(0);
        crewDao.update(crew);
        return "멤버가 되었습니다.";
    }

    public String refuse(Crew crew) {
        List<Crew> tmp = crewDao.search(crew);
        if (tmp.isEmpty()) {
            return "신청, 초대 된적이 없음";
        }
        crew.setCrewId(tmp.get(0).getCrewId());
        crewDao.delete(crew);
        return "신청 거부되어 table에서 삭제하였습니다.";
    }

    @Transactional
    @Override
    public String quitCrew(long crewId, String loginUser) {
        Crew crew = crewDao.select(crewId);
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
}
