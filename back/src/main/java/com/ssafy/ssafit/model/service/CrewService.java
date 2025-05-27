package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Crew;

import java.util.List;
import java.util.Map;

public interface CrewService {
    Map<String, Object> manageCrew(Crew crew, String manage, String userId);
    String quitCrew(Crew crew, String userId);
    List<Crew> getCrews(String userId, long guildId);
    List<Crew> getCandidates( long guildId);
}
