package com.revature.controller;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class TestController implements Controller{


    private Handler getTest = (ctx) ->{
        ctx.html("<h1> get /test</h1>");
    };

    private Handler postTest = (ctx) ->{
        ctx.html("<h1> post / test</h1>");
    };

    private Handler putTest = (ctx) ->{
        ctx.html("<h1> put / test</h1>");
    };

    private Handler patchTest = (ctx) ->{
        ctx.html("<h1> patch / test</h1>");
    };
    private Handler deleteTest = (ctx) ->{
        ctx.html("<h1> delete / test</h1>");
    };


    @Override
    public void mapEndPoints(Javalin app) {

        app.get("/test",getTest);
        app.post("/test",postTest);
        app.put("/test",putTest);
        app.patch("/test",patchTest);
        app.delete("/test",deleteTest);
    }
}
