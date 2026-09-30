using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.SmtpMail;
using Ninject;
using PersonalLib2.Data;
using Sigepro.net.WebServices.WsSIGePro;
using System.ComponentModel;
using System.Web.Services;

namespace SIGePro.Net.WebServices.WSSIGeProSmtpMail
{
    /// <summary>
    /// Summary description for SmtpMailSender
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [ToolboxItem(false)]
    public class SmtpMailSender : SigeproWebService
    {
        [Inject]
        public SmtpSender _smtpSender { get; set; }

        [WebMethod]
        public void Send(string token, string software, SIGeProMailMessage message)
        {
            // Autenticazione
            AuthenticationInfo authInfo = this.CheckToken(token);

            using (DataBase database = authInfo.CreateDatabase())
            {
                this._smtpSender.InviaEmail(database, authInfo.IdComune, software, message);
            }
        }

    }

}