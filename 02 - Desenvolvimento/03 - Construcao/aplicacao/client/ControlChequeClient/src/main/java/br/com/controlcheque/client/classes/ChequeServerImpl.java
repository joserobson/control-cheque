/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.classes;

import br.com.controlcheque.client.Enum.EnumSituacaoCheque;
import br.com.controlcheque.server.dominio.ESituacaoCheque;
import br.com.controlcheque.server.dominio.EStatusCheque;
import br.com.controlcheque.server.model.Cheque;
import br.com.controlcheque.transacao.classes.TnCheque;
import br.com.controlcheque.transacao.constantes.ConstanteCheque;
import java.util.List;

/**
 *
 * @author Robson
 */
public class ChequeServerImpl {

    /**************************************************************************
     * INICIO CHAMADA METODOS DO SERVER
     *************************************************************************/
    /**
     * metodo para gerar um novo codigo para o objeto cheque
     * @return
     */
    public String gerarCodigo() throws Exception {
        String codigo = "-1";
        try {
            TnCheque tn = new TnCheque();
            codigo = tn.gerarCodigoCheque(ConstanteCheque.NOME_EVENTO_GERAR_COD_CHEQUE);
        } catch (Exception ex) {
            throw ex;
        }

        return codigo;
    }

    /**
     * MantemBanco
     * @param banco
     * @return
     */
    public boolean mantemCheque(Cheque pCheque) throws Exception {

        boolean retorno = true;
        try {
            TnCheque tn = new TnCheque();
            tn.setCheque(pCheque);
            retorno = (Boolean) tn.salvar(ConstanteCheque.NOME_EVENTO_SALVAR_CHEQUE);
        } catch (Exception ex) {
            throw ex;
        }
        return retorno;
    }

    /**
     * RECUPERA TODOS OS CHEQUES DO SISTEMA
     * @return
     * @throws Exception
     */
    public List<Cheque> recCheques() throws Exception {
        List<Cheque> lListaCheque = null;
        try {
            TnCheque tn = new TnCheque();
            lListaCheque = tn.recuperarTodos(ConstanteCheque.NOME_EVENTO_BUSCAR_TODOS_CHEQUES);
        } catch (Exception ex) {
            throw ex;
        }

        return lListaCheque;
    }

    /**
     * RECUPERA OS CHEQUES DO SISTEMA pela Situacao
     * @return
     * @throws Exception
     */
    public List<Cheque> recChequesPorSituacao(int situacaoCheque) throws Exception {
        List<Cheque> lListaCheque = null;
        try {
            TnCheque tn = new TnCheque();
            tn.setEnumStatusCheque(EStatusCheque.ativo);

            if (situacaoCheque == EnumSituacaoCheque.EmMaos.getCodigo()) {
                tn.setEnumSituacaoCheque(ESituacaoCheque.EmMaos);
            } else {
                if (situacaoCheque == EnumSituacaoCheque.Repassado.getCodigo()) {
                    tn.setEnumSituacaoCheque(ESituacaoCheque.Repassado);
                } else {
                    tn.setEnumSituacaoCheque(ESituacaoCheque.Devolvido);
                }
            }

            lListaCheque = tn.recuperarChequePorSituacao(ConstanteCheque.NOME_EVENTO_BUSCAR_CHEQUES_POR_SITUACAO);
        } catch (Exception ex) {
            throw ex;
        }

        return lListaCheque;
    }
    /**************************************************************************
     * FIM CHAMADA METODOS DO SERVER
     *************************************************************************/
}
