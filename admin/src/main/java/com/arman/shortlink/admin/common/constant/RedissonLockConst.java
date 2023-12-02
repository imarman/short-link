package com.arman.shortlink.admin.common.constant;

/**
 * @author Arman
 */
public interface RedissonLockConst {

    String LOCK_PREFIX = "hort-link:";

    String LOCK_USER_REGISTER_KEY = LOCK_PREFIX + "lock_user_register";

}
