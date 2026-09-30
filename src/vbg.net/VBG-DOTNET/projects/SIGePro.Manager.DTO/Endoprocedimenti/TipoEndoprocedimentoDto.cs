using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
	[DataContract]
	public class TipoEndoprocedimentoDto
	{
		[DataMember]
		public int Codice { get; set; }

		[DataMember]
		public string Descrizione { get; set; }

		[DataMember]
		public int Ordine { get; set; }

		[DataMember]
		public List<EndoprocedimentoDto> Endoprocedimenti { get; set; }

		public TipoEndoprocedimentoDto():base()
		{
			Endoprocedimenti = new List<EndoprocedimentoDto>();
		}


		// Utilizzato da Area riservata!
		internal TipoEndoprocedimentoDto FiltraDaListaIdEndo(IEnumerable<int> listaIdSelezionati)
        {
			var tipo = new TipoEndoprocedimentoDto
			{
				Codice = this.Codice,
				Descrizione = this.Descrizione,
				Ordine = this.Ordine
			};

			tipo.Endoprocedimenti = this.Endoprocedimenti.Where(endo => listaIdSelezionati.Contains(endo.Codice)).ToList();

			return tipo;
		}
    }
}
