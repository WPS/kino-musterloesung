package de.wps.ddd.kino.common.fixtures;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixtureInstaller implements ApplicationRunner {

    private final List<Fixture> fixtures;

    @Override
    public void run(ApplicationArguments args) {
        log.info("{} fixtures found", fixtures.size());
        log.info("Installing fixtures...");
        fixtures.forEach(Fixture::install);
        log.info("Done");
    }
}
