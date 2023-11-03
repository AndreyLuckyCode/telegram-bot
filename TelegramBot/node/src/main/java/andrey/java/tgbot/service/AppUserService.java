package andrey.java.tgbot.service;

import andrey.java.tgbot.entity.AppUser;

public interface AppUserService {
    String registerUser(AppUser appUser);
    String setEmail(AppUser appUser, String email);
}
