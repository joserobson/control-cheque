/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.bo;

import br.com.controlcheque.server.dao.ChequeDao;
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
public interface ChequeBo<T extends Cheque> {

    public void setChequeDao(ChequeDao chequeDao);

    public T mantemCheque(T cheque);

    public List<T> consultaCheques();

    public List<T> consultaChequesPorSituacao(ESituacaoCheque eSituacao, EStatusCheque eStatus);

    public Cheque recChequePorID(String id);

    public List<T> consultaChequesPorBanco(Banco b);

    public String recuperaMaxCodigoCheque();
    
    public T deleteCheque(T cheque);
    
    public T deleteChequeLixeira(T cheque);
    
    public float recSomatarioChequesPorIntervaloDatas(Date dataInicial, Date dataFinal);
    
    public int recQuantidadeChequesPorIntervaloDatas(Date dataInicial, Date dataFinal);
}
