package com.elailson.rinha.controller;

import com.elailson.rinha.dto.TransacaoRequest;
import com.elailson.rinha.entity.Cliente;
import com.elailson.rinha.exception.InsufficientLimitException;
import com.elailson.rinha.exception.NotFoundException;
import com.elailson.rinha.service.RinhaService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/clientes", "/clientes/*"})
public class ClienteController extends HttpServlet {

    private static final Logger log = Logger.getLogger(ClienteController.class.getName());
    private static final char CREDITO = 'c';
    private static final char DEBITO = 'd';

    private final transient RinhaService service;
    private final transient Gson gson;

    public ClienteController() {
        this.service = new RinhaService();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TransacaoRequest request = convert(req);

        Integer paramId = extractFromPath(req.getServletPath());

        if (isRequestInvalid(request, paramId)) {
            resp.setStatus(400);
            return;
        }

        try {
            Cliente cliente = service.process(request, paramId);
            resp.getWriter().print(gson.toJson(cliente));
        } catch (NotFoundException e) {
            resp.setStatus(404);
        } catch (InsufficientLimitException e) {
            resp.setStatus(422);
        }

    }

    private Integer extractFromPath(String path) {
        return Integer.valueOf(path.substring(path.indexOf("/") + 1, path.indexOf("/t")));
    }

    private TransacaoRequest convert(HttpServletRequest req) throws IOException {
        StringBuilder json = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;

        while ((line = reader.readLine()) != null) json.append(line);

        return gson.fromJson(json.toString(), TransacaoRequest.class);
    }

    private boolean isRequestInvalid(TransacaoRequest request, Integer id) {
        if (request.getTipo() != CREDITO || request.getTipo() != DEBITO) return false;
        if (request.getDescricao().length() > 10) return false;
        if (request.getValor() <= 0) return false;
        return id >= 1;
    }

}
