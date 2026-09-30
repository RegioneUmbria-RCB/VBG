using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters.DatiDinamiciAdapterHelpers
{
	internal static class ValoreDatoDinamicoExtensions
	{
		public static ElementoValoreCampoDinamicoType ToElementoValoreCampoDinamico(this ValoreDatoDinamico val)
		{
			if (val == null)
				return null;

			return new ElementoValoreCampoDinamicoType
			{
				indice = val.IndiceScheda,
				indiceSpecified = true,
				indiceMolteplicita = val.IndiceMolteplicita,
				indiceMolteplicitaSpecified = true,
				codice = val.Valore,
				descrizione = val.ValoreDecodificato
			};
		}
	}

	internal class SchedeStcAdapter
	{
		IStrutturaModello _struttura;
		IDatiDinamiciReadInterface _datiDinamiciReadInterface;

		public SchedeStcAdapter(IStrutturaModello struttura, IDatiDinamiciReadInterface datiDinamiciReadInterface)
		{
			this._struttura = struttura;
			this._datiDinamiciReadInterface = datiDinamiciReadInterface;
		}

		public SchedaType CreaSchedaStc()
		{
			return new SchedaType
			{
				codice = this._struttura.IdModello.ToString(),
				nome = this._struttura.CodiceScheda,
				descrizione = this._struttura.Descrizione,
				campi = GetCampi().ToArray()
			};
			/*
			foreach (var campo in this._struttura.Campi.Where(c => c.Id >= 0))
			{
				var valori = this._datiDinamiciReadInterface.DatiDinamici.Where(x => x.IdCampo == campo.Id);

				return new CampoSchedaType
				{
					codice = campo.Id.ToString(),
					descrizione = String.IsNullOrEmpty(campo.Etichetta) ? String.Empty : campo.Etichetta,
					campoDinamico = new CampoDinamicoType
					{
						valoreUtente = new ValoreCampoDinamicoType
						{
							nome = campo.NomeCampo,
							valore = valori.GetValoriStc.ToArray()
						}

					}
				};

				foreach (var valore in valori)
				{

				}

				var valori = this._valoriReader.GetCampoStc(campo);

				var campoStc = new CampoSchedaType
				{
					codice = campo.Id.ToString(),
					descrizione = String.IsNullOrEmpty(campo.Etichetta) ? String.Empty : campo.Etichetta,
					campoDinamico = new CampoDinamicoType
					{
						valoreUtente = new ValoreCampoDinamicoType
						{
							nome = campo.NomeCampo,
							valore = valori.GetValoriStc.ToArray()
						}

					}
				};

			}*/
		}

		private IEnumerable<CampoSchedaType> GetCampi()
		{
			foreach (var campo in this._struttura.Campi.Where(c => c.Id >= 0))
			{
				yield return new CampoSchedaType
				{
					codice = campo.Id.ToString(),
					descrizione = String.IsNullOrEmpty(campo.Etichetta) ? String.Empty : campo.Etichetta,
					campoDinamico = new CampoDinamicoType
					{
						valoreUtente = new ValoreCampoDinamicoType
						{
							nome = campo.NomeCampo,
							valore = GetValoriStc(campo).ToArray()
						}
					}
				};
			}
		}

		private IEnumerable<ElementoValoreCampoDinamicoType> GetValoriStc(ICampoDinamico campo)
		{
			return this._datiDinamiciReadInterface
						.DatiDinamici
						.Where(x => x.IdCampo == campo.Id)
						.Select(x => x.ToElementoValoreCampoDinamico());
		}

	}
}
