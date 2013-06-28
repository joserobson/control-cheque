using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using AppConversaoDadosControlCheque.Classes.Persistencia.Conexao;
using AppConversaoDadosControlCheque.Classes.Negocio;
using MySql.Data.MySqlClient;
using System.Globalization;

namespace AppConversaoDadosControlCheque.Classes.Persistencia.DAO
{
    public class ChequeDestinoDao
    {
        //COMANDOS SQL USADOS        
        private string sql_Insert_Cheque =
           "INSERT INTO tb_Cheque " +
           "(idCheque,idBanco,contaBanco,agenciaBanco,numeroCheque,titularCheque,cpfTitular," +
           "dataBomPara,telefoneTitular,dataEmissao,obsCheque,situacaoCheque,valorCheque,recebiDe," +
           "passeiPara,statusCheque,idUsuario,codigoCheque) " +
           "VALUES " +
           "('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}', " +
           "'{8}','{9}','{10}',{11},'{12}','{13}','{14}',{15},{16},'{17}' )";

        private string sql_update_cheque =
            "Update tb_cheque set obsCheque = '{0}' where codigoCheque = '{1}'";


        //METODO PARA INSERIR UM CHEQUE
        public bool Inserir_Cheque(IList lstCheques)
        {
            IList lstChequesProblemas = new ArrayList();
            string insert = string.Empty;
            String paramTitular = null;
            String paramDataBomPara = null;
            String paramDataEmissao = null;
            String paramRecebiDe = null;
            String paramPasseiPara = null;

            try
            {
                if (ConexaoBD.CriarConexao_Destino())
                {
                    foreach (Cheque_Destino cheque in lstCheques)
                    {                        

                        //VERIFICAR PARAMETROS
                        if (cheque.Titular != null)
                            paramTitular = cheque.Titular.Replace("'", "");
                        if (cheque.DataBomPara != null)
                            paramDataBomPara = cheque.DataBomPara.ToString("yyyy-MM-dd");
                        if (cheque.DataEmissao != null)
                            paramDataEmissao = cheque.DataEmissao.ToString("yyyy-MM-dd"); 
                        if (cheque.RecebiDe != null)
                            paramRecebiDe = cheque.RecebiDe.Replace("'", "");
                        if (cheque.PasseiPara != null)
                            paramPasseiPara = cheque.PasseiPara.Replace("'", "");

                        //INSERINDO NA TABELA CLIENTE                        
                        insert = string.Format(sql_Insert_Cheque, cheque.Id, cheque.Banco.IdObjeto, cheque.Conta, cheque.Agencia, cheque.Numero, paramTitular,
                            cheque.CpfTitular,paramDataBomPara , cheque.TelefoneTitular,paramDataEmissao , cheque.Observacao, cheque.SituacaoCheque,
                            cheque.Valor.ToString("0.00", CultureInfo.InvariantCulture.NumberFormat).Replace(",", ""),
                            paramRecebiDe,paramPasseiPara , 1, "null", cheque.CodigoCheque);
                        MySqlCommand cmd_Insert = new MySqlCommand(insert, ConexaoBD.getConexao_Destino());
                        cmd_Insert.ExecuteNonQuery();
                        cmd_Insert.Dispose();

                    }

                }
            }
            catch (Exception ex)
            {
                //implementar update caso dê erro
                //throw new Exception("SQL ERRO: " + insert);
                lstChequesProblemas.Add(insert);
            }
            finally
            {
                ConexaoBD.fechaConexao_Destino();
            }
            return true;
        }

        //METODO PARA UPDATE CAMPO OBSERVACAO
        public void update_Cheque_Obs()
        {
            string update = string.Empty;
            string codigoCheque = string.Empty;
            string obsCheque = string.Empty;
            IList lstChequesErros = new ArrayList();
            try
            {
                System.IO.StreamReader str = new System.IO.StreamReader(AppConversaoDadosControlCheque.Properties.Settings.Default.arquivo_tratado);

                String line;

                try
                {
                    if (ConexaoBD.CriarConexao_Destino())
                    {
                        while ((line = str.ReadLine()) != null)
                        {
                            codigoCheque = line.Substring(0, 5);
                            obsCheque = line.Substring(5).TrimEnd();
                            update = string.Format(sql_update_cheque, obsCheque, codigoCheque);
                            MySqlCommand cmd_Insert = new MySqlCommand(update, ConexaoBD.getConexao_Destino());
                            cmd_Insert.ExecuteNonQuery();
                            cmd_Insert.Dispose();
                        }

                    }
                }

                catch (Exception ex)
                {
                    //implementar update caso dê erro
                    //throw new Exception("SQL ERRO: " + update);
                    lstChequesErros.Add(update);
                }
                finally
                {
                    ConexaoBD.fechaConexao_Destino();
                }


            }
            catch (Exception ex)
            {
                throw ex;
            } 
        }
    }
}
