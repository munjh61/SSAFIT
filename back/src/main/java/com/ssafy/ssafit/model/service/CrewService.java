package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Crew;

public interface CrewService {
    String apply(Crew crew);
    String applyReply(Crew crew);
    String invite(Crew crew);
    String inviteReply(Crew crew);
    String quitCrew(Crew crew);
}
