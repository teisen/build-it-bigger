/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.steelgirderdev.builditbigger.jokebackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.steelgirderdev.builditbigger.libjokes.example.Jokster;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "jokebackend.builditbigger.steelgirderdev.com",
    ownerName = "jokebackend.builditbigger.steelgirderdev.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that responds with a joke */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        Jokster joker = new Jokster();
        response.setData(joker.getJoke());
        return response;
    }
}
