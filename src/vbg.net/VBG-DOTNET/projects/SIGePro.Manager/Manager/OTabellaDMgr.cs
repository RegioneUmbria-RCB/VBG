using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using System;
using System.Data;

namespace Init.SIGePro.Manager
{
    public partial class OTabellaDMgr
    {
        public OTabellaD GetByClass(OTabellaD cls)
        {
            return (OTabellaD)this.db.GetClass(cls);
        }

        private OTabellaD DataIntegrations(OTabellaD cls)
        {
            if (cls.Costo.GetValueOrDefault(float.MinValue) == float.MinValue)
                cls.Costo = 0;

            return cls;
        }

        private OTabellaD Insert(OTabellaD cls)
        {
            throw new NotImplementedException("Il metodo OTabellaDMgr.Insert non è implementabile. Utilizzare il metodo OTabellaDMgr.Save");
        }

        public OTabellaD Save(OTabellaD cls)
        {
            cls = this.DataIntegrations(cls);

            if (this.Update(cls) == 0)
            {
                this.Validate(cls, AmbitoValidazione.Insert);
                this.db.Insert(cls);
                this.ChildInsert(cls);
            }

            return cls;
        }

        private int Update(OTabellaD cls)
        {
            var retVal = 0;
            var internalOpen = false;

            //TODO: query
            var cmdText = "UPDATE " +
                                "O_TABELLAD " +
                             "SET " +
                                "COSTO = " + cls.Costo.ToString().Replace(",", ".") + " " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_OVC_ID = " + cls.FkOvcId.ToString() + " AND " +
                                "FK_ODE_ID = " + cls.FkOdeId.ToString() + " AND " +
                                "FK_OCA_ID = " + cls.FkOcaId.ToString() + " AND " +
                                "FK_OTO_ID = " + cls.FkOtoId.ToString() + " AND ";

            cmdText += (cls.FkOinId.GetValueOrDefault(int.MinValue) == int.MinValue) ? "FK_OIN_ID IS NULL " : "FK_OIN_ID = " + cls.FkOinId.ToString();

            if (cls.Id.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND ID = " + cls.Id.ToString();

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                this.db.Connection.Open();
            }

            using (IDbCommand cmd = this.db.CreateCommand(cmdText))
            {
                retVal = cmd.ExecuteNonQuery();
            }

            if ((this.db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                this.db.Connection.Close();
            }

            return retVal;
        }

        public void Delete(OTabellaD cls)
        {
            //TODO: query
            var internalOpen = false;
            var cmdText = "DELETE FROM " +
                                "O_TABELLAD " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_OVC_ID = " + cls.FkOvcId.ToString();

            if (cls.Id.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND ID = " + cls.Id.ToString();

            if (cls.FkOdeId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_ODE_ID = " + cls.FkOdeId.ToString();

            if (cls.FkOcaId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_OCA_ID = " + cls.FkOcaId.ToString();

            if (cls.FkOtoId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_OTO_ID = " + cls.FkOtoId.ToString();

            foreach (var owc in cls.OthersWhereClause)
            {
                cmdText += " AND " + owc;
            }

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                this.db.Connection.Open();
            }

            using (IDbCommand cmd = this.db.CreateCommand(cmdText))
            {
                cmd.ExecuteNonQuery();
            }

            if ((this.db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                this.db.Connection.Close();
            }
        }

        public void DeleteSingleRow(OTabellaD cls)
        {
            //TODO: query
            var internalOpen = false;
            var cmdText = "DELETE FROM " +
                                "O_TABELLAD " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_OVC_ID = " + cls.FkOvcId.ToString() + " AND " +
                                "FK_ODE_ID = " + cls.FkOdeId.ToString() + " AND " +
                                "FK_OCA_ID = " + cls.FkOcaId.ToString() + " AND " +
                                "FK_OTO_ID = " + cls.FkOtoId.ToString() + " AND ";

            cmdText += (cls.FkOinId.GetValueOrDefault(int.MinValue) == int.MinValue) ? "FK_OIN_ID IS NULL " : "FK_OIN_ID = " + cls.FkOinId.ToString();

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                this.db.Connection.Open();
            }

            using (IDbCommand cmd = this.db.CreateCommand(cmdText))
            {
                cmd.ExecuteNonQuery();
            }

            if ((this.db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                this.db.Connection.Close();
            }
        }
    }
}
