using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Data.Odbc;
using AppConversaoDadosControlCheque.Classes.Persistencia.Conexao;
using AppConversaoDadosControlCheque.Classes.Persistencia.DAO;
using System.Collections;
using AppConversaoDadosControlCheque.Classes.Negocio;
using System.Text.RegularExpressions;

namespace AppConversaoDadosControlCheque
{
    public partial class Form1 : Form
    {

         

        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            importar_Tabela_Banco();
        }

        private void importar_Tabela_Banco()
        {
            BancoOrigemDAO bancoOrigemDao = new BancoOrigemDAO();
            IList lstBancosOrigem = bancoOrigemDao.Consultar_Banco();
            IList lstBancosDestino = new ArrayList();
            if (lstBancosOrigem != null &&
                lstBancosOrigem.Count > 0)
            {
                BancoDAO bancoDestinoDao = new BancoDAO();
                foreach (Banco_Origem bo in lstBancosOrigem)
                {
                    Banco_Destino bd = new Banco_Destino();
                    bd.IdObjeto = Guid.NewGuid().ToString();
                    bd.CodigoBanco = bo.CodigoBanco;
                    bd.DescricaoBanco = bo.NomeBanco;

                    lstBancosDestino.Add(bd);

                }
            }

            BancoDAO bancoDestinoDAO = new BancoDAO();
            if (bancoDestinoDAO.Deletar_Banco())
            {
                if (!bancoDestinoDAO.Inserir_Banco(lstBancosDestino))
                {
                    MessageBox.Show("ERRO AO IMPORTAR TABELA BANCO");
                }
                else
                {
                    MessageBox.Show("TABELA BANCO IMPORTADA COM SUCESSO.");
                }
            }
            else
            {
                MessageBox.Show("ERRO AO DELETAR TABELA BANCO.");
            }

        }

        private void btn_ImportarCliente_Click(object sender, EventArgs e)
        {
            importar_Tabela_Cliente();            
        }

        private void importar_Tabela_Cliente()
        {
            ClienteOrigemDAO clienteOrigemDao = new ClienteOrigemDAO();
            IList lstClientesOrigem = clienteOrigemDao.Consultar_Cliente();
            IList lstClientesDestino = new ArrayList();
            ClienteDestinoDAO clienteDestinoDao = new ClienteDestinoDAO();
            if (lstClientesOrigem != null &&
                lstClientesOrigem.Count > 0)
            {                
                foreach (Cliente_Origem clienteOrigem in lstClientesOrigem)
                {
                    Cliente_Destino clienteDestino = new Cliente_Destino();
                    clienteDestino.IdObjeto = Guid.NewGuid().ToString();
                    clienteDestino.NomeCliente = this.retiraAcentos(clienteOrigem.NomeCliente).ToUpper();
                    clienteDestino.CodigoCliente = this.retiraAcentos(clienteOrigem.CodigoCliente).ToUpper();
                    clienteDestino.TelefoneCliente = this.retiraAcentos(clienteOrigem.TelefoneCliente).ToUpper();
                    clienteDestino.RuaCliente = this.retiraAcentos(clienteOrigem.RuaCliente).ToUpper();
                    clienteDestino.BairroCliente = this.retiraAcentos(clienteOrigem.BairroCliente).ToUpper();
                    clienteDestino.CidadeCliente = this.retiraAcentos(clienteOrigem.CidadeCliente).ToUpper();
                    clienteDestino.ComplementoCliente = this.retiraAcentos(clienteOrigem.ComplementoCliente);
                    clienteDestino.NumeroCliente = clienteOrigem.NumeroCliente.ToString();

                    lstClientesDestino.Add(clienteDestino);

                }
            }
            
            if (clienteDestinoDao.Deletar_Cliente())
            {
                if (!clienteDestinoDao.Inserir_Cliente(lstClientesDestino))
                {
                    MessageBox.Show("ERRO AO IMPORTAR TABELA CLIENTE");
                }
                else
                {
                    MessageBox.Show("TABELA CLIENTE IMPORTADA COM SUCESSO.");
                }
            }
            else
            {
                MessageBox.Show("ERRO AO DELETAR TABELA CLIENTE.");
            }
        }


