using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Attivita;
using Init.SIGePro.DatiDinamici.Contesti;

namespace Init.SIGePro.DatiDinamici
{
	public class ModelloDinamicoAttivita : ModelloDinamicoBase
	{
		public int IdAttivita{get;private set;}

		public ModelloDinamicoAttivita(ModelloDinamicoLoader loader, int idModello, int idAttivita, int indice, bool readOnly)
			:
			this(loader, idModello, idAttivita, indice, readOnly, null)
		{
		}

		public ModelloDinamicoAttivita(ModelloDinamicoLoader loader, int idModello, int idAttivita, int indice, bool readOnly, int? idStorico)
			: base(  idModello, indice, readOnly, idStorico, loader)
		{
			IdAttivita = idAttivita;

			base.Inizializza();
		}

		protected override void SetContesto()
		{
			var templateReader = new ResourceScriptTemplateReader();

			Contesto = new ContestoModelloDinamico(Token, ContestoModelloEnum.Attivita, LeggiAttivita(), templateReader);
		}

		private IClasseContestoModelloDinamico LeggiAttivita()
		{
			return DataAccessProvider.GetAttivitaManager().LeggiAttivita(IdComune, IdAttivita);
		}

		protected override void PopolaValoriCampi()
		{
			IIAttivitaDyn2DatiManager mgr = DataAccessProvider.GetAttivitaDyn2DatiManager();

			foreach(var riga in this.Righe)
			{
				for (int j = 0; j < riga.NumeroColonne; j++)
				{
                    if (riga[j] == null)
                        continue;

					List<IIAttivitaDyn2Dati> valoriUtente = mgr.GetValoriCampo(IdComune, IdAttivita, riga[j].Id, IndiceModello);

					CampoDinamicoBase cbd = riga[j];

					foreach (var valoreUtente in valoriUtente)
					{
						int indice				  = valoreUtente.IndiceMolteplicita.GetValueOrDefault(0);
						string valore			  = valoreUtente.Valore;
						string valoreDecodificato = valoreUtente.Valoredecodificato;

						while (cbd.ListaValori.Count < (indice + 1))
							cbd.ListaValori.IncrementaMolteplicita();

						cbd.ListaValori[indice].Valore = valore;
						cbd.ListaValori[indice].ValoreDecodificato = valoreDecodificato;
					}

					// Per compatibilità con i vecchi dati dinamici
					if (cbd.ListaValori.Count == 0)
						cbd.ListaValori.IncrementaMolteplicita();

				}
			}
		}

		protected override void PopolaValoriCampiStorico()
		{
			var mgrStorico = DataAccessProvider.GetAttivitaDyn2DatiStoricoManager();

			foreach (var riga in this.Righe)
			{
				for (int j = 0; j < riga.NumeroColonne; j++)
				{
					if (riga[j] == null) continue;

					int codiceCampo = riga[j].Id;

					List<IIAttivitaDyn2DatiStorico> valoriUtenteStorico = mgrStorico.GetValoriCampo(IdComune, IdAttivita, codiceCampo, IndiceModello, IdVersioneStorico.Value);

					CampoDinamicoBase cbd = riga[j];

					foreach (var valoreUtente in valoriUtenteStorico)
					{
						int indice = valoreUtente.IndiceMolteplicita.GetValueOrDefault(0);
						string valore = valoreUtente.Valore;
						string valoreDecodificato = valoreUtente.Valore;

						while (cbd.ListaValori.Count < (indice + 1))
							cbd.ListaValori.IncrementaMolteplicita();

						cbd.ListaValori[indice].Valore = valore;
						cbd.ListaValori[indice].ValoreDecodificato = valoreDecodificato;
					}

					// Per compatibilità con i vecchi dati dinamici
					if (cbd.ListaValori.Count == 0)
						cbd.ListaValori.IncrementaMolteplicita();
				}
			}
		}

		protected override void  SalvaValoriCampi(bool salvaStorico, IEnumerable<CampoDinamico> campiDaSalvare)
		{
 			DataAccessProvider.GetAttivitaDyn2DatiManager().SalvaValoriCampi( salvaStorico , this,campiDaSalvare );
		}

		protected override void EliminaValoriCampi(IEnumerable<CampoDinamico> campiDaEliminare)
		{
 			DataAccessProvider.GetAttivitaDyn2DatiManager().EliminaValoriCampi( this,campiDaEliminare );
		}
		
	}
}
