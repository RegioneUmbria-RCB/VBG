using System;
using System.Data;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.Utils;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per IstanzeOneriMgr.\n	/// </summary>
	public class IstanzeOneriMgr: BaseManager
	{
		public IstanzeOneriMgr( DataBase dataBase ) : base( dataBase ) 
        {
            
        }


		#region Metodi per l'accesso di base al DB

		private IstanzeOneri DataIntegrations(IstanzeOneri p_class, string idComuneAlias)
		{
			IstanzeOneri retVal = (IstanzeOneri)p_class.Clone();

			#region Integrazione del campo NUMERORATA
			if (StringChecker.IsStringEmpty(retVal.NUMERORATA))
			{
				retVal.NUMERORATA = "1";

				IstanzeOneri pClass = new IstanzeOneri();
				pClass.IDCOMUNE = retVal.IDCOMUNE;
				pClass.CODICEISTANZA = retVal.CODICEISTANZA;
				pClass.FKIDTIPOCAUSALE = retVal.FKIDTIPOCAUSALE;
				pClass.OrderBy = "NUMERORATA DESC";

				//IstanzeOneriMgr istOneriMgr = new IstanzeOneriMgr( db );
				List<IstanzeOneri> al = this.GetList(pClass);

				if (al != null && al.Count > 0)
				{
					pClass = al[0];
					if (!StringChecker.IsStringEmpty(pClass.NUMERORATA))
						retVal.NUMERORATA = (Convert.ToInt32(pClass.NUMERORATA) + 1).ToString();
				}
			}

			retVal.NR_DOCUMENTO = SetNumeroDocumento(retVal, idComuneAlias);

			#endregion

			return retVal;
		}

		private string SetNumeroDocumento(IstanzeOneri p_class, string idComuneAlias)
		{
			if (string.IsNullOrEmpty(p_class.NR_DOCUMENTO))
			{
				TipiCausaliOneri tco = new TipiCausaliOneriMgr(db).GetById(p_class.IDCOMUNE, Convert.ToInt32(p_class.FKIDTIPOCAUSALE));
				if (tco.PagamentiRegulus.GetValueOrDefault(int.MinValue) == 1)
				{
					//verifico se c'è già una riga su istanzeoneri che ha il codice del pagamento di regulus impostato
					IstanzeOneri filtro = new IstanzeOneri();
					filtro.IDCOMUNE = p_class.IDCOMUNE;
					filtro.CODICEISTANZA = p_class.CODICEISTANZA;
					filtro.FKIDTIPOCAUSALE = p_class.FKIDTIPOCAUSALE;
					filtro.OthersWhereClause.Add("NR_DOCUMENTO IS NOT NULL");


					var lista = this.GetList(filtro);
					foreach (IstanzeOneri io in lista)
					{
						p_class.NR_DOCUMENTO = io.NR_DOCUMENTO;
						break;
					}

					//se arrivo qui vuol dire che non è stato possibile risalire ad un codice di pagamento esistente
					//e quindi va calcolato
					//1. Prendo il software dall'istanza
					Istanze istanza = new IstanzeMgr(db).GetById(p_class.IDCOMUNE, Convert.ToInt32(p_class.CODICEISTANZA));

					VerticalizzazioneSistemapagamentiAttivo vsa = new VerticalizzazioneSistemapagamentiAttivo(idComuneAlias, istanza.SOFTWARE);
					string nrDocumento = vsa.Numerodocumento;

					//[DATAPROTOCOLLO]
					if (istanza.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue) > DateTime.MinValue)
					{
						nrDocumento = nrDocumento.Replace("[DATAPROTOCOLLO]", istanza.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy"));
					}
					else
					{
						nrDocumento = nrDocumento.Replace("[DATAPROTOCOLLO]", "");
					}

					//[DATA]
					nrDocumento = nrDocumento.Replace("[DATA]", istanza.DATA.Value.ToString("dd/MM/yyyy"));

					//[CODICEISTANZA]
					nrDocumento = nrDocumento.Replace("[CODICEISTANZA]", istanza.CODICEISTANZA);

					//[NUMEROPROTOCOLLO]
					if (!string.IsNullOrEmpty(istanza.NUMEROPROTOCOLLO))
					{
						nrDocumento = nrDocumento.Replace("[NUMEROPROTOCOLLO]", istanza.NUMEROPROTOCOLLO);
					}
					else
					{
						nrDocumento = nrDocumento.Replace("[NUMEROPROTOCOLLO]", "");
					}

					//[NUMEROISTANZA]
					nrDocumento = nrDocumento.Replace("[NUMEROISTANZA]", istanza.NUMEROISTANZA);

					//[SOFTWARE]
					nrDocumento = nrDocumento.Replace("[SOFTWARE]", istanza.SOFTWARE);

					//[CODICECOMUNE]
					if (!string.IsNullOrEmpty(istanza.CODICECOMUNE))
					{
						nrDocumento = nrDocumento.Replace("[CODICECOMUNE]", istanza.CODICECOMUNE);
					}
					else
					{
						nrDocumento = nrDocumento.Replace("[CODICECOMUNE]", "");
					}

					//[FKIDTIPOCAUSALE]
					nrDocumento = nrDocumento.Replace("[FKIDTIPOCAUSALE]", p_class.FKIDTIPOCAUSALE);

					p_class.NR_DOCUMENTO = nrDocumento;
				}
			}

			return p_class.NR_DOCUMENTO;
		}

		public IstanzeOneri GetById(String pIDCOMUNE, String pID)
		{
			IstanzeOneri retVal = new IstanzeOneri();
			retVal.IDCOMUNE = pIDCOMUNE;
			retVal.ID = pID;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as IstanzeOneri;
			
			return null;
		}

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeOneri> GetList(IstanzeOneri p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<IstanzeOneri> GetList(IstanzeOneri p_class, IstanzeOneri p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeOneri>();
		}

		public void Delete(IstanzeOneri p_class)
		{
            VerificaRecordCollegati(p_class);

            EffettuaCancellazioneACascata(p_class);

			db.Delete( p_class) ;
		}
		/*
		public IstanzeOneri Insert(IstanzeOneri cls)
		{
			throw new NotImplementedException();
		}
		*/
        private void VerificaRecordCollegati(IstanzeOneri p_class)
        {
        }

        private void EffettuaCancellazioneACascata(IstanzeOneri cls)
        {
            IstanzeOneri_Canoni ioc = new IstanzeOneri_Canoni();
            ioc.Idcomune = cls.IDCOMUNE;
            ioc.FkIdIstoneri = Convert.ToInt32( cls.ID );
            List<IstanzeOneri_Canoni> lIstanzeOneri_Canoni = new IstanzeOneri_CanoniMgr(db).GetList(ioc);
            foreach (IstanzeOneri_Canoni tioc in lIstanzeOneri_Canoni)
            {
                IstanzeOneri_CanoniMgr mgr = new IstanzeOneri_CanoniMgr(db);
                mgr.Delete(tioc);
            }

            IstanzeCalcoloCanoniO icco = new IstanzeCalcoloCanoniO();
            icco.Idcomune = cls.IDCOMUNE;
            icco.FkIdistoneri = Convert.ToInt32(cls.ID);
            List<IstanzeCalcoloCanoniO> lIstanzeCalcoloCanoniO = new IstanzeCalcoloCanoniOMgr(db).GetList(icco);
            foreach (IstanzeCalcoloCanoniO ticco in lIstanzeCalcoloCanoniO)
            {
                IstanzeCalcoloCanoniOMgr mgr = new IstanzeCalcoloCanoniOMgr(db);
                mgr.Delete(ticco);
            }
        }

		/*
		public IstanzeOneri Insert( IstanzeOneri p_class, string idComuneAlias )
		{
            p_class = DataIntegrations(p_class, idComuneAlias);

			Validate(p_class, AmbitoValidazione.Insert);

			db.Insert( p_class );
			
			return p_class;
		}
		*/
		public IstanzeOneri Update( IstanzeOneri p_class, string idComuneAlias )
		{
            p_class = DataIntegrations(p_class, idComuneAlias);

            Validate(p_class, AmbitoValidazione.Update);
			
			db.Update( p_class );
			
			return p_class;
		}

		private void Validate(IstanzeOneri p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			#region ISTANZEONERI.DATA
			if ( IsObjectEmpty( p_class.DATA ) )
			{
				p_class.DATA = DateTime.Now;
			}
			#endregion

			#region ISTANZEONERI.CODICEINVENTARIO
			if ( ! string.IsNullOrEmpty( p_class.FKIDTIPOCAUSALE ) )
			{
				string cmdtext = String.Empty;

				cmdtext += "SELECT TIPICAUSALIONERI.CO_SERICHIEDEENDO " +
						   "FROM ISTANZEONERI, TIPICAUSALIONERI " +
						   "WHERE TIPICAUSALIONERI.IDCOMUNE=ISTANZEONERI.IDCOMUNE AND " +
						   "TIPICAUSALIONERI.CO_ID=ISTANZEONERI.FKIDTIPOCAUSALE AND " +
						   "ISTANZEONERI.FKIDTIPOCAUSALE = " + p_class.FKIDTIPOCAUSALE + " AND " + 
						   "ISTANZEONERI.IDCOMUNE = '" + p_class.IDCOMUNE + "'";


                bool closeCnn = false;
                if (db.Connection.State == ConnectionState.Closed)
                {
                    closeCnn = true;
                    db.Connection.Open();
                }

                object obj = db.CreateCommand( cmdtext ).ExecuteScalar();

                if (closeCnn)
                    db.Connection.Close();

				if ( ! IsObjectEmpty( obj ) )
				{
					if ( obj.ToString() == "1" && IsStringEmpty( p_class.CODICEINVENTARIO ) )
					{
						throw( new RequiredFieldException("ISTANZEONERI.CODICEINVENTARIO mancante e obbligatorio") );
					}
				}
			}
			#endregion

			#region ISTANZEONERI.FLENTRATAUSCITA && ISTANZEONERI.FLRIBASSO

			if ( IsStringEmpty( p_class.FLENTRATAUSCITA ) )
				p_class.FLENTRATAUSCITA = "1";

			if ( IsStringEmpty( p_class.FLRIBASSO ) )
				p_class.FLRIBASSO = "0";

			if ( p_class.FLENTRATAUSCITA != "0" && p_class.FLENTRATAUSCITA != "1" )
				throw( new TypeMismatchException("Impossibile inserire" + p_class.FLENTRATAUSCITA + " in ISTANZEONERI.FLENTRATAUSCITA") );

			if ( p_class.FLRIBASSO != "0" && p_class.FLRIBASSO != "1" )
				throw( new TypeMismatchException("Impossibile inserire" + p_class.FLRIBASSO + " in ISTANZEONERI.FLRIBASSO") );

			#endregion

			RequiredFieldValidate( p_class , ambitoValidazione);
			
			ForeignValidate( p_class );
		}

		private void ForeignValidate ( IstanzeOneri p_class )
		{
			#region ISTANZEONERI.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if (  this.recordCount( "ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZEONERI.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region ISTANZEONERI.CODICEUTENTE
			if ( ! IsStringEmpty( p_class.CODICEUTENTE ) )
			{
				if (  this.recordCount( "RESPONSABILI","CODICERESPONSABILE","WHERE CODICERESPONSABILE = " + p_class.CODICEUTENTE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZEONERI.CODICEUTENTE non trovato nella tabella RESPONSABILI"));
				}
			}
			#endregion

			#region ISTANZEONERI.FKIDTIPOCAUSALE
			if ( ! IsStringEmpty( p_class.FKIDTIPOCAUSALE ) )
			{
				if (  this.recordCount( "TIPICAUSALIONERI","CO_ID","WHERE CO_ID = " + p_class.FKIDTIPOCAUSALE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZEONERI.FKIDTIPOCAUSALE non trovato nella tabella TIPICAUSALIONERI"));
				}
			}
			#endregion

			#region ISTANZEONERI.CODICEINVENTARIO
			if ( ! IsStringEmpty( p_class.CODICEINVENTARIO ) )
			{
				if (  this.recordCount( "INVENTARIOPROCEDIMENTI","CODICEINVENTARIO","WHERE CODICEINVENTARIO = " + p_class.CODICEINVENTARIO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZEONERI.CODICEINVENTARIO non trovato nella tabella INVENTARIOPROCEDIMENTI"));
				}
			}
			#endregion

			#region ISTANZEONERI.FKMODALITAPAGAMENTO
			if ( ! IsStringEmpty( p_class.FKMODALITAPAGAMENTO ) )
			{
				if (  this.recordCount( "TIPIMODALITAPAGAMENTO","MP_ID","WHERE MP_ID = " + p_class.FKMODALITAPAGAMENTO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZEONERI.FKMODALITAPAGAMENTO non trovato nella tabella TIPIMODALITAPAGAMENTO"));
				}
			}
			#endregion

			#region ISTANZEONERI.TIPOMOVIMENTO
			if ( ! IsStringEmpty( p_class.TIPOMOVIMENTO ) )
			{
				if (  this.recordCount( "TIPIMOVIMENTO","TIPOMOVIMENTO","WHERE TIPOMOVIMENTO = '" + p_class.TIPOMOVIMENTO.Replace("'","''") + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZEONERI.TIPOMOVIMENTO non trovato nella tabella TIPIMOVIMENTO"));
				}
			}
			#endregion
		}
		#endregion

        #region Metodi usati per la rateizzazione/derateizzazione 

        private string GetDate(string dateDB)
        {
            string date = string.Empty;
            switch (db.ConnectionDetails.ProviderType)
            {
                case ProviderType.OracleClient:
                    date = "TO_CHAR(" + dateDB + ",'DD/MM/YYYY')";
                    break;
                case ProviderType.SqlClient:
                    date = "CONVERT(VARCHAR," + dateDB + ",103)";
                    break;
                case ProviderType.MySqlClient:
                    date = "date_format(" + dateDB + ",'%d/%m/%Y')";
                    break;
            }

            return date;
        }

        //Restituisce la lista degli oneri di un'istanza aventi una causale specifica
        public DataSet GetSommeDateListaOneri(string idComune, int codiceIstanza, int tipoCausale, int idTestata)
        {
            DataSet ds = new DataSet();

            IstanzeOneri istOneri = new IstanzeOneri();
            istOneri.SelectColumns = "SUM(istanzeoneri.prezzo) as prezzo, SUM(istanzeoneri.prezzoistruttoria) as prezzoistruttoria, MIN(istanzeoneri.datascadenza) as datascadenza, MIN(istanzeoneri.data) as data";
            istOneri.IDCOMUNE = idComune;
            istOneri.CODICEISTANZA = codiceIstanza.ToString();
            istOneri.FKIDTIPOCAUSALE = tipoCausale.ToString();
            istOneri.OthersWhereClause.Add("istanzeoneri.numerorata is not null");
            istOneri.OthersWhereClause.Add("istanzeoneri.datapagamento is null");
            istOneri.OthersTables.Add("tipicausalioneri");
            istOneri.OthersWhereClause.Add("istanzeoneri.idcomune = tipicausalioneri.idcomune  AND istanzeoneri.fkidtipocausale = tipicausalioneri.co_id");
            if (idTestata != int.MinValue)
                istOneri.OthersWhereClause.Add("istanzecalcolocanoni_o.fk_idtestata = " + idTestata);
            else
                istOneri.OthersWhereClause.Add("istanzecalcolocanoni_o.fk_idtestata is null ");
            istOneri.OthersJoinClause.Add("left join istanzecalcolocanoni_o on istanzeoneri.idcomune = istanzecalcolocanoni_o.idcomune AND istanzeoneri.id = istanzecalcolocanoni_o.fk_idistoneri");

            using (IDbCommand cmd = db.CreateCommand(istOneri))
            {
                IDataAdapter da = db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            return ds;
        }

        //Restituisce la lista delle testate per una specifica causale
        public DataSet GetListaTestate(string idComune, int codiceIstanza, int tipoCausale)
        {
            DataSet ds = new DataSet();

            IstanzeOneri istOneri = new IstanzeOneri();
            istOneri.SelectColumns = "istanzecalcolocanoni_o.fk_idtestata ";
            istOneri.IDCOMUNE = idComune;
            istOneri.CODICEISTANZA = codiceIstanza.ToString();
            istOneri.FKIDTIPOCAUSALE = tipoCausale.ToString();
            istOneri.OthersWhereClause.Add("istanzeoneri.numerorata is not null");
            istOneri.OthersWhereClause.Add("istanzeoneri.datapagamento is null");
            istOneri.OthersTables.Add("tipicausalioneri");
            istOneri.OthersWhereClause.Add("istanzeoneri.idcomune = tipicausalioneri.idcomune  AND istanzeoneri.fkidtipocausale = tipicausalioneri.co_id");
            istOneri.OthersJoinClause.Add("left join istanzecalcolocanoni_o on istanzeoneri.idcomune = istanzecalcolocanoni_o.idcomune AND istanzeoneri.id = istanzecalcolocanoni_o.fk_idistoneri");


            using (IDbCommand cmd = db.CreateCommand(istOneri))
            {
                cmd.CommandText += " group by istanzecalcolocanoni_o.fk_idtestata";
                //cmd.CommandText += " order by istanzeoneri.numerorata";
                IDataAdapter da = db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            return ds;
        }


        //Restituisce la lista degli oneri di un'istanza aventi una causale specifica ed una testata specifica
        public DataSet GetListaOneri(string idComune, int codiceIstanza, int tipoCausale, int idTestata, TipoOperazione tipoOper)
        {
            DataSet ds = new DataSet();

            IstanzeOneri istOneri = new IstanzeOneri();
            istOneri.SelectColumns = "istanzeoneri.prezzo,istanzeoneri.prezzoistruttoria," + GetDate("istanzeoneri.datascadenza") + " as datascadenza," + GetDate("istanzeoneri.data") + " as data,istanzeoneri.fkidtipocausale,tipicausalioneri.co_descrizione,istanzeoneri.nr_documento,istanzeoneri.id ";
            istOneri.IDCOMUNE = idComune;
            istOneri.CODICEISTANZA = codiceIstanza.ToString();
            istOneri.FKIDTIPOCAUSALE = tipoCausale.ToString();
            istOneri.OthersWhereClause.Add("istanzeoneri.numerorata is not null");
            istOneri.OthersWhereClause.Add("istanzeoneri.datapagamento is null");
            istOneri.OthersTables.Add("tipicausalioneri");
            istOneri.OthersWhereClause.Add("istanzeoneri.idcomune = tipicausalioneri.idcomune  AND istanzeoneri.fkidtipocausale = tipicausalioneri.co_id");
            if (tipoOper == TipoOperazione.Cancellazione)
            {
                if (idTestata != int.MinValue)
                    istOneri.OthersWhereClause.Add("istanzecalcolocanoni_o.fk_idtestata = " + idTestata);
                else
                    istOneri.OthersWhereClause.Add("istanzecalcolocanoni_o.fk_idtestata is null ");

                istOneri.OthersJoinClause.Add("left join istanzecalcolocanoni_o on istanzeoneri.idcomune = istanzecalcolocanoni_o.idcomune AND istanzeoneri.id = istanzecalcolocanoni_o.fk_idistoneri");
            }

            istOneri.OrderBy = "istanzeoneri.datascadenza,istanzeoneri.id";

            using (IDbCommand cmd = db.CreateCommand(istOneri))
            {
                IDataAdapter da = db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            return ds;
        }

        //Restituisce la lista degli oneri di un'istanza aventi una causale specifica
        public DataSet GetListaOneri(string idComune, int codiceIstanza, int tipoCausale)
        {
            return GetListaOneri(idComune, codiceIstanza, tipoCausale, int.MinValue, TipoOperazione.Lista);
        }

        //Cancella l'onere di un'istanza aventi una specifica causale e testata ed eventuali record collegati su ISTANZECALCOLOCANONI_O
        public void DeleteOnere(string idComune, int codiceIstanza, int tipoCausale, int idTestata)
        {
            DataSet ds = GetListaOneri(idComune, codiceIstanza, tipoCausale, idTestata, TipoOperazione.Cancellazione);
            if ((ds.Tables[0] != null) && (ds.Tables[0].Rows.Count != 0))
            {
                foreach (DataRow dr in ds.Tables[0].Rows)
                {
                    IstanzeOneri istOneri = GetById(idComune, dr["id"].ToString());
                    Delete(istOneri);
                }
            }
        }
        #endregion

		/// <summary>
		/// Se i pagamenti regulus sono attivi calcola il nr documento da assegnare all'onere.
		/// </summary>
		/// <param name="idComune">filtro idcomune</param>
		/// <param name="codiceIstanza">Codice istanza</param>
		/// <param name="codiceCausale">Codice causale</param>
		/// <returns>Numero documento</returns>
		public string CalcolaNumeroDocumento(string idComune, string idComuneAlias, int codiceIstanza , int codiceCausale)
		{
			var istanza = new IstanzeMgr(db).GetById( idComune , codiceIstanza);

			var tipoCausale = new TipiCausaliOneriMgr(db).GetById(idComune, codiceCausale);

			if (tipoCausale.PagamentiRegulus.GetValueOrDefault(0) == 0)
				return String.Empty;
			
			/* se esiste già una riga negli oneri dell'istanza con la stessa causale, allora NR_DOCUMENTO deve essere identico */
			var filtroIstanzeOneri = new IstanzeOneri{ IDCOMUNE = idComune , 
													   CODICEISTANZA = codiceIstanza.ToString(),
													   FKIDTIPOCAUSALE = codiceCausale.ToString() };

			filtroIstanzeOneri.OthersWhereClause.Add("NR_DOCUMENTO IS NOT NULL");

			var listaIstanzeOneri = new IstanzeOneriMgr(db).GetList(filtroIstanzeOneri);

			if (listaIstanzeOneri.Count > 0 && !String.IsNullOrEmpty(listaIstanzeOneri[0].NR_DOCUMENTO))
				return listaIstanzeOneri[0].NR_DOCUMENTO;

			/* Se la verticalizzazione SistemaPagamentiAttivo è attiva calcolo in nr documento */
			var verticalizzazionePagamenti = new VerticalizzazioneSistemapagamentiAttivo(idComuneAlias, istanza.SOFTWARE );

			if (!verticalizzazionePagamenti.Attiva)
				return String.Empty;

			var nrDocumento = verticalizzazionePagamenti.Numerodocumento;

			if(String.IsNullOrEmpty(nrDocumento))
				return string.Empty;

			var listaCampiSostituzione = new Dictionary<string,string>();

			listaCampiSostituzione["FKIDTIPOCAUSALE"] = codiceCausale.ToString();
			listaCampiSostituzione["DATAPROTOCOLLO"] = istanza.DATAPROTOCOLLO.HasValue ? istanza.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : String.Empty;
			listaCampiSostituzione["DATA"] = istanza.DATA.Value.ToString("dd/MM/yyyy");
			listaCampiSostituzione["CODICEISTANZA"] = istanza.CODICEISTANZA;
			listaCampiSostituzione["NUMEROPROTOCOLLO"] = istanza.NUMEROPROTOCOLLO;
			listaCampiSostituzione["NUMEROISTANZA"] = istanza.NUMEROISTANZA;
			listaCampiSostituzione["SOFTWARE"] = istanza.SOFTWARE;
			listaCampiSostituzione["CODICECOMUNE"] = istanza.CODICECOMUNE;

			foreach (var key in listaCampiSostituzione.Keys)
			{
				nrDocumento = nrDocumento.Replace( "[" + key + "]", listaCampiSostituzione[key] );
			}

			return nrDocumento;
		}
    }

    public enum TipoOperazione
    {
        Cancellazione, Lista
    }
}

