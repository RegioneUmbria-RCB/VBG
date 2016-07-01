
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.Validator;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class IstanzeDyn2ModelliTMgr
    {
		public class ElementoListaModelliIstanza : ElementoListaModelli
		{
			private string m_provenienza;

			public string Provenienza
			{
				get { return m_provenienza; }
				set { m_provenienza = value; }
			}

			public ElementoListaModelliIstanza(int id , string descrizione , string provenienza):base( id , descrizione )
			{
				m_provenienza = provenienza;
			}
		}


		public IstanzeDyn2ModelliT Insert(IstanzeDyn2ModelliT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);

			db.Insert(cls);

			InizializzaModello(cls);

			return cls;
		}

		private void InizializzaModello(IstanzeDyn2ModelliT cls)
		{
			
			Dyn2ModelliDMgr modelliCampiMgr = new Dyn2ModelliDMgr(db);
			IstanzeDyn2DatiMgr datiMgr = new IstanzeDyn2DatiMgr(db);
			Dyn2CampiProprietaMgr mgrProprieta = new Dyn2CampiProprietaMgr(db);
            List<Dyn2ModelliD> campiModello = modelliCampiMgr.GetCampiDinamiciModello(cls.Idcomune, cls.FkD2mtId.GetValueOrDefault(int.MinValue));

			var valoriCampiModello = datiMgr.GetValoriCampoDaIdModello(cls.Idcomune, cls.Codiceistanza.Value, cls.FkD2mtId.Value, 0);

			foreach (Dyn2ModelliD elementoModello in campiModello)
			{
				// Verificao se tra i campi del modello ci sono checkbox
				if (elementoModello.CampoDinamico.Tipodato != "Checkbox") continue;

                int idCampoDinamico = elementoModello.CampoDinamico.Id.GetValueOrDefault(int.MinValue);
				
				// Leggo che valore ha di default una checkbox non ceccata :P
				Dyn2CampiProprieta prop = mgrProprieta.GetById(cls.Idcomune, idCampoDinamico, "ValoreFalse");

				// se non è stato specificato un valore di default non inserisco nessun valore
				if (prop == null || String.IsNullOrEmpty(prop.Valore.Trim())) continue;

				if (valoriCampiModello.ContainsKey(idCampoDinamico))
					continue;

                //List<IIstanzeDyn2Dati> campiInseriti = datiMgr.GetValoriCampo(cls.Idcomune, cls.Codiceistanza.GetValueOrDefault(int.MinValue), idCampoDinamico, 0);

				// Il campo è già stato inserito???
				//if (campiInseriti.Count > 0)
				//	continue;




				// inserisco il valore di default per la checkbox
				IstanzeDyn2Dati dato = new IstanzeDyn2Dati();

				dato.Idcomune = cls.Idcomune;
				dato.Codiceistanza = cls.Codiceistanza;
				dato.FkD2cId = idCampoDinamico;
				dato.Valore = prop.Valore;
				dato.Valoredecodificato = prop.Valore;
				dato.Indice = 0;
				dato.IndiceMolteplicita = 0;

				datiMgr.Insert(dato);
			}


			var dap = new IstanzeDyn2DataAccessProvider(db, cls.Codiceistanza.GetValueOrDefault(int.MinValue), cls.Idcomune);
			var loader = new ModelloDinamicoLoader(dap, cls.Idcomune, false);
            var modello = new ModelloDinamicoIstanza(loader, cls.FkD2mtId.GetValueOrDefault(int.MinValue), cls.Codiceistanza.GetValueOrDefault(int.MinValue), 0,false);
			modello.EseguiScriptCaricamento();
			modello.EseguiScriptSalvataggio();
			modello.Salva();
		}


		public IEnumerable<BaseDto<int,string>> GetModelliNonUtilizzati(string idComune, int codiceIstanza,string partial, bool usaSoftwareTT)
		{
            Istanze istanza = new IstanzeMgr(db).GetById(idComune, codiceIstanza);

			var filtroSoftware = "'" + istanza.SOFTWARE + "'";

			if (usaSoftwareTT)
				filtroSoftware += ",'TT'";

			string sql = @"SELECT 
								DYN2_MODELLIT.id,
								DYN2_MODELLIT.descrizione 
							FROM
								DYN2_MODELLIT
							WHERE
								DYN2_MODELLIT.idcomune = {0} AND
								DYN2_MODELLIT.fk_d2bc_id = 'IS' AND
								DYN2_MODELLIT.Software in (" + filtroSoftware + @") AND
								" + db.Specifics.UCaseFunction( "DYN2_MODELLIT.descrizione") + @" like {1} and
								DYN2_MODELLIT.id NOT IN 
								( 
									SELECT 
										FK_D2MT_ID
									FROM 
										ISTANZEDYN2MODELLIT
									WHERE
										ISTANZEDYN2MODELLIT.idcomune = {2} AND
										ISTANZEDYN2MODELLIT.codiceIstanza = {3}
								)
							order by descrizione asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune1"),
									 db.Specifics.QueryParameterName("partial"),
									 db.Specifics.QueryParameterName("idComune2"),
									 db.Specifics.QueryParameterName("codiceIstanza") );

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				db.Connection.Open();
				closeCnn = true;
			}

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune1", idComune));
					cmd.Parameters.Add(db.CreateParameter("partial", "%" + partial.ToUpperInvariant() + "%"));
					cmd.Parameters.Add(db.CreateParameter("idComune2", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceIstanza", codiceIstanza));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<BaseDto<int, string>>();

						while (dr.Read())
							rVal.Add( new BaseDto<int, string>(Convert.ToInt32(dr["id"]), dr["descrizione"].ToString()) );

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

		public List<ElementoListaModelliIstanza> GetModelliIstanza(string idComune, int codiceIstanza, string codiceMovimento)
		{
			if (String.IsNullOrEmpty(codiceMovimento))
				return GetModelliNoMovimento(idComune, codiceIstanza);

			return GetModelliMovimento(idComune, codiceIstanza , Convert.ToInt32(codiceMovimento) );
		}

		private List<ElementoListaModelliIstanza> GetModelliMovimento(string idComune, int codiceIstanza , int codiceMovimento)
		{
			string sql = @"SELECT 
							dyn2_modellit.id,
							dyn2_modellit.descrizione,
							tipimovimento.movimento
						FROM
							istanzeDyn2Modellit,
							dyn2_modellit,
							movimentiDyn2Modellit,
							movimenti,
							tipimovimento
						WHERE
							dyn2_modellit.idcomune = istanzeDyn2Modellit.idcomune  AND
							dyn2_modellit.id = istanzeDyn2Modellit.fk_d2mt_id AND
							movimentiDyn2Modellit.idcomune = istanzeDyn2Modellit.idcomune AND
							movimentiDyn2Modellit.fk_d2mt_id = istanzeDyn2Modellit.fk_d2mt_id  and
							movimentiDyn2Modellit.codiceistanza = istanzeDyn2Modellit.codiceistanza AND
							movimenti.idcomune = movimentiDyn2Modellit.idcomune AND
							movimenti.codicemovimento = movimentiDyn2Modellit.codicemovimento AND
							tipimovimento.idcomune = movimenti.idcomune AND
							tipimovimento.tipomovimento = movimenti.tipomovimento and
							istanzeDyn2Modellit.idcomune = {0} AND
							istanzeDyn2Modellit.codiceistanza = {1} AND
							movimentiDyn2Modellit.codicemovimento = {2}
						order by dyn2_modellit.descrizione asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idcomune"),
										db.Specifics.QueryParameterName("codiceIstanza"),
										db.Specifics.QueryParameterName("codiceMovimento"));

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closeCnn = true;
				db.Connection.Open();
			}

			List<ElementoListaModelliIstanza> list = new List<ElementoListaModelliIstanza>();

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceIstanza", codiceIstanza));
					cmd.Parameters.Add(db.CreateParameter("codiceMovimento", codiceMovimento));

					using (IDataReader rd = cmd.ExecuteReader())
					{
						while (rd.Read())
						{
							int key = Convert.ToInt32(rd["id"]);
							string val = rd["descrizione"].ToString();
							string provenienza = rd["movimento"].ToString();
							list.Add(new ElementoListaModelliIstanza(key, val, provenienza ) );
						}
					}

					return list;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		private List<ElementoListaModelliIstanza> GetModelliNoMovimento(string idComune, int codiceIstanza)
		{
			string sql = @"SELECT 
							  dyn2_modellit.id,
							  dyn2_modellit.descrizione 
							FROM
							  istanzeDyn2Modellit,
							  dyn2_modellit
							WHERE
							   dyn2_modellit.idcomune = istanzeDyn2Modellit.idcomune  AND
							   dyn2_modellit.id = istanzeDyn2Modellit.fk_d2mt_id AND
							   istanzeDyn2Modellit.idcomune = {0} AND
							   istanzeDyn2Modellit.codiceistanza = {1}
							order by dyn2_modellit.descrizione asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idcomune"),
										db.Specifics.QueryParameterName("codiceIstanza"));

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closeCnn = true;
				db.Connection.Open();
			}

			List<ElementoListaModelliIstanza> list = new List<ElementoListaModelliIstanza>();

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceIstanza", codiceIstanza));

					using (IDataReader rd = cmd.ExecuteReader())
					{
						while (rd.Read())
						{
							int key = Convert.ToInt32(rd["id"]);
							string val = rd["descrizione"].ToString();

							list.Add(new ElementoListaModelliIstanza(key, val , String.Empty) );
						}
					}

					VerificaProvenienzaModello(idComune , codiceIstanza , list);

					return list;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		private void VerificaProvenienzaModello(string idComune, int codiceIstanza, List<ElementoListaModelliIstanza> list)
		{
			string sql = @"SELECT
								tipimovimento.movimento
							FROM
							  movimentiDyn2Modellit,
							  movimenti,
							  tipimovimento
							WHERE
							  tipimovimento.idcomune = movimenti.idcomune AND
							  tipimovimento.tipomovimento = movimenti.tipomovimento and
							  movimenti.idcomune = movimentiDyn2Modellit.idcomune AND
							  movimenti.codicemovimento = movimentiDyn2Modellit.codicemovimento AND
							  movimentiDyn2Modellit.idcomune = {0} AND
							  movimentiDyn2Modellit.codiceistanza = {1} AND
							  movimentiDyn2Modellit.fk_d2mt_id = {2}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
									db.Specifics.QueryParameterName("codiceIstanza"),
									db.Specifics.QueryParameterName("idModello"));

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closeCnn = true;
				db.Connection.Open();
			}

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceIstanza", -1));
					cmd.Parameters.Add(db.CreateParameter("idModello", -1));

					foreach (ElementoListaModelliIstanza it in list)
					{
						((IDataParameter)cmd.Parameters[db.Specifics.ParameterName("codiceIstanza")]).Value = codiceIstanza;
						((IDataParameter)cmd.Parameters[db.Specifics.ParameterName("idModello")]).Value = it.Id;

						object provenienza = cmd.ExecuteScalar();

						if (provenienza == null || provenienza.ToString() == String.Empty)
							it.Provenienza = "Istanza";
						else
							it.Provenienza = provenienza.ToString();
					}

				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}


		private void EffettuaCancellazioneACascata(IstanzeDyn2ModelliT cls)
		{
			// se sono stati inseriti valori elmino tutti quelli che non sono già utilizzati in altri modelli
			EliminaValoriInutilizzati(cls);
		}

		private void EliminaValoriInutilizzati(IstanzeDyn2ModelliT cls)
		{
			string sql = @"SELECT 
							dyn2_modellid.fk_d2c_ID
						FROM 
							istanzeDyn2modellit,
							dyn2_modellid
						WHERE
							dyn2_modellid.idcomune =istanzeDyn2modellit.idComune AND
							dyn2_modellid.fk_d2mt_id =istanzeDyn2modellit.fk_d2mt_id and 
							dyn2_modellid.fk_d2mdt_id IS NULL and
							istanzeDyn2modellit.idComune = {0} AND
							istanzeDyn2modellit.CodiceiStanza = {1} AND
							istanzeDyn2modellit.fk_d2mt_id = {2} AND
							dyn2_modellid.fk_d2c_ID NOT IN 
							(
								SELECT 
								  dyn2_modellid.fk_d2c_ID
								FROM 
								  istanzeDyn2modellit,
								  dyn2_modellid
								WHERE
								  dyn2_modellid.idcomune =istanzeDyn2modellit.idComune AND
								  dyn2_modellid.fk_d2mt_id =istanzeDyn2modellit.fk_d2mt_id and 
								  dyn2_modellid.fk_d2mdt_id IS NULL and
								  istanzeDyn2modellit.idComune = {3} AND
								  istanzeDyn2modellit.CodiceiStanza = {4} AND
								  istanzeDyn2modellit.fk_d2mt_id <> {5}
							)";

			sql = String.Format(sql,db.Specifics.QueryParameterName("idComune1"),
									db.Specifics.QueryParameterName("codiceIstanza1"),
									db.Specifics.QueryParameterName("codiceModello1"),
									db.Specifics.QueryParameterName("idComune2"),
									db.Specifics.QueryParameterName("codiceIstanza2"),
									db.Specifics.QueryParameterName("codiceModello2"));

			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				db.Connection.Open();
				closeCnn = true;
			}

			List<int> ids = new List<int>();

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune1", cls.Idcomune));
					cmd.Parameters.Add(db.CreateParameter("codiceIstanza1", cls.Codiceistanza));
					cmd.Parameters.Add(db.CreateParameter("codiceModello1", cls.FkD2mtId));
					cmd.Parameters.Add(db.CreateParameter("idComune2", cls.Idcomune));
					cmd.Parameters.Add(db.CreateParameter("codiceIstanza2", cls.Codiceistanza));
					cmd.Parameters.Add(db.CreateParameter("codiceModello2", cls.FkD2mtId));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						while (dr.Read())
							ids.Add(Convert.ToInt32(dr[0]));
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

			IstanzeDyn2DatiMgr mgrDati = new IstanzeDyn2DatiMgr( db );

			foreach (int id in ids)
			{
				List<IstanzeDyn2Dati> dati = mgrDati.GetByIdNoIndice(cls.Idcomune, cls.Codiceistanza.GetValueOrDefault(int.MinValue), id);

				for (int i = 0; i < dati.Count; i++)
					mgrDati.Delete(dati[i]);
			}
		}

		public List<int> GetListaIndiciScheda(string idComune, int codiceIstanza, int codiceModello)
		{
			string sql = @"SELECT 
							  distinct istanzedyn2dati.indice
							FROM 
							  dyn2_modellid,
							  istanzedyn2dati
							WHERE
							  istanzedyn2dati.idcomune = dyn2_modellid.idcomune AND
							  istanzedyn2dati.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
							  dyn2_modellid.idcomune = {0} AND
							  dyn2_modellid.fk_d2mt_id = {1} AND
							  istanzedyn2dati.codiceistanza = {2} 
							order by istanzedyn2dati.indice asc";

			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
										db.Specifics.QueryParameterName("codiceModello"),
										db.Specifics.QueryParameterName("codiceIstanza") );


			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				db.Connection.Open();
				closeCnn = true;
			}

			try
			{
				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceModello", codiceModello));
					cmd.Parameters.Add(db.CreateParameter("codiceIstanza", codiceIstanza));

					List<int> rVal = new List<int>();

					using (IDataReader dr = cmd.ExecuteReader())
					{
						while (dr.Read())
							rVal.Add(Convert.ToInt32(dr[0]));
					}

					return rVal;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		public void Delete(IstanzeDyn2ModelliT cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);

			NotificaRimozioneScheda(cls);
		}

		private void NotificaRimozioneScheda(IstanzeDyn2ModelliT scheda)
		{
			// La notifica deve avvenire solamente se l'istanza da cui sto eliminando la scheda è 
			// collegata ad un'attività

			var istanza = new IstanzeMgr(db).GetById(scheda.Idcomune, scheda.Codiceistanza.Value);

			if (istanza == null)
				throw new Exception(String.Format("Istanza con idcomune {0} e codiceistanza {1} non trovata durante la verifica del collegamento con una attivita", scheda.Idcomune, scheda.Codiceistanza.Value));

			if (!istanza.FK_IDI_ATTIVITA.HasValue)
				return;

			var svc = new EventiSchedeDinamicheAttivitaService(scheda.Idcomune);

			svc.Handle(new SchedaDinamicaIstanzaEliminata(istanza.FK_IDI_ATTIVITA.Value, scheda.Codiceistanza.Value, scheda.FkD2mtId.Value));
		}

	}
}
				