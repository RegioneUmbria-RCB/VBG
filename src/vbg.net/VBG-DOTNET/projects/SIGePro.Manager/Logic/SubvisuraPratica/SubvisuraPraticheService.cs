using Init.SIGePro.Data;
using Init.SIGePro.Manager.Stc;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication.ServiceReferences;
using Init.SIGePro.Manager.Authentication.WsSigeproSecurity;
using SIGePro.Manager.Verticalizzazioni;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.SubvisuraPratica
{
    public class SubvisuraPraticheService
    {
        private readonly AuthenticationInfo _authInfo;
        private readonly VerticalizzazioneStc _verticalizzazioneStc;
        private readonly SigeproSecurityProxy _sigeproSecurityProxy;
        ILog _log = LogManager.GetLogger(typeof(SubvisuraPraticheService));
        StcProxy _stcProxy = null;
        //string _aliasComune;

        public SubvisuraPraticheService(AuthenticationInfo authInfo, VerticalizzazioneStc verticalizzazioneStc)
        {
            _authInfo = authInfo;
            _verticalizzazioneStc = verticalizzazioneStc;
            _sigeproSecurityProxy = new SigeproSecurityProxy();
        }

        public string GetUidPraticaCollegataDaIdMovimento(Movimenti movimento)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = $@"SELECT 
                            istanze.codiceistanza AS idpratica,
                            istanze.software AS sportello_origine,
                            istanze.idcomune as idente_origine,

                            amministrazioni.stc_idnodo,
                            amministrazioni.stc_idente,
                            amministrazioni.stc_idsportello,
                            movimenti.codiceinventario AS idprocedimento
                        FROM     
                          movimenti 
                           
                            INNER JOIN amministrazioni ON
                              amministrazioni.idcomune = movimenti.idcomune AND
                              amministrazioni.codiceamministrazione = movimenti.codiceamministrazione_stc

                            INNER JOIN istanze ON 
                              istanze.idcomune = movimenti.idcomune AND
                              istanze.codiceistanza = movimenti.codiceistanza

                            LEFT JOIN COMUNIASSOCIATIESCLUSIONI ON 
                                COMUNIASSOCIATIESCLUSIONI.IDCOMUNE = ISTANZE.IDCOMUNE AND
                                COMUNIASSOCIATIESCLUSIONI.CODICECOMUNE = ISTANZE.CODICECOMUNE AND
                                COMUNIASSOCIATIESCLUSIONI.SOFTWARE = ISTANZE.SOFTWARE


                        WHERE
                            COMUNIASSOCIATIESCLUSIONI.IDCOMUNE IS NULL and
                            movimenti.idcomune = {db.Specifics.QueryParameterName("idcomune")} AND
                            movimenti.codicemovimento = {db.Specifics.QueryParameterName("codicemovimento")} AND
                            movimenti.inviato_con_stc <> 0 and
                            amministrazioni.stc_idnodo = {db.Specifics.QueryParameterName("idNodoInterno")}";


                var riferimentiPratica = db.ExecuteReader(sql, mp =>
                {
                    mp.AddParameter("idcomune", movimento.IDCOMUNE);
                    mp.AddParameter("codicemovimento", movimento.CODICEMOVIMENTO);
                    mp.AddParameter("idNodoInterno", this._verticalizzazioneStc.NlaIdnodo);
                },
                dr =>
                {
                    return new
                    {
                        IdPratica = dr.GetInt("idpratica"),
                        IdEnteOrigine = dr.GetString("idente_origine"),
                        IdSportelloOrigine = dr.GetString("sportello_origine"),

                        StcIdNodo = dr.GetInt("stc_idnodo"),
                        StcIdEnte = dr.GetString("stc_idente"),
                        StcIdSportello = dr.GetString("stc_idsportello"),

                        IdProcedimentoMitt = dr.GetInt("idprocedimento")
                    };
                }).FirstOrDefault();

                if (riferimentiPratica == null)
                {
                    _log.Debug($"Subvisura pratiche: la pratica collegata al movimento {movimento.CODICEMOVIMENTO} non ha generato una notifica interna");
                    return string.Empty;
                }

                // Devo fare una query (potenzialmente su un altro db) per verificare se sul software e sull'id comune della pratica di destinazione è attiva la subvisura
                var dbCnnInfo = this._sigeproSecurityProxy.GetDbConnectionInfo(riferimentiPratica.StcIdEnte);
                var subVisuraAttiva = this.SubvisuraAttivaSuSoftwareAltroEnte(dbCnnInfo, riferimentiPratica.StcIdSportello);

                if (!subVisuraAttiva)
                {
                    _log.Debug($"Subvisura pratiche: la pratica collegata al movimento {movimento.CODICEMOVIMENTO} non è stata notificata " +
                               $"ad un'amministrazione con subvisura attiva. stcidente={riferimentiPratica.StcIdEnte}," +
                               $"stcidsportello={riferimentiPratica.StcIdSportello}");

                    return string.Empty;
                }

                // Cerco di riutilizzare il proxy in modo da non fare troppe chiamate a stc per staccare il token
                if (this._stcProxy == null)
                {
                    this._stcProxy = new StcProxy(_verticalizzazioneStc, this._authInfo.Alias, riferimentiPratica.IdSportelloOrigine);
                }

                try
                {

                    var codiceIstanza = _stcProxy.PraticaCollegata(riferimentiPratica.IdPratica.Value,
                                                                    riferimentiPratica.IdProcedimentoMitt.GetValueOrDefault(Convert.ToInt32(movimento.CODICEMOVIMENTO)),
                                                                    riferimentiPratica.StcIdNodo.Value,
                                                                    riferimentiPratica.StcIdEnte,
                                                                    riferimentiPratica.StcIdSportello);

                    _log.Debug($"Subvisura pratiche: la pratica collegata al movimento {movimento.CODICEMOVIMENTO} ha codiceistanza={codiceIstanza}");

                    var uuid = GetUidPraticaRemota(dbCnnInfo, codiceIstanza);

                    _log.Debug($"Subvisura pratiche: la pratica collegata al movimento {movimento.CODICEMOVIMENTO} ha uuid={uuid}");

                    return uuid;

                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore nella chiamata a stcProxy.PraticaCollegata idPratica = {riferimentiPratica.IdPratica.Value}, codicemovimento= {movimento.CODICEMOVIMENTO}: {ex}");

                    return String.Empty;
                }
            }
        }

        private bool SubvisuraAttivaSuSoftwareAltroEnte(GetDbConnectionInfoResponse dbCnnInfo, string altroSportello)
        {

            using (var db = dbCnnInfo.CreateDatabase())
            {
                var sql = $"select flag_subvisura from softwareattivi where idcomune={db.Specifics.QueryParameterName("idcomune")} and fk_software={db.Specifics.QueryParameterName("software")}";

                var attivo = db.ExecuteScalar(sql, 0, mp =>
                {
                    mp.AddParameter("idcomune", dbCnnInfo.idComune);
                    mp.AddParameter("software", altroSportello);
                });

                var subVisuraAttiva = attivo != 0;

                _log.Debug($"Subvisura pratiche: stato subvisura per ente {dbCnnInfo.alias} (idcomune={dbCnnInfo.idComune}) ha subVisuraAttiva={subVisuraAttiva}");

                return subVisuraAttiva;
            }
        }

        private string GetUidPraticaRemota(GetDbConnectionInfoResponse dbCnnInfo, string codiceIstanza)
        {
            using (var db = dbCnnInfo.CreateDatabase())
            {

                var sql = $"select uuid from istanze where idcomune = {db.Specifics.QueryParameterName("idcomune")} and codiceistanza = {db.Specifics.QueryParameterName("codiceIstanza")}";

                return db.ExecuteReader(sql, mp =>
                {
                    mp.AddParameter("idcomune", dbCnnInfo.idComune);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                },
                dr => dr.GetString(0)).FirstOrDefault();

            }
        }
    }
}
