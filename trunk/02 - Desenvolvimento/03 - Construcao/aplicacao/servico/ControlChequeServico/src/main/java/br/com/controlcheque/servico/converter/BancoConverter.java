/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.converter;

import br.com.controlcheque.servico.beans.Banco;

/**
 *
 * @author Robson
 */
public class BancoConverter {

    public br.com.controlcheque.servico.beans.Banco converterToBean(Object pBanco) {
        br.com.controlcheque.server.model.Banco b = (br.com.controlcheque.server.model.Banco) pBanco;
        return new Banco(b.getId(), b.getCodigo(), b.getDescricao());
    }

    public br.com.controlcheque.server.model.Banco converterFromBean(
            Object pBanco) {
        Banco b = (Banco) pBanco;
        return new br.com.controlcheque.server.model.Banco(b.getId(), b.getCodigo(), b.getDescricao());
    }
}