        private string retiraAcentos(string strcomAcentos)
        {
            string strsemAcentos = strcomAcentos;
            strsemAcentos = Regex.Replace(strsemAcentos, "[áàâãª]", "a");
            strsemAcentos = Regex.Replace(strsemAcentos, "[ÁÀÂÃ]", "A");
            strsemAcentos = Regex.Replace(strsemAcentos, "[éèê]", "e");
            strsemAcentos = Regex.Replace(strsemAcentos, "[ÉÈÊ]", "e");
            strsemAcentos = Regex.Replace(strsemAcentos, "[íìî]", "i");
            strsemAcentos = Regex.Replace(strsemAcentos, "[ÍÌÎ]", "I");
            strsemAcentos = Regex.Replace(strsemAcentos, "[óòôõº]", "o");
            strsemAcentos = Regex.Replace(strsemAcentos, "[ÓÒÔÕ]", "O");
            strsemAcentos = Regex.Replace(strsemAcentos, "[úùû]", "u");
            strsemAcentos = Regex.Replace(strsemAcentos, "[ÚÙÛ]", "U");
            strsemAcentos = Regex.Replace(strsemAcentos, "[ç]", "c");
            strsemAcentos = Regex.Replace(strsemAcentos, "[Ç]", "C");
            strsemAcentos = Regex.Replace(strsemAcentos, "[']", "");
            return strsemAcentos;
        }

        private void btn_Importar_Cheque_Click(object sender, EventArgs e)
        {
            importa_Tabela_Cheque();
        }

        private void importa_Tabela_Cheque()
        {
            ChequeOrigemDao chequeOrigemDao = new ChequeOrigemDao();
            ChequeDestinoDao chequeDestinoDao = new ChequeDestinoDao();

            //chequeDestinoDao.update_Cheque_Obs();

            chequeOrigemDao.tratoCampoObservacaoParaSerImportado();

            if (MessageBox.Show("Tratar aquivo do campo obs?", "Arquivo Tratato", MessageBoxButtons.YesNo) ==
                DialogResult.Yes)
            {

                IList lstCheques = chequeOrigemDao.consultarCheques();
                BancoDAO bancoDestinoDao = new BancoDAO();
                IList lstDestino = new ArrayList();

                if (lstCheques != null &&
                    lstCheques.Count > 0)
                {
                    foreach (Cheque_Origem origem in lstCheques)
                    {
                        Cheque_Destino destino = new Cheque_Destino();
                        destino.Id = Guid.NewGuid().ToString();
                        destino.Agencia = origem.Agencia;
                        destino.Conta = origem.Conta;
                        destino.CpfTitular = origem.Cpf;
                        destino.DataBomPara = origem.BomPara;
                        destino.DataEmissao = origem.DataEmissao;
                        destino.Numero = origem.NumeroCheque;
                        destino.CodigoCheque = origem.CodigoCheque;
                        destino.PasseiPara = origem.PasseiPara;
                        destino.RecebiDe = origem.RecebiDe;
                        destino.SituacaoCheque = origem.CodSituacao;
                        destino.StatusCheque = 1;
                        destino.TelefoneTitular = origem.TelefoneTitular;
                        destino.Titular = origem.Titular;
                        destino.Valor = origem.Valor;

                        Banco_Destino bb = new Banco_Destino();
                        bb.IdObjeto = bancoDestinoDao.consultaIdBanco(origem.CodigoBanco);

                        if (bb.IdObjeto != null &&
                            bb.IdObjeto != String.Empty)
                        {
                            destino.Banco = bb;
                        }

                        lstDestino.Add(destino);
                    }

                    
                    if (chequeDestinoDao.Inserir_Cheque(lstDestino))
                    {
                        chequeDestinoDao.update_Cheque_Obs();
                    }
                }
            }
        }

    }
}
