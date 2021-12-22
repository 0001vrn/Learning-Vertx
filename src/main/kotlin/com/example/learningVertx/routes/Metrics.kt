package com.example.learningVertx.routes

import io.vertx.ext.web.Router
import io.vertx.micrometer.PrometheusScrapingHandler

class Metrics(val router: Router) {
  init {
    router["/metrics"].handler(PrometheusScrapingHandler.create())
  }
}
