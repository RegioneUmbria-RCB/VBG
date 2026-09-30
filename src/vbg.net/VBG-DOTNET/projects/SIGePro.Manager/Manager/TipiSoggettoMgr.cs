using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.TipiSoggetto;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipiSoggettoMgr.\n	/// </summary>
    public class TipiSoggettoMgr : BaseManager
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(TipiSoggettoMgr));
        private readonly string _idComune;

        public TipiSoggettoMgr(DataBase dataBase, string idComune) : base(dataBase)
        {
            this._idComune = idComune;
        }

        public TipiSoggetto GetById(int codice)
        {
            TipiSoggetto retVal = new TipiSoggetto();
            retVal.CODICETIPOSOGGETTO = codice.ToString();
            retVal.IDCOMUNE = this._idComune;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public TipoSoggettoDto GetById(int codiceTipoSoggetto, int? codiceIntervento)
        {
            if (!codiceIntervento.HasValue)
            {
                throw new ArgumentException("GetById: Codice intervento non impostato");
            }

            var intervento = new AlberoProcMgr(this.db).GetById(codiceIntervento.Value, this._idComune);

            if (intervento == null)
            {
                throw new ArgumentException("GetById: Codice intervento non valido");
            }

            return this.GetTipiSoggettoFrontofficeDaIntervento(intervento.SOFTWARE, intervento).FirstOrDefault(x => x.Id == codiceTipoSoggetto);
        }

        public TipiSoggettoInterventoDto GetTipiSoggettoFrontofficeDaIdIntervento(string software, int? codiceIntervento)
        {
            var tipiSoggetto = this.GetTipiSoggettoFrontofficeDaIdInterventoFlat(software, codiceIntervento);

            return new TipiSoggettoInterventoDto
            {
                PersoneFisiche = tipiSoggetto.Where(x => x.TipoAnagrafe == "F" || String.IsNullOrEmpty(x.TipoAnagrafe)),
                PersoneGiuridiche = tipiSoggetto.Where(x => x.TipoAnagrafe == "G" || String.IsNullOrEmpty(x.TipoAnagrafe)),
                NumeroSoggettiObbligatori = tipiSoggetto.Count(x => x.Richiesto),
                TotaleSoggetti = tipiSoggetto.Count()
            };
        }

        public IEnumerable<TipoSoggettoDto> GetTipiSoggettoFrontofficeDaIdInterventoFlat(string software, int? codiceIntervento)
        {
            var intervento = codiceIntervento.HasValue ? new AlberoProcMgr(this.db).GetById(codiceIntervento.Value, this._idComune) : null;

            return this.GetTipiSoggettoFrontofficeDaIntervento(intervento == null ? software : intervento.SOFTWARE, intervento);
        }

        private IEnumerable<TipoSoggettoDto> GetTipiSoggettoFrontofficeDaIntervento(string software, AlberoProc intervento)
        {
            if (intervento == null)
            {
                return this.GetTipiSoggettoFrontoffice(software);
            }

            try
            {
                // var intervento = new AlberoProcMgr(this.db).GetById(codiceIntervento.Value, this._idComune);

                var listaScCodice = intervento.GetListaScCodice().ToString();

                // Esistono soggetti legati all'intervento del tipo anagrafe passato
                var sql = $@"SELECT 
	                            tipisoggetto.CODICETIPOSOGGETTO AS {nameof(TipoSoggettoDto.Id)},
	                            tipisoggetto.TIPOSOGGETTO AS {nameof(TipoSoggettoDto.Descrizione)},
	                            COALESCE(alberoproc_tipisoggetto.override_descrizione, tipisoggetto.descrizione_estesa) AS {nameof(TipoSoggettoDto.DescrizioneEstesa)},
	                            tipisoggetto.TIPODATO AS {nameof(TipoSoggettoDto.FlagTipoDato)},
                                tipisoggetto.TIPOANAGRAFE AS {nameof(TipoSoggettoDto.TipoAnagrafe)},
	                            tipisoggetto.FLG_LEGALERAP AS {nameof(TipoSoggettoDto.FlagLegaleRappresentante)},
	                            alberoproc_tipisoggetto.occorrenze_max AS {nameof(TipoSoggettoDto.OccorrenzeMax)},
	                            tipisoggetto.RICHIEDIANAGRAFECOLL AS {nameof(TipoSoggettoDto.RichiedeAnagraficaCollegata)},
	                            tipisoggetto.FLG_DATIALBO AS {nameof(TipoSoggettoDto.RichiedeDatiAlbo)},
	                            tipisoggetto.FLG_SPECIFICADESCRIZIONE AS {nameof(TipoSoggettoDto.RichiedeSpecificaDescrizione)},
	                            alberoproc_tipisoggetto.obbligatorio AS {nameof(TipoSoggettoDto.Richiesto)}
                            FROM 
	                            alberoproc 
	
	                            INNER JOIN alberoproc_tipisoggetto ON 
		                            alberoproc_tipisoggetto.idcomune = alberoproc.idcomune AND
		                            alberoproc_tipisoggetto.fk_scid = alberoproc.sc_id 
		
	                            INNER JOIN tipisoggetto ON 
		                            tipisoggetto.idcomune = alberoproc_tipisoggetto.idcomune AND
		                            tipisoggetto.codicetiposoggetto = alberoproc_tipisoggetto.fk_codicetiposoggetto
                            WHERE 
								alberoproc.software = {this.db.QueryParameter("software")} AND 
								alberoproc.sc_codice IN (" + listaScCodice + $@") and 
								alberoproc.idComune = {this.db.QueryParameter("idComune")} 
                        order by alberoproc.SC_CODICE asc, tipisoggetto.tiposoggetto asc";

                var lista = this.db.ExecuteReader(sql,
                    mp => mp.Add("software", intervento.SOFTWARE)
                            .Add("idComune", this._idComune),

                    dr => new TipoSoggettoDto
                    {
                        Id = dr.GetInt(nameof(TipoSoggettoDto.Id)),
                        Descrizione = dr.GetString(nameof(TipoSoggettoDto.Descrizione)),
                        DescrizioneEstesa = dr.GetString(nameof(TipoSoggettoDto.DescrizioneEstesa)),
                        FlagTipoDato = dr.GetString(nameof(TipoSoggettoDto.FlagTipoDato)),
                        TipoAnagrafe = dr.GetString(nameof(TipoSoggettoDto.TipoAnagrafe)),
                        FlagLegaleRappresentante = dr.GetInt(nameof(TipoSoggettoDto.FlagLegaleRappresentante), 0) == 1,
                        OccorrenzeMax = dr.GetInt(nameof(TipoSoggettoDto.OccorrenzeMax), int.MaxValue),
                        RichiedeAnagraficaCollegata = dr.GetInt(nameof(TipoSoggettoDto.RichiedeAnagraficaCollegata), 0) == 1,
                        RichiedeDatiAlbo = dr.GetInt(nameof(TipoSoggettoDto.RichiedeDatiAlbo), 0) == 1,
                        RichiedeSpecificaDescrizione = dr.GetInt(nameof(TipoSoggettoDto.RichiedeSpecificaDescrizione), 0) == 1,
                        Richiesto = dr.GetInt(nameof(TipoSoggettoDto.Richiesto), 0) == 1
                    });

                if (lista.Any())
                {
                    // Lo stesso tipo soggetto potrebbe essere presente sia in nodi padre che in nodi figlio
                    // in questo caso il nodo più annidato deve "vincere" su quelli presenti nelle foglie precedenti.
                    // Uso un dictionary per inserire prima i valori dai nodi meno annidati sovrascrivendoli poi con i nodi più annidati
                    var dictionary = new Dictionary<int, TipoSoggettoDto>();

                    foreach (var ts in lista)
                    {
                        dictionary[ts.Id.Value] = ts;
                    }
                    return dictionary.Values.ToArray();
                }

                return this.GetTipiSoggettoFrontoffice(software); ;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("TipiSoggettoManager.GetTipiSoggettoDaCodiceIntervento: {0}", ex.ToString());

                throw;
            }
        }

        private IEnumerable<TipoSoggettoDto> GetTipiSoggettoFrontoffice(string software)
        {
            var sql = $@"SELECT 
	                            tipisoggetto.CODICETIPOSOGGETTO AS {nameof(TipoSoggettoDto.Id)},
	                            tipisoggetto.TIPOSOGGETTO AS {nameof(TipoSoggettoDto.Descrizione)},
	                            tipisoggetto.descrizione_estesa AS {nameof(TipoSoggettoDto.DescrizioneEstesa)},
	                            tipisoggetto.TIPODATO AS {nameof(TipoSoggettoDto.FlagTipoDato)},
                                tipisoggetto.TIPOANAGRAFE AS {nameof(TipoSoggettoDto.TipoAnagrafe)},
	                            tipisoggetto.FLG_LEGALERAP AS {nameof(TipoSoggettoDto.FlagLegaleRappresentante)},
	                            null AS {nameof(TipoSoggettoDto.OccorrenzeMax)},
	                            tipisoggetto.RICHIEDIANAGRAFECOLL AS {nameof(TipoSoggettoDto.RichiedeAnagraficaCollegata)},
	                            tipisoggetto.FLG_DATIALBO AS {nameof(TipoSoggettoDto.RichiedeDatiAlbo)},
	                            tipisoggetto.FLG_SPECIFICADESCRIZIONE AS {nameof(TipoSoggettoDto.RichiedeSpecificaDescrizione)},
	                            tipisoggetto.FO_OBBLIGATORIO AS {nameof(TipoSoggettoDto.Richiesto)}
                            FROM 
	                            tipisoggetto 
		                            
                            WHERE 
	                            tipisoggetto.idcomune = {this.db.QueryParameter("idcomune")} AND
	                            tipisoggetto.SOFTWARE = {this.db.QueryParameter("software")} AND
	                            COALESCE(tipisoggetto.UTILIZZO, 'F') = 'F'
                        order by ordine asc, tiposoggetto asc";

            var lista = this.db.ExecuteReader(sql,
                mp => mp.Add("idComune", this._idComune)
                        .Add("software", software),

                dr => new TipoSoggettoDto
                {
                    Id = dr.GetInt(nameof(TipoSoggettoDto.Id)),
                    Descrizione = dr.GetString(nameof(TipoSoggettoDto.Descrizione)),
                    DescrizioneEstesa = dr.GetString(nameof(TipoSoggettoDto.DescrizioneEstesa)),
                    FlagTipoDato = dr.GetString(nameof(TipoSoggettoDto.FlagTipoDato)),
                    TipoAnagrafe = dr.GetString(nameof(TipoSoggettoDto.TipoAnagrafe)),
                    FlagLegaleRappresentante = dr.GetInt(nameof(TipoSoggettoDto.FlagLegaleRappresentante), 0) == 1,
                    OccorrenzeMax = dr.GetInt(nameof(TipoSoggettoDto.OccorrenzeMax), int.MaxValue),
                    RichiedeAnagraficaCollegata = dr.GetInt(nameof(TipoSoggettoDto.RichiedeAnagraficaCollegata), 0) == 1,
                    RichiedeDatiAlbo = dr.GetInt(nameof(TipoSoggettoDto.RichiedeDatiAlbo), 0) == 1,
                    RichiedeSpecificaDescrizione = dr.GetInt(nameof(TipoSoggettoDto.RichiedeSpecificaDescrizione), 0) == 1,
                    Richiesto = dr.GetInt(nameof(TipoSoggettoDto.Richiesto), 0) == 1
                });

            return lista;
        }

        public IEnumerable<int> GetIdTipiSoggettoCheRicevonoNotifiche(IEnumerable<int> listaIdTipiSoggetto)
        {
            var listaId = String.Join(",", listaIdTipiSoggetto.Select(x => x.ToString()));

            var sql = $@"SELECT DISTINCT
	                        codicetiposoggetto
                        FROM 
	                        tipisoggetto
                        WHERE 
	                        idcomune = {this.db.QueryParameter("idComune")} AND 
	                        flag_riceve_notifiche = 1 AND
	                        codicetiposoggetto IN ({listaId})";

            return this.db.ExecuteReader(sql, mp => mp.Add("idComune", this._idComune), dr => dr.GetInt("codicetiposoggetto").Value);
        }
    }
}
