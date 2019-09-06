using Init.SIGePro.Manager.Logic.Livorno.VerificaPagamenti;
using Sigepro.net.WebServices.WsSIGePro;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Sigepro.net.WebServices.VerificaPagamenti
{
    /// <summary>
    /// Summary description for PagamentiLivornoService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class PagamentiLivornoService : SigeproWebService
    {
        public class EsitoVerificaPagamentolivorno
        {
            public bool Esito { get; set; }
            public float Importo { get; set; }
            public string DescrizioneErrore { get; set; }
        }


        [WebMethod]
        public EsitoVerificaPagamentolivorno VerificaCodicePagamento(string token, string software, string strCodiceIstanza, string codicePagamento)
        {
            var authInfo = CheckToken(token);
            var service = new VerificaPagamentiService(authInfo, software);

            var esito = service.VerificaPagamento(strCodiceIstanza, codicePagamento);

            return new EsitoVerificaPagamentolivorno
            {
                Esito = esito.Esito,
                DescrizioneErrore = esito.DescrizioneErrore,
                Importo = esito.Importo
            };
        }
    }
}
