using Init.SIGePro.Manager.Logic.GestioneAnagrafiche;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione;
using Init.SIGePro.Manager.Logic.GestioneSequenceTable;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Auditing
{
    public class DatabaseCommissioniAuditingService : ICommissioniAuditingService
    {
        private static class Constants
        {
            public const string SequenceName = "COMMISSIONI_EDILIZIE_LOG.ID";

            public static class Eventi
            {
                public const string AccessoACommissioneDaFrontend = "AR_ACCESSO_COMMISSIONE";
                public const string AccessoACommissioneNegatoAUtenteFrontend = "AR_ACCESSO_COMMISSIONE_NEGATO";
                public const string AccessoAPraticaDaFrontend = "AR_ACCESSO_PRATICA";
                public const string AccessoAPraticaNegatoAUtenteFrontend = "AR_ACCESSO_PRATICA_NEGATO";
                public const string AccessoAFileDaFrontend = "AR_ACCESSO_FILE";
                public const string AccessoAFileNegatoAUtenteFrontend = "AR_ACCESSO_FILE_NEGATO";
                public const string VotoInseritoDaFrontend = "AR_VOTO_INSERITO";
                public const string VotoModificatoDaFrontend = "AR_VOTO_MODIFICATO";
                public const string AllegatoApprovato = "AR_ALLEGATO_APPROVATO";
                public const string ApprovazioneAllegatoFallita = "AR_APPROVAZIONE_ALLEGATO_FALLITA";
                public const string ParereCommissioneProtocollato = "AR_PARERE_PROTOCOLLATO";
                public const string AccessoAVotoNegatoDaFrontend = "AR_ACCESSO_A_VOTO_NEGATO";
                public const string UtenteAssociatoACommissione = "AR_UTENTE_ASSOCIATO";
                public const string ErroreProtocollazioneVoto = "AR_PARERE_ERRORE_PROTOCOLLAZIONE";
            }
        }

        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly IAnagraficheBreviService _anagraficheBreviService;
        private readonly ILog _log = LogManager.GetLogger(typeof(DatabaseCommissioniAuditingService));

        public DatabaseCommissioniAuditingService(DataBase db, string idComune)
        {
            _db = db;
            _idComune = idComune;
            _anagraficheBreviService = new AnagraficheBreviService(db, idComune);
        }
        public void AccessoACommissioneDaFrontend(int idCommissione, int codiceAnagrafe)
        {
            var msg = $"L'utente {GetNomeUtente(codiceAnagrafe)} ha effettuato l'accesso tramite area riservata";

            InsertEvent(idCommissione, Constants.Eventi.AccessoACommissioneDaFrontend, msg);
        }

        public void AccessoACommissioneNegatoAUtenteFrontend(int idCommissione, int codiceAnagrafe)
        {
            var msg = $"ATTENZIONE: L'utente {GetNomeUtente(codiceAnagrafe)} ha tentato di effettuare l'accesso tramite area riservata ma la verifica dei permessi è fallita";

            InsertEvent(idCommissione, Constants.Eventi.AccessoACommissioneNegatoAUtenteFrontend, msg);
        }

        public void AccessoAPraticaDaFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza)
        {
            var msg = $"L'utente {GetNomeUtente(codiceAnagrafe)} ha effettuato l'accesso alla pratica {GetNumeroIstanza(codiceIstanza)} tramite area riservata";

            InsertEvent(idCommissione, Constants.Eventi.AccessoAPraticaDaFrontend, msg);
        }


        public void AccessoAPraticaNegatoAUtenteFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza)
        {
            var msg = $"ATTENZIONE: L'utente {GetNomeUtente(codiceAnagrafe)} ha cercato di accedere alla pratica {GetNumeroIstanza(codiceIstanza)} tramite area riservata ma la verifica dei permessi è fallita";

            InsertEvent(idCommissione, Constants.Eventi.AccessoAPraticaNegatoAUtenteFrontend, msg);
        }

        public void AccessoAFileDaFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza, int codiceOggetto)
        {
            var msg = $"L'utente {GetNomeUtente(codiceAnagrafe)} scarica il file {GetNomeFile(codiceOggetto)} della pratica {GetNumeroIstanza(codiceIstanza)} tramite area riservata";

            InsertEvent(idCommissione, Constants.Eventi.AccessoAPraticaDaFrontend, msg);
        }



        public void AccessoAFileNegatoAUtenteFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza, int codiceOggetto)
        {
            var msg = $"ATTENZIONE: L'utente {GetNomeUtente(codiceAnagrafe)} ha cercato di scaricare il file {GetNomeFile(codiceOggetto)} della pratica {GetNumeroIstanza(codiceIstanza)} tramite area riservata ma la verifica dei permessi è fallita";

            InsertEvent(idCommissione, Constants.Eventi.AccessoAPraticaDaFrontend, msg);
        }

        public void VotoModificatoDaFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza, string descrizioneParere, int? codiceOggetto)
        {
            var nomeFile = "non è stato allegato nessun file";

            if (codiceOggetto.HasValue)
            {
                nomeFile = "il file allegato è " + GetNomeFile(codiceOggetto.Value);
            }

            var msg = $"L'utente {GetNomeUtente(codiceAnagrafe)} ha modificato il voto per la pratica {GetNumeroIstanza(codiceIstanza)}. Il nuovo voto è \"{descrizioneParere}\", {nomeFile}";

            InsertEvent(idCommissione, Constants.Eventi.VotoModificatoDaFrontend, msg);
        }

        public void VotoInseritoDaFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza, string descrizioneParere, int? codiceOggetto, IEstremiProtocollazioneCommissione estremiProtocollazione = null)
        {
            var nomeFile = "non è stato allegato nessun file";

            if (codiceOggetto.HasValue)
            {
                nomeFile = "il file allegato è " + GetNomeFile(codiceOggetto.Value);
            }

            var msg = $"L'utente {GetNomeUtente(codiceAnagrafe)} ha inserito un nuovo voto per la pratica {GetNumeroIstanza(codiceIstanza)}. Il voto è \"{descrizioneParere}\", {nomeFile}.";

            if (estremiProtocollazione != null)
            {
                msg += $"Protocollato al n.{estremiProtocollazione.NumeroProtocollo} in data {estremiProtocollazione.DataProtocollo} (id protocollo: {estremiProtocollazione.IdProtocollo})";
            }

            InsertEvent(idCommissione, Constants.Eventi.VotoInseritoDaFrontend, msg);
        }

        private string GetNomeUtente(int codiceAnagrafe)
        {
            return this._anagraficheBreviService.GetNomeUtente(codiceAnagrafe);
        }

        private string GetNomeAmministrazione(int codiceAmministrazione)
        {
            var sql = $@"select amministrazione from amministrazioni 
                         where 
                            idcomune={this._db.QueryParameter("idComune")} and
                            codiceamministrazione={this._db.QueryParameter("codiceAmministrazione")}";

            return this._db.ExecuteScalar(sql, "Non disponibile",
                mp => mp.Add("idComune", this._idComune)
                        .Add("codiceAmministrazione", codiceAmministrazione));
        }

        private int NextId()
        {
            return new SequenceTableService(this._db, this._idComune).NextId(Constants.SequenceName);
        }

        private string GetNomeFile(int codiceOggetto)
        {
            var sql = $"select nomefile from oggetti where idcomune={this._db.QueryParameter("idComune")} and codiceoggetto={this._db.QueryParameter("codiceOggetto")}";

            return this._db.ExecuteScalar(sql, $"[SCONOSCIUTO({codiceOggetto})",
                                            mp => {
                                                mp.AddParameter("idComune", _idComune);
                                                mp.AddParameter("codiceOggetto", codiceOggetto);
                                            });
        }

        private string GetNumeroIstanza(int codiceIstanza)
        {
            var sql = $"select numeroistanza, software from istanze where idcomune={this._db.QueryParameter("idComune")} and codiceistanza={this._db.QueryParameter("codiceIstanza")}";

            var istanza = this._db.ExecuteReader(sql,
                mp => {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                },
                dr => new {
                    numeroistanza = dr.GetString("numeroistanza"),
                    software = dr.GetString("software")
                }).FirstOrDefault();

            if (istanza == null)
            {
                return $"[SCONOSCIUTO(codiceistanza={codiceIstanza})]";
            }

            return $"{istanza.numeroistanza}[{istanza.software}]({codiceIstanza})";
        }

        private void InsertEvent(int idCommissione, string categoria, string messaggio)
        {
            var sql = $@"
INSERT INTO 
    commissioni_edilizie_log 
    (idcomune, id, categoria, data, fk_commissione, messaggio) 
VALUES
(
    {_db.QueryParameter("idComune")}, 
    {_db.QueryParameter("id")}, 
    {_db.QueryParameter("categoria")}, 
    {_db.QueryParameter("data")}, 
    {_db.QueryParameter("fk_commissione")}, 
    {_db.QueryParameter("messaggio")}
)";

            var useTransaction = !this._db.IsInTransaction;

            try
            {
                if (useTransaction)
                {
                    this._db.BeginTransaction();
                }

                var id = NextId();

                this._db.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("id", id);
                    mp.AddParameter("categoria", categoria);
                    mp.AddParameter("data", DateTime.Now);
                    mp.AddParameter("fk_commissione", idCommissione);
                    mp.AddParameter("messaggio", messaggio);

                });
                
                if (useTransaction)
                {
                    this._db.CommitTransaction();
                }
            }
            catch(Exception ex)
            {
                if (useTransaction)
                {
                    this._db.RollbackTransaction();
                }

                this._log.Error($"Errore durante l'esecuzione della query {sql} (idComune={_idComune}, categoria={categoria}, fk_commissione={idCommissione}, messaggio={messaggio}): {ex}");
            }
        }

        public void AllegatoApprovato(int idCommissione, int codiceAnagrafe, int codiceOggetto, string hash)
        {
            var msg = $"L'utente {GetNomeUtente(codiceAnagrafe)} ha approvato l'allegato {GetNomeFile(codiceOggetto)} con hash {hash}";

            InsertEvent(idCommissione, Constants.Eventi.AllegatoApprovato, msg);
        }

        public void ApprovazioneAllegatoFallita(int idCommissione, int codiceAnagrafe, int idAllegato)
        {
            var msg = $"ATTENZIONE: L'utente {GetNomeUtente(codiceAnagrafe)} ha cercato di approvare l'allegato con id {idAllegato} ma la verifica dei permessi è fallita";

            InsertEvent(idCommissione, Constants.Eventi.ApprovazioneAllegatoFallita, msg);
        }

        public void ParereCommissioneProtocollato(int idCommissione, int codiceAnagrafe, string idProtocollo, string numeroProtocollo, string dataProtocollo)
        {
            var msg = $"Parere dell'utente {GetNomeUtente(codiceAnagrafe)} protocollato con numero {numeroProtocollo} e data {dataProtocollo} (id protocollo: {idProtocollo})";

            InsertEvent(idCommissione, Constants.Eventi.ParereCommissioneProtocollato, msg);
        }

        public void AccessoAVotoNegatoDaFrontend(int idCommissione, int codiceAnagrafe, int codiceIstanza)
        {
            var msg = $"ATTENZIONE: l'utente {GetNomeUtente(codiceAnagrafe)} ha cercato di inserire un voto per la pratica {GetNumeroIstanza(codiceIstanza)} ma la verifica dei permessi è fallita";

            InsertEvent(idCommissione, Constants.Eventi.AccessoAVotoNegatoDaFrontend, msg);
        }

        public void UtenteAssociatoACommissione(int idCommissione, int codiceAnagrafe, int idAmministrazione, string pin)
        {
            var msg = $"L'utente {GetNomeUtente(codiceAnagrafe)} è stato collegato all'amministrazione {GetNomeAmministrazione(idAmministrazione)} tramite pin {pin}";

            InsertEvent(idCommissione, Constants.Eventi.UtenteAssociatoACommissione, msg);
        }

        public void ErroreProtocollazioneVoto(int idCommissione, int codiceAnagrafe, string messaggioErrore)
        {
            var msg = $"Si è verificato un errore durante la protocollazione del voto per l'utente {GetNomeUtente(codiceAnagrafe)}: {messaggioErrore}";


            InsertEvent(idCommissione, Constants.Eventi.ErroreProtocollazioneVoto, msg);
        }
    }
}
