using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;

namespace Sigepro.net.Istanze.SIT.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class GeoInGetPermessiEditing : BaseHandler, IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";

            try
            {
                string software = HttpContext.Current.Request.QueryString["Software"];
                string modalita = HttpContext.Current.Request.QueryString["Modalita"];
                string result = GetPermessoEditing(modalita, software).ToString();

                context.Response.Write(result);
            }
            catch (Exception ex)
            {
                context.Response.StatusCode = 500;
                context.Response.Write("Error: " + ex.ToString());
            }
        }

        private bool GetPermessoEditing(string modalita, string software)
        {
			if(modalita == "I")
				return VerificaPermessoOperatore(software);

			return false;

			// ERA:
			////Verifico se la pagina GeoIn.aspx viene aperta da attività o istanza
			//bool bPermesso = false;
			//switch (modalita)
			//{
			//    case "A":
			//        break;
			//    case "I":
			//        bPermesso = 
			//        break;
			//}

			//return bPermesso;
        }

		/// <summary>
		/// Verifico se l'operatore è dotato del ruolo che permette l'editing
		/// </summary>
		/// <param name="software"></param>
		/// <returns></returns>
        private bool VerificaPermessoOperatore(string software)
        {
            var resp = new ResponsabiliMgr(Database).GetById(IdComune, AuthenticationInfo.CodiceResponsabile.Value);

            //Se l'operatore è di sola lettura o disabilitato non può eseguire editing
            if ((resp.READONLY == "1") || (resp.DISABILITATO == "1"))
                return false;
			            
            var datiVerticalizzazione = new VerticalizzazioneSitQuaestioflorenzia(IdComuneAlias, software);
			
			var codiceRuoloChePermetteEditing = datiVerticalizzazione.Attiva ? datiVerticalizzazione.CodRuoloEditing : string.Empty;
                        			
			var list = new ResponsabiliRuoliMgr(Database).GetList(new ResponsabiliRuoli
			{
				IDCOMUNE = IdComune,
				CODICERESPONSABILE = AuthenticationInfo.CodiceResponsabile.ToString()
			});

            foreach (var elem in list)
            {
                if (elem.IDRUOLO == codiceRuoloChePermetteEditing)
                    return true;
            }

            return false;

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
