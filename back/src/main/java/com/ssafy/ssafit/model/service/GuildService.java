package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Guild;

import java.util.List;

public interface GuildService {

    boolean createGuild(Guild guild);
    List<Guild> guildList(String search);
    String updateGuild(Guild guild, String loginUser);
    String deleteGuild(Guild guild);
    String quitMasterGuild(Guild guild, String loginUser);
    boolean quitSSAFIT(String userId);
    Guild getGuild(long GuildId);
}
