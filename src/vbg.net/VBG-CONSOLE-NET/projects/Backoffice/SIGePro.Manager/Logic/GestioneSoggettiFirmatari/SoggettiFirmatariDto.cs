using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari
{
    [Serializable]
    public class SoggettiFirmatariDto
    {
        public class TipoSoggettoFirmatarioDto
        {
            public int Id { get; set; }
            public string Descrizione { get; set; }

            public TipoSoggettoFirmatarioDto()
            {
            }

            public TipoSoggettoFirmatarioDto(int id, string descrizione)
            {
                this.Id = id;
                this.Descrizione = descrizione;
            }
        }

        [XmlElement(Order = 0)]
        public int CodiceDocumento { get; set; }

        [XmlElement(Order = 1)]
        public List<TipoSoggettoFirmatarioDto> TipiSoggetto { get; set; }

        public SoggettiFirmatariDto()
        {
            this.TipiSoggetto = new List<TipoSoggettoFirmatarioDto>();
        }

        public void AggiungiTipoSoggetto(int id, string descrizione)
        {
            this.TipiSoggetto.Add(new TipoSoggettoFirmatarioDto(id, descrizione));
        }
    }

    [Serializable]
    public class ConfigurazioneSoggettiFirmatariDto
    {
        [XmlElement(Order=0)]
        public SoggettiFirmatariDto[] SoggettiAllegatiIntervento { get; set; }

        [XmlElement(Order = 1)]
        public SoggettiFirmatariDto[] SoggettiAllegatiEndo { get; set; }
    }
}
