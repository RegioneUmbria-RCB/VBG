using Init.Sigepro.FrontEnd.AppLogic.DataAccess;
using Microsoft.Extensions.Logging;
using PersonalLib2.Data;
using VBG.AppLogic.SSU.GestioneCorrezioni;

namespace VBG.AppLogic.SSU.DataAccess
{

    public class FoDomandeCorrezioniRepository
    {
        private readonly DbConnectionFactory _connectionFactory;
        private readonly ILogger<FoDomandeCorrezioniRepository> _logger;
        private readonly DomandeSsuRepository _domandeSsuRepository;

        /* Struttura tabella FO_DOMANDE_SSU_CORREZIONI
        * IDCOMUNE varchar(6) NOT NULL,
        * ID decimal(10,0) NOT NULL,
        * FK_IDDOMANDA decimal(6,0) NOT NULL,
        * PROCEDIMENTO_ID decimal(10,0) NOT NULL,
        * CORREZIONE_RICHIESTA varchar(2000),
        * CREATA_IL datetime not null,
        * CORRETTA_IL datetime null,
        * PRIMARY KEY (IDCOMUNE,ID),
        * CONSTRAINT FK_SSU_CORR_FO_DOMANDE FOREIGN KEY (IDCOMUNE, FK_IDDOMANDA) REFERENCES fo_domande (IDCOMUNE, ID)
        */

        public FoDomandeCorrezioniRepository(DbConnectionFactory connectionFactory, ILogger<FoDomandeCorrezioniRepository> logger, DomandeSsuRepository domandeSsuRepository)
        {
            this._connectionFactory = connectionFactory;
            this._logger = logger;
            this._domandeSsuRepository = domandeSsuRepository;
        }

        public void InserisciCorrezioni(int idDomanda, IEnumerable<GestioneCorrezioni.SsuProcedimentoCorrezione> correzioni)
        {
            using var db = this._connectionFactory.CreateDatabase();
            try
            {
                db.BeginTransaction();

                foreach (var correzione in correzioni)
                {
                    var nextId = db.NextId(this._connectionFactory.IdComune, "FO_DOMANDE_SSU_CORREZIONI", "ID");

                    FormattableString insertSql = $@"
                    insert into FO_DOMANDE_SSU_CORREZIONI 
                        (IDCOMUNE, ID, FK_IDDOMANDA, PROCEDIMENTO_ID, CORREZIONE_RICHIESTA, CREATA_IL) 
                    values 
                        ({this._connectionFactory.IdComune}, {nextId}, {idDomanda}, {correzione.CodiceProcedimento}, {correzione.CorrezioneRichiesta}, {DateTime.Now})";
                    db.ExecuteNonQuery(insertSql);
                }

                // Marco la domanda come da correggere
                this._domandeSsuRepository.AggiornaStatoByIdDomandaInTransaction(db, idDomanda, StatiDomandaSsuEnum.DaCorreggere);

                db.CommitTransaction();
            }
            catch (Exception)
            {
                db.RollbackTransaction();
                throw;
            }
        }

        public int CountCorrezioniByIdDomanda(int idDomanda)
        {
            using var db = this._connectionFactory.CreateDatabase();

            FormattableString sql = $@"
            select 
                count(*) as TOTAL 
            from 
                FO_DOMANDE_SSU_CORREZIONI 
            where 
                IDCOMUNE = {this._connectionFactory.IdComune} 
                and FK_IDDOMANDA = {idDomanda}";

            return db.ExecuteScalar(sql, 0)!;
        }


        public IEnumerable<CorrezioneDomandaSsu> GetCorrezioniDaFareByIdDomanda(int idDomanda)
        {
            using var db = this._connectionFactory.CreateDatabase();

            FormattableString sql = $@"
            select 
                FO_DOMANDE_SSU_CORREZIONI.PROCEDIMENTO_ID,
                FO_DOMANDE_SSU_CORREZIONI.CORREZIONE_RICHIESTA,
                FO_DOMANDE_SSU.istat_ente
            from 
                FO_DOMANDE_SSU_CORREZIONI
                    inner join FO_DOMANDE_SSU on 
                    FO_DOMANDE_SSU.IDCOMUNE = FO_DOMANDE_SSU_CORREZIONI.IDCOMUNE and 
                    FO_DOMANDE_SSU.FK_IDDOMANDA = FO_DOMANDE_SSU_CORREZIONI.FK_IDDOMANDA
            where 
                FO_DOMANDE_SSU_CORREZIONI.IDCOMUNE = {this._connectionFactory.IdComune} 
                and FO_DOMANDE_SSU_CORREZIONI.FK_IDDOMANDA = {idDomanda}
                and FO_DOMANDE_SSU_CORREZIONI.corretta_il is null";

            return db.ExecuteReader(sql, dr => new CorrezioneDomandaSsu
            {
                IdProcedimento = dr.GetInt("PROCEDIMENTO_ID")!.Value,
                CorrezioneRichiesta = dr.GetString("CORREZIONE_RICHIESTA")!,
                CodiceEnte = dr.GetString("istat_ente")!
            });
        }

        public void EliminaCorrezioniByIdDomanda(int idDomanda)
        {
            using var db = this._connectionFactory.CreateDatabase();
            try
            {
                db.BeginTransaction();

                FormattableString insertSql = $@"
                    update FO_DOMANDE_SSU_CORREZIONI 
                    set CORRETTA_IL = {DateTime.Now}
                    where   
                        IDCOMUNE = {this._connectionFactory.IdComune} 
                        and FK_IDDOMANDA = {idDomanda}";

                db.ExecuteNonQuery(insertSql);

                // Marco la domanda come corretta
                this._domandeSsuRepository.AggiornaStatoByIdDomandaInTransaction(db, idDomanda, StatiDomandaSsuEnum.Corretta);

                db.CommitTransaction();
            }
            catch (Exception)
            {
                db.RollbackTransaction();
                throw;
            }
        }
    }
}
