using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga
{
    public class ParametriRegoleInfoAdapter
    {
        protected VerticalizzazioneProtocolloAuriga _vert;
        protected VerticalizzazioneProtocolloAttivo _base;
        protected string _token;

        public ParametriRegoleInfoAdapter(string token, string idComuneAlias, string software, string codiceComune, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._vert = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAuriga>(idComuneAlias, software, codiceComune);
            this._base = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(idComuneAlias, software, codiceComune);
            this._token = token;
        }

        public ParametriRegoleInfo Adatta()
        {

            if (!this._vert.Attiva)
                throw new Exception($"La verticalizzazione {this._vert.NomeVerticalizzazione} non è attiva");
            try
            {
                var par = new ParametriRegoleInfo
                {
                    Versione = this.VersioneDaVerticalizzazione(this._vert.Versione),
                    CodiceApplicazione = this._vert.CodApplicazione,
                    IstanzaApplicazione = this._vert.IstanzaApplicazione,
                    Password = this._vert.Password,
                    ProxyUrl = this._vert.ProxyUrl,
                    Token = this._token,
                    Url = this._vert.Url,
                    Username = this._vert.Username,
                    CaratteriDaEliminare = this._base.ListaCaratteriDaEliminare,
                    CodiceComune = this._vert.CodiceComune,
                    Software = this._vert.Software,
                };

                return par;
            }
            catch (Exception ex)
            {
                throw new Exception($"RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE {this._vert.NomeVerticalizzazione} FALLITO, {ex.Message}", ex);
            }
        }

        private Versione VersioneDaVerticalizzazione(string versione)
        {
            if (string.IsNullOrEmpty(versione))
            {
                return Versione.V1;
            }

            if (Enum.TryParse(versione, out Versione v))
            {
                return v;
            }
            else
            {
                throw new Exception($"La versione {versione} indicata nelle regole non è supportata!");
            }
        }
    }
}
