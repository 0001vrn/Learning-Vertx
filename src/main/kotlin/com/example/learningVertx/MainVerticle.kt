package com.example.learningVertx

import io.vertx.core.*
import io.vertx.ext.web.Router

import com.example.learningVertx.routes.AccessLogs
import com.example.learningVertx.routes.Default
import com.example.learningVertx.routes.HealthCheck
import com.example.learningVertx.routes.Metrics

class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val router = Router.router(vertx)
    vertx
      .createHttpServer()
      .requestHandler(AccessLogs(router).router)
      .requestHandler(HealthCheck(router, vertx).router)
      .requestHandler(Metrics(router).router)
      .requestHandler(Default(router).router)
      .requestHandler(router)
      .listen(8080) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8080")
        } else {
          startPromise.fail(http.cause())
        }
      }
  }
}
