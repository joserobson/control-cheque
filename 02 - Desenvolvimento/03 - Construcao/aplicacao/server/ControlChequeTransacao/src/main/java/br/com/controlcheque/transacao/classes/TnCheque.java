/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.transacao.classes;

import br.com.controlcheque.server.bo.ChequeBoImpl;
import br.com.controlcheque.server.dominio.ESituacaoCheque;
import br.com.controlcheque.server.dominio.EStatusCheque;
import br.com.controlcheque.server.model.Cheque;
import br.com.controlcheque.transacao.constantes.ConstanteCheque;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import br.com.controlcheque.transacao.util.TransacaoUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author Robson
 */
public class TnCheque extends TnBase {

    /**
     * ATRIBUTOS DA CLASSE
     */
    private Cheque cheque = null;
    private ESituacaoCheque enumSituacaoCheque = null;
    private EStatusCheque enumStatusCheque = null;
    private ChequeBoImpl chequeBo = null;
    private Date dataInicial;
    private Date dataFinal;

    /**
     * Construtor
     */
    public TnCheque() {
        this.chequeBo = (ChequeBoImpl) TransacaoUtil.getInstanceBo(Cheque.class);
    }

    /**
     * METODOS ACESSORES
     *
     * @return
     */
    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public ESituacaoCheque getEnumSituacaoCheque() {
        return enumSituacaoCheque;
    }

    public void setEnumSituacaoCheque(ESituacaoCheque enumSituacaoCheque) {
        this.enumSituacaoCheque = enumSituacaoCheque;
    }

    public EStatusCheque getEnumStatusCheque() {
        return enumStatusCheque;
    }

    public void setEnumStatusCheque(EStatusCheque enumStatusCheque) {
        this.enumStatusCheque = enumStatusCheque;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    /**
     * METODOS DA TRANSACAO
     */
    /**
     * METODO PARA SALVAR UM CHEQUE
     *
     * @param evento
     * @return
     * @throws TransacaoChequeException
     */
    @Override
    public Object salvar(String evento) throws TransacaoChequeException {
        Cheque retorno = null;
        try {
            if (validarDados(evento)) {
                retorno = chequeBo.mantemCheque(cheque);
            }
        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }

        return retorno;
    }

    /**
     * Valida Dados campos obrigatórios
     *
     * @param evento
     * @return
     * @throws TransacaoChequeException
     */
    @Override
    public boolean validarDados(String evento) throws TransacaoChequeException {
        boolean retorno = true;

        if (evento.equals(ConstanteCheque.NOME_EVENTO_SALVAR_CHEQUE)) {
            //valida campo Banco
            if (cheque.getBanco() == null) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteCheque.CODIGO_ERRO_BANCO_OBRIGATORIO);
            }

            //valida campo obrigatorio titular
            if (cheque.getTitular().isEmpty()) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteCheque.CODIGO_ERRO_TITULAR_OBRIGATORIO);
            }

            //valida campo obrigatorio valor
            if (cheque.getValor() == 0) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteCheque.CODIGO_ERRO_VALOR_OBRIGATORIO);
            }

            //valida campo obrigatorio data bompara
            if (cheque.getDataBomPara() == null) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteCheque.CODIGO_ERRO_DATA_BOMPARA_OBRIGATORIO);
            }

            //valida campo obrigatorio RecebiDe
            if (cheque.getRecebiDe().isEmpty()) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteCheque.CODIGO_ERRO_RECEBIDE_OBRIGATORIO);
            }
        }else{
            if (evento.equals(ConstanteCheque.NOME_EVENTO_RECQUANTIDADE_CHEQUES_PORINTERVALO_DATA) ||
                    evento.equals(ConstanteCheque.NOME_EVENTO_RECSOMATORIO_CHEQUES_PORINTERVALO_DATA))
            {
                if (dataInicial != null &&
                        dataFinal != null && 
                            dataInicial.after(dataFinal) &&
                            dataFinal.before(dataInicial))
                {
                    throw new TransacaoChequeException(ConstanteCheque.CODIGO_ERRO_VALIDAR_INTERVALO_DATAS);
                }
            }
        }

        return retorno;
    }

    /**
     *
     * @param evento
     * @return
     * @throws TransacaoChequeException
     */
    @Override
    public Object deletar(String evento) throws TransacaoChequeException {

        try {
            return chequeBo.deleteCheque(cheque);
        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }
    }

    /**
     * Recupera todos os Cheques cadastrados
     *
     * @param evento
     * @return
     */
    public List<Cheque> recuperarTodos(String evento) throws TransacaoChequeException {
        List<Cheque> retornoLista = null;
        try {
            retornoLista = chequeBo.consultaCheques();

        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }

        return retornoLista;
    }

    /**
     * Recupera Cheques filtrados pelos parametros de status e situacao do
     * cheque
     *
     * @param evento
     * @return
     */
    public List<Cheque> recuperarChequePorSituacao(String evento) throws TransacaoChequeException {
        List<Cheque> retornoLista = null;
        try {
            retornoLista = chequeBo.consultaChequesPorSituacao(enumSituacaoCheque, enumStatusCheque);
        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }

        return retornoLista;
    }

    /**
     * gerar código sequencial para Cheque
     *
     * @return
     */
    public String gerarCodigoCheque(String evento) throws TransacaoChequeException {
        String codigoRetorno = "-1";
        try {
            codigoRetorno = chequeBo.recuperaMaxCodigoCheque();

        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }

        return codigoRetorno;
    }

    /**
     *
     * @param evento
     * @return
     * @throws TransacaoChequeException
     */
    @Override
    public Object recObjectPorId(String evento) throws TransacaoChequeException {
        return (Cheque) chequeBo.recChequePorID(cheque.getId());
    }

    /**
     * REMOVE CHEQUE DA LIXEIRA
     *
     * @param cheque
     * @return
     */
    public Cheque deletarChequeLixeira(String evento) throws TransacaoChequeException {
        return chequeBo.deleteChequeLixeira(cheque);
    }

    /**
     * Retorna somatório de valores dos cheques entre as datas passadas
     * @param evento
     * @return
     * @throws TransacaoChequeException 
     */
    public float recSomatorioChequesPorIntervaloData(String evento) throws TransacaoChequeException {
        validarDados(evento);
        return chequeBo.recSomatarioChequesPorIntervaloDatas(this.dataInicial, this.dataFinal);
    }
    
    /**
     * Retorna quantidade de cheques entre as datas passadas
     * @param evento
     * @return
     * @throws TransacaoChequeException 
     */
    public int recQuantidadeChequesPorIntervaloData(String evento) throws TransacaoChequeException{
        validarDados(evento);
        return chequeBo.recQuantidadeChequesPorIntervaloDatas(this.dataInicial, this.dataFinal);
    }
}