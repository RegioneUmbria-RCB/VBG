//using Init.SIGePro.Data;
//using Init.SIGePro.Manager;
//using SIGePro.Manager.Verticalizzazioni;
//using SIGePro.Manager.VerticalizzazioniSIT;
//using System;
//using System.Web;
//using System.Web.Services;

//namespace Sigepro.net.Istanze.SIT.Handlers
//{
//    /// <summary>
//    /// Summary description for $codebehindclassname$
//    /// </summary>
//    [WebService(Namespace = "http://tempuri.org/")]
//    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
//    public class GeoInGetPermessiEditing : BaseHandler, IHttpHandler
//    {

//        protected override void DoProcessRequest(HttpContext context)
//        {
//            context.Response.ContentType = "text/plain";

//            try
//            {
//                string software = HttpContext.Current.Request.QueryString["Software"];
//                string modalita = HttpContext.Current.Request.QueryString["Modalita"];
//                string result = this.GetPermessoEditing(modalita, software).ToString();

//                context.Response.Write(result);
//            }
//            catch (Exception ex)
//            {
//                context.Response.StatusCode = 500;
//                context.Response.Write("Error: " + ex.ToString());
//            }
//        }

//        private bool GetPermessoEditing(string modalita, string software)
//        {
//            if (modalita == "I")
//                return this.VerificaPermessoOperatore(software);

//            return false;

//            // ERA:
//            ////Verifico se la pagina GeoIn.aspx viene aperta da attività o istanza
//            //bool bPermesso = false;
//            //switch (modalita)
//            //{
//            //    case "A":
//            //        break;
//            //    case "I":
//            //        bPermesso = 
//            //        break;
//            //}

//            //return bPermesso;
//        }

//        /// <summary>
//        /// Verifico se l'operatore è dotato del ruolo che permette l'editing
//        /// </summary>
//        /// <param name="software"></param>
//        /// <returns></returns>
//        private bool VerificaPermessoOperatore(string software)
//        {
//            var resp = new ResponsabiliMgr(this.Database).GetById(this.IdComune, this.AuthenticationInfo.CodiceResponsabile.Value);

//            //Se l'operatore è di sola lettura o disabilitato non può eseguire editing
//            if ((resp.READONLY == "1") || (resp.DISABILITATO == "1"))
//                return false;

//            var datiVerticalizzazione = new VerticalizzazioneSitQuaestioflorenzia(this.IdComuneAlias, software);

//            var codiceRuoloChePermetteEditing = datiVerticalizzazione.Attiva ? datiVerticalizzazione.CodRuoloEditing : string.Empty;

//            var list = new ResponsabiliRuoliMgr(this.Database).GetList(new ResponsabiliRuoli
//            {
//                IDCOMUNE = this.IdComune,
//                CODICERESPONSABILE = this.AuthenticationInfo.CodiceResponsabile.ToString()
//            });

//            foreach (var elem in list)
//            {
//                if (elem.IDRUOLO == codiceRuoloChePermetteEditing)
//                    return true;
//            }

//            return false;

//        }

//    }
//}
