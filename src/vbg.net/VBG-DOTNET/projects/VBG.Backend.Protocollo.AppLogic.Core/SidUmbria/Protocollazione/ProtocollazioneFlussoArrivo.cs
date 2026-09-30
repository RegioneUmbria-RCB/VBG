using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public class ProtocollazioneFlussoArrivo : IProtocollazioneFlusso
    {
        private readonly IDatiProtocollo _datiProto;

        public ProtocollazioneFlussoArrivo(IDatiProtocollo datiProto)
        {
            this._datiProto = datiProto;
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO_DOCAREA; }
        }

        public corrispondente[] GetCorrispondenti()
        {
            if (this._datiProto.AnagraficheProtocollo.Count > 0)
                return new corrispondente[] { this._datiProto.AnagraficheProtocollo.First().ToCorrispondenteAnagraficaArrivo() };
            else if (this._datiProto.AmministrazioniProtocollo.Count > 0)
                return new corrispondente[] { this._datiProto.AmministrazioniEsterne.First().ToCorrispondenteAmministrazioneArrivo() };
            else
                throw new Exception("NON SONO PRESENTI MITTENTI O MITTENTI NON VALIDI");
        }
    }
}
