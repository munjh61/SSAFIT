package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Crew;
import com.ssafy.ssafit.model.dto.Guild;

import java.util.List;

public interface CrewDao {
    Crew select(long crewId);
    int insert(Crew crew);
    List<Crew> myCrews(Crew crew);
    Crew selectByGuildIdAndUserId(Crew Crew);
    int delete(Crew crew);
    int update(Crew crew);
    List<Crew> search(Crew crew);
    List<Crew> candidates(long guildId);
}
