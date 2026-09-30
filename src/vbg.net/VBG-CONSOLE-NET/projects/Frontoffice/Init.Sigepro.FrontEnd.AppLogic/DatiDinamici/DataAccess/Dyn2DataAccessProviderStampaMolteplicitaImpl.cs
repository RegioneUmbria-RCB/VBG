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

		public override SerializableDictionary<int, List<IIstanzeDyn2Dati>> GetValoriCampoDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceScheda)
		{
            var strutturaModello = base.GetList(idComune, idModello);
            var idCampiNonMultipli = strutturaModello.Where(x => x.FkD2cId.HasValue && x.FlgMultiplo.GetValueOrDefault(0) == 0)
                                                             .Select(x => x.FkD2cId.Value);

            // Recupero tutti i valori dei dati dinamici della scheda
			var tmpRVal = base.GetValoriCampoDaIdModello(idComune, codiceIstanza, idModello, indiceScheda);
			
            // Dato che devo stampare un documento sposto all'indice 0 tutti i campi che si trovano all'indice 
            // che viene stampato
			foreach (var key in tmpRVal.Keys)
			{
				var valoreCampoTrovato = false;
				var valoriCampiDaRimuovere = new List<IIstanzeDyn2Dati>();

				var el = tmpRVal[key];

				for (int i = 0; i < el.Count; i++)
				{
					var campo = el[i];

                    // Se il campo non è multiplo, devo prendere il valore all'indice 0 indipendentemente dall'indice che sto stampando
                    // il controllo campo.IndiceMolteplicita == 0 è probabilmente ridondante
                    if (idCampiNonMultipli.Contains(key) && campo.IndiceMolteplicita == 0)   
                    {
                        valoreCampoTrovato = true;
                        continue;
                    }

					if (campo.IndiceMolteplicita == IndiceMolteplicita)
					{
						campo.IndiceMolteplicita = 0;
						valoreCampoTrovato = true;
						continue;
					}

					valoriCampiDaRimuovere.Add(campo);
				}

                // Rimuovo i valori dei campi che si trovano ad un altro indice di molteplicità
				foreach (var valoreCampo in valoriCampiDaRimuovere)
					el.Remove(valoreCampo);


                // Se il campo non
				if (!valoreCampoTrovato)
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
