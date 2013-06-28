using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using AppConversaoDadosControlCheque.Classes.Persistencia.Conexao;
using System.Data.Odbc;
using System.Data;
using AppConversaoDadosControlCheque.Classes.Negocio;
using System.Text.RegularExpressions;

namespace AppConversaoDadosControlCheque.Classes.Persistencia.DAO
{
    class ClienteOrigemDAO
    {
        private string sql_Consulta_Cliente =
    "SELECT CodCliente" +
    ",nome " +
    ",tel " +
    ",rua " +
    ",bairro " +
    ",cidade " +
    ",complemento " +
    ",numero " +
    "FROM Cliente ";

        //METODO PARA BUSCAR CLIENTES BASE ORIGEM
        public IList Consultar_Cliente()
        {
            try
            {
                if (ConexaoBD.CriarConexao_Origem())
                {

                    //CONSULTA TABELA Cliente
                    IList lst_Clientes = new ArrayList();

                    string consulta_Cliente = sql_Consulta_Cliente;
                    OdbcCommand cmd_Consulta_Cliente = new OdbcCommand(consulta_Cliente, ConexaoBD.getConexao_Origem());
                    OdbcDataAdapter da_Cliente = new OdbcDataAdapter(cmd_Consulta_Cliente);
                    DataSet ds_Cliente = new DataSet();
                    da_Cliente.Fill(ds_Cliente);
                    lst_Clientes = PercorreDataSetClientes(ds_Cliente);
                    cmd_Consulta_Cliente.Dispose();
                    return lst_Clientes;
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

        //METODO PARA PERCORRER DATASET ClienteS ORIGEM
        private IList PercorreDataSetClientes(DataSet ds)
        {
            IList lst_Clientes = new ArrayList();
            if (ds.Tables.Count > 0)
            {
                foreach (DataRow linha in ds.Tables[0].Rows)
                {
                    Cliente_Origem Cliente = new Cliente_Origem();
                    if (linha["codCliente"] != DBNull.Value)
                        Cliente.CodigoCliente = (string)linha["codCliente"];
                    if (linha["nome"] != DBNull.Value)
                        Cliente.NomeCliente = (string)linha["nome"];
                    if (linha["tel"] != DBNull.Value)
                        Cliente.TelefoneCliente = (string)linha["tel"];
                    if (linha["rua"] != DBNull.Value)
                        Cliente.RuaCliente = (string)linha["rua"];
                    if (linha["bairro"] != DBNull.Value)
                        Cliente.BairroCliente = (string)linha["bairro"];
                    if (linha["cidade"] != DBNull.Value)
                        Cliente.CidadeCliente = (string)linha["cidade"];
                    if (linha["complemento"] != DBNull.Value)
                        Cliente.ComplementoCliente = (string)linha["complemento"];
                    if (linha["numero"] != DBNull.Value)
                        Cliente.NumeroCliente = (int)linha["numero"];

                    lst_Clientes.Add(Cliente);
                }
            }
            return lst_Clientes;
        }

    }
}
