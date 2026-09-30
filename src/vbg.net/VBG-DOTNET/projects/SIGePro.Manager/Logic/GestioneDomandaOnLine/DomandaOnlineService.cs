using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication.Utils;
using log4net;
using PersonalLib2.Data;
using System.Linq;
using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Logic.GestioneDomandaOnLine
{
    public class DomandaOnlineService : IDomandaOnlineService
    {
        private readonly IDatabaseCreator _databaseCreator;
        private readonly IIdComuneResolver _idComune;

        private readonly ILog _log = LogManager.GetLogger(typeof(DomandaOnlineService));

        public DomandaOnlineService(IDatabaseCreator databaseCreator, IEventPublisher eventPublisher, IIdComuneResolver idComune)
        {
            this._databaseCreator = databaseCreator;
            this._idComune = idComune;
        }

        /// <summary>
        /// Copia DOCUMENTIISTANZA.CODICEOGGETTO dell'istanza di partenza in FO_DOMANDE_OGGETTI.CODICEOGGETTO dell' idDomandaDestinazione
        /// </summary>
        /// <param name="codiceIstanzaOrigine">ISTANZE.CODICEISTANZA di partenza da cui copiare DOCUMENTIISTANZA.CODICEOGGETTO</param>
        /// <param name="idDomandaDestinazione">FO_DOMANDE.ID di destinazione</param>
        public void RecuperaDocumentiIstanzaCollegata(int codiceIstanzaOrigine, int idDomandaDestinazione)
        {

            using (var db = this._databaseCreator.Create())
            {
                var sql = $@"
                select
                    codiceoggetto
                from
                    documentiistanza
                where
                    idcomune = {db.Specifics.QueryParameterName("idComune")} and
                    codiceistanza = {db.Specifics.QueryParameterName("codiceIstanzaOrigine")} and
                    codiceoggetto is not null
                ";

                var oggetti = db.ExecuteReader(sql,
                    mp =>
                    {
                        mp.AddParameter("idComune", this._idComune.IdComune);
                        mp.AddParameter("codiceIstanzaOrigine", codiceIstanzaOrigine);
                    },
                    x => x.GetInt("codiceoggetto").Value);


                oggetti.ToList().ForEach(
                    x =>
                    {

                        //controllo esistenza
                        sql = $@"
                        select 
                            count(codiceoggetto) as conteggio
                        from 
                            fo_domande_oggetti 
                        where 
                            idcomune = {db.Specifics.QueryParameterName("idComune")} and
                            iddomanda = {db.Specifics.QueryParameterName("idDomandaDestinazione")} and
                            codiceoggetto = {db.Specifics.QueryParameterName("codiceoggetto")}
                        ";

                        var conteggio = db.ExecuteScalar(sql,
                            0,
                            mp =>
                            {
                                mp.AddParameter("idComune", this._idComune.IdComune);
                                mp.AddParameter("idDomandaDestinazione", idDomandaDestinazione);
                                mp.AddParameter("codiceoggetto", x);
                            });

                        if (conteggio == 0)
                        {
                            //inserimento
                            sql = $@"
                            insert into
                                fo_domande_oggetti(idcomune,iddomanda,codiceoggetto) 
                            values
                                ({db.Specifics.QueryParameterName("idComune")},
                                    {db.Specifics.QueryParameterName("idDomandaDestinazione")},
                                    {db.Specifics.QueryParameterName("codiceoggetto")})
                        ";

                            db.ExecuteNonQuery(sql,
                            mp =>
                            {
                                mp.AddParameter("idComune", this._idComune.IdComune);
                                mp.AddParameter("idDomandaDestinazione", idDomandaDestinazione);
                                mp.AddParameter("codiceoggetto", x);
                            });
                        }
                    }
                );
            }
        }

        //public void EliminaDomanda(int idDomanda)
        //{
        //    this._log.Debug($"Inizio eliminazione della bozza di domanda on-line con id {idDomanda}");

        //    try
        //    {
        //        var dom = this.GetById(idDomanda);

        //        if (dom == null)
        //        {
        //            // Domanda già eliminata???
        //            return;
        //        }

        //        this._log.Debug($"Pubblico l'evento {nameof(DomandaFOInCancellazioneEvent)} per l'id domanda on-line {idDomanda}");

        //        try
        //        {
        //            this._eventPublisher.Publish(new DomandaFOInCancellazioneEvent(idDomanda));
        //        }
        //        catch (Exception)
        //        {
        //            this._log.Error($"Errore durante la notifica dell'evento {typeof(DomandaFOInCancellazioneEvent)} per l'id domanda {idDomanda}. La cancellazione verrà comunque effettuata");
        //        }
        //        this.Delete(dom);

        //        this._log.Debug($"Eliminazione della bozza di domanda on-line con id {idDomanda} riuscita");

        //    }
        //    catch (Exception ex)
        //    {
        //        this._log.Error($"Errore durante l'eliminazione della domanda on-line con id {idDomanda}: {ex.ToString()}");

        //        throw;
        //    }
        //}

        public FoDomande GetById(int idDomanda)
        {
            using (var db = this._databaseCreator.Create())
            {
                var mgr = new FoDomandeMgr(db);

                return mgr.GetById(this._idComune.IdComune, idDomanda);
            }
        }


    }
}
