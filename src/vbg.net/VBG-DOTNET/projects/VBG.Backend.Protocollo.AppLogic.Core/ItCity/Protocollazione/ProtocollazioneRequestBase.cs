using ItCityService;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Protocollazione
{
    public class ProtocollazioneRequestBase
    {
        protected ParametriRegoleInfo _parametri;
        protected IDatiProtocollo DatiProtocollo;
        private readonly int _idFascicolo;
        private readonly int? _numeroSottoFascicolo;

        public ProtocollazioneRequestBase(ParametriRegoleInfo parametri, IDatiProtocollo datiProtocollo, int idFascicolo, int? numeroSottoFascicolo)
        {
            this.DatiProtocollo = datiProtocollo;
            this._idFascicolo = idFascicolo;
            this._numeroSottoFascicolo = numeroSottoFascicolo;
            this._parametri = parametri;
        }
        
        public virtual CoordinateArchivio Coordinate
        {
            get
            {
                var retVal = new CoordinateArchivio
                {
                    OggettoDocumento = this.DatiProtocollo.ProtoIn.Oggetto,
                    FlagFascicolazione = FlagFascicolazione.none,
                    IdIndice = Convert.ToInt32(this.DatiProtocollo.ProtoIn.Classifica)
                };

                if (this._idFascicolo != 0)
                {
                    retVal.IdFascicolo = this._idFascicolo;
                }

                if (this._numeroSottoFascicolo.GetValueOrDefault(0) > 0 ) {
                    retVal.NumeroSottofascicolo = _numeroSottoFascicolo.Value;
                }
                return retVal;
            }
        }

        public virtual Allegato[] Allegati
        {
            get
            {
                if (this._parametri.Noallegati == "1")
                    return null;

                return this.DatiProtocollo.ProtoIn.RecuperaAllegati().Select((x, idx) => new Allegato { Descrizione = x.Descrizione, NomeFile = x.NOMEFILE, Tipo = 0, Primario = (idx == 0) }).ToArray();
            }
        }

        public virtual IEnumerable<byte[]> AllegatiBuffer
        {
            get
            {
                return this.DatiProtocollo.ProtoIn.RecuperaAllegati().Select(x => x.OGGETTO);
            }
        }

        public Indirizzo GetIndirizzoAmministrazione(IAnagraficaAmministrazione amministrazione)
        {
            if (amministrazione == null || String.IsNullOrEmpty(amministrazione.Indirizzo) || amministrazione.ComuneResidenza == null)
            {
                return null;
            }

            return new Indirizzo
            {
                Descrizione = amministrazione.Indirizzo,
                Comune = amministrazione.ComuneResidenza.DenominazioneComune,
                Provincia = amministrazione.ComuneResidenza.SiglaProvincia
            };
        }
    }
}
