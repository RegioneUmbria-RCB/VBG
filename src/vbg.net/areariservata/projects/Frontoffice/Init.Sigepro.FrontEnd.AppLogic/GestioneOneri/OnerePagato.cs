using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
    public class OnerePagato
    {
        public readonly IdOnere Id;
        public readonly ModalitaPagamentoOnereEnum ModalitaPagamento;
        public readonly EstremiPagamento EstremiPagamento;

        public OnerePagato(IdOnere id, ModalitaPagamentoOnereEnum modalitaPagamento, EstremiPagamento estremiPagamento)
        {
            this.Id = id;
            this.ModalitaPagamento = modalitaPagamento;
            this.EstremiPagamento = estremiPagamento;
        }
    }
}
