using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    public class DatiMovimentoDaEffettuareDto
    {
        [XmlElement(Order = 0)]
        public int CodiceMovimento { get; set; }

        [XmlElement(Order = 1)]
        public string TipoMovimento { get; set; }

        [XmlElement(Order = 2)]
        public string DescInventario { get; set; }

        [XmlElement(Order = 3)]
        public string Amministrazione { get; set; }

        [XmlElement(Order = 4)]
        public string Esito { get; set; }

        [XmlElement(Order = 5)]
        public string Parere { get; set; }

        [XmlElement(Order = 6)]
        public string Note { get; set; }

        [XmlElement(Order = 7)]
        public List<MovimentiAllegati> Allegati { get; set; }

        [XmlElement(Order = 8)]
        public string Descrizione { get; set; }

        [XmlElement(Order = 9)]
        public DateTime? DataMovimento { get; set; }

        [XmlElement(Order = 10)]
        public int CodiceIstanza { get; set; }

        [XmlElement(Order = 11)]
        public string NumeroIstanza { get; set; }

        [XmlElement(Order = 12)]
        public bool VisualizzaParere { get; set; }

        [XmlElement(Order = 13)]
        public bool VisualizzaEsito { get; set; }

        [XmlElement(Order = 14)]
        public bool Pubblica { get; set; }

        [XmlElement(Order = 15)]
        public string NumeroProtocollo { get; set; }

        [XmlElement(Order = 16)]
        public DateTime? DataProtocollo { get; set; }

        [XmlElement(Order = 17)]
        public string IdComune { get; set; }

        [XmlElement(Order = 18)]
        public string NumeroProtocolloIstanza { get; set; }

        [XmlElement(Order = 19)]
        public DateTime? DataProtocolloIstanza { get; set; }

        [XmlElement(Order = 20)]
        public DateTime DataIstanza { get; set; }

        [XmlElement(Order = 21)]
        public List<SchedaDinamicaMovimentoDto> SchedeDinamiche { get; set; }

        [XmlElement(Order = 22)]
        public string CodiceInventario { get; set; }

        [XmlElement(Order = 23)]
        public bool PubblicaSchede { get; set; }

        [XmlElement(Order = 24)]
        public string Software { get; internal set; }

        public DatiMovimentoDaEffettuareDto()
        {
            this.Allegati = new List<MovimentiAllegati>();
            this.SchedeDinamiche = new List<SchedaDinamicaMovimentoDto>();
        }


    }
}
