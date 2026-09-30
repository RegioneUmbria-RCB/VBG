using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class EscludiInterventiCondition : QueryConditionBase
    {
        public EscludiInterventiCondition(DataBase db) : base(db, "")
        {
            var forceIndex = "";

            if (db.Specifics.DBMSName() == Provider.MYSQL)
            {
                forceIndex = " FORCE INDEX (IDX_ALBEROPROC_004) ";
            }

            this.Query = $@"NOT EXISTS (
                SELECT 1
                FROM
                    ALBEROPROC FIGLI
                        INNER JOIN ALBEROPROC PADRE {forceIndex} ON 
                            PADRE.IDCOMUNE = FIGLI.IDCOMUNE AND 
                            PADRE.SOFTWARE = FIGLI.SOFTWARE AND 
                            PADRE.FLAG_ESCLUDI_RISULTATI_RICERCA = 1 AND
                            FIGLI.SC_CODICE LIKE {db.Specifics.ConcatFunction("PADRE.SC_CODICE", "'%'")}
                WHERE
                    FIGLI.IDCOMUNE = ALBEROPROC.IDCOMUNE AND
                    FIGLI.SC_ID = ALBEROPROC.SC_ID
                )";
        }
    }
}
