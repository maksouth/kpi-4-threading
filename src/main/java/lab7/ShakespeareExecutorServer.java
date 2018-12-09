package lab7;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ShakespeareExecutorServer {

    private static final String TAG = "[" + ShakespeareExecutorServer.class.getSimpleName() + "] ";

    private final PoemRepository poemRepository;
    private final ExecutorService executorService;

    private ServerSocket serverSocket;

    /**
     * @param executorService - should have at least 2 threads,
     *                        one for socket read loop, and other for socket command processing
     */
    public ShakespeareExecutorServer(
            PoemRepository poemRepository,
            ExecutorService executorService
    ) {
        this.poemRepository = poemRepository;
        this.executorService = executorService;
    }

    /**
     * cannot be restarted
     * @return true - if socket was created successfully, false - otherwise
     */
    public boolean startAsync(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(TAG + "Failed to create socket");
            e.printStackTrace();
            return false;
        }

        executorService.submit(() -> {
            try {
                loop();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.println(TAG + "Finished");
            }
        });

        return true;
    }

    private void loop() throws IOException {
        while (true) {
            Runnable handler = new PoemHandler(serverSocket.accept(), poemRepository);
            executorService.submit(handler);
        }
    }

    public void stop() throws IOException {
        executorService.shutdown();
        serverSocket.close();
    }

    private static class PoemHandler implements Runnable {

        private static final String TAG = "[" + PoemHandler.class.getSimpleName() + "] ";

        private PoemRepository poemRepository;
        private Socket clientSocket;
        private PrintWriter out;

        public PoemHandler(Socket socket, PoemRepository repository) {
            this.clientSocket = socket;
            this.poemRepository = repository;

            System.out.println(TAG + "Created");
        }

        public void run() {
            try {

                out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.write(poemRepository.getRandom());

            } catch (IOException e) {
                System.out.println(TAG + "Socket write failed");
                e.printStackTrace();
            } finally {
                out.close();
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
