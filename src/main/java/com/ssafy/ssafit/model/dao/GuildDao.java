package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Guild;

import java.util.List;

public interface GuildDao {
    int insert(Guild guild);
    List<Guild> searchGuild(String guildName);
    Guild select(int guildId);
    int update(Guild guild);
    int delete(Guild guild);
}
