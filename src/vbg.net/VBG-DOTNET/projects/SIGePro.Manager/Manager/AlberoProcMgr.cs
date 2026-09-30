using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.DTO.Interventi;
using Init.SIGePro.Manager.DTO.Normative;
using Init.SIGePro.Manager.DTO.Oneri;
using Init.SIGePro.Manager.DTO.Procedure;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Text.RegularExpressions;

namespace Init.SIGePro.Manager
{
    public enum TipoProcedimentoCart
    {
        ENDO,
        ATTIVITA,
        CATEGORIA
    }

    public class FiltroRicercaFlagPubblica
    {
        public static string Get(AmbitoRicerca ambito)
        {
            switch (ambito)
            {
                case AmbitoRicerca.AreaRiservata:
                    return "1,2";
                case AmbitoRicerca.FrontofficePubblico:
                    return "1,3";
            }

            return "0,1,2,3";
        }
    }

    public partial class AlberoProcMgr
    {
        private static class Constants
        {
            public const string MetadatoFrontendTitoloServizio = "FRONTEND_TITOLO_SERVIZIO";
            public const string MetadatoFrontendSottotitoloServizio = "FRONTEND_SOTTOTITOLO_SERVIZIO";
            public const string MetadatoFrontendUrlServizio = "FRONTEND_URL_SCHEDA_SERVIZIO";
            public const string MetadatoFrontendNomeCategoriaServizio = "FRONTEND_NOME_CATEGORIA_SERVIZIO";
            public const string MetadatoFrontendUrlCategoriaServizio = "FRONTEND_URL_CATEGORIA_SERVIZIO";
        }
        private readonly ILog _log = LogManager.GetLogger(typeof(AlberoProcMgr));
        private readonly DataBase _db;

        public AlberoProcMgr(DataBase dataBase)
        {
            this._db = dataBase;
        }


        public AlberoProc? GetById(string idComune, int idIntervento)
        {
            FormattableString query = $@"SELECT * FROM ALBEROPROC WHERE IDCOMUNE = {idComune} AND SC_ID = {idIntervento}";

            return this._db.GetClassList<AlberoProc>(query).FirstOrDefault();
        }

        public AlberoProc? GetById(int idIntervento, string idComune) => this.GetById(idComune, idIntervento);

        [Obsolete("non utilizzare, carica anche i dati del protocollo. L'operazione va spostata in un servizio dedicato")]
        public AlberoProc GetById(int idIntervento, string idComune, string codiceComune)
        {
            var filtro = new AlberoProc
            {
                Sc_id = idIntervento,
                Idcomune = idComune
            };

            var res = this._db.GetClass(filtro);

            this.ValorizzaDatiProtocollo(res, codiceComune);

            return res;

        }

        public TitoloInterventoDto GetTitoloInterventoDaMetadati(string idComune, int idIntervento)
        {
            var sql = $@"SELECT 
	A.valore AS SERVIZIO_TITOLO,
	B.valore AS SERVIZIO_SOTTOTITOLO,
	C.valore AS SERVIZIO_URL,
	D.valore AS CATEGORIA_NOME,
	E.valore AS CATEGORIA_URL
FROM 
	alberoproc
		LEFT OUTER JOIN alberoproc_metadati A ON
			A.idcomune = alberoproc.idcomune AND
			A.fk_scid = alberoproc.sc_id AND
			A.chiave = '{Constants.MetadatoFrontendTitoloServizio}' 
		LEFT OUTER JOIN alberoproc_metadati B ON
			B.idcomune = alberoproc.idcomune AND
			B.fk_scid = alberoproc.sc_id AND
			B.chiave = '{Constants.MetadatoFrontendSottotitoloServizio}'
		LEFT OUTER JOIN alberoproc_metadati C ON
			C.idcomune = alberoproc.idcomune AND
			C.fk_scid = alberoproc.sc_id AND
			C.chiave = '{Constants.MetadatoFrontendUrlServizio}'
		LEFT OUTER JOIN alberoproc_metadati D ON
			D.idcomune = alberoproc.idcomune AND
			D.fk_scid = alberoproc.sc_id AND
			D.chiave = '{Constants.MetadatoFrontendNomeCategoriaServizio}'
		LEFT OUTER JOIN alberoproc_metadati E ON
			E.idcomune = alberoproc.idcomune AND
			E.fk_scid = alberoproc.sc_id AND
			E.chiave = '{Constants.MetadatoFrontendUrlCategoriaServizio}'
WHERE 
    alberoproc.idcomune={this._db.QueryParameter("idComune")} AND 
    alberoproc.sc_id={this._db.QueryParameter("idIntervento")}";

            var res = this._db.ExecuteReader(
                sql,
                mp => mp.Add("idComune", idComune)
                        .Add("idIntervento", idIntervento),
                dr => new
                {
                    ServizioTitolo = dr.GetString("SERVIZIO_TITOLO"),
                    ServizioSottotitolo = dr.GetString("SERVIZIO_SOTTOTITOLO"),
                    ServizioUrl = dr.GetString("SERVIZIO_URL"),
                    CategoriaNome = dr.GetString("CATEGORIA_NOME"),
                    CategoriaUrl = dr.GetString("CATEGORIA_URL")
                }).FirstOrDefault();


            var dto = new TitoloInterventoDto
            {
                Id = idIntervento,
                Titolo = res?.ServizioTitolo,
                Note = res?.ServizioSottotitolo,
                UrlSchedaServizio = res?.ServizioUrl,
                NomeCategoria = res?.CategoriaNome,
                UrlCategoria = res?.CategoriaUrl
            };

            if (String.IsNullOrEmpty(dto.Titolo))
            {
                var intervento = this.GetById(idComune, idIntervento);

                dto.Titolo = intervento?.DescrizioneCompleta?.ToString() ?? "";
            }

            return dto;
        }

        public string GetDescrizioneCompletaDaIdIntervento(int idIntervento, string idComune, string software)
        {

            var albero = this.GetById(idIntervento, idComune);

            if (albero == null)
                return "";

            var descrizione = Enumerable.Range(0, (albero.SC_CODICE.Length - 2) / 2)
                             .Select(x =>
                             {
                                 var codice = albero.SC_CODICE.Substring(0, (x + 1) * 2);
                                 var ramo = this.GetByScCodice(idComune, software, codice);
                                 return ramo.SC_DESCRIZIONE;
                             });

            return String.Join(" - ", descrizione);
        }

        private void ValorizzaDatiProtocollo(AlberoProc albero, string codiceComune)
        {
            var mgr = new AlberoProcProtocolloMgr(this._db);
            var alberoProto = mgr.GetByCodiceComune(albero.Idcomune, Convert.ToInt32(albero.Sc_id), codiceComune);

            if (alberoProto != null)
            {
                albero.FascicolazioneAutomatica = alberoProto.ScFascautomatica.HasValue ? alberoProto.ScFascautomatica.Value.ToString() : "";
                albero.NumeroFascicolazione = alberoProto.ScFascNumero;
                albero.ClassificaFascicolazione = alberoProto.ScFascclassifica;
                albero.TestoTipoFascicolazione = alberoProto.ScFasccodtesto.HasValue ? alberoProto.ScFasccodtesto.Value.ToString() : "";
                albero.ProtocollazioneAutomatica = alberoProto.ScProtautomatica.HasValue ? alberoProto.ScProtautomatica.Value.ToString() : "";
                albero.ClassificaProtocollazione = alberoProto.ScProtclassifica;
                albero.TestoTipoProtocollazione = alberoProto.ScProtcodtesto.HasValue ? alberoProto.ScProtcodtesto.Value.ToString() : "";
                albero.TipoDocumentoProtocollazione = alberoProto.ScProttipodocumento;
                albero.CodiceAmministrazione = alberoProto.Codiceamministrazione.HasValue ? alberoProto.Codiceamministrazione.Value : (int?)null;
            }
        }



