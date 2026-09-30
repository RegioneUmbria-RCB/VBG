using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
    [DataContract]
    public class DatiMovimentoDaEffettuareDto
    {
        [DataMember]
        public int CodiceMovimento { get; set; }

        [DataMember]
        public string TipoMovimento { get; set; }

        [DataMember]
        public string DescInventario { get; set; }

        [DataMember]
        public string Amministrazione { get; set; }

        [DataMember]
        public string Esito { get; set; }

        [DataMember]
        public string Parere { get; set; }

        [DataMember]
        public string Note { get; set; }

        [DataMember]
        public List<MovimentiAllegatiDto> Allegati { get; set; }

        [DataMember]
        public string Descrizione { get; set; }

        [DataMember]
        public DateTime? DataMovimento { get; set; }

        [DataMember]
        public int CodiceIstanza { get; set; }

        [DataMember]
        public string NumeroIstanza { get; set; }

        [DataMember]
        public bool VisualizzaParere { get; set; }

        [DataMember]
        public bool VisualizzaEsito { get; set; }

        [DataMember]
        public bool Pubblica { get; set; }

        [DataMember]
        public string NumeroProtocollo { get; set; }

        [DataMember]
        public DateTime? DataProtocollo { get; set; }

        [DataMember]
        public string IdComune { get; set; }

        [DataMember]
        public string NumeroProtocolloIstanza { get; set; }

        [DataMember]
        public DateTime? DataProtocolloIstanza { get; set; }

        [DataMember]
        public DateTime DataIstanza { get; set; }

        [DataMember]
        public List<SchedaDinamicaMovimentoDto> SchedeDinamiche { get; set; }

        [DataMember]
        public string CodiceInventario { get; set; }

        [DataMember]
        public bool PubblicaSchede { get; set; }

        [DataMember]
        public string Software { get; set; }

        public DatiMovimentoDaEffettuareDto()
        {
            this.Allegati = new List<MovimentiAllegatiDto>();
            this.SchedeDinamiche = new List<SchedaDinamicaMovimentoDto>();
        }


    }
}
