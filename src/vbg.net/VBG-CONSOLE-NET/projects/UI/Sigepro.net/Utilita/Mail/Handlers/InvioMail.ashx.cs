using System;
using System.Web;
using SIGePro.Net.WebServices.WSSIGeProSmtpMail;
using Init.SIGePro.Manager.Logic.SmtpMail;

namespace Sigepro.net.Utilita.Mail.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    public class InvioMail : BaseHandler, IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            try
            {
                string tipoMail = context.Request.QueryString["TipoMail"];
                if( string.IsNullOrEmpty(tipoMail) )
                    throw new Exception("Specificare il tipo della mail in Querystring tramite il parametro \"TipoMail\"!!!");

                SIGeProMailMessage smm = null;
				string software = "TT";

                switch( tipoMail.ToUpper() )
                {
                    case "SCHEDULERSORTEGGI":
                    {

                        if (string.IsNullOrEmpty(context.Request.QueryString["IdSorteggio"]))
                            throw new Exception("Specificare il codice del sorteggio in Querystring tramite il parametro \"IdSorteggio\"!!!");

                        if (string.IsNullOrEmpty(context.Request.QueryString["IdSchedulazione"]))
                            throw new Exception("Specificare il codice della schedulazione in Querystring tramite il parametro \"IdSchedulazione\"!!!");

                        int idSorteggio = Convert.ToInt32(context.Request.QueryString["IdSorteggio"]);
                        int idSchedulazione = Convert.ToInt32(context.Request.QueryString["IdSchedulazione"]);

                        SchedulerSorteggiMessage ssm = new SchedulerSorteggiMessage( Database, IdComune, idSorteggio, idSchedulazione );
                        smm = ssm.GetMessage();
						software = ssm.Software;

                        break;
                    }
                    default:
                    {
                        throw new Exception("Specificare un TipoMail valido in Querystring!!!");
                    }
                }

                SmtpMailSender SMTPms = new SmtpMailSender();
                SMTPms.Send(Token, software, smm);

                context.Response.Write("MAIL INVIATA CORRETTAMENTE!");
            }
            catch (Exception ex)
            {
                context.Response.StatusCode = 500;
                context.Response.Write(ex.ToString());
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
