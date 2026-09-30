using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.Autenticazione;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.Fascicolazione
{
    public class FascicolazioneConfiguration
    {
        public VerticalizzazioniConfiguration Vert { get; private set; }
        public GestioneDocumentaleService DocWrapper { get; private set; }
        public FascicolazioneService FascWrapper { get; private set; }
        public Fascicolo DatiFascicolo { get; private set; }
        public AmbitoProtocollazioneEnum TipoAmbito { get; private set; }
        public IIstanzaDaProtocollare DatiIstanza { get; private set; }
        public IMovimentoDaProtocollare DatiMovimento { get; private set; }
        public IEnumerable<Movimenti> MovimentiProtocollati { get; private set; }
        public IAuthenticationService Auth { get; private set; }

        public FascicolazioneConfiguration(IAuthenticationService auth, VerticalizzazioniConfiguration vert, GestioneDocumentaleService docWrapper, FascicolazioneService fascWrapper, Fascicolo datiFascicolo, AmbitoProtocollazioneEnum tipoAmbito, IIstanzaDaProtocollare istanza, IMovimentoDaProtocollare movimento, IEnumerable<Movimenti> movimentiProtocollati)
        {
            this.Auth = auth;
            this.Vert = vert;
            this.DocWrapper = docWrapper;
            this.FascWrapper = fascWrapper;
            this.DatiFascicolo = datiFascicolo;
            this.TipoAmbito = tipoAmbito;
            this.DatiIstanza = istanza;
            this.DatiMovimento = movimento;
            this.MovimentiProtocollati = movimentiProtocollati;
        }
    }
}
