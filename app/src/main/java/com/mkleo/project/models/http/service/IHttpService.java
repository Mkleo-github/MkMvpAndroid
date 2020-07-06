package com.mkleo.project.models.http.service;

public interface IHttpService<ServiceInterface, ServiceProxy extends IHttpServiceProxy<ServiceInterface>> {

    void linkService(String host);

    void setToken(String token);

    ServiceProxy request();
}
