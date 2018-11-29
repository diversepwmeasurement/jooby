package io.jooby.internal;

import io.jooby.Renderer;
import io.jooby.Route;
import io.jooby.Router;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class RouterMatch implements Router.Match {

  boolean matches;

  private Route route;

  private Map vars = Collections.EMPTY_MAP;

  private Route.Handler handler;

  public void key(List<String> keys) {
    if (keys != null) {
      for (int i = 0; i < keys.size(); i++) {
        vars.put(keys.get(i), vars.remove(i));
      }
    }
  }

  public void value(String value) {
    if (vars == Collections.EMPTY_MAP) {
      vars = new HashMap();
    }
    vars.put(vars.size(), value);
  }

  public void pop() {
    vars.remove(vars.size() - 1);
  }

  public void methodNotAllowed() {
    handler = Route.METHOD_NOT_ALLOWED;
  }

  @Override public boolean matches() {
    return matches;
  }

  @Override public Route route() {
    return route;
  }

  @Override public Map<String, String> params() {
    return vars;
  }

  public RouterMatch found(RouteImpl route) {
    this.route = route;
    this.matches = true;
    return this;
  }


  public RouterMatch missing(Executor executor, String method, String path, Renderer renderer) {
    Route.Handler h;
    if (this.handler == null) {
      h = path.endsWith("/favicon.ico") ? Route.FAVICON : Route.NOT_FOUND;
    } else {
      h = this.handler;
    }
    this.route = new RouteImpl(method, path, h, h, renderer);
    this.matches = false;
    return this;
  }
}
