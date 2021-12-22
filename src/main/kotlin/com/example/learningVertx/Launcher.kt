package com.example.learningVertx

import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.micrometer.MicrometerMetricsOptions
import io.vertx.micrometer.VertxPrometheusOptions

class Launcher {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {

      val options = MicrometerMetricsOptions()
        .setPrometheusOptions(
          VertxPrometheusOptions()
            .setEnabled(true)
        )
        .setEnabled(true)
      val vertx: Vertx = Vertx.vertx(VertxOptions().setMetricsOptions(options))

      vertx.deployVerticle(MainVerticle())

    }
  }
}
