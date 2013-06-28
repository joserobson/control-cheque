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
import br.com.controlcheque.server.util.ChequeServerUtil;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class ChequeBoImpl<T extends Cheque> implements ChequeBo<Cheque> {

    private ChequeDao chequeDao;

    @Override
    public void setChequeDao(ChequeDao chequeDao) {
        this.chequeDao = chequeDao;
    }

    @Override
    public Cheque mantemCheque(Cheque cheque) {
        return this.chequeDao.mantemCheque(cheque);
    }

    @Override
    public List<Cheque> consultaCheques() {
        return chequeDao.consultarCheques();
    }

    @Override
    public List<Cheque> consultaChequesPorSituacao(ESituacaoCheque eSituacao, EStatusCheque eStatus) {
        return chequeDao.consultaChequesPorSituacao(eSituacao, eStatus);
    }

    @Override
    public Cheque recChequePorID(String id) {
        return chequeDao.recChequePorID(id);
    }

    @Override
    public List<Cheque> consultaChequesPorBanco(Banco b) {
        return chequeDao.consultaChequesPorBanco(b);
    }

    @Override
    public String recuperaMaxCodigoCheque() {
        int lCodigo = chequeDao.recuperaMaxcodigoCheque();
        return ChequeServerUtil.gerarCodigoFormatoPadrao(lCodigo);
    }

    @Override
    public Cheque deleteCheque(Cheque cheque) {
        return chequeDao.deletarCheque(cheque);
    }

    @Override
    public Cheque deleteChequeLixeira(Cheque cheque) {
        return chequeDao.deletarChequeLixeira(cheque);
    }

    @Override
    public float recSomatarioChequesPorIntervaloDatas(Date dataInicial, Date dataFinal) {
        return chequeDao.recSomatarioChequesPorIntervaloDatas(dataInicial, dataFinal);
    }

    @Override
    public int recQuantidadeChequesPorIntervaloDatas(Date dataInicial, Date dataFinal) {
        return chequeDao.recQuantidadeChequesPorIntervaloDatas(dataInicial, dataFinal);
    }
}
