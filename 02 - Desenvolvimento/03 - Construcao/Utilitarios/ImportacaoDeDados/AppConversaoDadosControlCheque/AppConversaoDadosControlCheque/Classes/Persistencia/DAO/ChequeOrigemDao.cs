using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using AppConversaoDadosControlCheque.Classes.Persistencia.Conexao;
using System.Data.Odbc;
using System.Data;
using AppConversaoDadosControlCheque.Classes.Negocio;

namespace AppConversaoDadosControlCheque.Classes.Persistencia.DAO
{
    public class ChequeOrigemDao
    {
        private static string coluna_codigo_cheque = "codCheque";
        private static string coluna_codigo_banco = "codBanco";
        private static string coluna_conta = "conta";
        private static string coluna_agencia = "agencia";
        private static string coluna_numero_cheque = "numCheque";
        private static string coluna_titular = "titular";
        private static string coluna_valor = "valor";
        private static string coluna_cpf = "cpf";
        private static string coluna_telefone_titular = "telTitular";
        private static string coluna_bom_para = "bomPara";
        private static string coluna_data_emissao = "dataEmissao";
        private static string coluna_recebiDe = "recibeDe";
        private static string coluna_passei_para= "passeiPara";
        private static string coluna_obs = "obs";
        private static string coluna_codigo_situacao = "codSituacao";
        

        private string sql_Consulta_Cheque = 
            "Select " + 
            "codCheque, " +
            "codBanco, " +
            "conta, " +
            "agencia, "+
            "numCheque, "+
            "titular, "+
            "valor, "+
            "cpf, "+
            "telTitular, " +
            "bomPara, " + 
            "dataEmissao, " +
            "recibeDe, "+
            "passeiPara, " +
            "codSituacao " +
            "From Cheque ";

        public IList consultarCheques()
        {
            try
            {
                if (ConexaoBD.CriarConexao_Origem())
                {

                    //CONSULTA TABELA CHEQUE
                    IList lst_Cheques = new ArrayList();

                    string consulta_Cheques = sql_Consulta_Cheque;                    
                    OdbcCommand cmd_Consulta_Cheque = new OdbcCommand(consulta_Cheques, ConexaoBD.getConexao_Origem());                    
                    OdbcDataAdapter da_Cheque = new OdbcDataAdapter(cmd_Consulta_Cheque);
                    DataSet ds = new DataSet();
                    da_Cheque.Fill(ds);
                    lst_Cheques = PercorreDataSetCheques(ds);
                    cmd_Consulta_Cheque.Dispose();
                    return lst_Cheques;
                }
            }
            catch (Exception ex)
            {
                return null;
                throw ex;
            }
            finally
            {
                ConexaoBD.fechaConexao_Origem();
            }
            return null;
        }

        private IList PercorreDataSetCheques(DataSet ds)
        {
            IList lst_Cheques = new ArrayList();
            IList lst_Cheques_Problema = new ArrayList();     
            if (ds.Tables.Count > 0)
            {
                foreach (DataRow linha in ds.Tables[0].Rows)
                {
                    Cheque_Origem cheque = new Cheque_Origem();

                    try
                    {

                        if (linha[coluna_codigo_cheque] != DBNull.Value)
                            cheque.CodigoCheque = linha[coluna_codigo_cheque].ToString(); ;

                        if (linha[coluna_codigo_banco] != DBNull.Value)
                            cheque.CodigoBanco = (string)linha[coluna_codigo_banco];

                        if (linha[coluna_conta] != DBNull.Value)
                            cheque.Conta = linha[coluna_conta].ToString();

                        if (linha[coluna_agencia] != DBNull.Value)
                            cheque.Agencia = linha[coluna_agencia].ToString();

                        if (linha[coluna_titular] != DBNull.Value)
                            cheque.Titular = linha[coluna_titular].ToString();

                        if (linha[coluna_valor] != DBNull.Value)
                            cheque.Valor = Double.Parse(linha[coluna_valor].ToString());

                        if (linha[coluna_cpf] != DBNull.Value)
                            cheque.Cpf = linha[coluna_cpf].ToString();

                        if (linha[coluna_telefone_titular] != DBNull.Value)
                            cheque.TelefoneTitular = linha[coluna_telefone_titular].ToString();

                        if (linha[coluna_numero_cheque] != DBNull.Value)
                            cheque.NumeroCheque = linha[coluna_numero_cheque].ToString();

                        if (linha[coluna_bom_para] != DBNull.Value)
                            cheque.BomPara = (DateTime)linha[coluna_bom_para];

                        if (linha[coluna_data_emissao] != DBNull.Value)
                            cheque.DataEmissao = (DateTime)linha[coluna_data_emissao];

                        if (linha[coluna_recebiDe] != DBNull.Value)
                        {
                            cheque.RecebiDe = linha[coluna_recebiDe].ToString();
                        }

                        if (linha[coluna_passei_para] != DBNull.Value)
                            cheque.PasseiPara = linha[coluna_passei_para].ToString(); ;

                        if (linha[coluna_codigo_situacao] != DBNull.Value)
                            cheque.CodSituacao = (int)linha[coluna_codigo_situacao];

                        lst_Cheques.Add(cheque);

                    }
                    catch (Exception ex)
                    {
                        lst_Cheques_Problema.Add(cheque);
                    }
                    
                }
            }
            return lst_Cheques;
        }

        public void tratoCampoObservacaoParaSerImportado()
        {
            string codigoCheque = string.Empty;
            string obsCheque= string.Empty;
                      
            try
            {
                System.IO.StreamReader str = new System.IO.StreamReader(AppConversaoDadosControlCheque.Properties.Settings.Default.arquivo_nao_tratado, System.Text.Encoding.Default);
                System.IO.StreamWriter wri = new System.IO.StreamWriter(AppConversaoDadosControlCheque.Properties.Settings.Default.arquivo_tratado);

                String line;
                while ((line = str.ReadLine()) != null)
                {                    
                    if (line.Length > 5)
                    {
                        wri.WriteLine(line.Substring(0,5) + " - " + line.Substring(5));
                    }

                }

                wri.Close();
            
            }
            catch (Exception ex)
            {
                throw ex;
            }            

        }

    }
}
