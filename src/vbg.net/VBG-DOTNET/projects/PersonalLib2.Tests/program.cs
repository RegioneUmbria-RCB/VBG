using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace PersonalLib2.Tests
{
    internal class program
    {
        private static void Main(string[] args)
        {
            var retVal = new Istanze();
            retVal.CODICEISTANZA = "8571";
            retVal.IDCOMUNE = "E256";
            retVal.UseForeign = Sql.useForeignEnum.Recoursive;

            var cnString = "data source=(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = dbora10g)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = ora10g)));User Id = SIGEPRO2; Password = Si24Init";

            var db = new DataBase(cnString, ProviderType.OracleClient);

            var clList = db.GetClassList(retVal, true);
        }

    }
}
