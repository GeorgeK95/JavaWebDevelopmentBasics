package org.galaxi.handlers.solets.contracts;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface IHttpSolet {
    void doGet(IHttpSoletRequest soletRequest, IHttpSoletResponse soletResponse);

    void doPost(IHttpSoletRequest soletRequest, IHttpSoletResponse soletResponse);

    void doPut(IHttpSoletRequest soletRequest, IHttpSoletResponse soletResponse);

    void doDelete(IHttpSoletRequest soletRequest, IHttpSoletResponse soletResponse);
}
