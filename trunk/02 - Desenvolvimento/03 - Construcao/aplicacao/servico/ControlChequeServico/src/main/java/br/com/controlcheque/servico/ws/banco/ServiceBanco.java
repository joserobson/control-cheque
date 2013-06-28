/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.ws.banco;

import br.com.controlcheque.servico.beans.Banco;
import br.com.controlcheque.servico.ws.ServiceBase;
import br.com.controlcheque.transacao.classes.TnBanco;
import br.com.controlcheque.transacao.constantes.ConstanteBanco;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Robson
 */
public class ServiceBanco extends ServiceBase {

    private TnBanco tnBanco = null;

    public ServiceBanco() {
        this.tnBanco = new TnBanco();
    }

    
    
    @Override
    public Object salvar(Object param) throws Exception {
        Banco retorno = null;
        try {
            tnBanco.setBanco(mapper.map(param, br.com.controlcheque.server.model.Banco.class));
            retorno = mapper.map(tnBanco.salvar(ConstanteBanco.NOME_EVENTO_SALVAR_BANCO), Banco.class);
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return retorno;
    }

    @Override
    public List recTodos() throws Exception {
        List<Banco> listBancos = null;
        try {
            List<br.com.controlcheque.server.model.Banco> listaRetorno = tnBanco.recuperarTodos(ConstanteBanco.NOME_EVENTO_BUSCAR_TODOS_BANCOS);
            if (listaRetorno != null
                    && listaRetorno.size() > 0) {
                listBancos = new ArrayList<Banco>();
                Iterator<br.com.controlcheque.server.model.Banco> iterator = listaRetorno.iterator();
                while (iterator.hasNext()) {
                    listBancos.add(mapper.map(iterator.next(), Banco.class));
                }
            }
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return listBancos;
    }

    @Override
    public Object recObjeto(Object param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object deletar(Object param) throws Exception {
        Banco retorno = null;
        try {
            tnBanco.setBanco(mapper.map(param, br.com.controlcheque.server.model.Banco.class));
            br.com.controlcheque.server.model.Banco b = (br.com.controlcheque.server.model.Banco) tnBanco.deletar(ConstanteBanco.NOME_EVENTO_DELETAR_BANCO);
            retorno = mapper.map(b, Banco.class);
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
                List<br.com.controlcheque.server.model.Banco> lstBancoModel = converterListaBancoBeansToModel(lstObjetos);
                for (br.com.controlcheque.server.model.Banco banco : lstBancoModel) {
                    tnBanco.setBanco(banco);
                    tnBanco.salvar(ConstanteBanco.NOME_EVENTO_SALVAR_LISTA_BANCO);
                }
            }

        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }

        return retorno;
    }

    /**
     * Metodo para converter de banco beans para banco Model
     * @param listaBancos
     * @return 
     */
    private List<br.com.controlcheque.server.model.Banco> converterListaBancoBeansToModel(List<Banco> listaBancos) {
        List<br.com.controlcheque.server.model.Banco> listBancos = null;
        if (listaBancos != null
                && listaBancos.size() > 0) {
            listBancos = new ArrayList<br.com.controlcheque.server.model.Banco>();
            Iterator<Banco> iterator = listaBancos.iterator();
            while (iterator.hasNext()) {
                listBancos.add(mapper.map(iterator.next(), br.com.controlcheque.server.model.Banco.class));
            }
        }
        return listBancos;
    }
}
