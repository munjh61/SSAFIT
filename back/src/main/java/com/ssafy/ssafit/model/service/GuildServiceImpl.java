package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.CrewDao;
import com.ssafy.ssafit.model.dao.GuildDao;
import com.ssafy.ssafit.model.dto.Board;
import com.ssafy.ssafit.model.dto.Crew;
import com.ssafy.ssafit.model.dto.Guild;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuildServiceImpl implements GuildService {

    private final GuildDao guildDao;
    private final CrewDao crewDao;

    @Transactional
    @Override
    public boolean createGuild(Guild guild) {
        try {
            guildDao.insert(guild);
            String userId = guild.getUserId();
            long guildId = guild.getGuildId();
            Crew crew = Crew.builder()
                    .userId(userId)
                    .guildId(guildId)
                    .status(2)
                    .build();
            crewDao.insert(crew);
        } catch (DuplicateKeyException e) {
            // 모임명은 unique값
            return false;
        }
        return true;
    }

    @Override
    public List<Guild> guildList(String search) {
        return guildDao.search(search);
    }

    @Transactional
    @Override
    public String updateGuild(Guild guild, String loginUser) {
        // 수정할게 없다면 Syntax 오류를 막기 위해 if문으로 먼저 처리함.
        if (guild.getGuildName() == null && guild.getUserId() == null && guild.getDescription() == null) {
            return "아무것도 수정하려하지 않습니다.";
        }
        Guild tmp = guildDao.select(guild.getGuildId());
        // 잘못된 guildId
        if (tmp == null) {
            return "잘못된 guildId";
        }
        // 모임장이 아닐 경우
        if (!tmp.getUserId().equalsIgnoreCase(loginUser)) {
            return "모임장이 아닙니다.";
        }

        try {
            Crew crew = Crew.builder()
                    .guildId(guild.getGuildId())
                    .userId(guild.getUserId())
                    .build();
            Crew tmp2 = crewDao.selectByGuildIdAndUserId(crew);
            if (tmp2 == null) {
                return "권한을 넘기려하였으나, 다음 모임장으로 받은 아이디가 모임에 참여하지 않은 아이디입니다.";
            }
            guildDao.update(guild);
        } catch (DuplicateKeyException e) {
            return "이미 존재하는 모임명입니다";
        }
        return "모임 정보가 수정되었습니다.";
    }

    @Transactional
    @Override
    public String deleteGuild(Guild guild) {
        Guild tmp = guildDao.select(guild.getGuildId());
        // 잘못된 guildId
        if (tmp == null) {
            return "잘못된 guildId";
        }
        // 모임장이 아닐 경우
        if (!tmp.getUserId().equalsIgnoreCase(guild.getUserId())) {
            return "모임장이 아닙니다.";
        }
        // 모임을 삭제할래
        guildDao.delete(guild.getGuildId());
        return "모임이 삭제되었습니다.";
    }

    @Transactional
    @Override
    public String quitMasterGuild(Guild guild, String loginUser) {
        Guild tmp = guildDao.select(guild.getGuildId());
        // 잘못된 guildId
        if (tmp == null) {
            return "잘못된 guildId";
        }
        // 모임장이 아닐 경우
        if (!tmp.getUserId().equalsIgnoreCase(loginUser)) {
            return "모임장이 아닙니다.";
        }

        // crewDao 사용하기 위해 crew 객체 생성. ... guildId와 userId만 사용해서 dto 만드는게 나은가?
        Crew crew = Crew.builder()
                .guildId(guild.getGuildId())
                .userId(guild.getUserId())
                .status(0)
                .build();

        // 탈퇴할래
        // 모임장이 아닌 나머지 인원
        List<Crew> crews = crewDao.myCrews(crew);
        if (crews.isEmpty()) {
            guildDao.delete(guild.getGuildId());
            return "탈퇴하였으며, 모임장을 위임받을 사람이 없어서 모임이 삭제되었습니다.";
        }
        // 모임장 위임
        if (guild.getUserId() == null || guild.getUserId().isEmpty()) {
            guild.setUserId(crews.get(0).getUserId());
        }
        guild.setHeadCount(guild.getHeadCount() - 1);
        guildDao.update(guild);
        crewDao.delete(crew);
        return "탈퇴하였으며, 다른 모임장을 위임하였습니다.";
    }

    @Transactional
    @Override
    public boolean quitSSAFIT(String userId) {
        List<Guild> guilds = guildDao.myGuilds(userId);
        for (Guild guild : guilds) {
            quitMasterGuild(guild, null);
        }
        return true;
    }


}
