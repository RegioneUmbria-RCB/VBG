using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.RicercaPratiche
{
    internal class LogicaEstrazioneUtenti
    {
        public class TipoSoggettoAnagrafica
        {
            public int Codice { get; set; } = -1;
            public string Descrizione { get; set; } = "";
        }

        public class CodiceAnagrafeConLivelloDiAccesso
        {
            public int CodiceAnagrafe;
            public string CodiceFiscale;
            public bool AccessoCompleto;
            public TipoSoggettoAnagrafica TipoSoggetto;
        }

        private readonly DataBase _db;
        private readonly string _idComune;

        public LogicaEstrazioneUtenti(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public IEnumerable<CodiceAnagrafeConLivelloDiAccesso> GetLivelliAccessoPerPratica(int idPratica)
        {
            var retVal = new List<CodiceAnagrafeConLivelloDiAccesso>();

            retVal.AddRange(this.EstraiRichiedenteTecnico(idPratica));
            retVal.AddRange(this.EstraiAltriSoggetti(idPratica));

            return retVal;
        }

        private IEnumerable<CodiceAnagrafeConLivelloDiAccesso> EstraiAltriSoggetti(int idPratica)
        {
            var sql = $@"SELECT 
	                        istanzerichiedenti.codicerichiedente,
	                        anagrafe.codicefiscale,
	                        tipisoggetto.FLAG_LIVELLI_VISURA_PRATICA,
                            tipisoggetto.CODICETIPOSOGGETTO,
                            tipisoggetto.TIPOSOGGETTO
                        FROM 
	                        istanzerichiedenti

		                        INNER JOIN tipisoggetto ON
			                        tipisoggetto.idcomune = istanzerichiedenti.idcomune AND
			                        tipisoggetto.CODICETIPOSOGGETTO = istanzerichiedenti.CODICETIPOSOGGETTO 
			
		                        INNER JOIN anagrafe ON 
			                        anagrafe.idcomune = istanzerichiedenti.idcomune AND
			                        anagrafe.codiceanagrafe = istanzerichiedenti.CODICERICHIEDENTE 
                        WHERE 
	                        istanzerichiedenti.idcomune={this._db.QueryParameter("idComune")} AND 
	                        istanzerichiedenti.codiceistanza={this._db.QueryParameter(nameof(idPratica))}";

            return this._db.ExecuteReader(sql,
                        mp => mp.Add("idComune", this._idComune)
                                .Add(nameof(idPratica), idPratica),
                        dr => new CodiceAnagrafeConLivelloDiAccesso
                        {
                            CodiceAnagrafe = dr.GetInt("codicerichiedente").Value,
                            AccessoCompleto = LivelloAccessoVisura.Completo == LivelloAccessoVisura.DaValoreFlag(dr.GetInt("FLAG_LIVELLI_VISURA_PRATICA").GetValueOrDefault(0)),
                            TipoSoggetto = new TipoSoggettoAnagrafica
                            {
                                Codice = dr.GetInt("CODICETIPOSOGGETTO").Value, // Al momento deve sempre essere presente un tipo soggetto altrimenti va modificata anche la join sopra
                                Descrizione = dr.GetString("TIPOSOGGETTO")
                            }
                        }).ToArray();
        }

        private IEnumerable<CodiceAnagrafeConLivelloDiAccesso> EstraiRichiedenteTecnico(int idPratica)
        {
            var retVal = new List<CodiceAnagrafeConLivelloDiAccesso>();

            var sqlRichiedenteTecnico = $@"SELECT 
                                            istanze.codicerichiedente,
                                            richiedente.codicefiscale as cfRichiedente, 
                                            istanze.codiceprofessionista,
                                            tecnico.codicefiscale as cfTecnico,
                                            istanze.FKCODICESOGGETTO as CodiceTipoSoggettoRichiedente,
                                            tipisoggetto.TIPOSOGGETTO as TipoSoggettoRichiedente,
                                            azienda.codiceanagrafe as codiceAzienda,
                                            azienda.codicefiscale as cfAzienda,
                                            azienda.partitaiva as pivaAzienda
                                        FROM
                                            istanze 
	                                        INNER JOIN anagrafe richiedente ON
		                                        richiedente.idcomune = istanze.idcomune AND 
		                                        richiedente.codiceanagrafe = istanze.codicerichiedente
	
	                                        LEFT OUTER JOIN anagrafe tecnico ON
		                                        tecnico.idcomune = istanze.idcomune AND 
		                                        tecnico.codiceanagrafe = istanze.codiceprofessionista

	                                        LEFT OUTER JOIN anagrafe azienda ON
		                                        azienda.idcomune = istanze.idcomune AND 
		                                        azienda.codiceanagrafe = istanze.CODICETITOLARELEGALE

		                                LEFT OUTER JOIN tipisoggetto ON
		                                        tipisoggetto.idcomune = istanze.idcomune AND 
		                                        tipisoggetto.codicetiposoggetto = istanze.FKCODICESOGGETTO
                                            
                                        Where
                                            istanze.idcomune={this._db.QueryParameter("idComune")} AND 
                                            istanze.codiceistanza={this._db.QueryParameter(nameof(idPratica))}";

            var richiedenteTecnico = this._db.ExecuteReader(sqlRichiedenteTecnico,
                                            mp => mp.Add("idComune", this._idComune)
                                                    .Add(nameof(idPratica), idPratica),
                                            dr => new
                                            {
                                                Richiedente = dr.GetInt("codicerichiedente"),
                                                CfRichiedente = dr.GetString("CfRichiedente"),
                                                CodiceTipoSoggettoRichiedente = dr.GetInt("CodiceTipoSoggettoRichiedente"),
                                                TipoSoggettoRichiedente = dr.GetString("TipoSoggettoRichiedente"),

                                                Tecnico = dr.GetInt("codiceprofessionista"),
                                                CfTecnico = dr.GetString("cfTecnico"),

                                                Azienda = dr.GetInt("codiceAzienda"),
                                                CfAzienda = dr.GetString("cfAzienda"),
                                                PIvaAzienda = dr.GetString("pivaAzienda")
                                            }).FirstOrDefault();

            if (richiedenteTecnico.Richiedente.HasValue)
            {
                retVal.Add(new CodiceAnagrafeConLivelloDiAccesso
                {
                    AccessoCompleto = true,
                    CodiceAnagrafe = richiedenteTecnico.Richiedente.Value,
                    CodiceFiscale = richiedenteTecnico.CfRichiedente,
                    TipoSoggetto = new TipoSoggettoAnagrafica
                    {
                        Codice = richiedenteTecnico.CodiceTipoSoggettoRichiedente.GetValueOrDefault(-1),
                        Descrizione = String.IsNullOrEmpty(richiedenteTecnico.TipoSoggettoRichiedente) ? "Non definito" : richiedenteTecnico.TipoSoggettoRichiedente
                    }
                });
            }

            if (richiedenteTecnico.Tecnico.HasValue)
            {
                retVal.Add(new CodiceAnagrafeConLivelloDiAccesso
                {
                    AccessoCompleto = true,
                    CodiceAnagrafe = richiedenteTecnico.Tecnico.Value,
                    CodiceFiscale = richiedenteTecnico.CfTecnico,
                    TipoSoggetto = new TipoSoggettoAnagrafica
                    {
                        Codice = -1,
                        Descrizione = "Tecnico"
                    }
                });
            }

            if (richiedenteTecnico.Azienda.HasValue)
            {
                retVal.Add(new CodiceAnagrafeConLivelloDiAccesso
                {
                    AccessoCompleto = false,
                    CodiceAnagrafe = richiedenteTecnico.Azienda.Value,
                    CodiceFiscale = richiedenteTecnico.CfAzienda,
                    TipoSoggetto = new TipoSoggettoAnagrafica
                    {
                        Codice = -1,
                        Descrizione = "Azienda"
                    }
                });
            }


            return retVal;
        }
    }
}
