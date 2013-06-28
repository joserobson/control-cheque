using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AppConversaoDadosControlCheque.Classes.Negocio
{
    public class Cheque_Origem
    {
        public string CodigoCheque { get; set; }
        public string CodigoBanco { get; set; }
        public string Conta { get; set; }
        public string Agencia { get; set; }
        public string NumeroCheque { get; set; }
        public string Titular { get; set; }
        public Double Valor { get; set; }
        public string Cpf { get; set; }
        public string TelefoneTitular { get; set; }
        public DateTime BomPara { get; set; }
        public DateTime DataEmissao { get; set; }
        public string RecebiDe { get; set; }
        public string PasseiPara { get; set; }
        public string Obs { get; set; }
        public int CodSituacao { get; set; }


        
    }
}
