using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
    [DataContract]
    public class EndoprocedimentiAreaRiservataDto
    {
        [DataMember]
        [XmlElement(Order = 1)]
        public List<FamigliaEndoprocedimentoDto> Principali { get; set; } = new List<FamigliaEndoprocedimentoDto>();
        
        [DataMember]
        [XmlElement(Order = 2)]
        public List<FamigliaEndoprocedimentoDto> Richiesti { get; set; } = new List<FamigliaEndoprocedimentoDto>();

        [DataMember]
        [XmlElement(Order = 3)]
        public List<FamigliaEndoprocedimentoDto> Ricorrenti { get; set; } = new List<FamigliaEndoprocedimentoDto>();

        [DataMember]
        [XmlElement(Order = 4)]
        public List<FamigliaEndoprocedimentoDto> Altri { get; set; } = new List<FamigliaEndoprocedimentoDto>();


        // Usato da AR, restituisce la lista degli id degli endo attivati di default (principali + attivati)
        public IEnumerable<int> GetEndoSelezionatiDiDefault()
        {
            var principali = this.Principali.SelectMany(famiglia => famiglia.TipiEndoprocedimenti.SelectMany(tipo => tipo.Endoprocedimenti.Select(endo => endo.Codice)));
            var richiesti = this.Richiesti.SelectMany(famiglia => famiglia.TipiEndoprocedimenti.SelectMany(tipo => tipo.Endoprocedimenti.Select(endo => endo.Codice)));

            return principali.Union(richiesti);
        } 


        public IEnumerable<EndoprocedimentoDto> FiltraEndoByListaId(IEnumerable<int> listaIdEndo)
        {
            var totaleEndo = this.Principali.SelectMany(fam => fam.ListaEndo)
                                .Union(this.Richiesti.SelectMany(fam => fam.ListaEndo))
                                .Union(this.Ricorrenti.SelectMany(fam => fam.ListaEndo))
                                .Union(this.Altri.SelectMany(fam => fam.ListaEndo));

            //var codiciEndo = totaleEndo.Select(endo => endo.Codice);
            var subEndo = totaleEndo.Where(endo => endo.SubEndo.Count > 0).SelectMany(endo => endo.SubEndo.SelectMany(sub => sub.ListaEndo));

            return totaleEndo.Union(subEndo).Where(endo => listaIdEndo.Contains(endo.Codice));
        }

        public IEnumerable<FamigliaEndoprocedimentoDto> GetFamiglieEndoFacoltativiDaListaIdEndo(IEnumerable<int> listaIdSelezionati)
        {
            return this.Altri.Select(famiglia => famiglia.FiltraDaListaIdEndo(listaIdSelezionati))
                        .Where(famiglia  => famiglia.TipiEndoprocedimenti.Any());
        }
    }
}
