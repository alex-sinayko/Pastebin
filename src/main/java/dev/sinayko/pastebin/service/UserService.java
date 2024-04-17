package dev.sinayko.pastebin.service;

import dev.sinayko.pastebin.domain.User;

public interface UserService {
    User create(User user);
    User get(int id);
    User getByUserName(String name);
    User update(User user);
    void delete(int id);
}
