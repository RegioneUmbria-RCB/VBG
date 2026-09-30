using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity
{
    public class ParametriRegoleInfoAdapter
    {

        protected VerticalizzazioneProtocolloItCity _vert;
        protected VerticalizzazioneProtocolloAttivo _base;
        protected string _token;

        public ParametriRegoleInfoAdapter(string token, string idComuneAlias, string software, string codiceComune, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._vert = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloItCity>(idComuneAlias, software, codiceComune);
            this._base = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(idComuneAlias, software, codiceComune);
            this._token = token;
        }

        public ParametriRegoleInfo Adatta()
        {

            if (!this._vert.Attiva)
                throw new Exception($"La verticalizzazione {this._vert.NomeVerticalizzazione} non è attiva");
            try
            {
                var par = new ParametriRegoleInfo();
                par.Password = this._vert.Password;
                par.Sigla = this._vert.Sigla;
                par.Url = this._vert.Url;
                par.Username = this._vert.Username;
                par.Noallegati = this._base.Noallegati;

                return par;
            }
            catch (Exception ex)
            {
                throw new Exception($"RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE {this._vert.NomeVerticalizzazione} FALLITO, {ex.Message}", ex);
            }
        }
    }
}
