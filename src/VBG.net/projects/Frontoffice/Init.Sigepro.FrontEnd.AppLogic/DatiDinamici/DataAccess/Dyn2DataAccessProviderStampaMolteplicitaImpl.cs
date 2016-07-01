using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.SIGePro.DatiDinamici.Utils;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
	public class Dyn2DataAccessProviderStampaMolteplicitaImpl : Dyn2DataAccessProviderImpl
	{
		public int IndiceMolteplicita { get; set; }

		public Dyn2DataAccessProviderStampaMolteplicitaImpl( DomandaOnline domanda, int idModello, int indiceMolteplicita) :
			base(domanda, idModello)
		{
			IndiceMolteplicita = indiceMolteplicita;
		}

		public override SerializableDictionary<int, List<IIstanzeDyn2Dati>> GetValoriCampoDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceCampo)
		{
			var tmpRVal = base.GetValoriCampoDaIdModello(idComune, codiceIstanza, idModello, indiceCampo);
			
			foreach (var key in tmpRVal.Keys)
			{
				var rigaTrovata = false;
				var righeDaRimuovere = new List<IIstanzeDyn2Dati>();

				var el = tmpRVal[key];

				for (int i = 0; i < el.Count; i++)
				{
					var campo = el[i];

					if (campo.IndiceMolteplicita == IndiceMolteplicita)
					{
						campo.IndiceMolteplicita = 0;
						rigaTrovata = true;
						continue;
					}

					righeDaRimuovere.Add(campo);
				}

				foreach (var rigaDaEliminare in righeDaRimuovere)
					el.Remove(rigaDaEliminare);

				if (!rigaTrovata)
				{
					var dati = new IstanzeDyn2Dati
					{
						FkD2cId = key,
						Codiceistanza = -1,
						Idcomune = String.Empty,
						Indice = 0,
						IndiceMolteplicita = 0,
						Valore = String.Empty,
						Valoredecodificato = String.Empty
					};

					el.Add(dati);
				}
			}


			return tmpRVal;
		}
	}
}
