package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Email;

public interface EmailDao {
    boolean insert(Email address);
    Email select(String address);
    boolean delete(int emailId);
}
