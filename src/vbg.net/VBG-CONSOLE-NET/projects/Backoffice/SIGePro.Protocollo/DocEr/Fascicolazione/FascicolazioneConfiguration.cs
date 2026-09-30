using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.DocEr.Autenticazione;

namespace Init.SIGePro.Protocollo.DocEr.Fascicolazione
{
    public class FascicolazioneConfiguration
    {
        public VerticalizzazioniConfiguration Vert { get; private set; }
        public GestioneDocumentaleService DocWrapper { get; private set; }
        public FascicolazioneService FascWrapper { get; private set; }
        public Fascicolo DatiFascicolo { get; private set; }
        public ProtocolloEnum.AmbitoProtocollazioneEnum TipoAmbito { get; private set; }
        public Istanze DatiIstanza { get; private set; }
        public Movimenti DatiMovimento { get; private set; }
        public IEnumerable<Movimenti> MovimentiProtocollati { get; private set; }
        public IAuthenticationService Auth { get; private set; }

        public FascicolazioneConfiguration(IAuthenticationService auth, VerticalizzazioniConfiguration vert, GestioneDocumentaleService docWrapper, FascicolazioneService fascWrapper, Fascicolo datiFascicolo, ProtocolloEnum.AmbitoProtocollazioneEnum tipoAmbito, Istanze istanza, Movimenti movimento, IEnumerable<Movimenti> movimentiProtocollati)
        {
            Auth = auth;
            Vert = vert;
            DocWrapper = docWrapper;
            FascWrapper = fascWrapper;
            DatiFascicolo = datiFascicolo;
            TipoAmbito = tipoAmbito;
            DatiIstanza = istanza;
            DatiMovimento = movimento;
            MovimentiProtocollati = movimentiProtocollati;
        }
    }
}