        /// <summary>
        /// Recupera la classifica controllando i valori dell'albero dei procedimenti del protocollo a ritroso a mano che non vengano trovati valori.
        /// </summary>
        /// <param name="idIntervento"></param>
        /// <param name="idComune"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public string GetClassificaProtocolloFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.ClassificaProtocollazione))
                return albero.ClassificaProtocollazione;

            var range = albero.GetListaScCodice()
                              .ToArray()
                              .Reverse()
                              .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.ClassificaProtocollazione))
                    return albero.ClassificaProtocollazione;
            }

            return "";
        }

        public string GetTipoDocumentoProtocolloFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.TipoDocumentoProtocollazione))
                return albero.TipoDocumentoProtocollazione;

            var range = albero.GetListaScCodice()
                              .ToArray()
                              .Reverse()
                              .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.TipoDocumentoProtocollazione))
                    return albero.TipoDocumentoProtocollazione;
            }

            return "";
        }

        public int? GetAmministrazioneFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (albero.CodiceAmministrazione.HasValue)
                return albero.CodiceAmministrazione;

            var range = albero.GetListaScCodice()
                              .ToArray()
                              .Reverse()
                              .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (albero.CodiceAmministrazione.HasValue)
                    return albero.CodiceAmministrazione;
            }

            return null;
        }

        public bool IsProtocollazioneAutomatica(int idIntervento, int source, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.ProtocollazioneAutomatica) && Convert.ToInt32(albero.ProtocollazioneAutomatica) != 8)
            {
                return (Convert.ToInt32(albero.ProtocollazioneAutomatica) & source) == source;
            }

            var range = albero.GetListaScCodice()
                              .ToArray()
                              .Reverse()
                              .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.ProtocollazioneAutomatica) && Convert.ToInt32(albero.ProtocollazioneAutomatica) != 8)
                {
                    return (Convert.ToInt32(albero.ProtocollazioneAutomatica) & source) == source;
                }
            }

            return false;
        }

        public bool IsFascicolazioneAutomatica(int idIntervento, int source, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.FascicolazioneAutomatica) && Convert.ToInt32(albero.FascicolazioneAutomatica) != 8)
                return (Convert.ToInt32(albero.FascicolazioneAutomatica) & source) == source;

            var range = albero.GetListaScCodice()
                              .ToArray()
                              .Reverse()
                              .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.FascicolazioneAutomatica) && Convert.ToInt32(albero.FascicolazioneAutomatica) != 8)
                    return (Convert.ToInt32(albero.FascicolazioneAutomatica) & source) == source;
            }

            return false;
        }

        /// <summary>
        /// Recupera la classifica del fascicolo controllando i valori dell'albero dei procedimenti del protocollo a ritroso a mano che non vengano trovati valori.
        /// </summary>
        /// <param name="idIntervento"></param>
        /// <param name="idComune"></param>
        /// <param name="software"></param>
        /// <param name="codiceComune"></param>
        /// <returns></returns>
        public string GetClassificaFascicoloFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.ClassificaFascicolazione))
                return albero.ClassificaFascicolazione;

            var range = albero.GetListaScCodice()
                              .ToArray()
                              .Reverse()
                              .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.ClassificaFascicolazione))
                    return albero.ClassificaFascicolazione;
            }

            return "";
        }
        public string GetNumeroFascicoloFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.NumeroFascicolazione))
                return albero.NumeroFascicolazione;

            var range = albero.GetListaScCodice()
                              .ToArray()
                              .Reverse()
                              .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.NumeroFascicolazione))
                    return albero.NumeroFascicolazione;
            }

            return "";
        }
        public string GetTestoTipoProtocolloFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.TestoTipoProtocollazione))
                return albero.TestoTipoProtocollazione;

            var range = albero.GetListaScCodice()
                              .ToArray()
                              .Reverse()
                              .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.TestoTipoProtocollazione))
                    return albero.TestoTipoProtocollazione;
            }

            return "";
        }
        public string GetTestoTipoFascicoloFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var albero = this.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.TestoTipoFascicolazione))
                return albero.TestoTipoFascicolazione;

            var range = albero.GetListaScCodice()
                              .ToArray()
                              .Reverse()
                              .Skip(1);

            foreach (var el in range)
            {
                albero = this.GetByScCodice(idComune, software, el, codiceComune);

                if (!String.IsNullOrEmpty(albero.TestoTipoFascicolazione))
                    return albero.TestoTipoFascicolazione;
            }

            return "";
        }

        public AlberoProc GetByScCodice(string idComune, string software, string scCodice, string codiceComune)
        {
            var res = this.GetByScCodice(idComune, software, scCodice);
            this.ValorizzaDatiProtocollo(res, codiceComune);

            return res;
        }

        public AlberoProc? GetByScCodice(string idComune, string software, string scCodice)
        {
            FormattableString query = $@"SELECT * FROM ALBEROPROC WHERE IDCOMUNE = {idComune} AND SOFTWARE = {software} AND SC_CODICE = {scCodice}";

            return this._db.GetClassList<AlberoProc>(query).FirstOrDefault();
        }

        public int? CodiceProceduraDaIdIntervento(string idComune, int codiceIntervento)
        {
            var ramoAlbero = this.GetById(codiceIntervento, idComune);

            if (ramoAlbero == null)
            {
                return null;
            }

            return this.CodiceProceduraDaIntervento(ramoAlbero);
        }

        private int? CodiceProceduraDaIntervento(AlberoProc ramoAlbero)
        {
            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var condWhere = ramoAlbero.GetListaScCodice().ToString();

                var sql = @"select 
								FKIDPROCEDURA 
							from 
								alberoproc 
							where 
								idcomune = {0} and 
								software = {1} and 
								SC_CODICE in (" + condWhere + @") and
								FKIDPROCEDURA is not null
							order by 
								SC_CODICE DESC";

                sql = this.PreparaQueryParametrica(sql, "idComune", "software");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", ramoAlbero.Idcomune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", ramoAlbero.SOFTWARE));

                    using (var dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                        {
                            var objCodProcedura = dr["fkidprocedura"];

                            if (String.IsNullOrEmpty(objCodProcedura.ToString()))
                            {
                                continue;
                            }

                            return Convert.ToInt32(objCodProcedura);
                        }

                        return null;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        public NodoConWorkflowDto? GetNodoConWorkflowDaIdIntervento(string idComune, int idIntervento)
        {
            var ramoAlbero = this.GetById(idIntervento, idComune);

            if (ramoAlbero == null)
            {
                return null;
            }

            var listaScId = ramoAlbero.GetListaScCodice();

            var condWhere = listaScId.ToString();

            var sql = $@"select 
                                sc_id,
                                sc_descrizione,
								codiceoggetto_workflow 
							from 
								alberoproc 
							where 
								alberoproc.idcomune = {this._db.QueryParameter("idComune")} and 
								alberoproc.software = {this._db.QueryParameter("software")} and 
								alberoproc.SC_CODICE in (" + condWhere + @") and
								alberoproc.codiceoggetto_workflow is not null								
							order by 
								alberoproc.SC_CODICE DESC";

            return this._db.ExecuteReader(sql,
                                    mp => mp.Add("idComune", ramoAlbero.Idcomune)
                                            .Add("software", ramoAlbero.SOFTWARE),
                                    dr => new NodoConWorkflowDto
                                    {
                                        Id = dr.GetInt("sc_id")!.Value,
                                        Descrizione = dr.GetString("sc_descrizione"),
                                        CodiceOggettoWorkflow = dr.GetInt("codiceoggetto_workflow")!.Value
                                    })
                                    .FirstOrDefault();
        }

        public int? GetCodiceOggettoWorkflowDaIdIntervento(string idComune, int idIntervento)
        {
            var ramoAlbero = this.GetById(idIntervento, idComune);

            if (ramoAlbero == null)
            {
                return null;
            }

            var listaScId = ramoAlbero.GetListaScCodice();

            var condWhere = listaScId.ToString();

            var sql = $@"select 
								codiceoggetto_workflow 
							from 
								alberoproc 
							where 
								alberoproc.idcomune = {this._db.QueryParameter("idComune")} and 
								alberoproc.software = {this._db.QueryParameter("software")} and 
								alberoproc.SC_CODICE in (" + condWhere + @") and
								alberoproc.codiceoggetto_workflow is not null								
							order by 
								alberoproc.SC_CODICE DESC";

            return this._db.ExecuteReader(sql,
                                    mp => mp.Add("idComune", ramoAlbero.Idcomune)
                                            .Add("software", ramoAlbero.SOFTWARE),
                                    dr => dr.GetInt("codiceoggetto_workflow"))
                                    .FirstOrDefault();
        }

        public int? GetIdRiepilogoDomandaDaIdIntervento(string idComune, int idIntervento)
        {
            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var ramoAlbero = this.GetById(idIntervento, idComune);

                var listaScId = ramoAlbero.GetListaScCodice();

                var condWhere = listaScId.ToString();

                var sql = @"SELECT 
								sm_id 
							FROM
								alberoproc, 
								alberoproc_documenti 
							WHERE 
								alberoproc_documenti.idcomune = alberoproc.idcomune AND
								alberoproc_documenti.sm_fkscid = alberoproc.sc_id AND  
								alberoproc.idcomune = {0} AND 
								alberoproc.software = {1} AND
								alberoproc.sc_codice IN (" + condWhere + @") and
								alberoproc_documenti.flg_domandafo = 1 and
								alberoproc_documenti.codiceoggetto IS NOT null and
                                alberoproc_documenti.pubblica in (1,2)
							ORDER BY alberoproc.sc_codice desc";

                sql = this.PreparaQueryParametrica(sql, "idComune", "software");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", ramoAlbero.Idcomune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", ramoAlbero.SOFTWARE));

                    using (var dr = cmd.ExecuteReader())
                    {
                        if (dr.Read())
                        {
                            var idDocumento = dr["sm_id"];

                            if (idDocumento != null && idDocumento != DBNull.Value)
                            {
                                return Convert.ToInt32(idDocumento);
                            }
                        }

                        return null;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        public int? GetIdCertificatoDiInvioDomandaDaIdIntervento(string idComune, int idIntervento)
        {
            var ramoAlbero = this.GetById(idIntervento, idComune);

            if (ramoAlbero == null)
            {
                return null;
            }

            return this.GetIdCertificatoDiInvioDomandaDaIdIntervento(ramoAlbero, idIntervento);
        }

        public int? GetIdCertificatoDiInvioDomandaDaIdIntervento(AlberoProc ramoAlbero, int idIntervento)
        {
            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var listaScId = ramoAlbero.GetListaScCodice();

                var condWhere = listaScId.ToString();

                var sql = @"select 
								codiceoggetto_certinvio 
							from 
								alberoproc,
								tipiprocedure 
							where 
								alberoproc.idcomune       = tipiprocedure.idcomune AND
								alberoproc.FKIDPROCEDURA  = tipiprocedure.codiceprocedura AND
								alberoproc.idcomune = {0} and 
								alberoproc.software = {1} and 
								alberoproc.SC_CODICE in (" + condWhere + @")								
							order by 
								alberoproc.SC_CODICE DESC";

                sql = this.PreparaQueryParametrica(sql, "idComune", "software");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", ramoAlbero.Idcomune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", ramoAlbero.SOFTWARE));

                    using (var dr = cmd.ExecuteReader())
                    {
                        if (dr.Read())
                        {
                            var objCodProcedura = dr["codiceoggetto_certinvio"];

                            if (objCodProcedura != null && objCodProcedura != DBNull.Value)
                            {
                                return Convert.ToInt32(objCodProcedura);
                            }
                        }

                        return null;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        public List<AlberoProcRuoli> GetRuoliDaIdIntervento(string idComune, int idIntervento)
        {
            var albero = this.GetById(idIntervento, idComune);

            var ruoli = new AlberoProcRuoli();
            ruoli.OthersWhereClause.Add("ALBEROPROC_RUOLI.FK_IDRUOLO IS NOT NULL");
            ruoli.OthersTables.Add("ALBEROPROC");
            ruoli.OthersWhereClause.Add("ALBEROPROC.IDCOMUNE = ALBEROPROC_RUOLI.IDCOMUNE");
            ruoli.OthersWhereClause.Add("ALBEROPROC.SC_ID = ALBEROPROC_RUOLI.FK_SC_ID");
            ruoli.OthersWhereClause.Add("ALBEROPROC.IDCOMUNE = '" + albero.Idcomune + "'");
            ruoli.OthersWhereClause.Add("ALBEROPROC.SOFTWARE = '" + albero.SOFTWARE + "'");

            var sc_codice = string.Empty;

            for (var i = 2; i <= albero.SC_CODICE.Length; i += 2)
            {
                sc_codice += "'" + albero.SC_CODICE.Substring(0, i) + "',";
            }

            sc_codice = sc_codice.Substring(0, sc_codice.Length - 1);

            ruoli.OthersWhereClause.Add("ALBEROPROC.SC_CODICE IN (" + sc_codice + ")");

            return new AlberoProcRuoliMgr(this._db).GetList(ruoli);
        }

        /// <summary>
        /// Legge l'albero dei procedimenti risalendo dall'endo passato.
        /// Restituisce il nodo corrispondente all'id passato e tutti  suoi nodi padre
        /// </summary>
        /// <param name="idComune">id comune</param>
        /// <param name="idIntervento">id del nodo dell'albero</param>
        /// <returns>nodo corrispondente all'id passato e tutti  suoi nodi padre</returns>
        public ClassTree<AlberoProc> RisaliStrutturaAlbero(string idComune, int idIntervento, bool verticalizzazioneCartAttiva)
        {
            var ramoAlbero = this.GetById(idIntervento, idComune);

            if (ramoAlbero == null)
            {
                return new ClassTree<AlberoProc>();
            }

            var listaScId = ramoAlbero.GetListaScCodice();

            var condWhere = listaScId.ToString();

            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var sql = this.PreparaQueryParametrica("select * from alberoproc where idComune = {0} and software = {1}", "idComune", "software");

                sql += String.Format(" and sc_codice in ({0}) order by sc_codice asc", condWhere);

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", ramoAlbero.SOFTWARE));

                    var lista = this._db.GetClassList<AlberoProc>(cmd);

                    // Ho sempre almeno un elemento
                    var albero = new ClassTree<AlberoProc>(lista[0]);
                    var tmpNodo = albero.NodiFiglio;

                    for (var i = 1; i < lista.Count; i++)
                    {
                        // Se è attiva la verticalizzazione CART devo mostrare solamente i nodi dell'albero che hanno un collegamento su 
                        // STP_ENDO_TIPO2 con STP_ENDO_TIPO2.codiceoggetto != null
                        if (verticalizzazioneCartAttiva)
                        {
                            var contieneOggettoCart = new StpEndoTipo2Mgr(this._db).GetList(new StpEndoTipo2
                            {
                                Idcomune = idComune,
                                FkScId = Convert.ToInt32(lista[i].Sc_id),
                                OthersWhereClause = new List<string> { "CODICEOGGETTO is not null" }
                            }).Count != 0;

                            if (!contieneOggettoCart)
                            {
                                lista[i].SC_NOTE = String.Empty;
                            }
                        }

                        tmpNodo.Add(new ClassTree<AlberoProc>(lista[i]));
                        tmpNodo = tmpNodo[0].NodiFiglio;
                    }

                    return albero;
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        internal int GetCodiceInterventoProcDaCodiceIstanza(string idComune, int codiceIstanza)
        {
            var istanza = new IstanzeMgr(this._db).GetById(idComune, codiceIstanza);

            if (istanza == null)
            {
                throw new InvalidOperationException($"Non è stato possibile individuare l'istanza con IDCOMUNE {idComune} e CODICEISTANZA {codiceIstanza}");
            }

            if (String.IsNullOrEmpty(istanza.CODICEINTERVENTOPROC))
            {
                throw new InvalidOperationException($"Non è stato possibile individuare CODICEINTERVENTOOPROC per l'istanza con IDCOMUNE {idComune} e CODICEISTANZA {codiceIstanza}");
            }

            return Convert.ToInt32(istanza.CODICEINTERVENTOPROC);
        }

        internal IEnumerable<AlberoProc> GetAlberaturaIntervento(string idComune, int idIntervento)
        {
            var intervento = this.GetById(idIntervento, idComune);

            if (intervento == null)
            {
                return Enumerable.Empty<AlberoProc>();
            }

            var software = intervento.SOFTWARE;

            var whereIn = intervento.GetListaScCodice().ToString();

            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"SELECT DISTINCT alberoproc.*
								FROM alberoproc
								WHERE idcomune   = {0}
								AND software     = {1}
								AND sc_codice IN (" + whereIn + @")
								ORDER BY sc_codice ASC";

                sql = this.PreparaQueryParametrica(sql, "idComune", "software", "scCodice");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", software));

                    return this._db.GetClassList<AlberoProc>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        [Obsolete]
        public string GetIdDrupalDaCodiceIntervento(string idComune, int idIntervento)
        {
            var rami = this.GetAlberaturaIntervento(idComune, idIntervento);

            var ramo = rami.Reverse().FirstOrDefault(x => !String.IsNullOrEmpty(x.DrupalNid));

            return ramo == null ? null : ramo.DrupalNid;
        }

        /// <summary>
        /// TODO: Il flag SC_ATTIVA deve essere controllato a seconda che il metodo venga chiamato dall'area riservata 
        /// oppure dal frontend.
        /// Se chiamato dall'area riservata il flag deve essere == 1 || == 2
        /// Se chiamato dal frontend deve essere == 1 || == 3
        /// </summary>
        internal List<AlberoProc> GetAlberaturaCompletaDaScCodice(string idComune, string software, string scCodice, AmbitoRicerca ambitoricerca)
        {
            var partiCodice = new List<string>();

            for (var i = 0; i < scCodice.Length; i = i + 2)
            {
                partiCodice.Add(scCodice.Substring(0, i + 2));
            }

            var whereIn = "'" + String.Join("','", partiCodice.ToArray()) + "'";

            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"SELECT DISTINCT alberoproc.*
								FROM alberoproc
								WHERE idcomune   = {0}
								AND software     = {1}
								AND ( sc_codice IN (" + whereIn + @")
								OR sc_codice LIKE {2} )
								AND sc_attivo  = 0
								AND (sc_pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoricerca) + @") or sc_pubblica is null )" +
                                " ORDER BY sc_codice ASC";

                sql = this.PreparaQueryParametrica(sql, "idComune", "software", "scCodice");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", software));
                    cmd.Parameters.Add(this._db.CreateParameter("scCodice", scCodice + "%"));

                    var interventi = this._db.GetClassList<AlberoProc>(cmd);
                    var listaFoglieDaRimuovere = new List<AlberoProc>();

                    // Prendo tutti i nodi di primo livello che hanno pubblica nullo
                    var q = interventi.Where(nodoAlbero => nodoAlbero.SC_CODICE.Length == 2 && String.IsNullOrEmpty(nodoAlbero.SC_PUBBLICA));

                    foreach (var nodoAlbero in q)
                    {
                        listaFoglieDaRimuovere.Add(nodoAlbero);
                    }

                    listaFoglieDaRimuovere.ForEach(nodoAlbero => interventi.Remove(nodoAlbero));

                    return interventi;
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        public List<OnereDto> GetListaOneriDaIdIntervento(string idComune, int idIntervento)
        {
            var ramoAlbero = this.GetById(idIntervento, idComune);

            if (ramoAlbero == null)
            {
                return new List<OnereDto>();
            }

            var listaScId = ramoAlbero.GetListaScCodice();

            var condWhere = listaScId.ToString();

            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"SELECT 
							  DISTINCT 
							  tipicausalioneri.co_id AS codice,
							  tipicausalioneri.co_descrizione AS descrizione,
							  alberoproc_oneri.ao_importocausale AS importo,
							  alberoproc_oneri.note as note,
                              alberoproc_oneri.DYN2CAMPI_ONERI_FRONT as D2C
							FROM 
							  alberoproc,
							  alberoproc_oneri,                                                             
							  tipicausalioneri
							WHERE
							  tipicausalioneri.idComune = alberoproc_oneri.idComune AND
							  tipicausalioneri.co_id = alberoproc_oneri.ao_fk_coid AND 
							  alberoproc_oneri.idComune = alberoproc.idComune AND
							  alberoproc_oneri.ao_fk_scid = alberoproc.sc_id  AND
							  alberoproc.idComune = {0} AND
							  alberoproc.software = {1} AND
							  alberoproc.sc_codice IN (" + condWhere + ") order by tipicausalioneri.co_descrizione";

                sql = this.PreparaQueryParametrica(sql, "idComune", "software");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", ramoAlbero.SOFTWARE));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var oneri = new List<OnereDto>();

                        while (dr.Read())
                        {
                            var el = new OnereDto
                            {
                                Codice = Convert.ToInt32(dr["codice"]),
                                Descrizione = dr["descrizione"].ToString(),
                                Importo = Convert.ToSingle(dr["importo"] == DBNull.Value ? 0 : dr["importo"]),
                                OrigineOnere = "I",
                                CodiceInterventoOEndoOrigine = ramoAlbero.Sc_id.Value,
                                InterventoOEndoOrigine = ramoAlbero.SC_DESCRIZIONE,
                                Note = dr["note"].ToString(),
                                CampoDinamico = dr["D2C"] == DBNull.Value ? (int?)null : Convert.ToInt32(dr["D2C"])
                            };

                            oneri.Add(el);
                        }

                        return oneri;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        public List<DocumentoInterventoDto> GetDocumentiDaIdIntervento(string idComune, int codiceIntervento, AmbitoRicerca ambitoRicercaDocumento)
        {
            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var ramoAlbero = this.GetById(codiceIntervento, idComune);

                if (ramoAlbero == null)
                {
                    return new List<DocumentoInterventoDto>();
                }

                var listaScId = ramoAlbero.GetListaScCodice();

                var condWhere = listaScId.ToString();

                var sql = @"SELECT 
								alberoproc_documenti.sm_id AS codice,
								alberoproc_documenti.descrizione AS descrizione,
								alberoproc_documenti.note AS note,
								alberoproc_documenti.codiceoggetto AS codiceoggetto,
								alberoproc_documenti.Richiesto AS obbligatorio,
								alberoproc_documenti.flg_domandafo AS domandafo,
								alberoproc_documenti.fo_richiedefirma AS richiedefirma,
								alberoproc_documenti.fo_tipodownload AS tipodownload,
                                alberoproc_documenti.fo_dimensione_massima,
                                alberoproc_documenti.fo_estensioni_ammesse,
								oggetti.nomefile AS nomeFile
							FROM                                                                                 
								alberoproc join alberoproc_documenti ON 
									alberoproc_documenti.idComune = alberoproc.idcomune AND
									alberoproc_documenti.sm_fkscid = alberoproc.sc_id
							left join oggetti ON 
								alberoproc_documenti.idComune = oggetti.idcomune AND
								alberoproc_documenti.codiceoggetto = oggetti.codiceoggetto
							WHERE
								alberoproc.idcomune = {0} AND
								alberoproc.software = {1} AND
								alberoproc_documenti.pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumento) + @") and
								alberoproc.sc_codice in (" + condWhere + @")
							ORDER BY
								alberoproc_documenti.ordine";

                sql = this.PreparaQueryParametrica(sql, "idComune", "software");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", ramoAlbero.SOFTWARE));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var documentiIntervento = new List<DocumentoInterventoDto>();

                        while (dr.Read())
                        {
                            var codice = Convert.ToInt32(dr["codice"]);
                            var descrizione = dr["descrizione"].ToString();
                            // var note = dr["note"].ToString();
                            var codiceoggetto = (int?)null;
                            var obbligatorio = dr["obbligatorio"].ToString() == "1";
                            var domandafo = dr["domandafo"].ToString() == "1";
                            var richiedefirma = dr["richiedefirma"].ToString() == "1";
                            var tipodownload = dr["tipodownload"].ToString();
                            var nomeFile = dr["nomeFile"].ToString();

                            var objCodiceOggetto = dr["codiceoggetto"];

                            if (objCodiceOggetto != DBNull.Value)
                            {
                                codiceoggetto = Convert.ToInt32(objCodiceOggetto);
                            }

                            var el = new DocumentoInterventoDto
                            {
                                Codice = codice,
                                Descrizione = descrizione,
                                CodiceOggetto = codiceoggetto,
                                DomandaFo = domandafo,
                                Obbligatorio = obbligatorio,
                                RichiedeFirma = richiedefirma,
                                TipoDownload = tipodownload,
                                NomeFile = nomeFile,
                                DimensioneMassima = dr["fo_dimensione_massima"] == DBNull.Value ? (int?)null : Convert.ToInt32(dr["fo_dimensione_massima"]),
                                EstensioniAmmesse = dr["fo_estensioni_ammesse"].ToString()
                            };

                            documentiIntervento.Add(el);
                        }

                        return documentiIntervento;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        public List<SchedaDinamicaInterventoDto> GetSchedeDinamicheFoDaIdIntervento(string idComune, int idIntervento)
        {
            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var ramoAlbero = this.GetById(idIntervento, idComune);

                if (ramoAlbero == null)
                {
                    return new List<SchedaDinamicaInterventoDto>();
                }

                var listaScId = ramoAlbero.GetListaScCodice();

                var condWhere = listaScId.ToString();

                var sql = $@"SELECT 
									distinct
									dyn2_modellit.id,
									dyn2_modellit.codice_scheda,
									dyn2_modellit.descrizione,
									alberoproc_dyn2modellit.flag_tipofirma,
									alberoproc_dyn2modellit.flag_facoltativa,
									alberoproc_dyn2modellit.ordine
								FROM 
									alberoproc,
									alberoproc_dyn2modellit,
									dyn2_modellit
								WHERE
									alberoproc_dyn2modellit.idComune = alberoproc.idComune AND
									alberoproc_dyn2modellit.fk_sc_id = alberoproc.sc_id AND
									dyn2_modellit.idComune = alberoproc_dyn2modellit.idComune AND
									dyn2_modellit.id = alberoproc_dyn2modellit.fk_d2mt_id AND
									alberoproc.idComune = {this._db.QueryParameter("IdComune")} and 
                                    alberoproc.software = {this._db.QueryParameter("Software")} and 
									alberoproc_dyn2modellit.flag_pubblica = 1";

                sql += String.Format(" AND alberoproc.sc_codice IN ({0})", condWhere);
                sql += " order by alberoproc_dyn2modellit.ordine, dyn2_modellit.descrizione";

                var schedeIntervento = new List<SchedaDinamicaInterventoDto>();

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", ramoAlbero.SOFTWARE));

                    using (var dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                        {
                            var idModello = Convert.ToInt32(dr["id"]);
                            // var codiceScheda = dr["codice_scheda"].ToString();
                            var descrizioneModello = dr["descrizione"].ToString();
                            var objTipoFirma = dr["flag_tipofirma"];
                            var facoltativa = dr["flag_facoltativa"].ToString() == String.Empty ? false : dr["flag_facoltativa"].ToString() == "1";
                            var ordine = dr["ordine"] == DBNull.Value ? null : (int?)Convert.ToInt32(dr["ordine"]);

                            var tipoFirma = TipoFirmaEnum.NessunaFirma;

                            if (objTipoFirma != DBNull.Value)
                            {
                                tipoFirma = (TipoFirmaEnum)Convert.ToInt32(objTipoFirma);
                            }

                            var schedaPresente = schedeIntervento.FirstOrDefault(x => x.Id == idModello);

                            if (schedaPresente != null)
                            {
                                var errMsg = $@"Nell'intervento {ramoAlbero.Sc_id} (o in uno dei nodi padre ) la scheda dinamica {idModello} è presente più di una volta. 
Parametri della scheda presente:
- tipo firma={schedaPresente.TipoFirma}, 
- facoltativa={schedaPresente.Facoltativa}

Parametri della scheda che si sta cercando di aggiungere:
- tipo firma={tipoFirma}, 
- facoltativa={facoltativa}";

                                throw new InvalidOperationException(errMsg);
                            }

                            var scheda = new SchedaDinamicaInterventoDto
                            {
                                CodiceIntervento = ramoAlbero.Sc_id!.Value,
                                Id = idModello,
                                Descrizione = descrizioneModello,
                                TipoFirma = tipoFirma,
                                Facoltativa = facoltativa,
                                Ordine = ordine
                            };

                            schedeIntervento.Add(scheda);
                        }
                    }
                }

                // Aggiungo le schede della procedura dell'intervento
                var schedeProcedura = this.GetSchedeDinamicheDaProceduraIntervento(ramoAlbero);

                schedeIntervento.AddRange(schedeProcedura.Where(schedaProcedura => schedeIntervento.FirstOrDefault(x => x.Id == schedaProcedura.Id) == null));

                // Le schede devono essere visualizzate in base all'ordine e in base alla descrizione.
                // Le schede senza ordine vanno mostrate prima di quelle con ordine
                schedeIntervento.Sort((x, y) =>
                {
                    if (x == y)
                    {
                        return 0;
                    }

                    if (x == null)
                    {
                        return 1;
                    }

                    if (y == null)
                    {
                        return -1;
                    }

                    var cmpRes = x.Ordine.GetValueOrDefault(-1).CompareTo(y.Ordine.GetValueOrDefault(-1));

                    if (cmpRes != 0)
                    {
                        return cmpRes;
                    }

                    return x.Descrizione.CompareTo(y.Descrizione);
                });

                return schedeIntervento;
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        private string GetValoreMetadato(string idComune, int idIntervento, string chiaveMetadato)
        {
            var sql = $@"
SELECT 
    valore 
FROM 
    alberoproc_metadati 
WHERE 
    idcomune={this._db.QueryParameter(nameof(idComune))} AND 
    fk_scid={this._db.QueryParameter(nameof(idIntervento))} AND 
    chiave={this._db.QueryParameter(nameof(chiaveMetadato))}";

            return this._db.ExecuteScalar(sql, "",
                mp => mp.Add(nameof(idComune), idComune)
                        .Add(nameof(idIntervento), idIntervento)
                        .Add(nameof(chiaveMetadato), chiaveMetadato));
        }

        public String GetPrimaOccorrenzaMetadato(string idComune, int idIntervento, string chiaveMetadato)
        {
            var closeCnn = false;
            string retVal = null;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var ramoAlbero = this.GetById(idIntervento, idComune);

                var listaScCodice = ramoAlbero.GetListaScCodice();

                var condWhere = listaScCodice.ToString();

                var sql = this.PreparaQueryParametrica(
                            @"select 
									alberoproc_metadati.valore
								from 
									alberoproc,
									alberoproc_metadati
								where
									alberoproc_metadati.idcomune = alberoproc.idcomune and
									alberoproc_metadati.fk_scid = alberoproc.sc_id and
									alberoproc.idcomune = {0} and alberoproc.software = {1} and 
									alberoproc_metadati.chiave = {2}",
                            "idcomune",
                            "software",
                            "chiave");
                sql += String.Format(" and alberoproc.sc_codice IN ({0})", condWhere);
                sql += " order by alberoproc.sc_codice desc";

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", ramoAlbero.SOFTWARE));
                    cmd.Parameters.Add(this._db.CreateParameter("chiave", chiaveMetadato));

                    using (var dr = cmd.ExecuteReader())
                    {
                        if (dr.Read())
                        {
                            retVal = dr["valore"].ToString();
                        }
                    }
                }

                return retVal;

            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        private List<SchedaDinamicaInterventoDto> GetSchedeDinamicheDaProceduraIntervento(AlberoProc ramoAlbero)
        {
            var codiceProcedura = this.CodiceProceduraDaIntervento(ramoAlbero);

            if (!codiceProcedura.HasValue)
            {
                return new List<SchedaDinamicaInterventoDto>();
            }

            var schedeIntervento = new List<SchedaDinamicaInterventoDto>();

            // Leggo la sita dei modelli dinamici collegati alla procedura dell'intervento selezionato
            var sql = this.PreparaQueryParametrica(
                        @"SELECT
							dyn2_modellit.id,
							dyn2_modellit.descrizione, 
							tipiprocedure_dyn2modellit.flag_tipofirma,
							tipiprocedure_dyn2modellit.flag_facoltativa 
						FROM
							tipiprocedure_dyn2modellit,
							dyn2_modellit
						WHERE
							dyn2_modellit.idcomune = tipiprocedure_dyn2modellit.idcomune AND
							dyn2_modellit.id = tipiprocedure_dyn2modellit.fk_d2mt_id AND
							tipiprocedure_dyn2modellit.idcomune = {0} AND 
							tipiprocedure_dyn2modellit.fk_codiceprocedura = {1} and tipiprocedure_dyn2modellit.flag_pubblica = 1",
                        "idComune",
                        "codiceprocedura");

            using (var cmd = this._db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this._db.CreateParameter("idComune", ramoAlbero.Idcomune));
                cmd.Parameters.Add(this._db.CreateParameter("codiceprocedura", codiceProcedura));

                using (var dr = cmd.ExecuteReader())
                {
                    while (dr.Read())
                    {
                        var idModello = Convert.ToInt32(dr["id"]);
                        var descrizioneModello = dr["descrizione"].ToString();
                        var objTipoFirma = dr["flag_tipofirma"];
                        var facoltativa = dr["flag_facoltativa"].ToString() == String.Empty ? false : dr["flag_facoltativa"].ToString() == "1";

                        var tipoFirma = TipoFirmaEnum.NessunaFirma;

                        if (objTipoFirma != DBNull.Value)
                        {
                            tipoFirma = (TipoFirmaEnum)Convert.ToInt32(objTipoFirma);
                        }

                        var schedaPresente = schedeIntervento.FirstOrDefault(x => x.Id == idModello);

                        // Se la scheda è già presente la ignoro e utilizzo quella dell'intervento
                        if (schedaPresente != null)
                        {
                            continue;
                        }

                        var scheda = new SchedaDinamicaInterventoDto
                        {
                            CodiceIntervento = ramoAlbero.Sc_id.Value,
                            Id = idModello,
                            Descrizione = descrizioneModello,
                            TipoFirma = tipoFirma,
                            Facoltativa = facoltativa
                        };

                        schedeIntervento.Add(scheda);
                    }
                }
            }

            return schedeIntervento;
        }

        public List<NormativaDto> GetListaNormativeDaIdIntervento(string idComune, int codiceIntervento)
        {
            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var ramoAlbero = this.GetById(codiceIntervento, idComune);

                var listaScId = ramoAlbero.GetListaScCodice();

                var condWhere = listaScId.ToString();

                var sql = @"SELECT 
								leggi.le_id AS codice,
								leggi.le_descrizione AS descrizione,
								leggi.codiceoggetto AS codiceoggetto,
								leggi.le_link as link,
								leggitipi.lt_descrizione AS categoria
							FROM 
								alberoproc join alberoproc_leggi ON
									alberoproc_leggi.idcomune = alberoproc.idcomune AND
									alberoproc_leggi.sl_fkscid = alberoproc.sc_id 
								join leggi ON
									leggi.idcomune = alberoproc_leggi.idcomune AND
									leggi.le_id = alberoproc_leggi.sl_fkleid
								left join leggitipi ON
									leggitipi.idcomune = leggi.idcomune AND
									leggitipi.lt_id = leggi.le_fkltid
							WHERE
								alberoproc.idcomune = {0} AND
								alberoproc.software={1} and
								alberoproc.sc_codice IN (" + condWhere + @")";

                sql = this.PreparaQueryParametrica(sql, "idComune", "software");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", ramoAlbero.SOFTWARE));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var normativeTrovate = new List<NormativaDto>();
                        var listaFamiglie = new Dictionary<int, FamigliaEndoprocedimentoDto>();
                        var listaTipi = new Dictionary<int, TipoEndoprocedimentoDto>();

                        while (dr.Read())
                        {
                            var objCodiceOggetto = dr["codiceoggetto"];

                            var el = new NormativaDto
                            {
                                Codice = Convert.ToInt32(dr["codice"]),
                                Descrizione = dr["descrizione"].ToString(),
                                CodiceOggetto = objCodiceOggetto == DBNull.Value ? (int?)null : Convert.ToInt32(objCodiceOggetto),
                                Categoria = dr["categoria"].ToString(),
                                Link = dr["link"].ToString()
                            };

                            normativeTrovate.Add(el);
                        }

                        return normativeTrovate;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        public List<FaseAttuativaDto> GetListaFasiAttuativeDaIdIntervento(string idComune, int codiceIntervento)
        {
            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var codiceProcedura = this.CodiceProceduraDaIdIntervento(idComune, codiceIntervento);

                if (!codiceProcedura.HasValue)
                {
                    return new List<FaseAttuativaDto>();
                }

                var sql = @"SELECT 
								id,
								titolosubprocedura,
								subprocedura,
								note
							FROM 
								subprocedure
							where 
								idcomune = {0} and 
								codiceprocedura = {1}
							order by numerosubprocedura asc";

                sql = this.PreparaQueryParametrica(sql, "idComune", "codiceProcedura");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("codiceProcedura", codiceProcedura));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var fasiAttuativeTrovate = new List<FaseAttuativaDto>();

                        while (dr.Read())
                        {
                            var el = new FaseAttuativaDto
                            {
                                Codice = Convert.ToInt32(dr["id"]),
                                Descrizione = dr["titolosubprocedura"].ToString(),
                                DescrizioneEstesa = dr["subprocedura"].ToString(),
                                Note = dr["note"].ToString()
                            };

                            fasiAttuativeTrovate.Add(el);
                        }

                        return fasiAttuativeTrovate;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        /// <summary>
        /// Utilizzato dal frontoffice. Restituisce una lista di InterventoDto con popolati solo i campi
        /// Codice, Descrizione, ScCodice,HaNodiFiglio, HaNote
        /// </summary>
        public List<InterventoDto> GetNodiFiglio(string idComune, string software, int idNodo, AmbitoRicerca ambito, string codiceComune)
        {
            var nodo = this.GetById(idNodo, idComune);

            var scCodice = nodo == null ? String.Empty : nodo.SC_CODICE;

            var interventiTrovati = this.GetNodiFiglioByScCodicePadre(idComune, software, scCodice, ambito, codiceComune).ToList();

            interventiTrovati.ForEach(x =>
            {
                var nodiFiglio = this.GetNodiFiglioByScCodicePadre(idComune, software, x.ScCodice, ambito, codiceComune);

                x.HaNodiFiglio = nodiFiglio.Any();

                if ((!x.HaNodiFiglio || idNodo <= 0) && ambito == AmbitoRicerca.FrontofficePubblico)
                {
                    x.PubblicaAreaRiservata = this.VerificaPubblicazioneNodo(idComune, x.ScCodice, software, AmbitoRicerca.AreaRiservata);
                }

                if (!x.HaNote)
                {
                    x.HaNote = this.VerificaPresenzaEndoCollegati(idComune, x.Codice);
                }
            });

            return interventiTrovati;
        }

        private IEnumerable<InterventoDto> GetNodiFiglioByScCodicePadre(string idComune, string software, string scCodice, AmbitoRicerca ambito, string codiceComune)
        {
            var sql = $@"SELECT DISTINCT
							alberoproc.sc_id,
							alberoproc.SC_CODICE,
							alberoproc.SC_DESCRIZIONE,
							alberoproc.sc_note,
							alberoproc.sc_pubblica,
                            alberoproc.SC_ORDINE
						FROM alberoproc
		                    LEFT JOIN alberoproc_comuni_esclusi ON 
			                    alberoproc_comuni_esclusi.idcomune = alberoproc.idcomune AND
			                    alberoproc_comuni_esclusi.fk_scid = alberoproc.sc_id AND
			                    alberoproc_comuni_esclusi.codicecomune = {{0}}
						WHERE sc_attivo  = 0
						    AND (
                                {this._db.Specifics.NvlFunction("sc_pubblica", -1)} in ({FiltroRicercaFlagPubblica.Get(ambito)}, -1)
                            )
						    AND alberoproc.idcomune     = {{1}}
						    AND alberoproc.software     = {{2}}
						    AND alberoproc.sc_codice like {{3}}
						    and (
                                FINE_VALIDITA is null or {{4}} between alberoproc.INIZIO_VALIDITA and alberoproc.FINE_VALIDITA
                            ) 
                            and {this._db.Specifics.LengthFunction("sc_codice")}={scCodice.Length + 2}
                            AND alberoproc_comuni_esclusi.codicecomune IS NULL
						order by 
                            alberoproc.SC_ORDINE asc,
                            alberoproc.SC_DESCRIZIONE asc";

            var query = FormattableStringFactory.Create(sql, codiceComune, idComune, software, scCodice + "%", DateTime.Now);

            return this._db.ExecuteReader(query, dr =>
            {
                var scPubblica = dr.GetString("sc_pubblica");
                var pubblicaAreaRiservata = scPubblica == "1" || scPubblica == "2";

                return new InterventoDto
                {
                    Codice = dr.GetInt("sc_id")!.Value,
                    Descrizione = dr.GetString("SC_DESCRIZIONE"),
                    ScCodice = dr.GetString("SC_CODICE"),
                    HaNote = dr.GetString("SC_NOTE").Length > 0,
                    PubblicaAreaRiservata = pubblicaAreaRiservata
                };
            });
        }



        private string GeneraCondizioneScCodice(string scCodice)
        {
            var length = scCodice.Length / 2;
            var arr = new string[length];

            for (var i = 0; i < length; i++)
            {
                arr[i] = scCodice.Substring(0, (i + 1) * 2);
            }

            return $"'{String.Join("','", arr)}'";
        }

        public bool VerificaPubblicazioneNodo(string idComune, string scCodice, string software, AmbitoRicerca ambitoRicerca)
        {
            return this.VerificaPubblicazioneNodo(idComune, scCodice, software, FiltroRicercaFlagPubblica.Get(ambitoRicerca));
        }

        public bool VerificaPubblicazioneNodo(string idComune, string scCodice, string software, string condizionePubblica/*AmbitoRicerca ambitoRicerca*/)
        {
            using (var db2 = new DataBase(this._db.ConnectionDetails.ConnectionString, this._db.ConnectionDetails.ProviderType))
            {
                var condizioneScCodice = this.GeneraCondizioneScCodice(scCodice);

                var sql = $@"SELECT 
							  Count(*) 
							FROM 
							  alberoproc
							WHERE
							  idcomune = {db2.QueryParameter("idComune")} AND 
							  software = {db2.QueryParameter("software")} AND
							  sc_codice IN ({condizioneScCodice}) AND
							  (sc_pubblica IS NULL OR sc_pubblica IN ({condizionePubblica}))";

                var count = db2.ExecuteScalar(sql, 0, mp => mp.Add("idComune", idComune)
                                                              .Add("software", software));

                return Convert.ToInt32(count) == scCodice.Length / 2;
            }
        }

        private bool VerificaPresenzaEndoCollegati(string idComune, int idIntervento)
        {
            FormattableString sql = $@"select 
							  count(*)
							FROM
							  inventarioprocedimenti,
							  alberoproc_endo
							where
							  inventarioprocedimenti.idcomune = alberoproc_endo.idcomune 
							  AND inventarioprocedimenti.codiceinventario = alberoproc_endo.codiceinventario
							  AND alberoproc_endo.idcomune = {idComune}
							  AND alberoproc_endo.fkscid = {idIntervento}
							  AND inventarioprocedimenti.disabilitato = 0";

            return this._db.ExecuteScalar(sql, 0) > 0;
        }

        public string GetNoteSingolaFoglia(string idComune, int codiceIntervento)
        {
            var sql = $"select SC_NOTE from alberoproc where idcomune = {this._db.Specifics.QueryParameterName("idcomune")} and SC_ID = {this._db.Specifics.QueryParameterName("scId")}";

            return this._db.ExecuteScalar(sql, "", mp =>
            {
                mp.AddParameter("idcomune", idComune);
                mp.AddParameter("scId", codiceIntervento);
            });

        }

        public string GetTestoCompletoNote(string idComune, int codiceIntervento)
        {
            const string FORMAT_STRING_PADRE = "<div class='notePadre'>{0}</div>";
            const string FORMAT_STRING_GENERALE = "<div class='nomeNodo'>{0}</div><div class='descrizioneNodo'>{1}</div>";

            var intervento = this.GetById(codiceIntervento, idComune);

            if (intervento == null)
            {
                return String.Empty;
            }

            var partiCodice = new List<string>();

            for (var i = 0; i < intervento.SC_CODICE.Length; i = i + 2)
            {
                partiCodice.Add(intervento.SC_CODICE.Substring(0, i + 2));
            }

            var whereIn = "'" + String.Join("','", partiCodice.ToArray()) + "'";

            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var sql = "select SC_NOTE,SC_ID,SC_DESCRIZIONE from alberoproc where idcomune = {0} and software={1} and SC_CODICE in (" + whereIn + ") order by SC_CODICE asc";

                sql = this.PreparaQueryParametrica(sql, "idComune", "software");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", intervento.SOFTWARE));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var sb = new StringBuilder();

                        while (dr.Read())
                        {
                            var note = dr[0].ToString();
                            var id = Convert.ToInt32(dr[1]);
                            var descrizione = dr[2].ToString();

                            if (String.IsNullOrEmpty(note))
                            {
                                continue;
                            }

                            var str = String.Format(FORMAT_STRING_GENERALE, descrizione, note);

                            sb.AppendFormat(FORMAT_STRING_PADRE, str);
                        }

                        return sb.ToString();
                    }
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        #region ricerca testuale
        public List<BaseDto<int, string>> RicercaTestualeInterventi(string idComune, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, AmbitoRicerca ambitoRicerca)
        {
            var closeCnn = false;

            matchCount = Math.Min(matchCount, 100);

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                using (var cmd = this.GeneraCommandRicercaInterventi(idComune, software, matchParziale, modoRicerca, tipoRicerca, ambitoRicerca))
                {
                    var filtroRicercaFlagPubblica = FiltroRicercaFlagPubblica.Get(ambitoRicerca);

                    using (var dr = cmd.ExecuteReader())
                    {
                        var listaElementi = new List<BaseDto<int, string>>();

                        while (dr.Read())
                        {
                            var id = dr.GetInt("sc_id").Value;
                            var scCodice = dr.GetString("sc_codice");
                            var descrizione = dr.GetString("sc_descrizione");

                            var pubblica = this.VerificaPubblicazioneNodo(idComune, scCodice, software, filtroRicercaFlagPubblica);

                            if (!pubblica)
                            {
                                continue;
                            }

                            listaElementi.Add(new BaseDto<int, string>(id, descrizione));

                            if (listaElementi.Count >= matchCount)
                            {
                                break;
                            }
                        }

                        return listaElementi;
                    }
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la ricerca testuale dell'intervento con matchParziale {0}, modoRicerca {1}, tipoRicerca {2}: {3}", matchParziale, modoRicerca, tipoRicerca, ex.ToString());

                throw;
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }

        private IDbCommand GeneraCommandRicercaInterventi(string idComune, string software, string matchParziale, string modoRicerca, string tipoRicerca, AmbitoRicerca ambitoRicerca)
        {
            const string MR_TITOLI_E_DESCRIZIONI = "mr2";
            const string TR_FRASE_COMPLETA = "tr1";
            const string TR_TUTTE_LE_PAROLE = "tr2";

            //var sb = new StringBuilder(this.PreparaQueryParametrica("select distinct sc_id,sc_descrizione, sc_codice from alberoproc where idComune={0} and software={1} and ", "idComune", "software"));

            var sqlBase = @$"select distinct 
                                sc_id, 
                                descrizione_completa as sc_descrizione, 
                                sc_codice 
                            from 
                                alberoproc 
                            where 
                                idComune={this._db.QueryParameter("idComune")} and 
                                software={this._db.QueryParameter("software")} and ";


            var sb = new StringBuilder(sqlBase);

            var listaParole = new List<string>();
            var listaParametri = new List<IDbDataParameter>();

            listaParametri.Add(this._db.CreateParameter("idComune", idComune));
            listaParametri.Add(this._db.CreateParameter("software", software));

            if (tipoRicerca == TR_FRASE_COMPLETA)
            {
                listaParole.Add(matchParziale);
            }
            else
            {
                listaParole = this.SplitListaParole(matchParziale);
            }

            sb.Append("( sc_pubblica is null or sc_pubblica in (").Append(FiltroRicercaFlagPubblica.Get(ambitoRicerca)).Append(")) and ")
               .Append('(');

            for (var i = 0; i < listaParole.Count; i++)
            {
                var parIdx = i * (modoRicerca == MR_TITOLI_E_DESCRIZIONI ? 2 : 1);
                var nomeParametro = $"parametro{parIdx}";
                var str = this.PreparaQueryParametrica(this._db.Specifics.UCaseFunction("sc_descrizione") + " like {0} ", nomeParametro);

                listaParametri.Add(this._db.CreateParameter(nomeParametro, "%" + listaParole[i].ToUpper() + "%"));

                if (modoRicerca == MR_TITOLI_E_DESCRIZIONI)
                {
                    nomeParametro = $"parametro{(parIdx + 1)}";
                    str += " or ";
                    str += this.PreparaQueryParametrica(this._db.Specifics.UCaseFunction("sc_note") + " like {0} ", nomeParametro);

                    listaParametri.Add(this._db.CreateParameter(nomeParametro, listaParole[i].ToUpper()));
                }

                sb.Append($"({str})");

                if (i < (listaParole.Count - 1))
                {
                    if (tipoRicerca == TR_TUTTE_LE_PAROLE)
                    {
                        sb.Append(" and ");
                    }
                    else
                    {
                        sb.Append(" or ");
                    }
                }
            }

            sb.Append(") order by sc_descrizione asc");

            var cmd = this._db.CreateCommand(sb.ToString());

            listaParametri.ForEach(x => cmd.Parameters.Add(x));

            return cmd;
        }

        private List<string> SplitListaParole(string matchParziale)
        {
            var parts = Regex.Split(matchParziale, @"[^a-zA-Z0-9]+");

            var parole = new List<string>();

            for (var i = 0; i < parts.Length; i++)
            {
                if (parts[i].Length > 0)
                {
                    parole.Add(parts[i]);
                }
            }

            return parole;
        }

        #endregion

        public List<int> GetListaIdNodiPadre(string idComune, int codiceIntervento)
        {
            var ramoAlbero = this.GetById(codiceIntervento, idComune);

            var listaScId = ramoAlbero.GetListaScCodice();

            var condWhere = listaScId.ToString();

            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var sql = this.PreparaQueryParametrica("select sc_id from alberoproc where idcomune={0} and software={1} and sc_codice in (" + condWhere + ") order by sc_codice desc", "idComune", "software");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", ramoAlbero.SOFTWARE));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var codiciIntervento = new List<int>();

                        while (dr.Read())
                        {
                            codiciIntervento.Add(Convert.ToInt32(dr[0]));
                        }

                        return codiciIntervento;
                    }
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in GetListaIdNodiPadre con idComune={0} e codiceIntervento={1}: {2}", idComune, codiceIntervento, ex.ToString());

                throw;
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }


        public bool DataInterventoValida(string idComune, int idIntervento)
        {
            var intervento = this.GetById(idIntervento, idComune);
            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var sql = this.PreparaQueryParametrica(@"select 
                              INIZIO_VALIDITA, 
                              FINE_VALIDITA 
                            from 
                              alberoproc 
                            where 
                              idcomune={0} and 
                              software={1} and 
                              sc_codice in (" + intervento.GetListaScCodice().ToString() + @") and
                              INIZIO_VALIDITA is not null 
                            order by sc_codice desc", "idComune", "software");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", intervento.SOFTWARE));

                    using (var dr = cmd.ExecuteReader())
                    {
                        if (!dr.Read())
                        {
                            return true;
                        }

                        var di = Convert.ToDateTime(dr["INIZIO_VALIDITA"]);
                        var df = Convert.ToDateTime(dr["FINE_VALIDITA"]);

                        return di <= DateTime.Now && DateTime.Now <= df;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                    this._db.Connection.Close();
            }

        }

        public bool HaPresentatoDomandePerIntervento(string idComune, int idIntervento, string codiceFiscaleRichiedente)
        {
            var sql = $"select FLAG_UNICA_DOMANDA from alberoproc where idcomune={this._db.Specifics.QueryParameterName("idComune")} and sc_id={this._db.Specifics.QueryParameterName("idIntervento")}";

            var flag = this._db.ExecuteScalar(sql, 0, mp =>
            {
                mp.AddParameter("idComune", idComune);
                mp.AddParameter("idIntervento", idIntervento);
            });

            if (flag == 0)
            {
                return false;
            }

            sql = $@"
select 
    count(*) 
from 
    istanze 
        inner join anagrafe on 
            anagrafe.idcomune = istanze.idcomune and
            anagrafe.codiceanagrafe = istanze.CODICERICHIEDENTE
where 
    istanze.idcomune={this._db.Specifics.QueryParameterName("idComune")} and 
    istanze.codiceinterventoproc={this._db.Specifics.QueryParameterName("idIntervento")} and
    {this._db.Specifics.UCaseFunction("anagrafe.codicefiscale")} = {this._db.Specifics.QueryParameterName("codiceFiscaleRichiedente")}";

            var count = this._db.ExecuteScalar(sql, 0, mp =>
            {
                mp.AddParameter("idComune", idComune);
                mp.AddParameter("idIntervento", idIntervento);
                mp.AddParameter("codiceFiscaleRichiedente", codiceFiscaleRichiedente.ToUpperInvariant());
            });

            return count != 0;
        }

        public bool InterventoSupportaRedirect(string idComune, int idIntervento)
        {

            var closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                var sql = this.PreparaQueryParametrica("select flag_ar_redirect from alberoproc where idcomune={0} and sc_id={1}", "idComune", "idIntervento");

                using (var cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("idIntervento", idIntervento));

                    var obj = cmd.ExecuteScalar();

                    if (obj == null || obj == DBNull.Value)
                    {
                        return false;
                    }

                    return Convert.ToInt32(obj) == 1;
                }
            }
            finally
            {
                if (closeCnn)
                    this._db.Connection.Close();
            }

        }

        internal string GetSoftwareByCodiceIntervento(string idComune, int idIntervento)
        {
            var sql = $"select software from alberoproc where idcomune={this._db.QueryParameter(nameof(idComune))} and sc_id={this._db.QueryParameter(nameof(idIntervento))}";

            return this._db.ExecuteScalar(sql, "",
                                    mp => mp.Add(nameof(idComune), idComune)
                                            .Add(nameof(idIntervento), idIntervento));
        }


        /// <summary>
        /// Prepara una query parametrica utilizzando i nomi dei parametri passati
        /// </summary>
        /// <example>
        /// string s = PreparaParametriQuery("Select * from t where a={0} and b={1}","primo","secondo");
        /// </example>
        /// <param name="sql">Query con segnaposto di sostituzione in cui inserire i parametri</param>
        /// <param name="nomiParametri">Lista di nomi di parametri da riportare nella query in base alle specifiche del db</param>
        /// <returns>Espressione sql con i nomi dei paramtri al posto dei segnaposto</returns>
        protected string PreparaQueryParametrica(string sql, params string[] nomiParametri)
        {
            for (var i = 0; i < nomiParametri.Length; i++)
                nomiParametri[i] = this._db.Specifics.QueryParameterName(nomiParametri[i]);

            return String.Format(sql, nomiParametri);
        }

    }
}