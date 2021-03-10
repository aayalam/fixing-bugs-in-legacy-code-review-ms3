import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ServiceBootsIT {
    static boolean isWindows = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");

    private static Process process;
    private static boolean startedApplication;

    @BeforeAll
    public static void bootService() throws Exception {
        String command = "docker-compose -f SearchService.docker-compose.yml up --no-build --remove-orphans";
        process = execInRootFolder(command);

        transferStreamToOutput(
                process.getInputStream(),
                line -> {
                    if (line.contains("Started Application"))
                        startedApplication = true;
                },
                () -> traceServer("exit code = " + process.exitValue()));
        transferStreamToOutput(
                process.getErrorStream(),
                line -> {
                },
                () -> {
                });

        final var tries = 1000;
        for (var i = 0; i < tries && !startedApplication; i++)
            Thread.sleep(10);
    }

    private static void transferStreamToOutput(InputStream stream, Consumer<String> onLine, Runnable after) {
        var thread = new Thread(() -> {
            try {
                var reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = reader.readLine()) != null) {
                    onLine.accept(line);
                    traceServer(line);
                }

                Thread.sleep(25);

                after.run();
            } catch (Exception exception) {
                System.err.println("[SERVER]: Failed to copy integration test output:");
                System.err.println(exception.toString());
            }
        });
        thread.start();
    }

    private static void traceServer(String line) {
        System.out.println("[SERVER]: " + line);
    }

    @AfterAll
    public static void killService() throws Exception {
        process.destroy();
        process.waitFor(5, TimeUnit.SECONDS);

        execInRootFolder("docker-compose -f SearchService.docker-compose.yml down --remove-orphans")
                .waitFor(5, TimeUnit.SECONDS);
    }

    @Test
    public void go() {
        when().get("http://localhost:8080/health/status")
                .then()
        .statusCode(200)
        .body("up", equalTo(true));
    }

    private static Process execInRootFolder(String command) throws IOException {
        String prefix = isWindows ? "cmd /c " : "sh -c ";
        var environmentVariables = new ArrayList<String>();
        var env = System.getenv();
        for (String envName : env.keySet()) {
            environmentVariables.add(String.format("%s=%s", envName, env.get(envName)));
        }

        return Runtime.getRuntime().exec(
                command,
                environmentVariables.toArray(String[]::new),
                new File(".."));
    }
}
