using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.RicercaPratiche
{

    public class RicercaPraticheService
    {
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly ILog _log = LogManager.GetLogger(typeof(RicercaPraticheService));

        public RicercaPraticheService(DataBase db, string idComune)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }

            this._db = db ?? throw new ArgumentNullException(nameof(db));
            this._idComune = idComune;
        }

        /// <summary>
        /// Ricerca una pratica in base ai parametri specificati. Se la pratica non viene trovata o se viene trovata più di una pratica restituisce null
        /// </summary>
        /// <param name="software"></param>
        /// <param name="numeroIstanza"></param>
        /// <param name="codiceAnagrafeUtenteRicerca"></param>
        /// <returns></returns>
        public RisultatoRicercaPratiche RicercaPraticaDaEstremiProtocollo(string software, string codiceComune, string numeroProtocollo, DateTime dataProtocollo, string cfAnagraficaUtenteRicerca)
        {
            var selectIstanze = $@"select codiceistanza from istanze where 
                                    idcomune = {this._db.QueryParameter("idComune")} and
                                    codiceComune= {this._db.QueryParameter(nameof(codiceComune))} and
                                    software={this._db.QueryParameter(nameof(software))} and 
                                    NUMEROPROTOCOLLO={this._db.QueryParameter(nameof(numeroProtocollo))} and 
                                    DATAPROTOCOLLO >= {this._db.QueryParameter("dataProtocolloFm")} and
                                    DATAPROTOCOLLO <= {this._db.QueryParameter("dataProtocolloTo")}";

            var idPratiche = this._db.ExecuteReader(selectIstanze,
                                                mp => mp.Add("idComune", this._idComune)
                                                        .Add(nameof(codiceComune), codiceComune)
                                                        .Add(nameof(software), software)
                                                        .Add(nameof(numeroProtocollo), numeroProtocollo)
                                                        .Add("dataProtocolloFm", new DateTime(dataProtocollo.Year, dataProtocollo.Month, dataProtocollo.Day, 0, 0, 0))
                                                        .Add("dataProtocolloTo", new DateTime(dataProtocollo.Year, dataProtocollo.Month, dataProtocollo.Day, 23, 59, 59)),
                                                dr => dr.GetInt32(0));

            if (idPratiche.Count() != 1)
            {
                return null;
            }

            return this.LeggiDatiPratica(idPratiche.First(), cfAnagraficaUtenteRicerca);
        }

        private RisultatoRicercaPratiche LeggiDatiPratica(int idPratica, string cfAnagraficaUtenteRicerca)
        {
            return new RisultatoRicercaPratiche
            {
                EstremiPratica = this.LeggiEstremiPratica(idPratica),
                Localizzazioni = this.LeggiLocalizzazioniEMappali(idPratica),
                Anagrafiche = this.LeggiDatiAnagrafiche(idPratica, cfAnagraficaUtenteRicerca).ToList()
            };
        }

        private EstremiPraticaTrovata LeggiEstremiPratica(int idPratica)
        {
            var sql = $@"SELECT	
	                        istanze.codiceistanza,
	                        istanze.numeroIstanza,
	                        istanze.DATA,
	                        istanze.numeroprotocollo,
	                        istanze.dataprotocollo,
                            alberoproc.DESCRIZIONE_COMPLETA as nome_intervento,
	                        istanze.lavoriestesa AS descrizione_lavori
                        FROM 
	                        istanze
		                        INNER JOIN alberoproc ON
			                        alberoproc.idcomune = istanze.idcomune AND
			                        alberoproc.sc_id = istanze.codiceinterventoproc 
                        WHERE
	                        istanze.idcomune={this._db.QueryParameter("idComune")} AND
	                        istanze.codiceistanza={this._db.QueryParameter("idPratica")}";

            return this._db.ExecuteReader(sql,
                    mp => mp.Add("idComune", this._idComune)
                            .Add("idPratica", idPratica),
                    dr => new EstremiPraticaTrovata
                    {
                        CodiceIstanza = dr.GetInt("codiceistanza").Value,
                        NumeroIstanza = dr.GetString("numeroIstanza"),
                        DataIstanza = dr.GetDateTime("DATA").Value,
                        NumeroProtocollo = dr.GetString("numeroprotocollo"),
                        DataProtocollo = dr.GetDateTime("dataprotocollo"),
                        Intervento = dr.GetString("nome_intervento"),
                        DescrizioneLavori = dr.GetString("descrizione_lavori")
                    }).FirstOrDefault();
        }

        /// <summary>
        /// Ricerca una pratica in base ai parametri specificati. Se la pratica non viene trovata o se viene trovata più di una pratica restituisce null
        /// </summary>
        /// <param name="software"></param>
        /// <param name="numeroIstanza"></param>
        /// <param name="codiceAnagrafeUtenteRicerca"></param>
        /// <returns></returns>
        public RisultatoRicercaPratiche RicercaPraticaDaNumeroIstanza(string software, string codiceComune, string numeroIstanza, string cfAnagraficaUtenteRicerca)
        {
            var selectIstanze = $@"select codiceistanza from istanze where 
                                    idcomune = {this._db.QueryParameter("idComune")} and
                                    codiceComune= {this._db.QueryParameter(nameof(codiceComune))} and
                                    software={this._db.QueryParameter(nameof(software))} and 
                                    numeroistanza={this._db.QueryParameter(nameof(numeroIstanza))}";

            _log.Debug($"Ricerca pratiche con query {selectIstanze}");

            var idPratiche = this._db.ExecuteReader(selectIstanze,
                                                mp => mp.Add("idComune", this._idComune)
                                                        .Add(nameof(codiceComune), codiceComune)
                                                        .Add(nameof(software), software)
                                                        .Add(nameof(numeroIstanza), numeroIstanza),
                                                dr => dr.GetInt32(0));



            if (idPratiche.Count() != 1)
            {
                return null;
            }

            var idPratica = idPratiche.First();

            return this.LeggiDatiPratica(idPratiche.First(), cfAnagraficaUtenteRicerca);
        }

        private IEnumerable<AnagraficaIstanzaTrovata> LeggiDatiAnagrafiche(int idPratica, string cfAnagraficaUtenteRicerca)
        {
            // Verifico quale livello di accesso ha l'utente che sta effettuando la richiesta. Se è un utente con accesso completo (in base ai flags della visura)
            // allora restituirò i dai completi delle anagrafiche. Altrimenti verrà restituito solo il set minimo di dati
            var utentiConLivelloAccesso = new LogicaEstrazioneUtenti(this._db, this._idComune).GetLivelliAccessoPerPratica(idPratica);

            bool accessoCompleto = utentiConLivelloAccesso.Where(x => x.CodiceFiscale == cfAnagraficaUtenteRicerca && x.AccessoCompleto).Any();

            var anagrafeMgr = new AnagrafeMgr(this._db);

            return utentiConLivelloAccesso.Select(x => anagrafeMgr.GetById(this._idComune, x.CodiceAnagrafe).ToAnagraficaIstanzaTrovata(accessoCompleto, x.TipoSoggetto));
        }

        private List<StradarioIstanzaTrovata> LeggiLocalizzazioniEMappali(int idPratica)
        {
            var sql = $@"select 
    istanzestradario.Id,
    istanzestradario.CodiceStradario,
    istanzestradario.Civico,
    istanzestradario.Colore,
    istanzestradario.Note,
    {this._db.Specifics.ConcatFunction("stradario.prefisso", "' '", "stradario.descrizione")} AS Stradario,
    stradario.CodiceComune,
    istanzestradario.Esponente,
    istanzestradario.Interno,
    istanzestradario.Scala,
    istanzestradario.EsponenteInterno,
    istanzestradario.Piano,
    istanzestradario.Fabbricato,
    istanzestradario.Km,
    istanzestradario.Circoscrizione,
    istanzestradario.Cap,
    istanzestradario.Latitudine,
    istanzestradario.Longitudine,
    istanzestradario.Uuid,
    istanzestradario.TipoLocalizzazione_id AS TipoLocalizzazione,
    stradario.CodViario,
    istanzestradario.CODICECIVICO AS CodCivico
from 
    istanzestradario 
	    INNER JOIN stradario ON
		    stradario.idcomune = istanzestradario.idcomune AND
		    stradario.codicestradario = istanzestradario.codicestradario
where 
    istanzestradario.idcomune={this._db.QueryParameter("idcomune")} and 
    istanzestradario.codiceistanza = {this._db.QueryParameter(nameof(idPratica))}";


            var stradari = this._db.ExecuteReader(sql,
                mp => mp.Add("idcomune", this._idComune)
                        .Add(nameof(idPratica), idPratica),
                dr => new StradarioIstanzaTrovata
                {
                    Id = dr.GetInt("Id").Value,
                    CodiceStradario = dr.GetString("CodiceStradario"),
                    Civico = dr.GetString("Civico"),
                    Colore = dr.GetString("Colore"),
                    Note = dr.GetString("Note"),
                    Stradario = dr.GetString("Stradario"),
                    CodiceComune = dr.GetString("CodiceComune"),
                    Esponente = dr.GetString("Esponente"),
                    Interno = dr.GetString("Interno"),
                    Scala = dr.GetString("Scala"),
                    EsponenteInterno = dr.GetString("EsponenteInterno"),
                    Piano = dr.GetString("Piano"),
                    Fabbricato = dr.GetString("Fabbricato"),
                    Km = dr.GetString("Km"),
                    Circoscrizione = dr.GetString("Circoscrizione"),
                    Cap = dr.GetString("Cap"),
                    Latitudine = dr.GetString("Latitudine"),
                    Longitudine = dr.GetString("Longitudine"),
                    Uuid = dr.GetString("Uuid"),
                    TipoLocalizzazione = dr.GetString("TipoLocalizzazione"),
                    CodViario = dr.GetString("CodViario"),
                    CodCivico = dr.GetString("CodCivico")
                }).ToList();

            foreach (var stradario in stradari)
            {
                stradario.Mappali = this.PopolaMappali(idPratica, stradario.Id);
            }

            return stradari;
        }

        private List<MappaleIstanzaTrovata> PopolaMappali(int idPratica, int idStradario)
        {
            var sql = $@"select 
                            * 
                        from 
                            istanzemappali 
                        where
                            istanzemappali.idcomune={this._db.QueryParameter("idcomune")} and 
                            istanzemappali.FKCODICEISTANZA = {this._db.QueryParameter(nameof(idPratica))} and
                            istanzemappali.FKIDISTANZESTRADARIO = {this._db.QueryParameter(nameof(idStradario))}";

            return this._db.ExecuteReader(sql,
                mp => mp.Add("idcomune", this._idComune)
                        .Add(nameof(idPratica), idPratica)
                        .Add(nameof(idStradario), idStradario),
                dr => new MappaleIstanzaTrovata
                {
                    CodiceTipoCatasto = dr.GetString("CODICECATASTO"),
                    TipoCatasto = dr.GetString("CODICECATASTO") == "T" ? "Terreni" : "Fabbricati",
                    Foglio = dr.GetString("FOGLIO"),
                    Particella = dr.GetString("PARTICELLA"),
                    Sub = dr.GetString("SUB"),
                    Sezione = dr.GetString("SEZIONE")
                }).ToList();
        }
    }
}
