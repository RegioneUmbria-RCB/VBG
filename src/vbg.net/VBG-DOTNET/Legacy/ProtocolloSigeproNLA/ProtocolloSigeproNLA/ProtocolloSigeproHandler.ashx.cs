using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;
using ProtocolloSigeproNLA.Classes;
using ProtocolloSigeproNLA.Properties;

namespace ProtocolloSigeproNLA
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class ProtocolloSigeproHandler : IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {

            string defError = "Attenzione, si è verificato un errore non previsto durante l'inserimento dei dati dell'istanza, i dati del protocollo sono stati comunque inseriti correttamente\r\nDETTAGLIO ERRORE\r\n";
            string returnValue = String.Empty;
            string statusText = String.Empty;
            int statusCode = 200;

            try
            {
                string idComune = context.Request.QueryString["idcomune"];
                string software = context.Request.QueryString["software"];
                string token = context.Request.QueryString["token"];
                int mittente = int.Parse(context.Request.QueryString["mittente"]);
                int idProtocollo = int.Parse(context.Request.QueryString["idprot"]);
                string numeroProtocollo = context.Request.QueryString["numprot"];
                DateTime dataProtocollo = DateTime.ParseExact(context.Request.QueryString["dataprot"], "yyyyMMdd", null);
                int intervento = int.Parse(context.Request.QueryString["intervento"]);
                int tipoProcedura = int.Parse(context.Request.QueryString["tipoprocedura"]);
                string tipoMovimentoAvvio = context.Request.QueryString["tipomovavvio"];
                int? codiceStradario = String.IsNullOrEmpty(context.Request.QueryString["codicestradario"]) ? (int?)null : int.Parse(context.Request.QueryString["codicestradario"]);
                string civico = context.Request.QueryString["civico"];
                string colore = context.Request.QueryString["colore"];
                int? inQualitaDi = String.IsNullOrEmpty(context.Request.QueryString["qualitadi"]) ? (int?)null : int.Parse(context.Request.QueryString["qualitadi"]);
                int? perContoDi = String.IsNullOrEmpty(context.Request.QueryString["percontodi"]) ? (int?)null : int.Parse(context.Request.QueryString["percontodi"]);
                string esponente = context.Request.QueryString["esponsente"];
                string piano = context.Request.QueryString["piano"];
                string codiceComune = context.Request.QueryString["codiceComune"];

                var lii = new LogicaInvioIstanza(token, idComune, software);
                lii.CodiceAnagrafe = mittente;
                lii.IdProtocollo = idProtocollo;
                lii.NumeroProtocollo = numeroProtocollo;
                lii.DataProtocollo = dataProtocollo;
                lii.Intervento = intervento;
                lii.TipoProcedura = tipoProcedura;
                lii.TipoMovimentoAvvio = tipoMovimentoAvvio;
                lii.CodiceStradario = codiceStradario;
                lii.Civico = civico;
                lii.Colore = colore;
                lii.InQualitaDi = inQualitaDi;
                lii.PerContoDi = perContoDi;
                lii.Esponente = esponente;
                lii.Piano = piano;
                lii.CodiceComune = codiceComune;

                lii.InviaIstanza();

            }
            catch (Exception ex)
            {
                returnValue = ex.ToString();
                statusText = ex.Source;
                statusCode = 500;
            }
            finally
            {
                context.Response.StatusCode = statusCode;
                context.Response.StatusDescription = statusText;
                context.Response.Write(defError + returnValue);
            }
        }

        public bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}
