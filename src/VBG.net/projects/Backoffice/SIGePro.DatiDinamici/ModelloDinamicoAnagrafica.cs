using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Anagrafe;
using Init.SIGePro.DatiDinamici.Contesti;

namespace Init.SIGePro.DatiDinamici
{
	public partial class ModelloDinamicoAnagrafica : ModelloDinamicoBase
	{
		public int IdAnagrafe{get;private set;}

		public ModelloDinamicoAnagrafica(ModelloDinamicoLoader loader, int idModello, int idAnagrafe, int indice, bool readOnly)
			:
			this(loader, idModello, idAnagrafe, indice, readOnly, null)
		{
		}

		public ModelloDinamicoAnagrafica(ModelloDinamicoLoader loader, int idModello, int idAnagrafe, int indice, bool readOnly, int? idStorico)
			: base(idModello, indice, readOnly, idStorico, loader)
		{
			IdAnagrafe = idAnagrafe;

			base.Inizializza();
		}

		protected override void SetContesto()
		{
			var templateReader = new ResourceScriptTemplateReader();

			Contesto = new ContestoModelloDinamico(Token, ContestoModelloEnum.Anagrafe, LeggiAnagrafica(), templateReader);
		}

		private IClasseContestoModelloDinamico LeggiAnagrafica()
		{
			return DataAccessProvider.GetAnagrafeManager().LeggiAnagrafica(IdComune, IdAnagrafe);
		}

