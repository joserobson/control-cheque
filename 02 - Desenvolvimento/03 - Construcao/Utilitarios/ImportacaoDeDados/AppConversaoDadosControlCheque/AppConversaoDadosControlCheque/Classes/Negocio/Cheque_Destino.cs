using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AppConversaoDadosControlCheque.Classes.Negocio
{
    public class Cheque_Destino
    {
        public String Id { get; set; }
        public String Conta { get; set; }
        public String Agencia { get; set; }
        public String Numero { get; set; }
        public String Titular { get; set; }
        public String CpfTitular { get; set; }
        public String TelefoneTitular { get; set; }
        public DateTime DataBomPara { get; set; }
        public DateTime DataEmissao { get; set; }
        public String Observacao { get; set; }
        public int SituacaoCheque { get; set; }
        public Double Valor { get; set; }
        public String RecebiDe { get; set; }
        public String PasseiPara { get; set; }
        public int StatusCheque { get; set; }
        public Banco_Destino Banco { get; set; }
        public string CodigoCheque { get; set; }

        public Cheque_Destino()
        {
            this.Id = string.Empty;
            this.Conta = string.Empty;
            this.Agencia = string.Empty;
            this.Numero = string.Empty;
            this.Titular = string.Empty;
            this.CpfTitular = string.Empty;
            this.TelefoneTitular = string.Empty;
            this.TelefoneTitular = string.Empty;
            this.Observacao = string.Empty;
            this.SituacaoCheque = 0;
            this.Valor = 0;
            this.RecebiDe = string.Empty;
            this.PasseiPara = string.Empty;
            this.StatusCheque = 1;
            this.Banco = null;
            this.CodigoCheque = string.Empty;
            

        }
    }
}
