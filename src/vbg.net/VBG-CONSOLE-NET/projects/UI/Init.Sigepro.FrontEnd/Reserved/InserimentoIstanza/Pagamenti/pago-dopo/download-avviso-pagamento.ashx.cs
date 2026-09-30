using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.Pagamenti;
using Ninject;
using Ninject.Web;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using static Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.AvvisoDiPagamento;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.pago_dopo
{
    /// <summary>
    /// Summary description for download_avviso_pagamento
    /// </summary>
    public class download_avviso_pagamento : HttpHandlerBase
    {
        [Inject]
        protected IPagamentiNodoPagamentiService _nodoPagamentiService { get; set; }

        protected override void DoProcessRequest(HttpContext context)
        {
            var idDomanda = new QsIdDomandaOnline(context.Request.QueryString);
            var uidOnere = new QsUidOnere(context.Request.QueryString);

            var avvisoPagamento = this._nodoPagamentiService.GetAvvisoPagamento(idDomanda.Value, uidOnere.Value);

            context.Response.AddHeader("x-stato-avviso-pagamento", avvisoPagamento.Stato.ToString());

            avvisoPagamento.File.WriteTo(context.Response);
        }

        public override bool IsReusable => false;
    }
}