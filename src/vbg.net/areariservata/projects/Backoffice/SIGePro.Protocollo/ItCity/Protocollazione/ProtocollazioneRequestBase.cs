using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloItCityService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using static Init.SIGePro.Protocollo.ProtocolloEnumerators.ProtocolloEnum;

namespace Init.SIGePro.Protocollo.ItCity.Protocollazione
{
    public class ProtocollazioneRequestBase
    {
        protected IDatiProtocollo DatiProtocollo;
        private readonly int _idFascicolo;
        private readonly int? _numeroSottoFascicolo;

        public ProtocollazioneRequestBase(IDatiProtocollo datiProtocollo, int idFascicolo, int? numeroSottoFascicolo)
        {
            this.DatiProtocollo = datiProtocollo;
            this._idFascicolo = idFascicolo;
            this._numeroSottoFascicolo = numeroSottoFascicolo;
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
                return this.DatiProtocollo.ProtoIn.Allegati.Select((x, idx) => new Allegato { Descrizione = x.Descrizione, NomeFile = x.NOMEFILE, Tipo = 0, Primario = (idx == 0) }).ToArray();
            }
        }

        public virtual IEnumerable<byte[]> AllegatiBuffer
        {
            get
            {
                return this.DatiProtocollo.ProtoIn.Allegati.Select(x => x.OGGETTO);
            }
        }
    }
}
