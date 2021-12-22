package com.example.learningVertx

import io.vertx.core.*
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.LoggerHandler
import io.vertx.micrometer.MicrometerMetricsOptions
import io.vertx.micrometer.PrometheusScrapingHandler
import io.vertx.micrometer.VertxPrometheusOptions

import com.example.learningVertx.routes.HealthCheck

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
    HealthCheck(router, vertx)
    router["/metrics"].handler(PrometheusScrapingHandler.create())
    router["/"].handler { rc -> rc.response().end("Hello from Vert.x")}

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(8080) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8888")
        } else {
          startPromise.fail(http.cause())
        }
      }
  }
}
