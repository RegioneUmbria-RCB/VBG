using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure
{
	public class ProcureReadInterface : IProcureReadInterface
	{
		PresentazioneIstanzaDbV2 _database;
		List<ProcuraDomandaOnline> _procure = new List<ProcuraDomandaOnline>();

		public ProcureReadInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;

			PreparaProcure();
		}

		private void PreparaProcure()
		{
			foreach (var procuraRow in this._database.Procure.Cast<PresentazioneIstanzaDbV2.ProcureRow>())
			{
				var anagraficaProcurato = this._database.ANAGRAFE
														.Where( 
															x => x.TIPOANAGRAFE == "F" && 
															x.CODICEFISCALE.ToUpperInvariant() == procuraRow.CodiceAnagrafe.ToUpperInvariant()
														)
														.FirstOrDefault();

                if (anagraficaProcurato == null)
                {
                    continue;
                }

				ProcuraDomandaOnline.SoggettoProcura procurato = ProcuraDomandaOnline.SoggettoProcura.FromAnagrafeRow(anagraficaProcurato);
				ProcuraDomandaOnline.SoggettoProcura procuratore = null;

				if (!String.IsNullOrEmpty(procuraRow.CodiceProcuratore))
				{
					var anagraficaProcuratore = this._database.ANAGRAFE
													.Where(
														x => x.TIPOANAGRAFE == "F" &&
														x.CODICEFISCALE.ToUpperInvariant() == procuraRow.CodiceProcuratore.ToUpperInvariant()
													)
													.FirstOrDefault();

					procuratore = ProcuraDomandaOnline.SoggettoProcura.FromAnagrafeRow(anagraficaProcuratore);
				}

				ProcuraDomandaOnline.AllegatoProcura allegato = null;
                ProcuraDomandaOnline.AllegatoProcura allegatoDocIdentita = null;

                if (!procuraRow.IsIdAllegatoNull() && procuraRow.IdAllegato != -1)
				{
					var allegatiRow = this._database.Allegati.FindById(procuraRow.IdAllegato);

					allegato = new ProcuraDomandaOnline.AllegatoProcura(procuratore.CodiceFiscale,allegatiRow.CodiceOggetto, allegatiRow.NomeFile, allegatiRow.FirmatoDigitalmente);
				}

                if (!procuraRow.IsIdDocumentoIdentitaNull() && procuraRow.IdDocumentoIdentita != -1)
                {
                    var allegatiRow = this._database.Allegati.FindById(procuraRow.IdDocumentoIdentita);

                    allegatoDocIdentita = new ProcuraDomandaOnline.AllegatoProcura(procuratore.CodiceFiscale, allegatiRow.CodiceOggetto, allegatiRow.NomeFile, allegatiRow.FirmatoDigitalmente);
                }

				this._procure.Add( new ProcuraDomandaOnline( procurato , procuratore , allegato, allegatoDocIdentita));
			}
		}

		#region IGestioneProcureReadInterface Members

		public IEnumerable<ProcuraDomandaOnline> Procure
		{
			get { return this._procure; }
		}


		public bool IsUtenteProcuratore(string codiceFiscaleUtente)
		{
			var specification = new UtentePuoSottoscrivereSpecification(this);

			return specification.IsSatisfiedBy(codiceFiscaleUtente);
		}


		public string GetCodiceFiscaleDelProcuratoreDi(string codiceFiscaleProcurato)
		{
			var procura = this.Procure
								.Where(x => x.Procurato.CodiceFiscale.ToUpperInvariant() == codiceFiscaleProcurato.ToUpperInvariant())
								.FirstOrDefault();

			if (procura == null || procura.Procuratore == null)
				return string.Empty;

			return procura.Procuratore.CodiceFiscale;
		}

		#endregion
	}
}
