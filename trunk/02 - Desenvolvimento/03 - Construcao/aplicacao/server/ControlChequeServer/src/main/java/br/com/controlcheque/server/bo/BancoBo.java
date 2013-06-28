/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.server.bo;

import br.com.controlcheque.server.dao.BancoDao;
import br.com.controlcheque.server.model.Banco;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface BancoBo <T extends Banco>{

    /**
     *
     * @param bancoDao
     */
    public void setBancoDao(BancoDao bancoDao);

    /**
     * 
     * @param descricao
     * @return
     */
    public List<T> consultarBanco();

    /**
     *
     * @param b
     */
    public T mantemBanco(T b);

    /**
     * 
     * @param b
     * @return
     */
    public T deleteBanco(T b);
}
