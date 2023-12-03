package com.arman.shortlink.admin.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Arman
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResp {

    private String token;

}
