package dev.sinayko.pastebin.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@EnableScheduling
public class ScheduledServiceImpl implements ScheduledService {

    private S3MappingService s3MappingService;

    @Scheduled(fixedDelay = 600_000)
    @Override
    public void deleteExpiredData() {
        var s3MappingList = s3MappingService.findByExpDateBefore(LocalDateTime.now());
        if (!s3MappingList.isEmpty()) {
            var listIds = s3MappingList.stream().map(s3m -> s3m.getId()).toList();
            s3MappingService.deleteBulk(listIds);
        }
    }
}
