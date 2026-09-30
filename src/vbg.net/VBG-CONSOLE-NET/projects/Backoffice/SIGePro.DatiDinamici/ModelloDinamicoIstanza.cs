using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.Utils;
using Init.SIGePro.DatiDinamici.Contesti;

namespace Init.SIGePro.DatiDinamici
{
	public class ModelloDinamicoIstanza : ModelloDinamicoBase
	{
		public int IdIstanza { get;private set; }

		public ModelloDinamicoIstanza(ModelloDinamicoLoader loader, int idModello, int idIstanza, int indice, bool readOnly)
			:
			this( loader, idModello, idIstanza, indice, readOnly, null)
		{
		}

		public ModelloDinamicoIstanza(ModelloDinamicoLoader loader, int idModello, int idIstanza, int indice, bool readOnly, int? idStorico)
			: base(idModello, indice, readOnly, idStorico, loader)
		{
			IdIstanza = idIstanza;

			using (var cp = new CodeProfiler("ModelloDinamicoIstanza.Inizializza"))
			{
				base.Inizializza();
			}
		}

		protected override void SetContesto()
		{

			var templateReader = new ResourceScriptTemplateReader();
			Contesto = new ContestoModelloDinamico(Token, ContestoModelloEnum.Istanza, LeggiIstanza(), templateReader);
		}

		protected override void PopolaValoriCampi()
		{
			var dyn2DatiMgr = DataAccessProvider.GetIstanzeDyn2DatiManager();
			var listaValoriNelDb = dyn2DatiMgr.GetValoriCampoDaIdModello(IdComune, IdIstanza, IdModello, IndiceModello);

			foreach (var riga in this.Righe)
			{
				for (int j = 0; j < riga.NumeroColonne; j++)
				{
					CampoDinamicoBase cdb = riga[j];

					if (cdb == null) continue;
					
					int codiceCampo = cdb.Id;

					if (codiceCampo < 0) continue;

					if (listaValoriNelDb.ContainsKey(codiceCampo))
					{
						var valoriUtenteAttuale = listaValoriNelDb[codiceCampo];

						for (int idxCampo = 0; idxCampo < valoriUtenteAttuale.Count; idxCampo++)
						{
							int indice = valoriUtenteAttuale[idxCampo].IndiceMolteplicita.GetValueOrDefault(0);
							string valore = valoriUtenteAttuale[idxCampo].Valore;
							string valoreDecodificato = valoriUtenteAttuale[idxCampo].Valoredecodificato;

							while (cdb.ListaValori.Count < (indice + 1))
								cdb.ListaValori.IncrementaMolteplicita();

							cdb.ListaValori[indice].Valore = valore;
							cdb.ListaValori[indice].ValoreDecodificato = valoreDecodificato;
						}
					}
					// Per compatibilità con i vecchi dati dinamici
					if (cdb.ListaValori.Count == 0)
						cdb.ListaValori.IncrementaMolteplicita();
				}
			}
		}

		protected override void PopolaValoriCampiStorico()
		{
			IIstanzeDyn2DatiStoricoManager mgrStorico = DataAccessProvider.GetIstanzeDyn2DatiStoricoManager();

			foreach(var riga in this.Righe)
			{
				for (int j = 0; j < riga.NumeroColonne; j++)
				{
					if (riga[j] == null) continue;

					CampoDinamicoBase cdb = riga[j];

					int codiceCampo = cdb.Id;

					List<IIstanzeDyn2DatiStorico> valoriUtenteStorico = mgrStorico.GetValoriCampo(IdComune, IdIstanza, codiceCampo, IndiceModello, IdVersioneStorico.Value);
									

					foreach (var valoreCampo in valoriUtenteStorico)
					{
						int indice = valoreCampo.IndiceMolteplicita.GetValueOrDefault(0);
						string valore = valoreCampo.Valore;
						string valoreDecodificato = valoreCampo.Valore;

						while (cdb.ListaValori.Count < (indice + 1))
							cdb.ListaValori.IncrementaMolteplicita();

						cdb.ListaValori[indice].Valore = valore;
						cdb.ListaValori[indice].ValoreDecodificato = valoreDecodificato;
					}

					// Per compatibilità con i vecchi dati dinamici
					if (cdb.ListaValori.Count == 0)
						cdb.ListaValori.IncrementaMolteplicita();
				}
			}
		}

		private IClasseContestoModelloDinamico LeggiIstanza()
		{
			return DataAccessProvider.GetIstanzeManager().LeggiIstanza(IdComune, IdIstanza);
		}

		protected override void SalvaValoriCampi(bool salvaStorico, IEnumerable<CampoDinamico> campiDaSalvare)
		{
			DataAccessProvider.GetIstanzeDyn2DatiManager().SalvaValoriCampi( salvaStorico , this , campiDaSalvare);
		}

		protected override void EliminaValoriCampi(IEnumerable<CampoDinamico> campiDaEliminare)
		{
			DataAccessProvider.GetIstanzeDyn2DatiManager().EliminaValoriCampi(this, campiDaEliminare);
		}

		
	}
}
