using Init.SIGePro.Data;
using Init.SIGePro.Manager.Stc;
using Init.SIGePro.Verticalizzazioni;
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
        ILog _log = LogManager.GetLogger(typeof(SubvisuraPraticheService));
        DataBase _db;
        VerticalizzazioneStc _verticalizzazioneStc;
        StcProxy _stcProxy = null;

        public SubvisuraPraticheService(DataBase db, VerticalizzazioneStc verticalizzazioneStc)
        {
            this._db = db;
            this._verticalizzazioneStc = verticalizzazioneStc;
        }

        public string GetUdiPraticaCollegataDaIdMovimento(Movimenti movimento)
        {
            var sql = $@"SELECT 
  istanze.codiceistanza AS idpratica,
  istanze.software AS sportello_origine,
  amministrazioni.stc_idnodo AS idnodo,
  amministrazioni.stc_idente AS idente,
  amministrazioni.stc_idsportello AS idsportello,
  movimenti.codicemovimento AS idprocedimento
FROM     
  movimenti 
                           
    INNER JOIN amministrazioni ON
      movimenti.idcomune = amministrazioni.idcomune AND
      movimenti.codiceamministrazione_stc = amministrazioni.codiceamministrazione

    INNER JOIN istanze ON 
      istanze.idcomune = movimenti.idcomune AND
      istanze.codiceistanza = movimenti.codiceistanza

    INNER JOIN softwareattivi ON
      softwareattivi.idcomune = amministrazioni.idcomune AND 
      softwareattivi.fk_software = amministrazioni.stc_idsportello

WHERE
    movimenti.idcomune = {this._db.Specifics.QueryParameterName("idcomune")} AND
    movimenti.codicemovimento = {this._db.Specifics.QueryParameterName("codicemovimento")} AND
    movimenti.inviato_con_stc <> 0 and
    amministrazioni.stc_idnodo = {this._db.Specifics.QueryParameterName("idNodoInterno")} AND
    softwareattivi.flag_subvisura = 1";


            var riferimentiPratica = this._db.ExecuteReader(sql, mp =>
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
                    IdNodo = dr.GetInt("IdNodo"),
                    IdEnte = dr.GetString("IdEnte"),
                    IdSportello = dr.GetString("IdSportello"),
                    IdSportelloOrigine = dr.GetString("sportello_origine")
                };
            }).FirstOrDefault();

            if (riferimentiPratica == null)
            {
                return string.Empty;
            }

            // Cerco di riutilizzare il proxy in modo da non fare troppe chiamate a stc per staccare il token
            if (this._stcProxy == null)
            {
                /* hack, passo come id ente lo stesso della pratica di destinazione (amministrazione), 
                   non è la cosa più corretta ma dovrebbe funzionare lo stesso  */
                this._stcProxy = new StcProxy(this._verticalizzazioneStc, riferimentiPratica.IdEnte, riferimentiPratica.IdSportelloOrigine);
            }

            try
            {


                var codiceIstanza = _stcProxy.PraticaCollegata(riferimentiPratica.IdPratica.Value,
                                                                Convert.ToInt32(movimento.CODICEMOVIMENTO),
                                                                riferimentiPratica.IdNodo.Value,
                                                                riferimentiPratica.IdEnte,
                                                                riferimentiPratica.IdSportello);

                sql = "select uuid from istanze where idcomune = {0} and codiceistanza = {1}";

                return this._db.ExecuteReader(sql, mp =>
                {
                    mp.AddParameter("idcomune", movimento.IDCOMUNE);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                },
                dr => dr.GetString(0)).FirstOrDefault();
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a stcProxy.PraticaCollegata idPratica = {0}, codicemovimento= {1}: {2}", riferimentiPratica.IdPratica.Value, movimento.CODICEMOVIMENTO, ex.ToString());

                return String.Empty;
            }

        }
    }
}
