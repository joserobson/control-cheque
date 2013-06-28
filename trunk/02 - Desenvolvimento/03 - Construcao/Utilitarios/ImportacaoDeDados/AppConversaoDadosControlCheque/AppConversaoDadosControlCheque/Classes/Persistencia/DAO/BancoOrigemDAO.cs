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
    class BancoOrigemDAO
    {
        private string sql_Consulta_Banco =
      "SELECT CodBanco" +
      ",Banco " +
      "FROM Banco ";

        //METODO PARA BUSCAR CLIENTES BASE ORIGEM
        public IList Consultar_Banco()
        {
            try
            {
                if (ConexaoBD.CriarConexao_Origem())
                {

                    //CONSULTA TABELA BANCO
                    IList lst_Bancos = new ArrayList();

                    string consulta_Banco = sql_Consulta_Banco;
                    OdbcCommand cmd_Consulta_Banco = new OdbcCommand(consulta_Banco, ConexaoBD.getConexao_Origem());
                    OdbcDataAdapter da_Banco = new OdbcDataAdapter(cmd_Consulta_Banco);
                    DataSet ds_Banco = new DataSet();
                    da_Banco.Fill(ds_Banco);
                    lst_Bancos = PercorreDataSetBancos(ds_Banco);
                    cmd_Consulta_Banco.Dispose();
                    return lst_Bancos;
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

        //METODO PARA PERCORRER DATASET BANCOS ORIGEM
        private IList PercorreDataSetBancos(DataSet ds)
        {
            IList lst_Bancos = new ArrayList();
            if (ds.Tables.Count > 0)
            {
                foreach (DataRow linha in ds.Tables[0].Rows)
                {
                    Banco_Origem banco = new Banco_Origem();
                    if (linha["codBanco"] != DBNull.Value)
                        banco.CodigoBanco = (string)linha["codBanco"];
                    if (linha["Banco"] != DBNull.Value)
                        banco.NomeBanco = (string)linha["Banco"];

                    lst_Bancos.Add(banco);
                }
            }
            return lst_Bancos;
        }
    }
}
