/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dao;

import br.com.controlcheque.server.dominio.ESituacaoCheque;
import br.com.controlcheque.server.dominio.EStatusCheque;
import br.com.controlcheque.server.model.Banco;
import br.com.controlcheque.server.model.Cheque;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface ChequeDao<T extends Cheque> {

    public T mantemCheque(Cheque cheque);

    public List<T> consultarCheques();

    public List<T> consultaChequesPorSituacao(ESituacaoCheque eSituacao, EStatusCheque eStatus);

    public T recChequePorID(String id);

    public List<T> consultaChequesPorBanco(Banco b);

    public int recuperaMaxcodigoCheque();

    public T deletarCheque(T cheque);

    public T deletarChequeLixeira(T cheque);

    public float recSomatarioChequesPorIntervaloDatas(Date dataInicial, Date dataFinal);

    public int recQuantidadeChequesPorIntervaloDatas(Date dataInicial, Date dataFinal);
}
