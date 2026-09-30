using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier;
using log4net;
using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Piacenza
{
    public class PiacenzaAnagrafeSearcher : AnagrafeSearcherAdrierBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(PiacenzaAnagrafeSearcher));

        public PiacenzaAnagrafeSearcher(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
            : base(verticalizzazioniFactory, bindingFactory, "PIACENZA")
        {
        }

        private string ConnectionString
        {
            get
            {
                return this.GetconfigValue("ConnectionString");
            }
        }


        private string View
        {
            get
            {
                return this.GetconfigValue("View");
            }
        }

        private string Owner
        {
            get
            {
                return this.GetconfigValue("Owner");
            }
        }

        private string Provider
        {
            get
            {
                return this.GetconfigValue("Provider");
            }
        }


        private string GetconfigValue(string configParam)
        {
            if (this.Configuration.ContainsKey(configParam))
            {
                return this.Configuration[configParam];
            }

            var ucaseConfigParam = configParam.ToUpperInvariant();

            if (this.Configuration.ContainsKey(ucaseConfigParam))
            {
                return this.Configuration[ucaseConfigParam];
            }

            this._log.ErrorFormat("La configurazione della ricerca anagrafica non contiene il parametro {0}", configParam);

            return string.Empty;
        }


        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            this._log.Debug("Ricerca per codice fiscale: " + codiceFiscale);

            using (var db = new DataBase(this.ConnectionString, (ProviderType)Enum.Parse(typeof(ProviderType), this.Provider, true)))
            {
                var table = (string.IsNullOrEmpty(this.Owner) ? this.View : this.Owner + "." + this.View);
                var sql = @"SELECT IDPERSONA,
                                        COGNOME,
                                        NOME,
                                        DATANASCITA,
                                        RESIDENTE,
                                        IDFAMIGLIA,
                                        IDPARENTELA,
                                        IDCOMUNENASCITA,
                                        IDSTATONASCITA,
                                        CODICEFISCALE,
                                        SESSO,
                                        IDSTATOCIVILE,
                                        DATAMORTE,
                                        IDSTATOCITT,
                                        RESIDQUARTIERE,
                                        RESIDFRAZIONE,
                                        RESIDVIA,
                                        RESCIVICO,
                                        RESBARRATO,
                                        RESCAP,
                                        RESIDCOMUNEITALIANO,
                                        RESIDSTATO,
                                        TSULTIMOAGGIORNAMENTO
                                FROM " + table +
                               @" WHERE upper(CODICEFISCALE) = :codiceFiscale 
                                 AND residente = 'S'";

                this._log.Debug("Sql: " + sql);

                sql = String.Format(sql, db.Specifics.QueryParameterName("CODICEFISCALE"));

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("codiceFiscale", codiceFiscale.ToUpper()));
                    using (var ds = new DataSet())
                    {
                        IDataAdapter da = db.CreateDataAdapter(cmd);
                        da.Fill(ds);

                        var a = new Anagrafe();

                        if (ds.Tables[0].Rows.Count == 0)
                        {
                            this._log.ErrorFormat("LA RICERCA CON CODICE FISCALE {0} NON HA RESTITUITO ALCUNA ANAGRAFICA", codiceFiscale);

                            return null;
                        }

                        if (ds.Tables[0].Rows.Count > 1)
                        {
                            this._log.ErrorFormat("LA RICERCA CON CODICE FISCALE {0} HA RESTITUITO {1} ANAGRAFICHE", codiceFiscale, ds.Tables[0].Rows.Count);

                            return null;
                        }

                        return this.GetAnagrafe(ds.Tables[0].Rows[0]);
                    }
                }
            }
        }

        public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
        {
            if (tipoPersona == TipoPersona.PersonaFisica)
            {
                return this.ByCodiceFiscaleImp(codiceFiscale);
            }
            else
            {
                //Persona giuridica
                return this.ByPartitaIvaImp(codiceFiscale);
            }
        }

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            throw new NotImplementedException();
        }

        public Anagrafe GetAnagrafe(DataRow r)
        {
            var a = new Anagrafe();

            a.IDCOMUNE = this.IdComune;
            this._log.Debug("IdComune: " + this.IdComune);

            a.CODICEFISCALE = r["CODICEFISCALE"].ToString();
            this._log.Debug("CodiceFiscale: " + this.IdComune);

            a.NOMINATIVO = r.IsNull("COGNOME") ? String.Empty : r["COGNOME"].ToString();
            this._log.Debug("Nominativo: " + a.NOMINATIVO);

            a.NOME = r.IsNull("NOME") ? String.Empty : r["NOME"].ToString();
            this._log.Debug("Nome: " + a.NOME);

            a.DATANASCITA = r.IsNull("DATANASCITA") ? (DateTime?)null : DateTime.Parse(r["DATANASCITA"].ToString());
            this._log.Debug("DataNascita: " + a.DATANASCITA);

            a.CODCOMNASCITA = this.GetCodiceComuneNascita(r);
            this._log.Debug("Comune di Nascita: " + a.CODCOMNASCITA);

            if (r.IsNull("SESSO"))
                throw new Exception("NELL'ORIGINE DATI NON E' SPECIFICATO IL SESSO PER IL SOGGETTO ANAGRAFICO: " + a.NOME + " " + a.NOMINATIVO);
            else
            {
                if (r["SESSO"].ToString() != "M" && r["SESSO"].ToString() != "F")
                    throw new Exception("NELL'ORIGINE DATI IL SESSO DEL SOGGETTO ANAGRAFICO E' DIVERSO DA -M- E -F- (" + r["SESSO"].ToString() + ")");
            }

            a.SESSO = r["SESSO"].ToString();
            this._log.Debug("Sesso: " + a.SESSO);

            a.CAP = r.IsNull("RESCAP") ? String.Empty : r["RESCAP"].ToString();
            this._log.Debug("Cap: " + a.CAP);

            /*
            a.CAPCORRISPONDENZA = r.IsNull("RESCAP") ? String.Empty : r["RESCAP"].ToString();
            _log.Debug("CapCorrispondenza: " + a.CAPCORRISPONDENZA);
            */

            this.SetIndirizzo(r, a);

            a.TIPOLOGIA = "0";
            this._log.Debug("Tipologia: " + a.TIPOLOGIA);

            if (!r.IsNull("RESIDCOMUNEITALIANO"))
            {
                //Viene fatto il padding della stringa che arriva dal campo RESIDCOMUNEITALIANO in quanto sul DB di VBG il tracciato del campo 
                //Stradario.CodViario è codificato con IDCOMUNE (4 caratteri), CodiceStradario (8 caratteri), se meno di 8 caratteri aggiunge "0"
                var comuneResidenza = new ComuniMgr(this.SigeproDb).GetByClass(new Comuni
                {
                    CODICEISTAT = r["RESIDCOMUNEITALIANO"].ToString().PadLeft(6, '0')
                });
                if (comuneResidenza != null)
                {
                    a.CITTA = comuneResidenza.COMUNE;
                    this._log.Debug("Citta: " + a.CITTA);
                    a.PROVINCIA = comuneResidenza.SIGLAPROVINCIA;
                    this._log.Debug("Provincia: " + a.PROVINCIA);
                    a.COMUNERESIDENZA = comuneResidenza.CODICECOMUNE;
                    this._log.Debug("ComuneResidenza: " + a.COMUNERESIDENZA);
                    /*
                    a.CITTACORRISPONDENZA = comuneResidenza.COMUNE;
                    _log.Debug("CittaCorrispondenza: " + a.CITTACORRISPONDENZA);
                    a.PROVINCIACORRISPONDENZA = comuneResidenza.SIGLAPROVINCIA;
                    _log.Debug("ProvinciaCorrispondenza: " + a.PROVINCIACORRISPONDENZA);
                    a.COMUNECORRISPONDENZA = comuneResidenza.CODICECOMUNE;
                    _log.Debug("ComuneCorrispondenza: " + a.COMUNECORRISPONDENZA);
                     * */
                }
            }

            a.CODICECITTADINANZA = this.GetCittadinanza(r["IDSTATOCITT"].ToString());
            this._log.Debug("Cittadinanza: " + a.TIPOLOGIA);

            a.TIPOANAGRAFE = "F";

            return a;
        }

        /*public override Anagrafe ByPartitaIvaImp(string partitaIva)
        {
            throw new NotImplementedException();
        }*/

        /// <summary>
        /// Popola i dati Anagrafici relativi all'indirizzo
        /// </summary>
        /// <param name="r"></param>
        /// <param name="a"></param>
        private void SetIndirizzo(DataRow r, Anagrafe a)
        {
            var codViario = "%" + r["RESIDVIA"].ToString().PadLeft(8, '0');

            var sql = "select * from stradario where idcomune = ?idComune and codviario like ?codViario";

            sql = String.Format(sql, this.SigeproDb.Specifics.QueryParameterName("idComune"), this.SigeproDb.Specifics.QueryParameterName("codViario"));

            this._log.Debug("Sql Stradario: " + sql);

            this._log.Debug("Parametri, IdComune: " + this.IdComune);
            this._log.Debug("Parametri, codViario " + codViario);

            using (IDbCommand cmd = this.SigeproDb.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.SigeproDb.CreateParameter("idComune", this.IdComune));
                cmd.Parameters.Add(this.SigeproDb.CreateParameter("codViario", codViario));

                using (var ds = new DataSet())
                {
                    IDataAdapter da = this.SigeproDb.CreateDataAdapter(cmd);
                    da.Fill(ds);

                    this._log.Debug("Numero righe restituite: " + ds.Tables[0].Rows.Count);

                    if (ds.Tables[0].Rows.Count == 1)
                    {
                        DataRow rs = ds.Tables[0].Rows[0];
                        var prefisso = rs.IsNull("PREFISSO") ? String.Empty : rs["PREFISSO"].ToString();
                        var via = rs.IsNull("DESCRIZIONE") ? String.Empty : rs["DESCRIZIONE"].ToString();
                        var civico = r.IsNull("RESCIVICO") ? String.Empty : ", " + r["RESCIVICO"].ToString();

                        this._log.Debug("prefisso: " + prefisso);
                        this._log.Debug("via: " + via);
                        this._log.Debug("civico: " + civico);

                        a.INDIRIZZO = String.Concat(prefisso, via, civico);
                        this._log.Debug("Indirizzo: " + a.INDIRIZZO);
                        /*
                        a.INDIRIZZOCORRISPONDENZA = String.Concat(prefisso, via, civico);
                        _log.Debug("IndirizzoCorrispondenza: " + a.INDIRIZZOCORRISPONDENZA);
                         * */
                    }
                    else
                    {
                        this._log.Debug("Sono stati trovati " + ds.Tables[0].Rows.Count + " record nella ricerca per codViario " + codViario);
                    }
                }
            }
        }

        /// <summary>
        /// /// Cerca il comune di nascita.
        /// </summary>
        /// <param name="r"></param>
        /// <returns></returns>
        private string GetCodiceComuneNascita(DataRow r)
        {

            var codiceStatoEsteroNascita = r.IsNull("IDSTATONASCITA") ? String.Empty : r["IDSTATONASCITA"].ToString();
            var codiceIstatNascita = r.IsNull("IDCOMUNENASCITA") ? String.Empty : r["IDCOMUNENASCITA"].ToString().PadLeft(6, '0');

            var comMgr = new ComuniMgr(this.SigeproDb);
            Comuni com = null;
            if (String.IsNullOrEmpty(codiceIstatNascita))
            {
                if (!String.IsNullOrEmpty(codiceStatoEsteroNascita))
                {
                    com = comMgr.GetByClass(new Comuni { CODICESTATOESTERO = codiceStatoEsteroNascita });
                }
            }
            else
            {
                com = comMgr.GetByClass(new Comuni { CODICEISTAT = codiceIstatNascita });
            }

            return (com != null) ? com.CODICECOMUNE : String.Empty;

        }

        private Comuni GetComune(string codiceIstat)
        {
            return new ComuniMgr(this.SigeproDb).GetByClass(new Comuni { CODICEISTAT = codiceIstat.PadLeft(6, '0') });
        }

        /// <summary>
        /// Ottiene la cittadinanza del soggetto anagrafico controllando sul database di VBG
        /// </summary>
        /// <param name="codice">codice proveniente dal campo IDSTATOCITT della vista SFTCH_ANAGRAFE</param>
        /// <returns>Ritorna il codice dalla tabella CITTADINANZA anche se è ITALIANA</returns>
        private string GetCittadinanza(string codice)
        {
            var returnValue = String.Empty;

            if (!String.IsNullOrEmpty(codice))
            {
                //Se il codice = 100 significa che la cittadinanza è italiana.
                if (codice != "100")
                {
                    var comuniMgr = new ComuniMgr(this.SigeproDb);
                    var comuni = comuniMgr.GetByClass(new Comuni { CODICESTATOESTERO = codice });

                    if (comuni == null)
                        this._log.Debug("Cittandinanza nella tabella Comuni non trovata: CODICESTATOESTERO = " + codice);
                    else
                    {
                        var cittadinanzaMgr = new CittadinanzaMgr(this.SigeproDb);
                        var cittadinanza = cittadinanzaMgr.GetByClass(new Cittadinanza { Cf = comuni.CF, Disabilitato = 0 });

                        if (cittadinanza == null)
                            this._log.Debug("Cittandinanza nella tabella CITTADINANZA non trovata: CF = " + comuni.CF + " and DISABILITATO = 0");
                        else
                            returnValue = cittadinanza.Codice.Value.ToString();
                    }
                }
                else
                {
                    var cittadinanzaMgr = new CittadinanzaMgr(this.SigeproDb);
                    var cittadinanza = new Cittadinanza { Disabilitato = 0 };
                    cittadinanza.OthersWhereClause.Add("cf is null");
                    cittadinanza = cittadinanzaMgr.GetByClass(cittadinanza);

                    if (cittadinanza == null)
                        this._log.Debug("Cittandinanza nella tabella CITTADINANZA non trovata: DISABILITATO = 0 and CF IS NULL");
                    else
                        returnValue = cittadinanza.Codice.Value.ToString();
                }
            }
            return returnValue;
        }
    }
}
