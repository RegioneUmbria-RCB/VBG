using Init.Sigepro.FrontEnd.AppLogic.DataAccess;
using Microsoft.Extensions.Logging;
using PersonalLib2.Data;
using VBG.AppLogic.SSU.DataAccess.Dto;

namespace VBG.AppLogic.SSU.DataAccess
{
    public class DomandeSsuRepository : IDomandeSsuRepository
    {
        private const int FAILED_ATTEMPTS_THRESHOLD = 10;

        private readonly DbConnectionFactory _connectionFactory;
        private readonly ILogger<DomandeSsuRepository> _logger;

        public DomandeSsuRepository(DbConnectionFactory connectionFactory, ILogger<DomandeSsuRepository> logger)
        {
            this._connectionFactory = connectionFactory;
            this._logger = logger;
        }
        public void MarcaDomandaComePresentata(int idDomanda, string codiceDomandaSsu, string numeroDomandaSsu, string istatEnte)
        {
            using var db = this._connectionFactory.CreateDatabase();

            try
            {
                db.BeginTransaction();

                var nextId = db.NextId(this._connectionFactory.IdComune, "FO_DOMANDE_SSU", "ID");

                FormattableString sql = $@"insert into FO_DOMANDE_SSU 
                (IDCOMUNE, ID, FK_IDDOMANDA, CUI, NUMERO_DOMANDA_SSU, DATA_PRESENTAZIONE, ALIAS, STATO, ISTAT_ENTE) 
                values 
                ({this._connectionFactory.IdComune}, {nextId}, {idDomanda}, {codiceDomandaSsu}, {numeroDomandaSsu}, {DateTime.Now}, {this._connectionFactory.Alias}, {(int)StatiDomandaSsuEnum.Inviata}, {istatEnte})";

                db.ExecuteNonQuery(sql);

                db.CommitTransaction();
            }
            catch (Exception)
            {
                db.RollbackTransaction();

                throw;
            }

        }
        public FoDomandeSsu? GetByIdDomanda(int idDomanda)
        {
            using var db = this._connectionFactory.CreateDatabase();

            FormattableString sql = $"select * from FO_DOMANDE_SSU where IDCOMUNE = {this._connectionFactory.IdComune} and FK_IDDOMANDA = {idDomanda}";

            return db.ExecuteReader(sql, dr => new FoDomandeSsu
            {
                IdComune = dr.GetString("IDCOMUNE"),
                FkIdDomanda = dr.GetInt("FK_IDDOMANDA")!.Value,
                IdDomandaSsu = dr.GetString("CUI"),
                NumeroDomandaSsu = dr.GetString("NUMERO_DOMANDA_SSU"),
                DataInvio = dr.GetDateTime("DATA_PRESENTAZIONE")!.Value,
                Stato = dr.GetInt("STATO").GetValueOrDefault(0),
                CodiceoggettoRicevuta = dr.GetInt("CODICEOGGETTO_RICEVUTA"),
                NonElaborabile = dr.GetInt("NON_ELABORABILE").GetValueOrDefault(0) == 1,
                Alias = dr.GetString("ALIAS"),
                IstatEnte = dr.GetString("ISTAT_ENTE")
            }).FirstOrDefault();
        }

        public void AggiornaStatoByIdDomanda(int idDomanda, StatiDomandaSsuEnum statoAsEnum)
        {
            using var db = this._connectionFactory.CreateDatabase();

            try
            {
                db.BeginTransaction();

                this.AggiornaStatoByIdDomandaInTransaction(db, idDomanda, statoAsEnum);

                db.CommitTransaction();
            }
            catch (Exception)
            {
                db.RollbackTransaction();
                throw;
            }
        }

        internal void AggiornaStatoByIdDomandaInTransaction(IDatabase db, int idDomanda, StatiDomandaSsuEnum statoAsEnum)
        {
            FormattableString sql = $@"
                        update 
                            FO_DOMANDE_SSU
                        set 
                            STATO = {(int)statoAsEnum}
                        where 
                            IDCOMUNE = {this._connectionFactory.IdComune} and 
                            FK_IDDOMANDA = {idDomanda}";

            db.ExecuteNonQuery(sql);
        }

        public void AggiungiRicevutaByIdDomanda(int idDomanda, int codiceOggettoRicevuta)
        {
            using var db = this._connectionFactory.CreateDatabase();

            try
            {
                db.BeginTransaction();

                FormattableString sql = $@"update 
                    FO_DOMANDE_SSU
                set 
                    CODICEOGGETTO_RICEVUTA = {codiceOggettoRicevuta}
                where 
                    IDCOMUNE = {this._connectionFactory.IdComune} and 
                    FK_IDDOMANDA = {idDomanda}";

                db.ExecuteNonQuery(sql);

                db.CommitTransaction();
            }
            catch (Exception ex)
            {
                this._logger.LogError(ex, "Errore durante l'aggiornamento del codice oggetto ricevuta per la domanda SSU con id domanda {IdDomanda}. Codice oggetto ricevuta: {CodiceOggettoRicevuta}", idDomanda, codiceOggettoRicevuta);

                db.RollbackTransaction();
                throw;
            }
        }

