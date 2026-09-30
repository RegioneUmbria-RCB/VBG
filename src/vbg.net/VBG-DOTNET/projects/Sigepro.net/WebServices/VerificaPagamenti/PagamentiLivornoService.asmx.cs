using Init.SIGePro.Manager.Logic.Livorno.VerificaPagamenti;
using Ninject;
using Sigepro.net.WebServices.WsSIGePro;
using SIGePro.Manager.VerticalizzazioniBase;
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
        [Inject]
        public IVerticalizzazioniFactory _verticalizzazioniFactory { get; set; }

        public class EsitoVerificaPagamentolivorno
        {
            public bool Esito { get; set; }
            public float Importo { get; set; }
            public string DescrizioneErrore { get; set; }
        }


        [WebMethod]
        public EsitoVerificaPagamentolivorno VerificaCodicePagamento(string token, string software, string strCodiceIstanza, string codicePagamento)
        {
            var authInfo = this.CheckToken(token);
            var service = new VerificaPagamentiService(this._verticalizzazioniFactory, authInfo, software);

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
