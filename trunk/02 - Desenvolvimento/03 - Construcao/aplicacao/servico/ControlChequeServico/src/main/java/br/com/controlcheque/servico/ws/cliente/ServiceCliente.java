/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.ws.cliente;

import br.com.controlcheque.servico.beans.Cliente;
import br.com.controlcheque.servico.ws.ServiceBase;
import br.com.controlcheque.transacao.classes.TnCliente;
import br.com.controlcheque.transacao.constantes.ConstanteCliente;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Robson
 */
public class ServiceCliente extends ServiceBase {

    private TnCliente tnCliente = null;

    public ServiceCliente() {
        this.tnCliente = new TnCliente();
    }

    public String gerarCodigoCliente() throws Exception {
        String codigo = "-1";
        try {
            codigo = tnCliente.gerarCodigoCliente(ConstanteCliente.NOME_EVENTO_GERAR_COD_CLIENTE);
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return codigo;
    }

    @Override
    public Object salvar(Object param) throws Exception {
        Cliente retorno = null;
        try {
            tnCliente.setCliente(mapper.map(param, br.com.controlcheque.server.model.Cliente.class));
            retorno = mapper.map(tnCliente.salvar(ConstanteCliente.NOME_EVENTO_SALVAR_CLIENTE), Cliente.class);
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return retorno;
    }

    @Override
    public List recTodos() throws Exception {
        List<Cliente> listClientes = null;
        try {
            List<br.com.controlcheque.server.model.Cliente> listaRetorno = tnCliente.recuperarTodos(ConstanteCliente.NOME_EVENTO_BUSCAR_TODOS_CLIENTE);
            if (listaRetorno != null
                    && listaRetorno.size() > 0) {
                listClientes = new ArrayList<Cliente>();
                Iterator<br.com.controlcheque.server.model.Cliente> iterator = listaRetorno.iterator();
                while (iterator.hasNext()) {
                    listClientes.add(mapper.map(iterator.next(), Cliente.class));
                }
            }
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return listClientes;
    }

    @Override
    public Object deletar(Object param) throws Exception {
        Cliente retorno = null;
        try {
            tnCliente.setCliente(mapper.map(param, br.com.controlcheque.server.model.Cliente.class));
            br.com.controlcheque.server.model.Cliente c = (br.com.controlcheque.server.model.Cliente) tnCliente.deletar(ConstanteCliente.NOME_EVENTO_DELETAR_CLIENTE);
            retorno = mapper.map(c, Cliente.class);
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return retorno;
    }

    @Override
    public boolean salvarListObjetos(List lstObjetos) throws Exception {
        boolean retorno = true;
        try {
            //converte lista de bancos
            if (lstObjetos != null
                    && lstObjetos.size() > 0) {
                List<br.com.controlcheque.server.model.Cliente> lstClienteModel = converterListaClienteBeansToModel(lstObjetos);
                for (br.com.controlcheque.server.model.Cliente cliente : lstClienteModel) {
                    tnCliente.setCliente(cliente);
                    tnCliente.salvar(ConstanteCliente.NOME_EVENTO_SALVAR_LISTA_CLIENTE);
                }
            }

        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }

        return retorno;
    }

    /**
     * Metodo para converter de cliente beans para cliente Model
     * @param listaBancos
     * @return 
     */
    private List<br.com.controlcheque.server.model.Cliente> converterListaClienteBeansToModel(List<Cliente> listaClientesBeans) {
        List<br.com.controlcheque.server.model.Cliente> listClientes = null;
        if (listaClientesBeans != null
                && listaClientesBeans.size() > 0) {
            listClientes = new ArrayList<br.com.controlcheque.server.model.Cliente>();
            Iterator<Cliente> iterator = listaClientesBeans.iterator();
            while (iterator.hasNext()) {
                listClientes.add(mapper.map(iterator.next(), br.com.controlcheque.server.model.Cliente.class));
            }
        }
        return listClientes;
    }
}
