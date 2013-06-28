package br.com.controlcheque.client.ServicesImpl;

///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
import br.com.controlcheque.client.Enum.EnumSituacaoCheque;
import br.com.controlcheque.client.Enum.EnumStatusCheque;
import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.client.util.DateUtil;
import br.com.controlcheque.service.Cheque;
import br.com.controlcheque.service.Exception_Exception;
import br.com.controlcheque.service.ValorCheque;
import java.util.Date;
import java.util.List;

///**
// *
// * @author Robson
// */
public class ChequeServerImpl extends BaseServerImpl {

    /**
     * Construtor
     *
     * @throws Exception
     */
    public ChequeServerImpl() throws Exception {
        super();
    }

    /**
     * ************************************************************************
     * INICIO CHAMADA METODOS DO SERVER
     * ***********************************************************************
     */
    /**
     * metodo para gerar um novo codigo para o objeto cheque
     *
     * @return
     */
    public String gerarCodigo() throws Exception {
        String codigo = "-1";
        try {
            // TODO process result here
            codigo = port.gerarCodigoCheque();
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return codigo;
    }

    /**
     * MantemBanco
     *
     * @param banco
     * @return
     */
    @Override
    public Object mantemObject(Object pObject) throws ClientChequeException {

        Cheque retorno = null;

        try { // Call Web Service Operation            
            retorno = (Cheque) port.salvarCheque((Cheque) pObject);
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return retorno;
    }

    /**
     * RECUPERA TODOS OS CHEQUES DO SISTEMA
     *
     * @return
     * @throws Exception
     */
    @Override
    public List recTodos() throws ClientChequeException {
        List lListaCheque = null;
        try {
            lListaCheque = port.recTodosCheques();
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return lListaCheque;
    }

    @Override
    public Object deletar(Object pObject) throws ClientChequeException {
        Cheque result = null;
        try {
            result = port.deletarCheque((Cheque) pObject);
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return result;
    }

    @Override
    public boolean salvarListObject(List pListObjects) throws ClientChequeException {
        boolean result = false;
        try {
            result = port.salvarListCheques(pListObjects);
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }
        return result;
    }

    /**
     * RECUPERA OS CHEQUES DO SISTEMA pela Situacao
     *
     * @return
     * @throws Exception
     */
    public List<Cheque> recChequesPorSituacao(int situacaoCheque) throws Exception {
        List<Cheque> listResult = null;
        try {
            listResult = port.recChequesPorSituacao(situacaoCheque, EnumStatusCheque.Ativo.getCodigo());
        } catch (Exception ex) {
            throw ex;
        }
        return listResult;
    }

    /**
     * Recupera todos os cheque removidos para lixeira
     *
     * @return
     * @throws Exception
     */
    public List<Cheque> recChequesRemovidos() throws Exception {
        List<Cheque> listResult = null;
        try {
            listResult = port.recChequesPorSituacao(EnumSituacaoCheque.Todos.getCodigo(), EnumStatusCheque.Removido.getCodigo());
        } catch (Exception ex) {
            throw ex;
        }
        return listResult;
    }

    @Override
    public Object recObjeto(Object pObject) throws ClientChequeException {

        Cheque retorno = null;
        try {
            retorno = port.recChequePorId((Cheque) pObject);
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return retorno;
    }

    /**
     * DELETAR CHEQUE DA LIXEIRA
     *
     * @param pObject
     * @return
     * @throws ClientChequeException
     */
    public Object deletarChequeLixeira(Object pObject) throws ClientChequeException {

        Cheque retorno = null;
        try {
            retorno = port.deletarChequeLixeira((Cheque) pObject);
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return retorno;
    }

    /**
     * 
     * @param dataInicial
     * @param dataFinal
     * @return
     * @throws ClientChequeException 
     */
    public ValorCheque recValorChequePorIntervaloData(Date dataInicial, Date dataFinal) throws ClientChequeException {
        ValorCheque retorno = null;
        try {
            retorno = port.recValorChequePorIntervaloData(DateUtil.dateAsXMLGregorianCalendar(dataInicial), DateUtil.dateAsXMLGregorianCalendar(dataFinal));
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }
        return retorno;
    }
    /**
     * ************************************************************************
     * FIM CHAMADA METODOS DO SERVER
     * ***********************************************************************
     */
}
