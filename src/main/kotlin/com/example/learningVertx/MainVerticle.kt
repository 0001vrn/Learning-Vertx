package com.example.learningVertx

import io.vertx.core.*
import io.vertx.ext.web.Router
import io.vertx.micrometer.MicrometerMetricsOptions
import io.vertx.micrometer.VertxPrometheusOptions

import com.example.learningVertx.routes.AccessLogs
import com.example.learningVertx.routes.Default
import com.example.learningVertx.routes.HealthCheck
import com.example.learningVertx.routes.Metrics

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
    vertx
      .createHttpServer()
      .requestHandler(AccessLogs(router).router)
      .requestHandler(HealthCheck(router, vertx).router)
      .requestHandler(Metrics(router).router)
      .requestHandler(Default(router).router)
      .requestHandler(router)
      .listen(config().getInteger("http.port", 8080)) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port ${config().getInteger("http.port")}")
        } else {
          startPromise.fail(http.cause())
        }
      }
  }
}
