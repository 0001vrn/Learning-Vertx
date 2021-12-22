package com.example.learningVertx.routes

import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.LoggerHandler

class AccessLogs(val router: Router) {
  init {
    router.route().handler(LoggerHandler.create())
  }
}
