package com.elailson.rinha.controller;

import com.elailson.rinha.entity.Cliente;
import com.elailson.rinha.entity.Transacao;
import com.elailson.rinha.exception.InsufficientLimitException;
import com.elailson.rinha.exception.NotFoundException;
import com.elailson.rinha.service.RinhaService;
import com.elailson.rinha.util.GsonInstantTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;

@WebServlet(urlPatterns = {"/clientes", "/clientes/*"})
public class RinhaController extends HttpServlet {

    private static final String CREDITO = "c";
    private static final String DEBITO = "d";

    private final transient RinhaService service;
    private final transient Gson gson;

    public RinhaController() {
        this.service = new RinhaService();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new GsonInstantTypeAdapter())
                .create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer paramId = extractFromPathExtrato(req.getPathInfo());

        if (paramId <= 0) resp.setStatus(404);

        try {
            resp.getWriter().print(gson.toJson(service.getExtrato(paramId)));
        } catch (NotFoundException e) {
            resp.setStatus(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Transacao request = convert(req);

        Integer paramId = extractFromPath(req.getPathInfo());

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

    private Integer extractFromPathExtrato(String path) {
        return Integer.valueOf(path.substring(path.indexOf("/") + 1, path.indexOf("/e")));
    }

    private Transacao convert(HttpServletRequest req) {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;

            while ((line = reader.readLine()) != null) json.append(line);

            return gson.fromJson(json.toString(), Transacao.class);
        } catch (IOException e) {
            throw new JsonParseException("Não foi possível fazer parse da request de transação");
        }
    }

    private boolean isRequestInvalid(Transacao request, Integer id) {
        if (!CREDITO.equals(request.getTipo()) && !DEBITO.equals(request.getTipo())) return true;
        if (request.getDescricao().length() > 10) return true;
        if (request.getValor() <= 0) return true;
        return id <= 0;
    }

}
