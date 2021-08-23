package br.com.guilhermealvessilve.communication.platform;

import br.com.guilhermealvessilve.communication.platform.infrastructure.MainVerticle;
import io.vertx.core.Launcher;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

    public static void main(final String[] args) {
        LOGGER.info("Starting the MainVerticle...");
        Launcher.executeCommand("run", MainVerticle.class.getName());
    }
}
