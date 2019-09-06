using Init.SIGePro.Protocollo.Logs;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Net;
using System.Text;
using System.Web;

namespace Init.SIGePro.Protocollo.Pal.Autenticazione
{
    public class AutenticazioneServiceWrapper : BaseProtocolloServiceWrapper
    {
        enum _nomiParametri { codute, password, codente, codaoo };
        const string _creaToken = "creaToken";

        public AutenticazioneServiceWrapper(ProtocolloLogs logs, string baseUrlWs) : base(logs, baseUrlWs)
        {

        }

        public string GetToken(string username, string password, string codiceEnte, string codiceAoo)
        {
            try
            {
                var uri = new Uri(new Uri(_baseUrlWs), _creaToken);

                using (var client = new WebClient())
                {
                    var metadati = new List<KeyValuePair<string, string>>();

                    var passwordEncoded = HttpContext.Current.Server.UrlEncode(Convert.ToBase64String(Encoding.UTF8.GetBytes(password)));

                    var parametri = new NameValueCollection();

                    parametri.Add(_nomiParametri.codute.ToString(), username);
                    parametri.Add(_nomiParametri.password.ToString(), passwordEncoded);
                    parametri.Add(_nomiParametri.codente.ToString(), codiceEnte);
                    parametri.Add(_nomiParametri.codaoo.ToString(), codiceAoo);

                    _logs.InfoFormat("CHIAMATA AD AUTENTICAZIONE, {0} = {1}, {2} = {3}, {4} = {5}, {6} = {7}, PASSWORD NON CODIFICATA: {8}", _nomiParametri.codute.ToString(), username, _nomiParametri.password.ToString(), passwordEncoded, _nomiParametri.codente.ToString(), codiceEnte, _nomiParametri.codaoo.ToString(), codiceAoo, password);
                    var buffer = client.UploadValues(uri, _wsMethodType.POST.ToString(), parametri);
                    var token = Encoding.UTF8.GetString(buffer);

                    _logs.InfoFormat("AUTENTICAZIONE AVVENUTA CON SUCCESSO, TOKEN RESTITUITO: {0}", token);

                    return token;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE L'AUTENTICAZIONE, UTENTE: {0}, ERRORE: {1}", username, ex.Message), ex);
            }
        }

    }
}
