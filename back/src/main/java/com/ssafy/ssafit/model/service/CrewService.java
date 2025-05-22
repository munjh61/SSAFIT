package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Crew;

public interface CrewService {
    String manageCrew(Crew crew, String manage, String userId);
    String quitCrew(long crewId, String userId);
}
