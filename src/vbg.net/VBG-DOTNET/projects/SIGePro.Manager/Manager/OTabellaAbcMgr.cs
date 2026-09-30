using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using System;
using System.Data;

namespace Init.SIGePro.Manager
{
    public partial class OTabellaAbcMgr
    {
        private OTabellaAbc DataIntegrations(OTabellaAbc cls)
        {
            if (cls.Costo.GetValueOrDefault(float.MinValue) == float.MinValue)
                cls.Costo = 0;
            return cls;
        }

        public OTabellaAbc GetByClass(OTabellaAbc cls)
        {
            return (OTabellaAbc)this.db.GetClass(cls);
        }

        public void Delete(OTabellaAbc cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            //TODO: query
            var internalOpen = false;
            var cmdText = "DELETE FROM " +
                                "O_TABELLAABC " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_OVC_ID = " + cls.FkOvcId.ToString();

            if (cls.Id.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND ID = " + cls.Id.ToString();

            if (cls.FkOdeId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_ODE_ID = " + cls.FkOdeId.ToString();

            if (cls.FkOitId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_OIT_ID = " + cls.FkOitId.ToString();

            if (cls.FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_AREE_CODICEAREA_ZTO = " + cls.FkAreeCodiceareaZto.ToString();

            if (cls.FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_AREE_CODICEAREA_PRG = " + cls.FkAreeCodiceareaPrg.ToString();

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

        public void DeleteSingleRow(OTabellaAbc cls)
        {
            //TODO: query
            var internalOpen = false;
            var cmdText = "DELETE FROM " +
                                "O_TABELLAABC " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_OVC_ID = " + cls.FkOvcId.ToString() + " AND " +
                                "FK_ODE_ID = " + cls.FkOdeId.ToString();

            cmdText += (cls.FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue) != int.MinValue) ? " AND FK_AREE_CODICEAREA_ZTO = " + cls.FkAreeCodiceareaZto.ToString() : " AND FK_AREE_CODICEAREA_ZTO IS NULL";
            cmdText += (cls.FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue) != int.MinValue) ? " AND FK_AREE_CODICEAREA_PRG = " + cls.FkAreeCodiceareaPrg.ToString() : " AND FK_AREE_CODICEAREA_PRG IS NULL";
            cmdText += (cls.FkOinId.GetValueOrDefault(int.MinValue) != int.MinValue) ? " AND FK_OIN_ID = " + cls.FkOinId.ToString() : " AND FK_OIN_ID IS NULL";
            cmdText += (cls.FkOitId.GetValueOrDefault(int.MinValue) != int.MinValue) ? " AND FK_OIT_ID = " + cls.FkOitId.ToString() : " AND FK_OIT_ID IS NULL";
            cmdText += (cls.FkOtoId.GetValueOrDefault(int.MinValue) != int.MinValue) ? " AND FK_OTO_ID = " + cls.FkOtoId.ToString() : " AND FK_OTO_ID IS NULL";

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

        private OTabellaAbc Insert(OTabellaAbc cls)
        {
            throw new NotImplementedException("Il metodo OTabellaAbcMgr.Insert non è implementabile. Utilizzare il metodo OTabellaAbcMgr.Save");
        }

        public OTabellaAbc Save(OTabellaAbc cls)
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

        private int Update(OTabellaAbc cls)
        {
            var retVal = 0;
            var internalOpen = false;

            //TODO: query
            var cmdText = "UPDATE " +
                                "O_TABELLAABC " +
                             "SET " +
                                "COSTO = " + cls.Costo.ToString().Replace(",", ".") + " " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_OVC_ID = " + cls.FkOvcId.ToString() + " AND " +
                                "FK_ODE_ID = " + cls.FkOdeId.ToString() + " AND " +
                                "FK_OTO_ID = " + cls.FkOtoId.ToString();

            cmdText += (cls.FkOitId.GetValueOrDefault(int.MinValue) != int.MinValue) ? " AND FK_OIT_ID = " + cls.FkOitId.ToString() : " AND FK_OIT_ID IS NULL";
            cmdText += (cls.FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue) != int.MinValue) ? " AND FK_AREE_CODICEAREA_ZTO = " + cls.FkAreeCodiceareaZto.ToString() : " AND FK_AREE_CODICEAREA_ZTO IS NULL";
            cmdText += (cls.FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue) != int.MinValue) ? " AND FK_AREE_CODICEAREA_PRG = " + cls.FkAreeCodiceareaPrg.ToString() : " AND FK_AREE_CODICEAREA_PRG IS NULL";
            cmdText += (cls.FkOinId.GetValueOrDefault(int.MinValue) != int.MinValue) ? " AND FK_OIN_ID = " + cls.FkOinId.ToString() : " AND FK_OIN_ID IS NULL";

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
    }
}
