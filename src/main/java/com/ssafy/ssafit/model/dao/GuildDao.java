package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Guild;

import java.util.List;

public interface GuildDao {
    int insert(Guild guild);
    List<Guild> search(String guildName);
    Guild select(long guildId);
    int update(Guild guild);
    int delete(long guildId);
    List<Guild> myGuilds (String userId);
}
