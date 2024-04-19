package dev.sinayko.pastebin.service;

import dev.sinayko.pastebin.domain.S3Mapping;
import dev.sinayko.pastebin.domain.User;
import dev.sinayko.pastebin.repository.S3MappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class S3MappingServiceImpl implements S3MappingService {

    private S3MappingRepository repository;
    private UserService userService;

    @Override
    public S3Mapping getById(String id) {
        return repository.getReferenceById(id);
    }

    @Override
    public S3Mapping save(S3Mapping s3m) {
        if (s3m.getUser() == null) {
            s3m.setUser(User.defaultUser());
        }
        if (s3m.getUser().getId() == null && s3m.getUser().getUserName() != null) {
            var user = userService.getByUserName(s3m.getUser().getUserName());
            if (user != null) {
                s3m.setUser(user);
            } else {
                s3m.setUser(userService.create(s3m.getUser()));
            }
        }
        return repository.save(s3m);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);

    }

    @Override
    public List<S3Mapping> findByExpDateBefore(LocalDateTime dateTime) {
        return repository.findByDateExpirationBefore(dateTime);
    }

    @Override
    public void deleteBulk(List<String> list) {
        repository.deleteAllByIdInBatch(list);
    }
}
