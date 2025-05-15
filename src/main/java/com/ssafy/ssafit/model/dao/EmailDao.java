package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Emailtmp;

public interface EmailDao {
    boolean insert(Emailtmp emailtmp);
    Emailtmp select(String email);
    boolean delete(int emailtmpId);
}
