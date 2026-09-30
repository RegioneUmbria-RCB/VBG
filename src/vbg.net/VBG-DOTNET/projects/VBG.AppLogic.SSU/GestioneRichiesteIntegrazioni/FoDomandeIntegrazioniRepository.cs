using DocumentFormat.OpenXml.Office2010.Excel;
using Init.Sigepro.FrontEnd.AppLogic.DataAccess;
using Microsoft.Extensions.Logging;
using PersonalLib2.Data;
using System.Text;
using VBG.AppLogic.SSU.DataAccess;
using VBG.AppLogic.SSU.GestioneRichiesteIntegrazioni.Struct;

namespace VBG.AppLogic.SSU.GestioneRichiesteIntegrazioni
{
    public class FoDomandeIntegrazioniRepository
    {
        private readonly DbConnectionFactory _connectionFactory;
        private readonly ILogger<FoDomandeIntegrazioniRepository> _logger;
        private readonly DomandeSsuRepository _domandeSsuRepository;

        /* Struttura tabella FO_DOMANDE_SSU_INTEGRAZIONI
        * IDCOMUNE varchar(6) NOT NULL,
        * ID decimal(10,0) NOT NULL,
        * FK_IDDOMANDA decimal(6,0) NOT NULL,
        * ID_AMMINISTRAZIONE decimal(10,0),
        * AMMINISTRAZIONE varchar(256),
        * INTEGRATA_IL datetime
        */

        /* Struttura tabella fo_domande_ssu_integr_richieste (
            idcomune VARCHAR(6) NOT NULL,
            id DECIMAL(10,0) NOT NULL,
            fk_idIntegrazione DECIMAL(10,0) NOT NULL,
            id_procedimento DECIMAL(10,0),
            procedimento VARCHAR(256),
            richiesta VARCHAR(4000)
        */

        /* Struttura tabella fo_domande_ssu_integr_allegati (
            idcomune VARCHAR(6) NOT NULL,
            id DECIMAL(10,0) NOT NULL,
            fk_idIntegrazione DECIMAL(10,0) NOT NULL,
            codiceOggetto DECIMAL(10,0)
        */

        public FoDomandeIntegrazioniRepository(DbConnectionFactory connectionFactory, ILogger<FoDomandeIntegrazioniRepository> logger, DomandeSsuRepository domandeSsuRepository)
        {
            this._connectionFactory = connectionFactory;
            this._logger = logger;
            this._domandeSsuRepository = domandeSsuRepository;
        }

        public void MarcaDomandaComeIntegrata(int idDomanda)
        {
            using var db = this._connectionFactory.CreateDatabase();
            try
            {
                db.BeginTransaction();

                FormattableString updateSql = $@"
                    update FO_DOMANDE_SSU_INTEGRAZIONI
                    set INTEGRATA_IL = CURRENT_TIMESTAMP
                    where IDCOMUNE = {this._connectionFactory.IdComune}
                      and FK_IDDOMANDA = {idDomanda}";

                db.ExecuteNonQuery(updateSql);

                this._domandeSsuRepository.AggiornaStatoByIdDomandaInTransaction(db, idDomanda, StatiDomandaSsuEnum.Integrata);
                db.CommitTransaction();
            }
            catch (Exception)
            {
                db.RollbackTransaction();
                throw;
            }
        }

        public void InserisciRichiestaIntegrazioni(int idDomanda, IEnumerable<SsuRichiestaIntegrazione> integrazioni)
        {
            using var db = this._connectionFactory.CreateDatabase();
            try
            {
                db.BeginTransaction();

                foreach (var integrazione in integrazioni)
                {
                    var nextId = db.NextId(this._connectionFactory.IdComune, "FO_DOMANDE_SSU_INTEGRAZIONI", "ID");

                    FormattableString insertSql = $@"
                        insert into FO_DOMANDE_SSU_INTEGRAZIONI 
                            (IDCOMUNE, ID, FK_IDDOMANDA, ID_AMMINISTRAZIONE, AMMINISTRAZIONE) 
                        values 
                            ({this._connectionFactory.IdComune}, {nextId}, {idDomanda}, {integrazione.Amministrazione.Codice}, {integrazione.Amministrazione.Descrizione})";
                    db.ExecuteNonQuery(insertSql);

                    foreach (var procedimento in integrazione.Procedimenti)
                    {
                        foreach (var richiesta in procedimento.Richieste)
                        {
                            var nextIdRichiesta = db.NextId(this._connectionFactory.IdComune, "FO_DOMANDE_SSU_INTEGR_RICHIESTE", "ID");

                            insertSql = $@"
                                insert into FO_DOMANDE_SSU_INTEGR_RICHIESTE 
                                    (IDCOMUNE, ID, FK_IDINTEGRAZIONE, ID_PROCEDIMENTO, PROCEDIMENTO, RICHIESTA) 
                                values 
                                    ({this._connectionFactory.IdComune}, {nextIdRichiesta}, {nextId}, {procedimento.Codice}, {procedimento.Descrizione}, {richiesta})";
                            db.ExecuteNonQuery(insertSql);
                        }
                    }

                    foreach (var idAllegato in integrazione.Allegati.CodiciOggetto)
                    {
                        var nextIdAllegati = db.NextId(this._connectionFactory.IdComune, "FO_DOMANDE_SSU_INTEGR_ALLEGATI", "ID");

                        insertSql = $@"
                                insert into FO_DOMANDE_SSU_INTEGR_ALLEGATI 
                                    (IDCOMUNE, ID, FK_IDINTEGRAZIONE, CODICEOGGETTO) 
                                values 
                                    ({this._connectionFactory.IdComune}, {nextIdAllegati}, {nextId}, {idAllegato})";
                        db.ExecuteNonQuery(insertSql);
                    }
                }

                // Marco la domanda come da integrare
                this._domandeSsuRepository.AggiornaStatoByIdDomandaInTransaction(db, idDomanda, StatiDomandaSsuEnum.DaIntegrare);

                db.CommitTransaction();
            }
            catch (Exception)
            {
                db.RollbackTransaction();
                throw;
            }
        }

