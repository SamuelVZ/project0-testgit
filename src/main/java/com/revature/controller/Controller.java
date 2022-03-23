package com.revature.controller;

//TODO 25: create the controller interface for the controller layer

import io.javalin.Javalin;

public interface Controller {

    public void mapEndPoints(Javalin app);
}
