package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Guild;

import java.util.List;

public interface GuildService {

    boolean createGuild(Guild guild);
    List<Guild> guildList(String search);
    boolean updateGuild(Guild guild);
    boolean deleteGuild(long guildId);
    String giveOwnershipOrDeleteGuild(Guild guild, String nextOwner, boolean quit, boolean delete);
    boolean quitSSAFIT(String userId);
}
