/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.service;


import br.com.controlcheque.servico.beans.*;
import br.com.controlcheque.servico.util.ServiceUtil;
import br.com.controlcheque.servico.ws.banco.ServiceBanco;
import br.com.controlcheque.servico.ws.cheque.ServiceCheque;
import br.com.controlcheque.servico.ws.cliente.ServiceCliente;
import br.com.controlcheque.servico.ws.usuario.ServiceUsuario;
import java.util.Date;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Robson
 */
@WebService(serviceName = "WsControlChequeService")
public class WsControlChequeService {

    /**
     * INICIO METODOS SERVICE BANCO
     */
    /**
     * Salvar Banco
     *
     * @param param
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "salvarBanco")
    public Banco salvarBanco(@WebParam(name = "pBanco") Banco param) throws Exception {
        return (Banco) new ServiceBanco().salvar(param);
    }

    /**
     * Rec Todos os Bancos
     *
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "recTodosBancos")
    public List<Banco> recTodosBancos() throws Exception {
        return new ServiceBanco().recTodos();
    }

    /**
     * Deletar Banco
     *
     * @param param
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "deletarBanco")
    public Banco deletarBanco(Banco param) throws Exception {
        return (Banco) new ServiceBanco().deletar(param);
    }

    /**
     * Salvar lista de Bancos
     *
     * @param lstObjetos
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "salvarListBancos")
    public boolean salvarListBancos(List<Banco> lstObjetos) throws Exception {
        return new ServiceBanco().salvarListObjetos(lstObjetos);
    }

    /**
     * FIM METODOS SERVICE BANCO
     */
    /**
     * INICIO METODOS SERVICE CLIENTE
     */
    /**
     * Gera Codigo para o Cliente
     *
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "gerarCodigoCliente")
    public String gerarCodigoCliente() throws Exception {
        return new ServiceCliente().gerarCodigoCliente();
    }

    /**
     * Salvar Cliente
     *
     * @param param
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "salvarCliente")
    public Cliente salvarCliente(@WebParam(name = "pCliente") Cliente param) throws Exception {
        return (Cliente) new ServiceCliente().salvar(param);
    }

    /**
     * Rec Todos os Clientes
     *
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "recTodosClientes")
    public List<Cliente> recTodosClientes() throws Exception {
        return new ServiceCliente().recTodos();
    }

    /**
     * Deletar Cliente
     *
     * @param param
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "deletarCliente")
    public Cliente deletarCliente(Cliente param) throws Exception {
        return (Cliente) new ServiceCliente().deletar(param);
    }

    /**
     * Salvar lista de Clientes
     *
     * @param lstObjetos
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "salvarListClientes")
    public boolean salvarListClientes(List<Cliente> lstObjetos) throws Exception {
        return new ServiceCliente().salvarListObjetos(lstObjetos);
    }

    /**
     * FIM METODOS SERVICE CLIENTE
     */
    /**
     * INICIO METODOS SERVICE CHEQUE
     */
    /**
     * Gera Codigo para o Cheque
     *
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "gerarCodigoCheque")
    public String gerarCodigoCheque() throws Exception {
        return new ServiceCheque().gerarCodigoCheque();
    }

    /**
     * Salvar Cheque
     *
     * @param param
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "salvarCheque")
    public Cheque salvarCheque(@WebParam(name = "pCheque") Cheque param) throws Exception {
        return (Cheque) new ServiceCheque().salvar(param);
    }

    /**
     * Rec Todos os Cheques
     *
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "recTodosCheques")
    public List<Cheque> recTodosCheques() throws Exception {
        return new ServiceCheque().recTodos();
    }

    /**
     * Deletar Cheque
     *
     * @param param
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "deletarCheque")
    public Cheque deletarCheque(Cheque param) throws Exception {
        return (Cheque) new ServiceCheque().deletar(param);
    }

    /**
     * Salvar lista de Cheques
     *
     * @param lstObjetos
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "salvarListCheques")
    public boolean salvarListCheques(List<Cheque> lstObjetos) throws Exception {
        return new ServiceCheque().salvarListObjetos(lstObjetos);
    }

    /**
     * Recupera cheques por situacao
     *
     * @param situacaoCheque
     * @param statusCheque
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "recChequesPorSituacao")
    public List<Cheque> recChequesPorSituacao(int situacaoCheque, int statusCheque) throws Exception {
        return new ServiceCheque().recChequesPorSituacao(situacaoCheque, statusCheque);
    }

    /**
     *
     * @param pCheque
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "recChequePorId")
    public Cheque recChequePorId(Cheque pCheque) throws Exception {
        return (Cheque) new ServiceCheque().recObjeto(pCheque);
    }

    /**
     *
     * @param pCheque
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "deletarChequeLixeira")
    public Cheque deletarChequeLixeira(Cheque pCheque) throws Exception {
        return (Cheque) new ServiceCheque().deletarChequeLixeira(pCheque);
    }

    /**
     *
     * @param dataInicial
     * @param dataFinal
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "recValorChequePorIntervaloData")
    public ValorCheque recValorChequePorIntervaloData(Date dataInicial, Date dataFinal) throws Exception {
        return new ServiceCheque().recValorChequePorIntervaloData(dataInicial, dataFinal);
    }

    /**
     * FIM METODOS SERVICE CHEQUE
     */
    /**
     * INICIO METODOS SERVICE USUARIO
     */
    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "salvarUsuario")
    public Usuario salvarUsuario(@WebParam(name = "pUsuario") Usuario param) throws Exception {
        return (Usuario) new ServiceUsuario().salvar(param);
    }

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    @WebMethod(operationName = "loginUsuario")
    public Usuario loginUsuario(@WebParam(name = "pUsuario") Usuario param) throws Exception {
        Object retorno = new ServiceUsuario().loginUsuario(param);
        return retorno != null ? (Usuario) retorno : null;
    }

    /**
     * Operacao de servico web
     */
    @WebMethod(operationName = "backupControlCheque")
    public void backupControlCheque() throws Exception {
        new ServiceUtil().gerarBackup();
    }
    /**
     * FIM METODOS SERVICE USUARIO
     */
}
