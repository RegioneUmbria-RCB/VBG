using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public class AmbitoMgr : BaseManager
    {
        private static readonly (string Tabella, string Colonna)[] _mapping =
        {
            ("istanze", "codiceistanza"),
            ("movimenti", "codicemovimento"),
            ("autorizzazioni", "id")
        };

        protected readonly DataBase _db;

        public AmbitoMgr(DataBase dataBase) : base(dataBase)
        {
            _db = dataBase;
        }

        public (AmbitoProtocollazioneEnum, int) GetAmbitoProtocollazione(string idComune, string numeroProtocollo, string annoProtocollo)
        {
            if (_db.Connection.State == ConnectionState.Closed)
                _db.Connection.Open();

            try
            {
                foreach (var entry in _mapping)
                {
                    var codice = GetCodiceTabellaDelProtocollo(entry.Tabella, entry.Colonna, idComune, numeroProtocollo, annoProtocollo);

                    if (codice > -1)
                    {
                        switch (entry.Tabella)
                        {
                            case "istanze":
                                return (AmbitoProtocollazioneEnum.DA_ISTANZA, codice);
                            case "movimenti":
                                return (AmbitoProtocollazioneEnum.DA_MOVIMENTO, codice);
                            case "autorizzazioni":
                                return (AmbitoProtocollazioneEnum.DA_AUTORIZZAZIONE, codice);
                        };
                    }
                }

                return (AmbitoProtocollazioneEnum.NESSUNO, -1);
            }
            finally
            {
                _db.Connection.Close();
            }
        }

        public (AmbitoProtocollazioneEnum, int) GetAmbitoProtocollazione(string idComune, string idProtocollo)
        {
            if (_db.Connection.State == ConnectionState.Closed)
                _db.Connection.Open();

            try
            {
                foreach (var entry in _mapping)
                {
                    var codice = GetCodiceTabellaDelProtocollo(entry.Tabella, entry.Colonna, idComune, idProtocollo);

                    if (codice > -1)
                    {
                        switch (entry.Tabella)
                        {
                            case "istanze":
                                return (AmbitoProtocollazioneEnum.DA_ISTANZA, codice);
                            case "movimenti":
                                return (AmbitoProtocollazioneEnum.DA_MOVIMENTO, codice);
                            case "autorizzazioni":
                                return (AmbitoProtocollazioneEnum.DA_AUTORIZZAZIONE, codice);
                        }
                        ;
                    }
                }

                return (AmbitoProtocollazioneEnum.NESSUNO, -1);
            }
            finally
            {
                _db.Connection.Close();
            }
        }

        private int GetCodiceTabellaDelProtocollo(string nomeTabella, string nomeColonna, string idComune, string idProtocollo)
        {
            var sql = $@"
                SELECT {nomeColonna} 
                FROM {nomeTabella}
                WHERE idcomune = {this._db.QueryParameter("idComune")}
                  AND fkidprotocollo = {this._db.QueryParameter("fkidprotocollo")}
            ";

            var result = this._db.ExecuteScalar(sql, -1,
                mp => mp.Add("idComune", idComune)
                        .Add("fkidprotocollo", idProtocollo));

            return Convert.ToInt32(result);
        }

        private int GetCodiceTabellaDelProtocollo(string nomeTabella, string nomeColonna, string idComune, string numeroProtocollo, string annoProtocollo)
        {
            var sql = $@"
                SELECT {nomeColonna} 
                FROM {nomeTabella}
                WHERE idcomune = {this._db.QueryParameter("idComune")}
                  AND numeroprotocollo = {this._db.QueryParameter("numeroProtocollo")}
                  AND EXTRACT(YEAR FROM dataprotocollo) = {this._db.QueryParameter("annoProtocollo")}
            ";

            var result = this._db.ExecuteScalar(sql, -1,
                mp => mp.Add("idComune", idComune)
                        .Add("numeroProtocollo", numeroProtocollo)
                        .Add("annoProtocollo", annoProtocollo));

            return Convert.ToInt32(result);
        }

    }
}
