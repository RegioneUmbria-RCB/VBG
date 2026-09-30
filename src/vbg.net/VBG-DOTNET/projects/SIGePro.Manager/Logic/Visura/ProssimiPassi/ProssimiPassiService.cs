using Init.SIGePro.Manager.DTO.Visura.ProssimiPassi;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.Visura.ProssimiPassi
{
    public class ProssimiPassiService : IProssimiPassiService
    {
        private static class Constants
        {
            public const string TipoScadenzaRelativo = "R";
        }

        private DataBase _db;
        private string _idComune;

        public ProssimiPassiService(DataBase db, string idComune)
        {

            this._db = db;
            this._idComune = idComune;
        }
        public IEnumerable<ProssimiPassiDto> GetProssimiPassi(int codiceIstanza)
        {
            // TODO: risolvere la data di presentazione dell'istanza
            var datiIstanza = this.GetDatiIstanza(codiceIstanza);

            // TODO: risolvere la lista dei prossimi passi (se configurata)
            var prossimiPassi = this.GetProssimiPassi(datiIstanza);

            return prossimiPassi;
        }

        private IEnumerable<ProssimiPassiDto> GetProssimiPassi(DatiPresentazioneIstanza datiIstanza)
        {
            var sql = $@"SELECT
	                    tempi_fo_d.titolo,
	                    tempi_fo_d.descrizione,
	                    tempi_fo_d.tipo,
	                    tempi_fo_d.giorni,
	                    tempi_fo_d.scadenza
                    FROM
	                    alberoproc
		                    INNER JOIN alberoproc_tempi ON
			                    alberoproc_tempi.idcomune = alberoproc.idcomune AND
			                    alberoproc_tempi.fk_scid = alberoproc.sc_Id
		                    INNER JOIN tempi_fo_d ON
			                    tempi_fo_d.idcomune = alberoproc_tempi.idcomune AND 
			                    tempi_fo_d.fkid_tempi = alberoproc_tempi.fkid_tempi
                    WHERE
	                    alberoproc.idcomune = {this._db.QueryParameter("idComune")} AND
	                    alberoproc.sc_id = {this._db.QueryParameter("codiceIntervento")}
                    ORDER BY 
	                    tempi_fo_d.ordine";

            return this._db.ExecuteReader(sql,
                mp => mp.Add("idComune", this._idComune)
                        .Add("codiceIntervento", datiIstanza.CodiceIntervento),
                dr => new ProssimiPassiDto
                {
                    Titolo = dr.GetString("titolo"),
                    Sottotitolo = dr.GetString("descrizione"),
                    Data = dr.GetString("tipo") == Constants.TipoScadenzaRelativo ?
                            datiIstanza.DataPresentazione.AddDays(dr.GetInt("giorni").Value) :
                            dr.GetDateTime("scadenza") ?? DateTime.MinValue
                });
        }

        internal class DatiPresentazioneIstanza
        {
            public DateTime DataPresentazione { get; set; }
            public int CodiceIntervento { get; set; }
        }

        private DatiPresentazioneIstanza GetDatiIstanza(int codiceIstanza)
        {
            var sql = $"SELECT DATA, CODICEINTERVENTOPROC  FROM istanze WHERE idcomune = {this._db.QueryParameter("idComune")} AND codiceistanza={this._db.QueryParameter("codiceIstanza")}";

            var dati = this._db.ExecuteReader(sql,
                mp => mp.Add("idComune", this._idComune)
                        .Add("codiceIstanza", codiceIstanza),
                dr => new DatiPresentazioneIstanza
                {
                    DataPresentazione = dr.GetDateTime("DATA").Value,
                    CodiceIntervento = dr.GetInt("CODICEINTERVENTOPROC").Value
                });

            if ((dati?.Count() ?? 0) == 0)
            {
                throw new Exception($"Istanza con id {this._idComune}-{codiceIstanza} non trovata");
            }

            if (dati.Count() > 1)
            {
                throw new Exception($"Trovata più di una istanza con id {this._idComune}-{codiceIstanza}!");
            }

            return dati.First();
        }


    }
}
