package com.arman.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @author Arman
 */
@Data
public class UserLoginReq {

    private String username;

    private String password;
}
