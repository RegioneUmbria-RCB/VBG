using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Storico
{
    public class ParametriStoriciAdapter
    {
        protected VerticalizzazioneProtocolloStorico _base;
        protected string _token;

        public ParametriStoriciAdapter(string token, string idComuneAlias, string software, string codiceComune, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._base = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloStorico>(idComuneAlias, software, codiceComune);
            this._token = token;
        }

        public ParametriStorici Adatta()
        {

            if (!this._base.Attiva)
                throw new Exception($"La verticalizzazione {this._base.NomeVerticalizzazione} non è attiva");
            try
            {
                var par = new ParametriStorici();
                par.CodiceRegistro = this._base.CodiceRegistro;
                par.CodiceUfficio = this._base.CodiceUfficio;
                par.URLLeggiAllegati = this._base.URLLeggiAllegati;
                par.URLLeggiProtocollo = this._base.URLLeggiProtocollo;
                par.Utente = this._base.Utente;
                par.Token = this._token;

                return par;
            }
            catch (Exception ex)
            {
                throw new Exception($"RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE {this._base.NomeVerticalizzazione} FALLITO, {ex.Message}", ex);
            }
        }
    }
}
