using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneAutorizzazioni
{
    public class AutorizzazioniService
    {
        AuthenticationInfo _ai = null;

        public AutorizzazioniService(AuthenticationInfo ai)
        {
            this._ai = ai;
        }

        public void RegistraNuovoDocumentoInAutorizzazione(int idAutorizzazione, int codiceOggetto, string riferimentoEsterno = null, bool principale = false)
        {
            using (var db = this._ai.CreateDatabase())
            {
                string sql = $@"insert into documenti_autorizzazione
                                    (idcomune,codiceoggetto,id_autorizzazione,principale,riferimento_esterno)
                                values
                                    (
                                    {db.Specifics.QueryParameterName("idComune")},
                                    {db.Specifics.QueryParameterName("codiceOggetto")},
                                    {db.Specifics.QueryParameterName("idAutorizzazione")},
                                    {db.Specifics.QueryParameterName("principale")},
                                    {db.Specifics.QueryParameterName("riferimentoEsterno")}
                                    )";
                db.ExecuteNonQuery(sql,
                    mp =>
                    {
                        mp.AddParameter("idComune", this._ai.IdComune);
                        mp.AddParameter("codiceOggetto", codiceOggetto);
                        mp.AddParameter("idAutorizzazione", idAutorizzazione);
                        mp.AddParameter("principale", principale ? 1 : 0);
                        mp.AddParameter("riferimentoEsterno", riferimentoEsterno);
                    });
            }
        }

        public List<Autorizzazioni> GetAutorizzazioniAttiveDellIstanza(int codiceIstanza)
        {
            using (var db = _ai.CreateDatabase())
            {
                try
                {
                    db.Connection.Open();

                    var filtro = new Autorizzazioni
                    {
                        IDCOMUNE = _ai.IdComune,
                        FKIDISTANZA = codiceIstanza.ToString(),
                        FlagAttiva = 1,
                        UseForeign = PersonalLib2.Sql.useForeignEnum.Yes
                    };

                    var autMgr = new AutorizzazioniMgr(db);

                    return autMgr.GetList(filtro);
                }
                finally
                {
                    db.Connection.Close();
                }
            }
        }

        public Autorizzazioni GetAutorizzazioneDaFkIdProtocollo(string riferimentoEsterno)
        {
            using (var db = _ai.CreateDatabase())
            {
                try
                {
                    db.Connection.Open();

                    var filtro = new Autorizzazioni
                    {
                        IDCOMUNE = this._ai.IdComune,
                        FkIdProtocollo = riferimentoEsterno
                    };

                    var autMgr = new AutorizzazioniMgr(db);

                    var elenco = autMgr.GetList(filtro);

                    if (elenco == null || !elenco.Any())
                    {
                        return null;
                    }

                    if (elenco.Count > 1)
                    {
                        throw new Exception($"Ci sono due autorizzazioni che fanno riferimento allo stesso identificativo di protocollo {riferimentoEsterno}; impossibile individuare univocamente l'autorizzazione");
                    }

                    return elenco.First();

                }
                finally
                {
                    db.Connection.Close();
                }
            }
        }
    }
}
