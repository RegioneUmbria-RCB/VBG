using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Civilia.Protocollazione
{
    public class ProtocolloInfo
    {
        public IDatiProtocollo Metadati { get; private set; }
        public VerticalizzazioniParametriServiceWrapper ParametriRegola { get; private set; }
        public TipoProtocolloEnum Flusso { get; private set; } 
        public IEnumerable<IAnagraficaAmministrazione> Anagrafiche { get; private set; }
        public string ProtocollatoDa { get; private set; }

        public ProtocolloInfo(VerticalizzazioniParametriServiceWrapper vert, IDatiProtocollo metadati, List<IAnagraficaAmministrazione> anagrafiche, string protocollatoDa)
        {
            this.ParametriRegola = vert;
            this.Metadati = metadati;
            this.Flusso = TipoProtocolloEnum.INGRESSO;

            if (metadati.Flusso == ProtocolloConstants.COD_PARTENZA)
            {
                this.Flusso = TipoProtocolloEnum.USCITA;
            }

            if (metadati.Flusso == ProtocolloConstants.COD_INTERNO)
            {
                this.Flusso = TipoProtocolloEnum.INTERNO;
            }

            this.Anagrafiche = anagrafiche;
            this.ProtocollatoDa = protocollatoDa;
        }
    }
}
