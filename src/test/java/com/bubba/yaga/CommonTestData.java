package com.bubba.yaga;

import com.bubba.yaga.entity.User;
import com.bubba.yaga.entity.UserWithCity;

import java.util.List;
import java.util.Set;

public class CommonTestData {

    public static final User USER_MAURICE = new User(1, "Maurise", "Shieldon", "mshieldon0@squidoo.com", "192.57.232.111", 34.003135, -117.7228641);
    public static final User USER_BENDIX = new User(2,"Bendix", "Halgarth", "bhalgarth1@timesonline.co.uk", "4.185.73.82", -2.9623869, 104.7399789);
    public static final UserWithCity USER_MEGHAN = new UserWithCity(3,"Meghan", "Southall", "msouthall2@ihg.com", "41.243.184.215", 15.45033, 44.12768,"Qaryat al Qābil");

    public static final List<User> USERS_WITHOUT_CITY = List.of(USER_MAURICE, USER_BENDIX);
    public static final List<User> USERS_WITH_CITY = List.of(USER_MEGHAN);

    public static final Set<User> USER_SET = Set.of(USER_MAURICE, USER_BENDIX);

}
