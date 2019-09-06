
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;
using Init.SIGePro.Validator;
using Init.SIGePro.DatiDinamici.Interfaces.Attivita;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
	public partial class IAttivitaDyn2DatiMgr : IIAttivitaDyn2DatiManager
    {



		/// <summary>
		/// Utilizzato per mantenere la compatibilità.
		/// va in errore se trova un campo con più di un indice
		/// </summary>
		/// <param name="idcomune"></param>
		/// <param name="fk_ia_id"></param>
		/// <param name="fk_d2c_id"></param>
		/// <param name="indice"></param>
		/// <returns></returns>
		public IAttivitaDyn2Dati GetById(string idcomune, int fk_ia_id, int fk_d2c_id, int indice)
		{
			IAttivitaDyn2Dati c = new IAttivitaDyn2Dati();

			c.Idcomune = idcomune;
			c.FkIaId = fk_ia_id;
			c.FkD2cId = fk_d2c_id;
			c.Indice = indice;

			return (IAttivitaDyn2Dati)db.GetClass(c);
		}

		internal List<IAttivitaDyn2Dati> GetValoriCampoNoIndice(string idComune, int idAttivita, int idCampo)
		{
			var filtro = new IAttivitaDyn2Dati
			{
				Idcomune = idComune,
				FkIaId = idAttivita,
				FkD2cId = idCampo,
				OrderBy = "indice_molteplicita asc"
			};

			var list = GetList(filtro);
			var rVal = new List<IAttivitaDyn2Dati>(list.Count);

			list.ForEach(x => rVal.Add(x));

			return rVal;
		}

		private void Validate(IAttivitaDyn2Dati cls, AmbitoValidazione ambitoValidazione)
		{
			if (ambitoValidazione == AmbitoValidazione.Insert && !cls.IndiceMolteplicita.HasValue)
				cls.IndiceMolteplicita = 0;

			RequiredFieldValidate(cls, ambitoValidazione);
		}


		public IEnumerable<int> GetCampiModelloPresentiAncheNelleIstanze(string idComune, int idModello, int idAttivita)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = PreparaQueryParametrica(@"SELECT
													  istanzedyn2dati.fk_d2c_id AS idCampo,
													  Count(istanzedyn2dati.fk_d2c_id) AS datiPresenti
													FROM
													  dyn2_modellid,
													  istanze,
													  istanzedyn2dati
													WHERE
													  istanzedyn2dati.idcomune = dyn2_modellid.idcomune AND
													  istanzedyn2dati.fk_d2c_id= dyn2_modellid.fk_d2c_id and
													  istanze.idcomune         = istanzedyn2dati.idcomune and
													  istanze.codiceistanza    = istanzedyn2dati.codiceistanza AND
													  dyn2_modellid.idcomune   = {0} AND
													  dyn2_modellid.fk_d2mt_id = {1} AND
													  istanze.fk_idi_attivita = {2}
													GROUP BY
													  istanze.fk_idi_attivita,
													  istanzedyn2dati.fk_d2c_id", "idComune", "idModello", "codiceAttivita");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idModello", idModello));
					cmd.Parameters.Add(db.CreateParameter("codiceAttivita", idAttivita));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<int>();

						while (dr.Read())
						{
							var idCampo = Convert.ToInt32(dr["idCampo"]);
							var datiPresenti = Convert.ToInt32(dr["datiPresenti"]);

							if (datiPresenti > 0)
								rVal.Add(idCampo);
						}

						return rVal;
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		#region IIAttivitaDyn2DatiManager Members

		public List<IIAttivitaDyn2Dati> GetValoriCampo(string idComune, int idAttivita, int idCampo, int indiceCampo)
		{
			var filtro = new IAttivitaDyn2Dati
			{
				Idcomune = idComune,
				FkIaId = idAttivita,
				FkD2cId = idCampo,
				Indice = indiceCampo,
				OrderBy = "indice_molteplicita asc",
			};
			var list = GetList(filtro);

			var rVal = new List<IIAttivitaDyn2Dati>(list.Count);

			list.ForEach(x => rVal.Add(x));

			return rVal;
		}

		public void SalvaValoriCampi(bool salvaStorico, ModelloDinamicoAttivita modello, IEnumerable<CampoDinamico> campiDaSalvare)
		{
			bool commitTrans = !db.IsInTransaction;

			if (commitTrans)
				db.BeginTransaction();
			try
			{

				if (salvaStorico)
					SalvaStoricoModello(modello);

				var indiceModello = modello.IndiceModello;
				var idComune = modello.IdComune;
				var codiceAttivita = modello.IdAttivita;


				// Elimino tutti i valori del campo per la scheda corrente
				foreach (var campo in campiDaSalvare)
					EliminaValoriMultipliCampo(idComune, codiceAttivita, campo.Id, indiceModello);

				// Ricreo tutti i valori dei campi che non siano == ""
				foreach (var campo in campiDaSalvare)
				{
					for (int indiceMolteplicita = 0; indiceMolteplicita < campo.ListaValori.Count; indiceMolteplicita++)
					{
						var valore = campo.ListaValori[indiceMolteplicita].Valore;
						var valoreDecodificato = campo.ListaValori[indiceMolteplicita].ValoreDecodificato;

						if (String.IsNullOrEmpty(valore.Trim()))
							continue;

						if (String.IsNullOrEmpty(valoreDecodificato))
							valoreDecodificato = valore;

						var cls = new IAttivitaDyn2Dati
						{
							Idcomune = idComune,
							FkD2cId = campo.Id,
							FkIaId = codiceAttivita,
							Valore = valore,
							Valoredecodificato = valoreDecodificato,
							Indice = indiceModello,
							IndiceMolteplicita = indiceMolteplicita
						};

						Insert(cls);
					}
				}

				if (commitTrans)
				{
					db.CommitTransaction();
					commitTrans = false;
				}

				// Notifico al servizio di snapshotting che la scheda è stata modificata
				this.SchedaAttivitaSalvata(idComune, codiceAttivita);
			}
			catch (Exception)
			{
				if (commitTrans)
					db.RollbackTransaction();

				throw;
			}
		}

		private void SchedaAttivitaSalvata(string idComune, int idAttivita)
		{
			var svc = new EventiSchedeDinamicheAttivitaService();

			svc.Handle(new SchedaDinamicaAttivitaSalvata( idAttivita ));
		}

		private void SalvaStoricoModello(ModelloDinamicoAttivita modello)
		{
			// Carico le righe modificate
			List<IAttivitaDyn2DatiStorico> righeStorico = new List<IAttivitaDyn2DatiStorico>();

			foreach (var riga in modello.Righe)
			{
				for (int j = 0; j < riga.NumeroColonne; j++)
				{
					if (riga[j] == null) continue;

					var valoriDb = GetValoriCampoNoIndice(modello.IdComune, modello.IdAttivita, riga[j].Id);

					int indiceMin = int.MaxValue;

					if (valoriDb.Count == 0)
						indiceMin = 0;

					foreach (var valoreCampo in valoriDb)
					{
						var fkD2cId = valoreCampo.FkD2cId.Value;
						var indice = valoreCampo.Indice.GetValueOrDefault(0);
						var indiceMolteplicita = valoreCampo.IndiceMolteplicita.GetValueOrDefault(0);
						var valore = valoreCampo.Valore;
						var valoreDecodificato = valoreCampo.Valoredecodificato;

						if (indiceMin > indiceMolteplicita)
							indiceMin = indiceMolteplicita;

						IAttivitaDyn2DatiStorico rigaStorico = new IAttivitaDyn2DatiStorico
						{
							Idcomune = modello.IdComune,
							FkIaId = modello.IdAttivita,
							FkD2mtId = modello.IdModello,
							FkD2cId = fkD2cId,
							Indice = indice,
							IndiceMolteplicita = indiceMolteplicita,
							Valore = String.IsNullOrEmpty(valoreDecodificato) ? valore : valoreDecodificato

						};
						righeStorico.Add(rigaStorico);
					}
				}
			}

			// Se non è stata caricata nessuna riga allora non salvo la versione perchè sarebbe un modello vuoto
			if (righeStorico.Count == 0)
				return;

			// Preparo il salvataggio della testata
			var testataStoricoMgr = new IAttivitaDyn2ModelliTStoricoMgr(db);
			var righeStoricoMgr = new IAttivitaDyn2DatiStoricoMgr(db);

			// Salvo una nuova riga in IAttivitaDyn2Modellit_storico
			var testataStorico = new IAttivitaDyn2ModelliTStorico
			{
				Idcomune = modello.IdComune,
				FkIaId = modello.IdAttivita,
				FkD2mtId = modello.IdModello
			};

			testataStorico = testataStoricoMgr.Insert(testataStorico);

			for (int i = 0; i < righeStorico.Count; i++)
			{
				righeStorico[i].Idversione = testataStorico.Idversione;

				righeStoricoMgr.Insert(righeStorico[i]);
			}
		}

		private void EliminaValoriMultipliCampo(string idComune, int idAttivita, int idCampo, int indiceMolteplicitaModello)
		{
			var filtro = new IAttivitaDyn2Dati
			{
				Idcomune = idComune,
				FkIaId = idAttivita,
				FkD2cId = idCampo,
				Indice = indiceMolteplicitaModello
			};

			var lista = GetList(filtro);

			for (int i = 0; i < lista.Count; i++)
				Delete(lista[i]);
		}

		public void EliminaValoriCampi(ModelloDinamicoAttivita modello, IEnumerable<CampoDinamico> campiDaEliminare)
		{
			bool commitTrans = !db.IsInTransaction;

			if (commitTrans)
				db.BeginTransaction();
			try
			{
				foreach (var campo in campiDaEliminare)
				{
					for (int j = 0; j < campo.ListaValori.Count; j++)
					{
						var cls = GetById(modello.IdComune, modello.IdAttivita, campo.Id, campo.ModelloCorrente.IndiceModello, j);

						if (cls != null)
							Delete(cls);
					}
				}
			}
			catch (Exception)
			{
				if (commitTrans)
					db.RollbackTransaction();

				throw;
			}
			finally
			{
				if (commitTrans)
					db.CommitTransaction();
			}
		}

		#endregion
	}
}
				