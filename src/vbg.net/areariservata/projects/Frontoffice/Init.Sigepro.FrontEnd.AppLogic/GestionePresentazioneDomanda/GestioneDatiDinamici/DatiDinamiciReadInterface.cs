using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters.DatiDinamiciAdapterHelpers;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.SIGePro.DatiDinamici.VisibilitaCampi;
using Init.Utils;
using System.IO;
using System.Xml.Serialization;
using System.Xml;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici
{
	public class DatiDinamiciReadInterface: IDatiDinamiciReadInterface
	{
		public static class Constants
		{
			public const string CampiNonVisibiliKeyFmtString = "campi:non:visibili:{0}";
			public const string TipoModelloEndo = "E";
			public const string TipoModelloIntervento = "I";

		}

		PresentazioneIstanzaDbV2 _database;
		IEnumerable<ValoreDatoDinamico> _datiDinamici;
		IEnumerable<ModelloDinamico> _modelli;
		IRiepiloghiSchedeDinamicheReadInterface _riepiloghiReadInterface;
		IEnumerable<ModelloDinamicoOrdinato> _modelliIntervento;
		IEnumerable<ModelloDinamico> _modelliEndoprocedimenti;
		ModelloDinamico _modelloCittadinoExtracomunitario;

		public DatiDinamiciReadInterface(PresentazioneIstanzaDbV2 database, IRiepiloghiSchedeDinamicheReadInterface riepiloghiReadInterface)
		{
			this._database = database;
			this._riepiloghiReadInterface = riepiloghiReadInterface;

			PreparaDatiDinamici();
			PreparaModelli();
			PreparaModelliIntervento();
			PreparaModelliEndo();
			PreparaModellOCittadinoExtracomunitario();
		}

		private void PreparaModellOCittadinoExtracomunitario()
		{
			var idmodello = this._database
								.ModelliInterventiEndo
								.Where( x => x.TipoRecord == RiepiloghiSchedeDinamicheConstants.TipoRiepilogo.CittadinoExtracomunitario)
								.Select(x => x.CodiceModello)
								.FirstOrDefault();

			this._modelloCittadinoExtracomunitario = this._modelli.Where(x => x.IdModello == idmodello).FirstOrDefault();
		}

		private void PreparaModelliEndo()
		{
			var listaidModelli = this._database.ModelliInterventiEndo
												.Where(x => x.TipoRecord == Constants.TipoModelloEndo)
												.Select(x => x.CodiceModello);

			this._modelliEndoprocedimenti = this._modelli.Where(x => listaidModelli.Contains(x.IdModello));
		}

		private void PreparaModelliIntervento()
		{
			var listaModelli = this._database.ModelliInterventiEndo
												.Where(x => x.TipoRecord == Constants.TipoModelloIntervento)
												.Select(x => new
												{
													id = x.CodiceModello,
													ordine = x.Ordine
												});

			this._modelliIntervento = listaModelli.Select(x => new ModelloDinamicoOrdinato(x.ordine, GetModelloById(x.id)));
		}

		private void PreparaModelli()
		{
			this._modelli = this._database.Dyn2Modelli.Select(x => ModelloDinamico.FromDyn2ModelliRow(x));
		}

		private void PreparaDatiDinamici()
		{
			this._datiDinamici = this._database.Dyn2Dati.Select(x => ValoreDatoDinamico.FromDyn2DatiRow(x));
		}


        #region IDatiDinamiciReadInterface Members
        /// <summary>
        /// Restituisce l'ordine assoluto di un modello all'intrno della lista dei modelli della domanda.
        /// Se il modello è presente tra i modelli dell'intervento allora restituisce l'ordine della lista dell'intervento
        /// altrimenti restituisce l'ordine all'interno della lista dei modelli dell'endo
        /// </summary>
        /// <param name="it">Modello di cui si vuole trovare l'ordine</param>
        /// <returns>ordine dell'elemento</returns>
        public ModelloDinamicoOrdinato OrdineAssoluto(ModelloDinamico it)
		{
			var idModello = it.IdModello;

			var modello = this._database
										.ModelliInterventiEndo
										.Where(x => x.TipoRecord == Constants.TipoModelloIntervento && x.CodiceModello == idModello)
										.FirstOrDefault();

			if (modello != null)
			{
				return new ModelloDinamicoOrdinato(modello.Ordine, it);
			}

			modello = this._database
							.ModelliInterventiEndo
							.Where(x => x.TipoRecord == Constants.TipoModelloEndo && x.CodiceModello == idModello)
							.FirstOrDefault();

			if (modello != null)
			{
				return new ModelloDinamicoOrdinato(modello.Ordine, it);
			}

			return new ModelloDinamicoOrdinato(int.MaxValue, it);
		}

		public IEnumerable<ValoreDatoDinamico> DatiDinamici
		{
			get { return this._datiDinamici; }
		}



		public IEnumerable<ModelloDinamico> Modelli
		{
			get { return this._modelli; }
		}


		public bool EsistonoModelliObbligatoriNonCompilati()
		{
			return this._modelli.Where( x => !x.Facoltativo && !x.Compilato ).Count() > 0;
		}

		public IEnumerable<string> VerificaUploadModelliRichiesti()
		{
			var errori = new List<string>();

			// è possibile uscire dalla pagina se tutti i riepiloghi dei dati dinamici che richiedono la firma 
			// hanno un file allegato e firmato digitalmente

			var rows = this._riepiloghiReadInterface.Riepiloghi.Where( x => x.AllegatoDellUtente == null );

			foreach (var r in rows)
			{
				var modello = this._modelli.Where( x => x.IdModello == r.IdModello).FirstOrDefault();

				if (modello.TipoFirma != ModelloDinamico.TipoFirmaEnum.Nessuna)
					errori.Add("Scaricare, firmare e allegare il file relativo alla dichiarazione/scheda \"" + r.Descrizione + "\"");
			}

			rows = this._riepiloghiReadInterface.Riepiloghi.Where(x => x.AllegatoDellUtente != null && !x.AllegatoDellUtente.FirmatoDigitalmente);

			foreach (var r in rows)
			{
				var modello = this._modelli.Where(x => x.IdModello == r.IdModello).FirstOrDefault();

				if (modello.TipoFirma != ModelloDinamico.TipoFirmaEnum.Nessuna)
					errori.Add("E'obbligatorio firmare digitalmente il file relativo alla dichiarazione/scheda \"" + r.Descrizione + "\"");
			}

			return errori;
		}

		public IEnumerable<ModelloDinamicoOrdinato> ModelliIntervento { get { return this._modelliIntervento; } }
		public IEnumerable<ModelloDinamico> ModelliEndoprocedimenti { get { return this._modelliEndoprocedimenti; } }

		public IEnumerable<ModelloDinamicoOrdinato> GetModelliEndo(int codiceEndoprocedimento)
		{
			var listaModelli = this._database.ModelliInterventiEndo
									.Where(x => x.TipoRecord == "E" && x.Codice == codiceEndoprocedimento)
									.Select(x => new 
									{
										id = x.CodiceModello,
										ordine = x.Ordine
									});

			return listaModelli.Select(x => new ModelloDinamicoOrdinato(x.ordine, GetModelloById(x.id)));
		}

		public ModelloDinamico ModelloCittadinoExtracomunitario { get { return this._modelloCittadinoExtracomunitario; } }


		public ModelloDinamico GetModelloById(int idModello)
		{
			return this._modelli.Where(x => x.IdModello == idModello).FirstOrDefault();
		}
		#endregion


		public IEnumerable<int> GetIndiciSchede(IStrutturaModello strutturaModello)
		{
			var indiciCampi = strutturaModello.Campi.SelectMany(campo =>
			{
				return this.DatiDinamici
							.Where(x => x.IdCampo == campo.Id)
							.Select(x => x.IndiceScheda);
			});

			return indiciCampi.DefaultIfEmpty().Distinct();
		}


		public IEnumerable<IdValoreCampo> GetCampiNonVisibili(int idModello)
		{
			var key = String.Format(Constants.CampiNonVisibiliKeyFmtString, idModello);
			var item = this._database.DatiExtra.FindByChiave(key);
			
			if (item == null)
				return Enumerable.Empty<IdValoreCampo>();

			var xs = new XmlSerializer(typeof(List<IdValoreCampo>));
			return (List<IdValoreCampo>)xs.Deserialize(new StringReader( item.Valore));
		}
	}
}
