using Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Web;
using System.Web.Script.Services;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.IntegrazioneSit
{
    /// <summary>
    /// Summary description for IntegrazioneSitService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [System.Web.Script.Services.ScriptService]
    public class IntegrazioneSitService : System.Web.Services.WebService
    {
        [Inject]
        protected ISitService _sitService { get; set; }


        public IntegrazioneSitService()
        {
            FoKernelContainer.Inject(this);
        }


        [WebMethod]
        [ScriptMethod]
        public object ValidaCampo(string nomeCampo, string codiceStradario, string civico, string esponente, string tipoCatasto, string sezione, string foglio, string particella, string sub, string accessoTipo, string fabbricato)
        {
            if (String.IsNullOrEmpty(codiceStradario))
            {
                return new
                {
                    error = true,
                    errorDescription = "E'necessario selezionare una via"
                };
            }

            var codiceComune = HttpContext.Current.Request.QueryString["codiceComune"];

            var parametriRicerca = new ParametriRicercaLocalizzazione
            {
                CodViario = codiceStradario,
                Civico = civico,
                Esponente = esponente,
                TipoCatasto = tipoCatasto,
                Sezione = sezione,
                Foglio = foglio,
                Particella = particella,
                Sub = sub,
                AccessoTipo = accessoTipo,
                CodiceComune = codiceComune,
                Fabbricato = fabbricato
            };


            var r = this._sitService.ValidaCampo(nomeCampo, parametriRicerca);

            if (r == null)
                return new
                {
                    error = true,
                    errorDescription = "Il valore immesso non è stato trovato negli stradari comunali"
                };

            return new
            {
                cap = r.CAP,
                circoscrizione = r.Circoscrizione,
                civico = r.Civico,
                codCivico = r.CodCivico,
                codVia = r.CodVia,
                colore = r.Colore,
                descrizioneVia = r.DescrizioneVia,
                esponente = r.Esponente,
                esponenteInterno = r.EsponenteInterno,
                fabbricato = r.Fabbricato,
                foglio = r.Foglio,
                frazione = r.Frazione,
                interno = r.Interno,
                km = r.Km,
                particella = r.Particella,
                piano = r.Piano,
                quartiere = r.Quartiere,
                scala = r.Scala,
                sezione = r.Sezione,
                sub = r.Sub,
                tipoCatasto = r.TipoCatasto,
                ui = r.Ui,
                zona = r.Zona,
                accessoTipo = r.AccessoTipo,
                accessoNumero = r.AccessoNumero,
                accessoDescrizione = r.AccessoDescrizione
            };
        }


        [WebMethod]
        [ScriptMethod]
        public object GetListaCampi(string nomeCampo, string codiceStradario, string civico, string esponente, string tipoCatasto, string sezione, string foglio, string particella, string sub, string fabbricato)
        {
            if (String.IsNullOrEmpty(codiceStradario))
            {
                return new
                {
                    error = true,
                    errorDescription = "E'necessario selezionare una via",
                    items = new string[0]
                };
            }

            var codiceComune = HttpContext.Current.Request.QueryString["codiceComune"];

            var parametriRicerca = new ParametriRicercaLocalizzazione
            {
                CodViario = codiceStradario,
                Civico = civico,
                Esponente = esponente,
                TipoCatasto = tipoCatasto,
                Sezione = sezione,
                Foglio = foglio,
                Particella = particella,
                Sub = sub,
                CodiceComune = codiceComune,
                Fabbricato = fabbricato
            };

            var listaCampi = this._sitService.RicercaValori(nomeCampo, parametriRicerca);

            if (listaCampi == null || listaCampi.Length == 0)
                return new
                {
                    error = true,
                    errorDescription = "Impossibile reperire la lista corrispondente al campo \"" + nomeCampo + "\"",
                    items = new string[0]
                };

            return new
            {
                items = listaCampi
            };
        }
    }
}