        public bool AddNewError(int idDomanda, StatiDomandaSsuEnum stato, string errore)
        {
            var data = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
            using var db = this._connectionFactory.CreateDatabase();

            try
            {
                db.BeginTransaction();

                var nextId = db.NextId(this._connectionFactory.IdComune, "FO_DOMANDE_SSU_AUDIT", "ID");

                FormattableString sql = $@"
                    INSERT INTO fo_domande_ssu_audit
                        (idcomune, id, fk_iddomanda, stato, data, errore)
                    VALUES
                        ({this._connectionFactory.IdComune}, {nextId}, {idDomanda}, {(int)stato}, {data}, {errore})";

                db.ExecuteNonQuery(sql);
                db.CommitTransaction();

                this._logger.LogInformation("Nuovo errore registrato per la domanda SSU relativa alla domanda {IdDomandaSsu}: {Errore}", idDomanda, errore);
                return true;
            }
            catch (Exception)
            {
                db.RollbackTransaction();
                return false;
            }
        }

        public int CountErroriDomandaPerStato(int idDomanda, StatiDomandaSsuEnum stato)
        {
            FormattableString countSql = $@"
                SELECT COUNT(*)
                FROM fo_domande_ssu_audit
                WHERE idcomune = {this._connectionFactory.IdComune}
                AND fk_iddomanda = {idDomanda}
                AND stato = {(int)stato}";


            using var db = this._connectionFactory.CreateDatabase();

            return db.ExecuteScalar<int>(countSql, 0);
        }

        public void SetNonElaborabile(int idDomanda, bool isElaborabile)
        {
            var elaborabileText = isElaborabile ? "0" : "1";

            FormattableString sql = $@"
                UPDATE  fo_domande_ssu 
                SET non_elaborabile = {elaborabileText} 
                WHERE idcomune = {this._connectionFactory.IdComune} 
                AND fk_iddomanda = {idDomanda}";

            using var db = this._connectionFactory.CreateDatabase();

            db.ExecuteNonQuery(sql);
        }


        public IEnumerable<ElementoListaDomandeSsuDto> GetDomandeByIdStato(int codiceAnagrafe, string software, StatiDomandaSsuEnum stato)
        {
            using var db = this._connectionFactory.CreateDatabase();

            FormattableString sql = @$"
                SELECT d.idcomune,
                       d.id,
                       d.identificativodomanda,
                       dssu.numero_domanda_ssu,
                       d.data_ultima_modifica,
                       dssu.data_presentazione,
                       dssu.stato,
                       dssu.alias,
                       d.codiceoggetto,
                       d.richiedente,
                       d.intervento,
                       d.codiceintervento,
                       d.oggetto,
                       dssu.istat_ente
                FROM 
                    fo_domande d
                        INNER JOIN fo_domande_ssu dssu on 
                            dssu.idcomune = d.idcomune and
                            dssu.fk_iddomanda = d.id
                WHERE 
                    d.idcomune = {this._connectionFactory.IdComune} AND 
                    d.codiceanagrafe = {codiceAnagrafe} AND      
                    d.software = {software} AND
                    dssu.stato = {stato}";

            return db.ExecuteReader(sql, dr => new ElementoListaDomandeSsuDto
            {
                IdComune = dr.GetString("IDCOMUNE"),
                Id = dr.GetInt("ID")!.Value,
                IdentificativoDomanda = dr.GetString("IDENTIFICATIVODOMANDA"),
                NumeroDomandaSsu = dr.GetString("NUMERO_DOMANDA_SSU"),
                DataUltimaModifica = dr.GetDateTime("DATA_ULTIMA_MODIFICA"),
                DataInvio = dr.GetDateTime("DATA_PRESENTAZIONE")!.Value,
                Stato = dr.GetInt("STATO").GetValueOrDefault(0),
                Alias = dr.GetString("ALIAS"),
                CodiceOggetto = dr.GetInt("CODICEOGGETTO"),
                Richiedente = dr.GetString("RICHIEDENTE"),
                Intervento = dr.GetString("INTERVENTO"),
                CodiceIntervento = dr.GetInt("CODICEINTERVENTO"),
                Oggetto = dr.GetString("OGGETTO"),
                CodiceEnte = dr.GetString("istat_ente")
            });
        }

    }
}
