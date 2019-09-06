using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Civilia.Protocollazione
{
    public enum TipoProtocolloEnum
    {
        INGRESSO,
        USCITA,
        INTERNO
    }

    public class PraticaWS
    {
        public string Oggetto { get; set; }
        public string Note { get; set; }
        public bool IsFromModificaAllegato { get; set; }
        public bool IsFromModificaCorrispondente { get; set; }
        public string MotivoModifica { get; set; }
        public string TipoProtocollo { get; set; }
        public DateTime? DataConsegnaDocumento { get; set; }
        public IEnumerable<IndividuoProtocolloWS> IdCorrispondentiList { get; set; }
        public IEnumerable<AllegatoWS> AllegatiList { get; set; }
        public string TipoContenuto { get; set; }
        public string ProtocollatoDa { get; set; }
        public string CodiceLivelloOrganigramma { get; set; }
        //public string IdCodiceAOO { get; set; }
        public DateTime? DataRegistrazione { get; set; }
        public int? NumeroProtocollo { get; set; }

        public PraticaWS()
        {

        }
    }
}