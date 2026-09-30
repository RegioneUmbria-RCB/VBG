using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
    [DataContract]
    public class FamigliaEndoprocedimentoDto
    {
        [DataMember]
        public int Codice { get; set; }

        [DataMember]
        public string Descrizione { get; set; }

        [DataMember]
        public int Ordine { get; set; }

        [DataMember]
        public List<TipoEndoprocedimentoDto> TipiEndoprocedimenti { get; set; }


        public FamigliaEndoprocedimentoDto() : base()
        {
            TipiEndoprocedimenti = new List<TipoEndoprocedimentoDto>();
        }

        // Utilizzato da Area riservata!
        [IgnoreDataMember]
        public IEnumerable<EndoprocedimentoDto> ListaEndo => this.TipiEndoprocedimenti.SelectMany(tipo => tipo.Endoprocedimenti);

        // Utilizzato da Area riservata!
        internal FamigliaEndoprocedimentoDto FiltraDaListaIdEndo(IEnumerable<int> listaIdSelezionati)
        {
            var famiglia = new FamigliaEndoprocedimentoDto
            {
                Codice = this.Codice,
                Descrizione = this.Descrizione,
                Ordine = this.Ordine
            };

            famiglia.TipiEndoprocedimenti = this.TipiEndoprocedimenti
                                                .Select(tipo => tipo.FiltraDaListaIdEndo(listaIdSelezionati))
                                                .Where(tipo => tipo.Endoprocedimenti.Any())
                                                .ToList();

            return famiglia;
        }
    }
}
