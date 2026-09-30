using Init.SIGePro.Manager.Authentication;
using PersonalLib2.Data;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneOneri.PosizioniDebitorie
{
    public class VerificaAnnullamentoPosizioniDebitorieService : IVerificaAnnullamentoPosizioniDebitorieService
    {
        private static class Constants
        {
            public const string CodiceStatoPosizioneAnnullata = "ANNULLATO";
        }

        private readonly AuthenticationInfo _authenticationInfo;

        public VerificaAnnullamentoPosizioniDebitorieService(AuthenticationInfo authenticationInfo)
        {
            this._authenticationInfo = authenticationInfo;
        }

        public bool PosizioniDebitorieOnereAnnullate(int idOnere)
        {
            using (var db = this._authenticationInfo.CreateDatabase())
            {
                var almenoUnRecordTrovato = false;

                // ISTONERI_DETT_POSIZIONI
                var sql = $@"SELECT 
                                STATO 
                            FROM                                
                                istoneri_dett_posizioni 

                                INNER JOIN dett_posizione_debitoria ON 
                                    dett_posizione_debitoria.idcomune = istoneri_dett_posizioni.idcomune and
                                    dett_posizione_debitoria.id = istoneri_dett_posizioni.fk_dettposdebitoria_id

                            WHERE 
                                istoneri_dett_posizioni.IDCOMUNE = {db.QueryParameter("idComune")} and
                                istoneri_dett_posizioni.fk_istanzeoneri_id = {db.QueryParameter("idOnere")}";

                var res = db.ExecuteReader(sql,
                    mp => mp.Add("idComune", this._authenticationInfo.IdComune)
                            .Add("idOnere", idOnere),
                    dr => dr.GetString("STATO"));

                almenoUnRecordTrovato = res.Any();

                if (res.Any(x => x != Constants.CodiceStatoPosizioneAnnullata))
                {
                    return false;
                }

                // BOLL_GEST_DETTAGLIO
                sql = $@"SELECT 
                            dett_posizione_debitoria.stato
                        FROM
                            boll_gest_istanzeoneri

                            INNER JOIN boll_gest_dettaglio ON
                                boll_gest_dettaglio.idcomune = boll_gest_istanzeoneri.idcomune AND
                                boll_gest_dettaglio.id = boll_gest_istanzeoneri.fk_bollgestdet_id

                            INNER JOIN dett_posizione_debitoria ON
                                dett_posizione_debitoria.idcomune = boll_gest_dettaglio.idcomune and
                                dett_posizione_debitoria.id = boll_gest_dettaglio.fk_posdebdettaglio_id

                        WHERE
                            boll_gest_istanzeoneri.idcomune = {db.QueryParameter("idComune")} AND
                            boll_gest_istanzeoneri.fk_codiceistanzeoneri = {db.QueryParameter("idOnere")}";

                res = db.ExecuteReader(sql,
                    mp => mp.Add("idComune", this._authenticationInfo.IdComune)
                            .Add("idOnere", idOnere),
                    dr => dr.GetString("STATO"));

                almenoUnRecordTrovato = almenoUnRecordTrovato || res.Any();

                if (res.Any(x => x != Constants.CodiceStatoPosizioneAnnullata))
                {
                    return false;
                }

                // BOLL_GEST_DETT_RATE
                sql = $@"SELECT 
                            dett_posizione_debitoria.stato 
                        FROM 
                            boll_gest_istanzeoneri

                            INNER JOIN boll_gest_dett_rate ON 
                                boll_gest_dett_rate.idcomune = boll_gest_istanzeoneri.idcomune AND
                                boll_gest_dett_rate.fk_bollgestdet_id = boll_gest_istanzeoneri.fk_bollgestdet_id

                            INNER JOIN dett_posizione_debitoria ON 
                                dett_posizione_debitoria.idcomune = boll_gest_dett_rate.idcomune and
                                dett_posizione_debitoria.id = boll_gest_dett_rate.fk_posdebdettaglio_id

                        WHERE
                            boll_gest_istanzeoneri.idcomune = {db.QueryParameter("idComune")} AND
                            boll_gest_istanzeoneri.fk_codiceistanzeoneri = {db.QueryParameter("idOnere")}";

                res = db.ExecuteReader(sql,
                    mp => mp.Add("idComune", this._authenticationInfo.IdComune)
                            .Add("idOnere", idOnere),
                    dr => dr.GetString("STATO"));

                almenoUnRecordTrovato = almenoUnRecordTrovato || res.Any();

                if (res.Any(x => x != Constants.CodiceStatoPosizioneAnnullata))
                {
                    return false;
                }

                return almenoUnRecordTrovato;
            }
        }
    }
}
