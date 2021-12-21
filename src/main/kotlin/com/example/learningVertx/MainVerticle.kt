package com.example.learningVertx

import io.vertx.core.*
import io.vertx.ext.healthchecks.HealthCheckHandler
import io.vertx.ext.healthchecks.Status
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.LoggerHandler
import io.vertx.micrometer.MicrometerMetricsOptions
import io.vertx.micrometer.PrometheusScrapingHandler
import io.vertx.micrometer.VertxPrometheusOptions

class MainVerticle : AbstractVerticle() {

  override fun init(vertx: Vertx?, context: Context?) {
    val updatedVertx = Vertx.vertx(
      VertxOptions().setMetricsOptions(
        MicrometerMetricsOptions().setPrometheusOptions(
          VertxPrometheusOptions().setEnabled(true)
        ).setEnabled(true)
      )
    )
    super.init(updatedVertx, context)
  }

  override fun start(startPromise: Promise<Void>) {
    val router = Router.router(vertx)
    router.route().handler(LoggerHandler.create())
    router["/health"].handler(HealthCheckHandler.create(vertx)
      .register("server-online") { promise ->
        promise.complete(Status.OK())
    })
    router["/metrics"].handler(PrometheusScrapingHandler.create());
    router["/"].handler { rc -> rc.response().end("Hello from Vert.x")}

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(8888) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8888")
        } else {
          startPromise.fail(http.cause())
        }
      }
  }
}
