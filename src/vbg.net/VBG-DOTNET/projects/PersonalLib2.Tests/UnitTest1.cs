using Init.SIGePro.Data;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using PersonalLib2.Data;
using PersonalLib2.Data.Metadata;
using PersonalLib2.Sql;
using System.Diagnostics;
using System.Text.Json;

namespace PersonalLib2.Tests
{
    [TestClass]
    public class UnitTest1
    {
        private DataBase db;

        [TestInitialize]
        public void Initialize()
        {
            //var cnString = "data source=(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = dbora10g)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = ora10g)));User Id = SIGEPRO2; Password = Si24Init";

            var cnString = "Server=mysql;Database=ibcback;uid=ibcback;pwd=ibcback4init;";
            this.db = new DataBase(cnString, ProviderType.MySqlClient);

            MetadataAnalyzer.Instance.EnsureAnalysisExistsFor(typeof(Istanze));
        }

        [TestMethod]
        public void TestMySql()
        {
            var retVal = new Istanze();
            retVal.CODICEISTANZA = "8571";
            retVal.IDCOMUNE = "E256";
            retVal.UseForeign = Sql.useForeignEnum.Recoursive;

            var clList = this.db.GetClassList(retVal, true);
            Debug.WriteLine("--------------------------------------------------");
            Debug.WriteLine(JsonSerializer.Serialize(clList));
        }

        [TestMethod]
        public void TestMethod1()
        {
            var retVal = new Istanze();
            retVal.CODICEISTANZA = "9125";//"8571";
            retVal.IDCOMUNE = "E256";
            retVal.UseForeign = Sql.useForeignEnum.Recoursive;

            var clList = this.db.GetClassList(retVal, true);
        }

        [TestMethod]
        public void OthersWhereClause()
        {
            var filtro = new Dyn2ModelliD();

            filtro.Idcomune = "E256";
            filtro.FkD2mtId = 12;
            filtro.OthersWhereClause.Add("FK_D2C_ID is not null");
            filtro.UseForeign = useForeignEnum.Recoursive;
            filtro.OrderBy = "FK_D2C_ID asc";

            var res = this.db.GetClassList(filtro, true);
        }

        [TestMethod]
        public void ErroreOneriPistoia_ticket2022020910000198()
        {
            // CCITabella3Mgr mgr = new CCITabella3Mgr(authInfo.CreateDatabase());

            var filtro = new CCITabella3();

            filtro.Idcomune = "E256";
            filtro.FkCcicId = 123;
            filtro.OthersTables.Add("CC_TABELLA3");
            filtro.OthersWhereClause.Add("CC_ITABELLA3.IDCOMUNE = CC_TABELLA3.IDCOMUNE");
            filtro.OthersWhereClause.Add("CC_ITABELLA3.FK_CCT3_ID = CC_TABELLA3.ID");
            filtro.OrderBy = "CC_TABELLA3.FK_CCDS_ID ASC, CC_ITABELLA3.ID ASC";
            filtro.UseForeign = PersonalLib2.Sql.useForeignEnum.Recoursive;

            var result = this.db.GetClassList(filtro);
        }
    }
}
