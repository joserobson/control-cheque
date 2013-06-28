/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.ws.cheque;

import br.com.controlcheque.server.dominio.ESituacaoCheque;
import br.com.controlcheque.server.dominio.EStatusCheque;
import br.com.controlcheque.servico.beans.Cheque;
import br.com.controlcheque.servico.beans.ValorCheque;
import br.com.controlcheque.servico.ws.ServiceBase;
import br.com.controlcheque.transacao.classes.TnCheque;
import br.com.controlcheque.transacao.constantes.ConstanteCheque;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.dozer.MappingException;

/**
 *
 * @author Robson
 */
public class ServiceCheque extends ServiceBase {

    private TnCheque tnCheque = null;

    public ServiceCheque() {
        this.tnCheque = new TnCheque();
    }

    public String gerarCodigoCheque() throws Exception {
        String codigo = "-1";
        try {
            TnCheque tn = new TnCheque();
            codigo = tn.gerarCodigoCheque(ConstanteCheque.NOME_EVENTO_GERAR_COD_CHEQUE);
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }

        return codigo;
    }

    @Override
    public Object salvar(Object param) throws Exception {
        Cheque retorno = null;
        try {
            tnCheque.setCheque(mapper.map(param, br.com.controlcheque.server.model.Cheque.class));
            retorno = mapper.map(tnCheque.salvar(ConstanteCheque.NOME_EVENTO_SALVAR_CHEQUE), Cheque.class);
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return retorno;
    }

    @Override
    public List recTodos() throws Exception {
        List<Cheque> listCheques = null;
        try {
            List<br.com.controlcheque.server.model.Cheque> listaRetorno = tnCheque.recuperarTodos(ConstanteCheque.NOME_EVENTO_BUSCAR_TODOS_CHEQUES);
            if (listaRetorno != null
                    && listaRetorno.size() > 0) {
                listCheques = new ArrayList<Cheque>();
                Iterator<br.com.controlcheque.server.model.Cheque> iterator = listaRetorno.iterator();
                while (iterator.hasNext()) {
                    listCheques.add(mapper.map(iterator.next(), Cheque.class));
                }
            }
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return listCheques;
    }

    @Override
    public Object deletar(Object param) throws Exception {
        Cheque retorno = null;
        try {
            tnCheque.setCheque(mapper.map(param, br.com.controlcheque.server.model.Cheque.class));
            br.com.controlcheque.server.model.Cheque c = (br.com.controlcheque.server.model.Cheque) tnCheque.deletar(ConstanteCheque.NOME_EVENTO_DELETAR_CHEQUE);
            retorno = mapper.map(c, Cheque.class);
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return retorno;
    }

    public List<Cheque> recChequesPorSituacao(int situacaoCheque, int statusCheque) throws TransacaoChequeException {
        List<Cheque> lstResult = null;
        try {
            tnCheque.setEnumSituacaoCheque(ESituacaoCheque.getSituacaoCheque(situacaoCheque));
            tnCheque.setEnumStatusCheque(EStatusCheque.getStatusCheque(statusCheque));

            List<br.com.controlcheque.server.model.Cheque> listaRetorno = tnCheque.recuperarChequePorSituacao(ConstanteCheque.NOME_EVENTO_BUSCAR_CHEQUES_POR_SITUACAO);
            if (listaRetorno != null
                    && listaRetorno.size() > 0) {
                lstResult = new ArrayList<Cheque>();
                Iterator<br.com.controlcheque.server.model.Cheque> iterator = listaRetorno.iterator();
                while (iterator.hasNext()) {
                    lstResult.add(mapper.map(iterator.next(), Cheque.class));
                }
            }
        } catch (TransacaoChequeException ex) {
            throw ex;
        } catch (MappingException ex) {
            throw new TransacaoChequeException(ex);
        }

        return lstResult;
    }

    @Override
    public boolean salvarListObjetos(List lstObjetos) throws Exception {
        boolean retorno = true;
        try {
            //converte lista de bancos
            if (lstObjetos != null
                    && lstObjetos.size() > 0) {
                List<br.com.controlcheque.server.model.Cheque> lstChequeModel = converterListaChequeBeansToModel(lstObjetos);
                for (br.com.controlcheque.server.model.Cheque cheque : lstChequeModel) {
                    tnCheque.setCheque(cheque);
                    tnCheque.salvar(ConstanteCheque.NOME_EVENTO_SALVAR_LISTA_CHEQUE);
                }
            }

        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }

        return retorno;
    }

    /**
     *
     * @param pLstObjetos
     * @return
     */
    private List<br.com.controlcheque.server.model.Cheque> converterListaChequeBeansToModel(List<Cheque> pLstObjetos) {
        List<br.com.controlcheque.server.model.Cheque> listCheques = null;
        if (pLstObjetos != null
                && pLstObjetos.size() > 0) {
            listCheques = new ArrayList<br.com.controlcheque.server.model.Cheque>();
            Iterator<Cheque> iterator = pLstObjetos.iterator();
            while (iterator.hasNext()) {
                listCheques.add(this.mapper.map(iterator.next(), br.com.controlcheque.server.model.Cheque.class));
            }
        }
        return listCheques;
    }

    @Override
    public Object recObjeto(Object param) throws Exception {

        Cheque retorno = null;
        try {
            tnCheque.setCheque(mapper.map(param, br.com.controlcheque.server.model.Cheque.class));
            retorno = this.mapper.map(tnCheque.recObjectPorId(ConstanteCheque.NOME_EVENTO_BUSCAR_CHEQUE_POR_ID), Cheque.class);
        } catch (TransacaoChequeException ex) {
            throw ex;
        }

        return retorno;
    }

    /**
     * REMOVE CHEQUE DA LIXEIRA
     *
     * @param param
     * @return
     * @throws Exception
     */
    public Cheque deletarChequeLixeira(Cheque param) throws Exception {
        Cheque retorno = null;
        try {
            tnCheque.setCheque(mapper.map(param, br.com.controlcheque.server.model.Cheque.class));
            br.com.controlcheque.server.model.Cheque c = (br.com.controlcheque.server.model.Cheque) tnCheque.deletarChequeLixeira(ConstanteCheque.NOME_EVENTO_DELETAR_CHEQUE_LIXEIRA);
            retorno = mapper.map(c, Cheque.class);
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return retorno;
    }
    
    /**
     * Retorna o valor Cheque de acordo com o Intervalo de data passado usado na tela 
     * soma entre datas para cheques
     * @param dataInicial
     * @param dataFinal
     * @return
     * @throws Exception 
     */
    public ValorCheque recValorChequePorIntervaloData(Date dataInicial, Date dataFinal) throws Exception {
        ValorCheque retorno = new ValorCheque();
        try {
            tnCheque.setDataInicial(dataInicial);
            tnCheque.setDataFinal(dataFinal);
            retorno.setQuantidade(tnCheque.recQuantidadeChequesPorIntervaloData(ConstanteCheque.NOME_EVENTO_RECQUANTIDADE_CHEQUES_PORINTERVALO_DATA));
            retorno.setValor(tnCheque.recSomatorioChequesPorIntervaloData(ConstanteCheque.NOME_EVENTO_RECSOMATORIO_CHEQUES_PORINTERVALO_DATA));
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }

        return retorno;
    }
}
