package com.example.learningVertx.routes

import io.vertx.core.Vertx
import io.vertx.ext.healthchecks.HealthCheckHandler
import io.vertx.ext.healthchecks.Status
import io.vertx.ext.web.Router

class HealthCheck(val router: Router, vertx: Vertx) {
  init {
    router["/health"].handler(
      HealthCheckHandler.create(vertx)
        .register("server-online") { promise ->
          promise.complete(Status.OK())
        })
  }
}
