using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.Segnatura;
using Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.MittentiDestinatari;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.Allegati;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo
{
    public class LeggiProtocolloAdapter
    {
        Segnatura.Segnatura _response;

        public LeggiProtocolloAdapter(Segnatura.Segnatura response)
        {
            _response = response;
        }

        public DatiProtocolloLetto Adatta(OggettiMgr oggetti)
        {
            var mittentiDestinatari = MittentiDestinatariFactory.Create(_response);

            var data = DateTime.Parse(_response.Intestazione.Identificatore.DataRegistrazione);

            var adapterAllegati = new AllegatiResponseAdapter(_response.Descrizione);
            
            return new DatiProtocolloLetto
            {
                NumeroProtocollo = _response.Intestazione.Identificatore.NumeroRegistrazione.ToString(),
                AnnoProtocollo = data.ToString("yyyy"),
                Oggetto = _response.Intestazione.Oggetto,
                Origine = GetFlusso(),
                InCaricoA = mittentiDestinatari.InCaricoA,
                InCaricoA_Descrizione = mittentiDestinatari.InCaricoADescrizione,
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario(),
                DataProtocollo = data.ToString("dd/MM/yyyy"),
                Allegati = adapterAllegati.Adatta(oggetti)
            };
        }

        

        private string GetFlusso()
        {
            if (_response.Intestazione.Identificatore.Flusso == ProtocolloConstants.COD_ARRIVO_DOCAREA)
                return ProtocolloConstants.COD_ARRIVO;
            else if (_response.Intestazione.Identificatore.Flusso == ProtocolloConstants.COD_INTERNO_DOCAREA)
                return ProtocolloConstants.COD_INTERNO;
            else if (_response.Intestazione.Identificatore.Flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
                return ProtocolloConstants.COD_PARTENZA;
            else
                return ProtocolloConstants.NON_DEFINITO;
        }
    }
}