        public int CountIntegrazioniByIdDomanda(int idDomanda)
        {
            using var db = this._connectionFactory.CreateDatabase();

            FormattableString sql = $@"
                select 
                    count(*) as TOTAL 
                from 
                    FO_DOMANDE_SSU_INTEGRAZIONI 
                where 
                    IDCOMUNE = {this._connectionFactory.IdComune} 
                    and FK_IDDOMANDA = {idDomanda}";

            return db.ExecuteScalar(sql, 0)!;
        }

        public SsuRichiestaIntegrazione GetIntegrazioniDaFareByIdDomanda(int idDomanda)
        {
            using var db = this._connectionFactory.CreateDatabase();

            FormattableString sql = $@"
                select
                    ID,
                    ID_AMMINISTRAZIONE,
                    AMMINISTRAZIONE
                from
                    FO_DOMANDE_SSU_INTEGRAZIONI
                where
                    IDCOMUNE = {this._connectionFactory.IdComune}
                    and FK_IDDOMANDA = {idDomanda}
                    and INTEGRATA_IL is null";

            var integrazione = db.ExecuteReader(sql, dr => new
            {
                Id = dr.GetInt("ID")!.Value,
                Amministrazione = new SsuAmministrazione
                {
                    Codice = dr.GetInt("ID_AMMINISTRAZIONE")!.Value,
                    Descrizione = dr.GetString("AMMINISTRAZIONE")!
                }
            }).FirstOrDefault();

            if (integrazione is null)
                return null;

            FormattableString richiesteSql = $@"
                select
                    ID_PROCEDIMENTO,
                    PROCEDIMENTO,
                    RICHIESTA
                from
                    FO_DOMANDE_SSU_INTEGR_RICHIESTE
                where
                    IDCOMUNE = {this._connectionFactory.IdComune}
                    and FK_IDINTEGRAZIONE = {integrazione.Id}
                order by ID";

            var procedimenti = db.ExecuteReader(richiesteSql, dr => new
            {
                Codice = dr.GetInt("ID_PROCEDIMENTO")!.Value,
                Descrizione = dr.GetString("PROCEDIMENTO")!,
                Richiesta = dr.GetString("RICHIESTA")!
            })
            .GroupBy(x => new { x.Codice, x.Descrizione })
            .Select(g => new SsuProcedimentoRichiesta
            {
                Codice = g.Key.Codice,
                Descrizione = g.Key.Descrizione,
                Richieste = g.Select(x => x.Richiesta).ToList()
            })
            .ToList();

            FormattableString allegatiSql = $@"
                select
                    CODICEOGGETTO
                from
                    FO_DOMANDE_SSU_INTEGR_ALLEGATI
                where
                    IDCOMUNE = {this._connectionFactory.IdComune}
                    and FK_IDINTEGRAZIONE = {integrazione.Id}
                order by ID";

            var allegati = db.ExecuteReader(
                allegatiSql,
                dr => dr.GetInt("CODICEOGGETTO")!.Value
            ).ToList();

            return new SsuRichiestaIntegrazione
            {
                Amministrazione = integrazione.Amministrazione,
                Procedimenti = procedimenti,
                Allegati = new SsuAllegati
                {
                    CodiciOggetto = allegati
                }
            };
        }

        public IEnumerable<RichiestaIntegrazioneSsu> GetProcedimentiByIdIntegrazione(int idIntegrazione)
        {
            using var db = this._connectionFactory.CreateDatabase();

            FormattableString sql = $@"
                select 
                    FO_DOMANDE_SSU_INTEGR_RICHIESTE.ID_PROCEDIMENTO,
                    FO_DOMANDE_SSU_INTEGR_RICHIESTE.PROCEDIMENTO,
                    FO_DOMANDE_SSU_INTEGR_RICHIESTE.RICHIESTA
                from 
                    FO_DOMANDE_SSU_INTEGR_RICHIESTE
                where 
                    FO_DOMANDE_SSU_INTEGR_RICHIESTE.IDCOMUNE = {this._connectionFactory.IdComune} 
                    and FO_DOMANDE_SSU_INTEGR_RICHIESTE.FK_IDINTEGRAZIONE = {idIntegrazione}";

            return db.ExecuteReader(sql, dr => new RichiestaIntegrazioneSsu
            {
                IdProcedimento = dr.GetInt("ID_PROCEDIMENTO")!.Value,
                DescrizioneProcedimento = dr.GetString("PROCEDIMENTO")!,
                Richiesta = dr.GetString("RICHIESTA")!
            });
        }

        public IEnumerable<int> GetAllegatiByIdIntegrazione(int idIntegrazione)
        {
            using var db = this._connectionFactory.CreateDatabase();

            FormattableString sql = $@"
                select 
                    FO_DOMANDE_SSU_INTEGR_ALLEGATI.CODICE_OGGETTO
                from 
                    FO_DOMANDE_SSU_INTEGR_ALLEGATI
                where 
                    FO_DOMANDE_SSU_INTEGR_ALLEGATI.IDCOMUNE = {this._connectionFactory.IdComune} 
                    and FO_DOMANDE_SSU_INTEGR_ALLEGATI.FK_IDINTEGRAZIONE = {idIntegrazione}";

            return db.ExecuteReader(sql, dr => dr.GetInt("CODICE_OGGETTO")!.Value);
        }
    }
}
