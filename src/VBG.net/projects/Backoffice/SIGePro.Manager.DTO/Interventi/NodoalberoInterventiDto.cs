using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Utils;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager.DTO.Interventi
{
	public class NodoAlberoInterventiDto : ClassTree<InterventoDto>
	{
		public NodoAlberoInterventiDto()
		{
			this.Elemento = new InterventoDto();
		}

		public static NodoAlberoInterventiDto CreaAlberoConSchedeDinamicheDaListaNodi(ClassTree<AlberoProc> strutturaAlberoOrdinata, Dictionary<int, List<SchedaDinamicaDto>> schedeDinamiche)
		{
			var root = new NodoAlberoInterventiDto();

			root.Elemento = PopolaIntervento(strutturaAlberoOrdinata.Elemento, schedeDinamiche);

			if (strutturaAlberoOrdinata.NodiFiglio.Count > 0)
				root.NodiFiglio = PopolaNodiFiglio(strutturaAlberoOrdinata.NodiFiglio, schedeDinamiche);

			return root;
		}

		private static List<ClassTree<InterventoDto>> PopolaNodiFiglio(List<ClassTree<AlberoProc>> nodiFiglio, Dictionary<int, List<SchedaDinamicaDto>> schedeDinamiche)
		{
			var rVal = new List<ClassTree<InterventoDto>>();

			for (int i = 0; i < nodiFiglio.Count; i++)
			{
				var nf = nodiFiglio[i];

				var el = new ClassTree<InterventoDto>(PopolaIntervento(nf.Elemento, schedeDinamiche));
				el.NodiFiglio = PopolaNodiFiglio(nf.NodiFiglio, schedeDinamiche);

				rVal.Add(el);
			}

			return rVal;
		}

		private static InterventoDto PopolaIntervento(AlberoProc sc, Dictionary<int, List<SchedaDinamicaDto>> schedeDinamiche)
		{
			var rVal = new InterventoDto
			{
				Codice = sc.Sc_id.Value,
				Descrizione = sc.SC_DESCRIZIONE,
				Note = sc.SC_NOTE,
				HaNote = !String.IsNullOrEmpty(sc.SC_NOTE)
			};

			if (schedeDinamiche.ContainsKey(rVal.Codice))
			{
				var listaSchedeDinamiche = schedeDinamiche[rVal.Codice];
				rVal.SchedeDinamiche = listaSchedeDinamiche;
			}

			return rVal;
		}

		public static NodoAlberoInterventiDto CreaAlberoSenzaSchedeDinamicheDaListaNodi(ClassTree<AlberoProc> listaNodiAlbero)
		{
			return CreaAlberoConSchedeDinamicheDaListaNodi(listaNodiAlbero, new Dictionary<int, List<SchedaDinamicaDto>>());
		}


	}
}
