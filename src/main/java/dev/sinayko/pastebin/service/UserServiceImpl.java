package dev.sinayko.pastebin.service;

import dev.sinayko.pastebin.domain.User;
import dev.sinayko.pastebin.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User get(int id) {
        return repository.getReferenceById(id);
    }

    @Override
    public User getByUserName(String name) {
        return repository.findByUserName(name);
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
