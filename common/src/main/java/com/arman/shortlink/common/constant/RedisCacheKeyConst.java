package com.arman.shortlink.common.constant;

/**
 * @author Arman
 */
public interface RedisCacheKeyConst {

    String LOCK_PREFIX = "hort-link:lock:";

    String LOCK_USER_REGISTER_KEY = LOCK_PREFIX + "lock_user_register:";

    String USER_LOGIN_PREFIX = "hort-link:login:";

}