		protected override void PopolaValoriCampi()
		{
			IAnagrafeDyn2DatiManager mgr = DataAccessProvider.GetAnagrafeDyn2DatiManager();

			foreach (var riga in this.Righe)
			{
				for (int j = 0; j < riga.NumeroColonne; j++)
				{
					CampoDinamicoBase cdb = riga[j];
					
					List<IAnagrafeDyn2Dati> valoriUtente = mgr.GetValoriCampo(IdComune, IdAnagrafe, cdb.Id, IndiceModello);
					
					foreach (var valoreCampo in valoriUtente)
					{
						int indice = valoreCampo.IndiceMolteplicita.GetValueOrDefault(0);
						string valore = valoreCampo.Valore;
						string valoreDecodificato = valoreCampo.Valoredecodificato;

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

		protected override void PopolaValoriCampiStorico()
		{
			var mgrStorico = DataAccessProvider.GetAnagrafeDyn2DatiStoricoManager();

			foreach (var riga in this.Righe)
			{
				for (int j = 0; j < riga.NumeroColonne; j++)
				{
					CampoDinamicoBase cbd = riga[j];

					if (cbd == null) continue;

					int codiceCampo = cbd.Id;

					List<IAnagrafeDyn2DatiStorico> valoriUtenteStorico = mgrStorico.GetValoriCampo(IdComune, IdAnagrafe, codiceCampo, IndiceModello, IdVersioneStorico.Value);

					foreach (var valoreCampo in valoriUtenteStorico)
					{
						int indice					= valoreCampo.IndiceMolteplicita.GetValueOrDefault(0);
						string valore				= valoreCampo.Valore;
						string valoreDecodificato	= valoreCampo.Valore;

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
 			DataAccessProvider.GetAnagrafeDyn2DatiManager().SalvaValoriCampi( salvaStorico , this, campiDaSalvare );
		}

		protected override void EliminaValoriCampi(IEnumerable<CampoDinamico> campiDaEliminare)
		{
 			DataAccessProvider.GetAnagrafeDyn2DatiManager().EliminaValoriCampi( this,campiDaEliminare );
		}
		/*
		protected override void SalvaValoreCampoInternal(CampoDinamico campo)
		{
			AnagrafeDyn2DatiMgr mgr = new AnagrafeDyn2DatiMgr(Database);

			for (int i = 0; i < campo.ListaValori.Count; i++)
			{
				AnagrafeDyn2Dati cls = mgr.GetById(IdComune, m_idAnagrafe, campo.Id, IndiceModello, i);

				string valore = campo.ListaValori[i].Valore;
				string valoreDecodificato = campo.ListaValori[i].ValoreDecodificato;

				if (String.IsNullOrEmpty(valoreDecodificato))
					valoreDecodificato = valore;

				if (cls != null)
				{
					if (String.IsNullOrEmpty(valore.Trim()))
					{
						mgr.Delete(cls);
					}
					else
					{
						cls.Valore = valore;
						cls.Valoredecodificato = valoreDecodificato;

						mgr.Update(cls);
					}
				}
				else
				{
					if (!String.IsNullOrEmpty(valore.Trim()))
					{

						cls = new AnagrafeDyn2Dati();

						cls.Idcomune = IdComune;
						cls.FkD2cId = campo.Id;
						cls.Codiceanagrafe = m_idAnagrafe;
						cls.Valore = valore;
						cls.Valoredecodificato = valoreDecodificato;
						cls.Indice = IndiceModello;
						cls.IndiceMolteplicita = i;

						mgr.Insert(cls);
					}
				}
			}
		}
		*/
		/*
		protected override void EliminaCampoInternal(CampoDinamico campo)
		{
			AnagrafeDyn2DatiMgr mgr = new AnagrafeDyn2DatiMgr(Database);

			// Sto eliminando il modello quindi elimino tutti i campi del modello

			for (int i = 0; i < campo.ListaValori.Count; i++)
			{
				AnagrafeDyn2Dati cls = mgr.GetById(IdComune, m_idAnagrafe, campo.Id, IndiceModello, i);

				if (cls != null)
					mgr.Delete(cls);
			}
		}
		*/
		/*
		protected override void SalvaStoricoModelloInternal()
		{
			AnagrafeDyn2DatiMgr mgr = new AnagrafeDyn2DatiMgr(Database);

			// Carico le righe modificate
			List<AnagrafeDyn2DatiStorico> righeStorico = new List<AnagrafeDyn2DatiStorico>();

			for (int i = 0; i < Righe.Count; i++)
			{
				for (int j = 0; j < Righe[i].NumeroColonne; j++)
				{
					if (Righe[i][j] == null) continue;

					List<AnagrafeDyn2Dati> valoriDb = mgr.GetValoriCampoNoIndice(IdComune, m_idAnagrafe, Righe[i][j].Id);

					int indiceMin = int.MaxValue;

					if (valoriDb.Count == 0)
						indiceMin = 0;

					for (int idxCampo = 0; idxCampo < valoriDb.Count; idxCampo++)
					{
						int fkD2cId = valoriDb[idxCampo].FkD2cId.Value;
						int indice = valoriDb[idxCampo].Indice.GetValueOrDefault(0);
						int indiceMolteplicita = valoriDb[idxCampo].IndiceMolteplicita.GetValueOrDefault(0);
						string valore = valoriDb[idxCampo].Valore;
						string valoreDecodificato = valoriDb[idxCampo].Valoredecodificato;

						if (indiceMin > indiceMolteplicita)
							indiceMin = indiceMolteplicita;

						AnagrafeDyn2DatiStorico riga = new AnagrafeDyn2DatiStorico();
						riga.Idcomune = IdComune;
						riga.Codiceanagrafe = m_idAnagrafe;
						riga.FkD2mtId = IdModello;
						riga.FkD2cId = fkD2cId;
						riga.Indice = indice;
						riga.IndiceMolteplicita = indiceMolteplicita;
						riga.Valore = String.IsNullOrEmpty(valoreDecodificato) ? valore : valoreDecodificato;

						righeStorico.Add(riga);
					}
				}
			}

			// Se non è stata caricata nessuna riga allora non salvo la versione perchè sarebbe un modello vuoto
			if (righeStorico.Count == 0)
				return;


			// Preparo il salvataggio della testata
			AnagrafeDyn2ModelliTStoricoMgr testataStoricoMgr = new AnagrafeDyn2ModelliTStoricoMgr(Database);
			AnagrafeDyn2DatiStoricoMgr righeStoricoMgr = new AnagrafeDyn2DatiStoricoMgr(Database);

			// Salvo una nuova riga in AnagrafeDyn2Modellit_storico
			AnagrafeDyn2ModelliTStorico testataStorico = new AnagrafeDyn2ModelliTStorico();
			testataStorico.Idcomune = this.IdComune;
			testataStorico.Codiceanagrafe = m_idAnagrafe;
			testataStorico.FkD2mtId = IdModello;

			testataStorico = testataStoricoMgr.Insert(testataStorico);

			for (int i = 0; i < righeStorico.Count; i++)
			{
				righeStorico[i].Idversione = testataStorico.Idversione;

				righeStoricoMgr.Insert(righeStorico[i]);
			}

		}
		*/
	}
}
