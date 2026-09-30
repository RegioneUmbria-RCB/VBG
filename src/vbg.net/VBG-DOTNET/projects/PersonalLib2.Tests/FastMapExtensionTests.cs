using Init.SIGePro.Data;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using PersonalLib2.Data;
using System;
using System.Linq;

namespace PersonalLib2.Tests
{
    [TestClass]
    public class FastMapExtensionTests
    {
        private DataBase db;

        [TestInitialize]
        public void Initialize()
        {
            var cnString = "Server=mysql;Database=ibcback;uid=ibcback;pwd=ibcback4init;";
            this.db = new DataBase(cnString, ProviderType.MySqlClient);
        }

        [TestMethod]
        public void Select_da_istanze()
        {
            FormattableString sql = $"select * from istanze where idcomune='E256' limit 1000";

            var istanze = this.db.FastMap<Istanze>(sql);

            Assert.AreEqual(1000, istanze.Count());
        }

        [TestMethod]
        public void Select_da_istanze_old()
        {
            var sql = "select * from istanze where idcomune='E256' limit 1000";

            var cmd = this.db.CreateCommand(sql);
            var istanze = this.db.GetClassList<Istanze>(cmd);

            Assert.AreEqual(1000, istanze.Count());
        }

    }
}
