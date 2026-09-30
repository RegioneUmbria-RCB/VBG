using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari
{
    [DataContract]
    public class SoggettiFirmatariDto
    {
        [DataMember(Order = 0)]
        public int CodiceDocumento { get; set; }

        [DataMember(Order = 1)]
        public List<TipoSoggettoFirmatarioDto> TipiSoggetto { get; set; }

        public SoggettiFirmatariDto()
        {
            this.TipiSoggetto = new List<TipoSoggettoFirmatarioDto>();
        }

        public void AggiungiTipoSoggetto(int id, string descrizione)
        {
            this.TipiSoggetto.Add(new TipoSoggettoFirmatarioDto(id, descrizione));
        }

        public void AggiungiTipoSoggetto(TipiSoggetto tipoSoggetto)
        {
            var id = Convert.ToInt32(tipoSoggetto.CODICETIPOSOGGETTO);
            var descr = tipoSoggetto.TIPOSOGGETTO;

            this.AggiungiTipoSoggetto(id, descr);
        }
    }

    [DataContract]
    public class TipoSoggettoFirmatarioDto
    {
        [DataMember(Order = 0)]
        public int Id { get; set; }
        [DataMember(Order = 1)]
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

    [DataContract]
    public class ConfigurazioneSoggettiFirmatariDto
    {
        [DataMember(Order = 0)]
        public SoggettiFirmatariDto[] SoggettiAllegatiIntervento { get; set; }

        [DataMember(Order = 1)]
        public SoggettiFirmatariDto[] SoggettiAllegatiEndo { get; set; }
    }

    [DataContract]
    public class VerificaSoggettiFirmatariRiepilogoDomandaDto
    {
        [DataMember(Order = 0)]
        public List<TipoSoggettoFirmatarioDto> SoggettiFirmatari { get; set; }

        [DataMember(Order = 1)]
        public bool VerificaFirmaUtenteLoggato { get; set; }
    }
}
