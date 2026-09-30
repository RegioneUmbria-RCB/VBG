using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneOneri;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per IstanzeOneriMgr.\n	/// </summary>
    public class IstanzeOneriMgr : BaseManager
    {
        public enum TipoOperazione
        {
            Cancellazione, Lista
        }

        private readonly IOneriService _oneriService;
        private readonly AuthenticationInfo _authenticationInfo;
        private readonly ILog _log = LogManager.GetLogger(typeof(IstanzeOneriMgr));

        public IstanzeOneriMgr(DataBase dataBase, AuthenticationInfo authInfo) : base(dataBase)
        {
            this._authenticationInfo = authInfo;
            this._oneriService = new OneriService(authInfo);
        }




        #region Metodi per l'accesso di base al DB

        //private IstanzeOneri DataIntegrations(IstanzeOneri p_class, string idComuneAlias)
        //{
        //    IstanzeOneri retVal = (IstanzeOneri)p_class.Clone();

        //    #region Integrazione del campo NUMERORATA
        //    if (String.IsNullOrEmpty(retVal.NUMERORATA))
        //    {
        //        retVal.NUMERORATA = "1";

        //        IstanzeOneri pClass = new IstanzeOneri();
        //        pClass.IDCOMUNE = retVal.IDCOMUNE;
        //        pClass.CODICEISTANZA = retVal.CODICEISTANZA;
        //        pClass.FKIDTIPOCAUSALE = retVal.FKIDTIPOCAUSALE;
        //        pClass.OrderBy = "NUMERORATA DESC";

        //        //IstanzeOneriMgr istOneriMgr = new IstanzeOneriMgr( db );
        //        List<IstanzeOneri> al = this.GetList(pClass);

        //        if (al != null && al.Count > 0)
        //        {
        //            pClass = al[0];
        //            if (!String.IsNullOrEmpty(pClass.NUMERORATA))
        //                retVal.NUMERORATA = (Convert.ToInt32(pClass.NUMERORATA) + 1).ToString();
        //        }
        //    }

        //    retVal.NR_DOCUMENTO = SetNumeroDocumento(retVal, idComuneAlias);

        //    #endregion

        //    return retVal;
        //}

        //private string SetNumeroDocumento(IstanzeOneri p_class, string idComuneAlias)
        //{
        //    if (string.IsNullOrEmpty(p_class.NR_DOCUMENTO))
        //    {
        //        TipiCausaliOneri tco = new TipiCausaliOneriMgr(db).GetById(p_class.IDCOMUNE, Convert.ToInt32(p_class.FKIDTIPOCAUSALE));
        //        if (tco.PagamentiRegulus.GetValueOrDefault(int.MinValue) == 1)
        //        {
        //            //verifico se c'è già una riga su istanzeoneri che ha il codice del pagamento di regulus impostato
        //            IstanzeOneri filtro = new IstanzeOneri();
        //            filtro.IDCOMUNE = p_class.IDCOMUNE;
        //            filtro.CODICEISTANZA = p_class.CODICEISTANZA;
        //            filtro.FKIDTIPOCAUSALE = p_class.FKIDTIPOCAUSALE;
        //            filtro.OthersWhereClause.Add("NR_DOCUMENTO IS NOT NULL");


        //            var lista = this.GetList(filtro);
        //            foreach (IstanzeOneri io in lista)
        //            {
        //                p_class.NR_DOCUMENTO = io.NR_DOCUMENTO;
        //                break;
        //            }

        //            //se arrivo qui vuol dire che non è stato possibile risalire ad un codice di pagamento esistente
        //            //e quindi va calcolato
        //            //1. Prendo il software dall'istanza
        //            Istanze istanza = new IstanzeMgr(db).GetById(p_class.IDCOMUNE, Convert.ToInt32(p_class.CODICEISTANZA));

        //            VerticalizzazioneSistemapagamentiAttivo vsa = new VerticalizzazioneSistemapagamentiAttivo(idComuneAlias, istanza.SOFTWARE);
        //            string nrDocumento = vsa.Numerodocumento;

        //            //[DATAPROTOCOLLO]
        //            if (istanza.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue) > DateTime.MinValue)
        //            {
        //                nrDocumento = nrDocumento.Replace("[DATAPROTOCOLLO]", istanza.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy"));
        //            }
        //            else
        //            {
        //                nrDocumento = nrDocumento.Replace("[DATAPROTOCOLLO]", "");
        //            }

        //            //[DATA]
        //            nrDocumento = nrDocumento.Replace("[DATA]", istanza.DATA.Value.ToString("dd/MM/yyyy"));

        //            //[CODICEISTANZA]
        //            nrDocumento = nrDocumento.Replace("[CODICEISTANZA]", istanza.CODICEISTANZA);

        //            //[NUMEROPROTOCOLLO]
        //            if (!string.IsNullOrEmpty(istanza.NUMEROPROTOCOLLO))
        //            {
        //                nrDocumento = nrDocumento.Replace("[NUMEROPROTOCOLLO]", istanza.NUMEROPROTOCOLLO);
        //            }
        //            else
        //            {
        //                nrDocumento = nrDocumento.Replace("[NUMEROPROTOCOLLO]", "");
        //            }

        //            //[NUMEROISTANZA]
        //            nrDocumento = nrDocumento.Replace("[NUMEROISTANZA]", istanza.NUMEROISTANZA);

        //            //[SOFTWARE]
        //            nrDocumento = nrDocumento.Replace("[SOFTWARE]", istanza.SOFTWARE);

        //            //[CODICECOMUNE]
        //            if (!string.IsNullOrEmpty(istanza.CODICECOMUNE))
        //            {
        //                nrDocumento = nrDocumento.Replace("[CODICECOMUNE]", istanza.CODICECOMUNE);
        //            }
        //            else
        //            {
        //                nrDocumento = nrDocumento.Replace("[CODICECOMUNE]", "");
        //            }

        //            //[FKIDTIPOCAUSALE]
        //            nrDocumento = nrDocumento.Replace("[FKIDTIPOCAUSALE]", p_class.FKIDTIPOCAUSALE);

        //            p_class.NR_DOCUMENTO = nrDocumento;
        //        }
        //    }

        //    return p_class.NR_DOCUMENTO;
        //}

        public IstanzeOneri GetById(string idComune, int id)
        {
            return this.GetById(idComune, id.ToString());
        }


        public IstanzeOneri GetById(String pIDCOMUNE, String pID)
        {
            var retVal = new IstanzeOneri();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.ID = pID;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeOneri> GetList(IstanzeOneri p_class)
        {
            return this.db.GetClassList(p_class).ToList<IstanzeOneri>();
        }

        //private void EffettuaCancellazioneACascata(IstanzeOneri cls)
        //{
        //    IstanzeOneri_Canoni ioc = new IstanzeOneri_Canoni();
        //    ioc.Idcomune = cls.IDCOMUNE;
        //    ioc.FkIdIstoneri = Convert.ToInt32(cls.ID);
        //    List<IstanzeOneri_Canoni> lIstanzeOneri_Canoni = new IstanzeOneri_CanoniMgr(db).GetList(ioc);
        //    foreach (IstanzeOneri_Canoni tioc in lIstanzeOneri_Canoni)
        //    {
        //        IstanzeOneri_CanoniMgr mgr = new IstanzeOneri_CanoniMgr(db);
        //        mgr.Delete(tioc);
        //    }

        //    IstanzeCalcoloCanoniO icco = new IstanzeCalcoloCanoniO();
        //    icco.Idcomune = cls.IDCOMUNE;
        //    icco.FkIdistoneri = Convert.ToInt32(cls.ID);
        //    List<IstanzeCalcoloCanoniO> lIstanzeCalcoloCanoniO = new IstanzeCalcoloCanoniOMgr(db).GetList(icco);
        //    foreach (IstanzeCalcoloCanoniO ticco in lIstanzeCalcoloCanoniO)
        //    {
        //        IstanzeCalcoloCanoniOMgr mgr = new IstanzeCalcoloCanoniOMgr(db);
        //        mgr.Delete(ticco);
        //    }
        //}



        /*
		public IstanzeOneri Insert( IstanzeOneri p_class, string idComuneAlias )
		{
            p_class = DataIntegrations(p_class, idComuneAlias);

			Validate(p_class, AmbitoValidazione.Insert);

			db.Insert( p_class );
			
			return p_class;
		}
		*/
        //public IstanzeOneri Update( IstanzeOneri p_class, string idComuneAlias )
        //{
        //          p_class = DataIntegrations(p_class, idComuneAlias);

        //          Validate(p_class, AmbitoValidazione.Update);

        //	db.Update( p_class );

        //	return p_class;
        //}

        //private void Validate(IstanzeOneri p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
        //{
        //	#region ISTANZEONERI.DATA
        //	if ( IsObjectEmpty( p_class.DATA ) )
        //	{
        //		p_class.DATA = DateTime.Now;
        //	}
        //	#endregion

        //	#region ISTANZEONERI.CODICEINVENTARIO
        //	if ( ! string.IsNullOrEmpty( p_class.FKIDTIPOCAUSALE ) )
        //	{
        //		string cmdtext = String.Empty;

        //		cmdtext += "SELECT TIPICAUSALIONERI.CO_SERICHIEDEENDO " +
        //				   "FROM ISTANZEONERI, TIPICAUSALIONERI " +
        //				   "WHERE TIPICAUSALIONERI.IDCOMUNE=ISTANZEONERI.IDCOMUNE AND " +
        //				   "TIPICAUSALIONERI.CO_ID=ISTANZEONERI.FKIDTIPOCAUSALE AND " +
        //				   "ISTANZEONERI.FKIDTIPOCAUSALE = " + p_class.FKIDTIPOCAUSALE + " AND " + 
        //				   "ISTANZEONERI.IDCOMUNE = '" + p_class.IDCOMUNE + "'";


        //              bool closeCnn = false;
        //              if (db.Connection.State == ConnectionState.Closed)
        //              {
        //                  closeCnn = true;
        //                  db.Connection.Open();
        //              }

        //              object obj = db.CreateCommand( cmdtext ).ExecuteScalar();

        //              if (closeCnn)
        //                  db.Connection.Close();

        //		if ( ! IsObjectEmpty( obj ) )
        //		{
        //			if ( obj.ToString() == "1" && String.IsNullOrEmpty( p_class.CODICEINVENTARIO ) )
        //			{
        //				throw( new RequiredFieldException("ISTANZEONERI.CODICEINVENTARIO mancante e obbligatorio") );
        //			}
        //		}
        //	}
        //	#endregion

        //	#region ISTANZEONERI.FLENTRATAUSCITA && ISTANZEONERI.FLRIBASSO

        //	if (String.IsNullOrEmpty( p_class.FLENTRATAUSCITA ) )
        //		p_class.FLENTRATAUSCITA = "1";

        //	if (String.IsNullOrEmpty( p_class.FLRIBASSO ) )
        //		p_class.FLRIBASSO = "0";

        //	if ( p_class.FLENTRATAUSCITA != "0" && p_class.FLENTRATAUSCITA != "1" )
        //		throw( new TypeMismatchException("Impossibile inserire" + p_class.FLENTRATAUSCITA + " in ISTANZEONERI.FLENTRATAUSCITA") );

        //	if ( p_class.FLRIBASSO != "0" && p_class.FLRIBASSO != "1" )
        //		throw( new TypeMismatchException("Impossibile inserire" + p_class.FLRIBASSO + " in ISTANZEONERI.FLRIBASSO") );

        //	#endregion

        //	RequiredFieldValidate( p_class , ambitoValidazione);

        //	ForeignValidate( p_class );
        //}

        //private void ForeignValidate ( IstanzeOneri p_class )
        //{
        //	#region ISTANZEONERI.CODICEISTANZA
        //	if ( !String.IsNullOrEmpty( p_class.CODICEISTANZA ) )
        //	{
        //		if (  this.recordCount( "ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
        //		{
        //			throw( new RecordNotfoundException("ISTANZEONERI.CODICEISTANZA non trovato nella tabella ISTANZE"));
        //		}
        //	}
        //	#endregion

        //	#region ISTANZEONERI.CODICEUTENTE
        //	if ( !String.IsNullOrEmpty( p_class.CODICEUTENTE ) )
        //	{
        //		if (  this.recordCount( "RESPONSABILI","CODICERESPONSABILE","WHERE CODICERESPONSABILE = " + p_class.CODICEUTENTE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
        //		{
        //			throw( new RecordNotfoundException("ISTANZEONERI.CODICEUTENTE non trovato nella tabella RESPONSABILI"));
        //		}
        //	}
        //	#endregion

        //	#region ISTANZEONERI.FKIDTIPOCAUSALE
        //	if ( !String.IsNullOrEmpty( p_class.FKIDTIPOCAUSALE ) )
        //	{
        //		if (  this.recordCount( "TIPICAUSALIONERI","CO_ID","WHERE CO_ID = " + p_class.FKIDTIPOCAUSALE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
        //		{
        //			throw( new RecordNotfoundException("ISTANZEONERI.FKIDTIPOCAUSALE non trovato nella tabella TIPICAUSALIONERI"));
        //		}
        //	}
        //	#endregion

        //	#region ISTANZEONERI.CODICEINVENTARIO
        //	if ( !String.IsNullOrEmpty( p_class.CODICEINVENTARIO ) )
        //	{
        //		if (  this.recordCount( "INVENTARIOPROCEDIMENTI","CODICEINVENTARIO","WHERE CODICEINVENTARIO = " + p_class.CODICEINVENTARIO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
        //		{
        //			throw( new RecordNotfoundException("ISTANZEONERI.CODICEINVENTARIO non trovato nella tabella INVENTARIOPROCEDIMENTI"));
        //		}
        //	}
        //	#endregion

        //	#region ISTANZEONERI.FKMODALITAPAGAMENTO
        //	if ( !String.IsNullOrEmpty( p_class.FKMODALITAPAGAMENTO ) )
        //	{
        //		if (  this.recordCount( "TIPIMODALITAPAGAMENTO","MP_ID","WHERE MP_ID = " + p_class.FKMODALITAPAGAMENTO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
        //		{
        //			throw( new RecordNotfoundException("ISTANZEONERI.FKMODALITAPAGAMENTO non trovato nella tabella TIPIMODALITAPAGAMENTO"));
        //		}
        //	}
        //	#endregion

        //	#region ISTANZEONERI.TIPOMOVIMENTO
        //	if ( !String.IsNullOrEmpty( p_class.TIPOMOVIMENTO ) )
        //	{
        //		if (  this.recordCount( "TIPIMOVIMENTO","TIPOMOVIMENTO","WHERE TIPOMOVIMENTO = '" + p_class.TIPOMOVIMENTO.Replace("'","''") + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
        //		{
        //			throw( new RecordNotfoundException("ISTANZEONERI.TIPOMOVIMENTO non trovato nella tabella TIPIMOVIMENTO"));
        //		}
        //	}
        //	#endregion
        //}
        #endregion

        #region Metodi usati per la rateizzazione/derateizzazione 

        private string GetDate(string dateDB)
        {
            string date = string.Empty;
            switch (this.db.ConnectionDetails.ProviderType)
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
        private DataSet GetSommeDateListaOneri(string idComune, int codiceIstanza, int tipoCausale, int idTestata)
        {
            var ds = new DataSet();

            var istOneri = new IstanzeOneri();
            istOneri.SelectColumns = "SUM(istanzeoneri.prezzo) as prezzo, sum(ISTANZEONERI.IMPORTO_INTERESSE) as interesse, SUM(istanzeoneri.prezzoistruttoria) as prezzoistruttoria, MIN(istanzeoneri.datascadenza) as datascadenza, MIN(istanzeoneri.data) as data";
            istOneri.IDCOMUNE = idComune;
            istOneri.CODICEISTANZA = codiceIstanza.ToString();
            istOneri.FKIDTIPOCAUSALE = tipoCausale.ToString();
            istOneri.OthersWhereClause.Add("istanzeoneri.numerorata is not null");
            istOneri.OthersWhereClause.Add("istanzeoneri.datapagamento is null");
            istOneri.OthersTables.Add("tipicausalioneri");
            istOneri.OthersWhereClause.Add("istanzeoneri.idcomune = tipicausalioneri.idcomune  AND istanzeoneri.fkidtipocausale = tipicausalioneri.co_id");
            // Controlla se l'id testata è stato passato
            if (idTestata != int.MinValue)
                istOneri.OthersWhereClause.Add("istanzecalcolocanoni_o.fk_idtestata = " + idTestata);
            else
                istOneri.OthersWhereClause.Add("istanzecalcolocanoni_o.fk_idtestata is null ");
            istOneri.OthersJoinClause.Add("left join istanzecalcolocanoni_o on istanzeoneri.idcomune = istanzecalcolocanoni_o.idcomune AND istanzeoneri.id = istanzecalcolocanoni_o.fk_idistoneri");

            using (IDbCommand cmd = this.db.CreateCommand(istOneri))
            {
                IDataAdapter da = this.db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            return ds;
        }

        //Restituisce la lista delle testate per una specifica causale
        public DataSet GetListaTestate(string idComune, int codiceIstanza, int tipoCausale)
        {
            var ds = new DataSet();

            var istOneri = new IstanzeOneri();
            istOneri.SelectColumns = "istanzecalcolocanoni_o.fk_idtestata ";
            istOneri.IDCOMUNE = idComune;
            istOneri.CODICEISTANZA = codiceIstanza.ToString();
            istOneri.FKIDTIPOCAUSALE = tipoCausale.ToString();
            istOneri.OthersWhereClause.Add("istanzeoneri.numerorata is not null");
            istOneri.OthersWhereClause.Add("istanzeoneri.datapagamento is null");
            istOneri.OthersTables.Add("tipicausalioneri");
            istOneri.OthersWhereClause.Add("istanzeoneri.idcomune = tipicausalioneri.idcomune  AND istanzeoneri.fkidtipocausale = tipicausalioneri.co_id");
            istOneri.OthersJoinClause.Add("left join istanzecalcolocanoni_o on istanzeoneri.idcomune = istanzecalcolocanoni_o.idcomune AND istanzeoneri.id = istanzecalcolocanoni_o.fk_idistoneri");


            using (IDbCommand cmd = this.db.CreateCommand(istOneri))
            {
                cmd.CommandText += " group by istanzecalcolocanoni_o.fk_idtestata";
                //cmd.CommandText += " order by istanzeoneri.numerorata";
                IDataAdapter da = this.db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            return ds;
        }

        //internal void UpdatePrezzoIstruttoria(string idComune, string id, double somma)
        //{
        //    var io = this.GetById(idComune, id);
        //    io.PREZZOISTRUTTORIA = somma;

        //    this.Update(io);
        //}


        //Restituisce la lista degli oneri di un'istanza aventi una causale specifica ed una testata specifica
        private DataSet GetListaOneri(string idComune, int codiceIstanza, int tipoCausale, int idTestata, TipoOperazione tipoOper)
        {
            var ds = new DataSet();

            var istOneri = new IstanzeOneri();
            istOneri.SelectColumns = "istanzeoneri.prezzo,istanzeoneri.prezzoistruttoria," + this.GetDate("istanzeoneri.datascadenza") + " as datascadenza," + this.GetDate("istanzeoneri.data") + " as data,istanzeoneri.fkidtipocausale,tipicausalioneri.co_descrizione,istanzeoneri.nr_documento,istanzeoneri.id ";
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

            using (IDbCommand cmd = this.db.CreateCommand(istOneri))
            {
                IDataAdapter da = this.db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            return ds;
        }

        //Restituisce la lista degli oneri di un'istanza aventi una causale specifica
        public DataSet GetListaOneri(string idComune, int codiceIstanza, int tipoCausale)
        {
            return this.GetListaOneri(idComune, codiceIstanza, tipoCausale, int.MinValue, TipoOperazione.Lista);
        }

        //internal void UpdateDataPagamento(string idComune, string id, DateTime? data)
        //{
        //    var io = this.GetById(idComune, id);
        //    io.DATAPAGAMENTO = data;

        //    this.Update(io);
        //}

        //Cancella l'onere di un'istanza aventi una specifica causale e testata ed eventuali record collegati su ISTANZECALCOLOCANONI_O
        public void DeleteOnere(string idComune, int codiceIstanza, int tipoCausale, int idTestata)
        {
            DataSet ds = this.GetListaOneri(idComune, codiceIstanza, tipoCausale, idTestata, TipoOperazione.Cancellazione);
            if ((ds.Tables[0] != null) && (ds.Tables[0].Rows.Count != 0))
            {
                foreach (DataRow dr in ds.Tables[0].Rows)
                {
                    this._oneriService.Elimina(Convert.ToInt32(dr["id"]));
                }
            }
        }

        public bool IsOnereRateizzato(string idComune, int codiceIstanza, int codiceOnere)
        {
            var sql = $@"select 
                            count(*)
                        from
                            istanzeoneri
                        where
                            istanzeoneri.idcomune = {this.db.Specifics.QueryParameterName("idComune")} and
                            istanzeoneri.codiceistanza = {this.db.Specifics.QueryParameterName("codiceIstanza")} and
                            istanzeoneri.id = {this.db.Specifics.QueryParameterName("codiceOnere")} and
                            istanzeoneri.flag_onere_rateizzato = 1";

            var count = this.db.ExecuteScalar(sql, 0,
                mp =>
                {
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                    mp.AddParameter("codiceOnere", codiceOnere);
                });

            return count > 0;
        }

        public bool IsRaggruppamentoRateizzato(string idComune, int codiceIstanza, int codiceRaggruppamento)
        {
            var sql = $@"select 
                            count(*)
                        from
                            istanzeoneri
                                inner join tipicausalioneri on
                                    tipicausalioneri.idcomune = istanzeoneri.idcomune and
                                    tipicausalioneri.co_id = istanzeoneri.fkidtipocausale
                        where
                            istanzeoneri.idcomune = {this.db.Specifics.QueryParameterName("idComune")} and
                            istanzeoneri.codiceistanza = {this.db.Specifics.QueryParameterName("codiceIstanza")} and
                            tipicausalioneri.fk_rco_id = {this.db.Specifics.QueryParameterName("codiceRaggruppamento")} and
                            istanzeoneri.flag_onere_rateizzato = 1";

            var count = this.db.ExecuteScalar(sql, 0,
                mp =>
                {
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                    mp.AddParameter("codiceRaggruppamento", codiceRaggruppamento);
                });

            return count > 0;
        }

        #endregion

        /// <summary>
        /// Se i pagamenti regulus sono attivi calcola il nr documento da assegnare all'onere.
        /// </summary>
        /// <param name="idComune">filtro idcomune</param>
        /// <param name="codiceIstanza">Codice istanza</param>
        /// <param name="codiceCausale">Codice causale</param>
        /// <returns>Numero documento</returns>
        //public string CalcolaNumeroDocumento(string idComune, string idComuneAlias, int codiceIstanza , int codiceCausale)
        //{
        //	var istanza = new IstanzeMgr(db).GetById( idComune , codiceIstanza);

        //	var tipoCausale = new TipiCausaliOneriMgr(db).GetById(idComune, codiceCausale);

        //	if (tipoCausale.PagamentiRegulus.GetValueOrDefault(0) == 0)
        //		return String.Empty;

        //	/* se esiste già una riga negli oneri dell'istanza con la stessa causale, allora NR_DOCUMENTO deve essere identico */
        //	var filtroIstanzeOneri = new IstanzeOneri{ IDCOMUNE = idComune , 
        //											   CODICEISTANZA = codiceIstanza.ToString(),
        //											   FKIDTIPOCAUSALE = codiceCausale.ToString() };

        //	filtroIstanzeOneri.OthersWhereClause.Add("NR_DOCUMENTO IS NOT NULL");

        //	var listaIstanzeOneri = new IstanzeOneriMgr(db).GetList(filtroIstanzeOneri);

        //	if (listaIstanzeOneri.Count > 0 && !String.IsNullOrEmpty(listaIstanzeOneri[0].NR_DOCUMENTO))
        //		return listaIstanzeOneri[0].NR_DOCUMENTO;

        //	/* Se la verticalizzazione SistemaPagamentiAttivo è attiva calcolo in nr documento */
        //	var verticalizzazionePagamenti = new VerticalizzazioneSistemapagamentiAttivo(idComuneAlias, istanza.SOFTWARE );

        //	if (!verticalizzazionePagamenti.Attiva)
        //		return String.Empty;

        //	var nrDocumento = verticalizzazionePagamenti.Numerodocumento;

        //	if(String.IsNullOrEmpty(nrDocumento))
        //		return string.Empty;

        //	var listaCampiSostituzione = new Dictionary<string,string>();

        //	listaCampiSostituzione["FKIDTIPOCAUSALE"] = codiceCausale.ToString();
        //	listaCampiSostituzione["DATAPROTOCOLLO"] = istanza.DATAPROTOCOLLO.HasValue ? istanza.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : String.Empty;
        //	listaCampiSostituzione["DATA"] = istanza.DATA.Value.ToString("dd/MM/yyyy");
        //	listaCampiSostituzione["CODICEISTANZA"] = istanza.CODICEISTANZA;
        //	listaCampiSostituzione["NUMEROPROTOCOLLO"] = istanza.NUMEROPROTOCOLLO;
        //	listaCampiSostituzione["NUMEROISTANZA"] = istanza.NUMEROISTANZA;
        //	listaCampiSostituzione["SOFTWARE"] = istanza.SOFTWARE;
        //	listaCampiSostituzione["CODICECOMUNE"] = istanza.CODICECOMUNE;

        //	foreach (var key in listaCampiSostituzione.Keys)
        //	{
        //		nrDocumento = nrDocumento.Replace( "[" + key + "]", listaCampiSostituzione[key] );
        //	}

        //	return nrDocumento;
        //}

        public void UpdateImporto(string idComune, int idOnere, decimal prezzo)
        {
            var sql = $"update ISTANZEONERI set prezzo = {this.db.Specifics.QueryParameterName("prezzo")} " +
                      $"where idcomune = {this.db.Specifics.QueryParameterName("idComune")} and id = {this.db.Specifics.QueryParameterName("id")}";

            this.db.ExecuteNonQuery(sql, mp =>
            {
                mp.AddParameter("prezzo", prezzo);
                mp.AddParameter("idComune", idComune);
                mp.AddParameter("id", idOnere);
            });
        }

        public void UpdateImporto(string idComune, int idOnere, double prezzo)
        {
            var sql = $"update ISTANZEONERI set prezzo = {this.db.Specifics.QueryParameterName("prezzo")} " +
                      $"where idcomune = {this.db.Specifics.QueryParameterName("idComune")} and id = {this.db.Specifics.QueryParameterName("id")}";

            this.db.ExecuteNonQuery(sql, mp =>
            {
                mp.AddParameter("prezzo", prezzo);
                mp.AddParameter("idComune", idComune);
                mp.AddParameter("id", idOnere);
            });
        }

        public void UpdateNumeroRata(string idComune, int id, int numeroRata)
        {
            var io = this.GetById(idComune, id);
            io.NUMERORATA = numeroRata.ToString();

            this.db.Update(io);
        }

        public IEnumerable<int> GetListaIdNonPagatiDaTipoCausale(string idComune, int codiceIstanza, int tipoCausale)
        {
            var sql = $@"select 
                            id 
                        from 
                            istanzeoneri 
                        where 
                            idcomune={this.db.Specifics.QueryParameterName("idComune")} and
                            CODICEISTANZA={this.db.Specifics.QueryParameterName("codiceIstanza")} and
                            FKIDTIPOCAUSALE={this.db.Specifics.QueryParameterName("tipoCausale")} and
                            datapagamento is null";

            return this.db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                    mp.AddParameter("tipoCausale", tipoCausale);
                },
                dr => dr.GetInt("id").Value).ToArray();
        }

        public void CalcolaDerateizzazione(int codiceIstanza, int tipoCausale, int idTestata, string numeroDocumento)
        {
            var IdComune = this._authenticationInfo.IdComune;
            var ds = this.GetSommeDateListaOneri(this._authenticationInfo.IdComune, codiceIstanza, tipoCausale, idTestata);
            var erroreBase = $"Impossibile derateizzare gli oneri con CodiceIstanza ={codiceIstanza}, tipoCausale ={tipoCausale}, " +
                             $"idTestata={idTestata}, numeroDocumento={numeroDocumento}.";

            // elimino gli oneri in base alla causale o testata
            try
            {
                this.DeleteOnere(IdComune, codiceIstanza, tipoCausale, idTestata);
            }
            catch (Exception ex)
            {
                this._log.Error($"{erroreBase} Impossibile eliminare gli oneri precedenti: {ex}");
            }

            using (var db = this._authenticationInfo.CreateDatabase())
            {
                foreach (DataRow dr in ds.Tables[0].Rows)
                {
                    double prezzo = dr["prezzo"] != DBNull.Value ? Convert.ToDouble(dr["prezzo"]) : 0;
                    double interesse = dr["interesse"] != DBNull.Value ? Convert.ToDouble(dr["interesse"]) : 0;
                    double importo = prezzo - interesse;

                    double? importoIstruttoria = dr["prezzoistruttoria"] != DBNull.Value ? Convert.ToDouble(dr["prezzoistruttoria"]) : (double?)null;
                    DateTime? dataScadenza = dr["datascadenza"] != DBNull.Value ? Convert.ToDateTime(dr["datascadenza"]) : (DateTime?)null;
                    DateTime? data = dr["data"] != DBNull.Value ? Convert.ToDateTime(dr["data"]) : (DateTime?)null;

                    try
                    {
                        // Creo i nuovi oneri
                        var io = new IstanzeOneri();
                        io.IDCOMUNE = IdComune;
                        io.CODICEISTANZA = codiceIstanza.ToString();
                        io.FKIDTIPOCAUSALE = tipoCausale.ToString();

                        //io.NUMERORATA = iNumeroRata.ToString();
                        io.NR_DOCUMENTO = numeroDocumento;
                        io.PREZZO = importo;
                        io.PREZZOISTRUTTORIA = importoIstruttoria;

                        io.ImportoInteressi = 0;
                        io.FlagOnereRateizzato = "0";

                        io.FLENTRATAUSCITA = "1";
                        io.DATA = data;
                        io.DATASCADENZA = dataScadenza;

                        //Verifico se l'onere era collegato ad un record nella tabella ISTANZECALCOLOCANONI_O (ISTANZEONERI_CANONI non viene gestita)
                        var result = this._oneriService.Inserisci(new[] { io });

                        if (idTestata != int.MinValue)
                        {
                            var listRate = new List<IstanzeOneri>();
                            listRate.Add(result.ElementAt(0));
                            new IstanzeCalcoloCanoniOMgr(db).InserisciOneri(IdComune, idTestata, listRate);
                        }

                        //Database.CommitTransaction();
                    }
                    catch (Exception ex)
                    {
                        // se siamo qui è un disastro: i vecchi oneri sono stati eliminati e i nuovi oneri derateizzati ancora non sono stati creati
                        this._log.Error($"{erroreBase}. Impossibile creare il nuovo onere: {ex}");

                        throw;

                        //Database.RollbackTransaction();
                    }
                }
            }
        }
    }


}

