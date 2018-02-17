package be.jarivo.vertxpoc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);
    private HttpServer httpServer;

    @Override
    public void start() throws Exception {
        httpServer = vertx.createHttpServer().requestHandler(req -> req.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "text/plain")
                .end("Hello from Vert.x")).listen(8080);

        logger.info("HTTP Server started on port 8080");
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        httpServer.close((asyncResult) -> {
            if(asyncResult.succeeded()) {
                logger.info("HTTP Server successfully closed");
                stopFuture.complete();
            } else {
                stopFuture.fail(asyncResult.cause());
            }
        });
    }
}