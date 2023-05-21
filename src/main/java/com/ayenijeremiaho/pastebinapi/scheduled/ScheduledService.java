package com.ayenijeremiaho.pastebinapi.scheduled;

import com.ayenijeremiaho.pastebinapi.pasteText.service.PasteTextService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@AllArgsConstructor
public class ScheduledService {

    private final PasteTextService pasteTextService;

    //used static 5 minutes, since we have a minimum of 10 minutes for expiration
    @Scheduled(fixedDelayString = "300000")
    public void updateDeletedStatus() {
        log.info("Starting deleted status update scheduler");

        pasteTextService.getPasteTextsToDelete();

        log.info("Completed deleted status update scheduler");
    }
}
