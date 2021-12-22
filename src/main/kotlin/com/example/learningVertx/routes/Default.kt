package com.example.learningVertx.routes

import io.vertx.ext.web.Router

class Default(val router: Router) {
  init {
    router["/"].handler { rc -> rc.response().end("Hello from Vert.x")}
  }
}
