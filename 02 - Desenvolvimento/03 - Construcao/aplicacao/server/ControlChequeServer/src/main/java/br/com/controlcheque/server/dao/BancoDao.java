/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dao;

import br.com.controlcheque.server.model.Banco;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface BancoDao<T extends Banco> {

    public T mantemBanco(T b);

    public List<T> consultarBanco();

    public T deleteBanco(T b);
}
