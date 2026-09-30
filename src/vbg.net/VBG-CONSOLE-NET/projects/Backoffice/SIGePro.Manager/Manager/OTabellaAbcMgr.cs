using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.Data;
using Init.SIGePro.Validator;

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
            return (OTabellaAbc)db.GetClass(cls);
        }

        public void Delete(OTabellaAbc cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            //TODO: query
            bool internalOpen = false;
            string cmdText = "DELETE FROM " +
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

            foreach (string owc in cls.OthersWhereClause)
            {
                cmdText += " AND " + owc;
            }


            if (db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                db.Connection.Open();
            }

            using (IDbCommand cmd = db.CreateCommand(cmdText))
            {
                cmd.ExecuteNonQuery();
            }

            if ((db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                db.Connection.Close();
            }
        }

        public void DeleteSingleRow(OTabellaAbc cls)
        {
            //TODO: query
            bool internalOpen = false;
            string cmdText = "DELETE FROM " +
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

            if (db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                db.Connection.Open();
            }

            using (IDbCommand cmd = db.CreateCommand(cmdText))
            {
                cmd.ExecuteNonQuery();
            }

            if ((db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                db.Connection.Close();
            }
        }

        private OTabellaAbc Insert(OTabellaAbc cls)
        {
            throw new NotImplementedException("Il metodo OTabellaAbcMgr.Insert non è implementabile. Utilizzare il metodo OTabellaAbcMgr.Save");
        }

        public OTabellaAbc Save(OTabellaAbc cls)
        {
            cls = DataIntegrations(cls);

            if (Update(cls) == 0)
            {
                Validate(cls, AmbitoValidazione.Insert);
                db.Insert(cls);
                cls = (OTabellaAbc)ChildDataIntegrations(cls);

                ChildInsert(cls);
            }

            return cls;
        }

        private int Update(OTabellaAbc cls)
        {
            int retVal = 0;
            bool internalOpen = false;

            //TODO: query
            string cmdText = "UPDATE " +
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

            if (db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                db.Connection.Open();
            }

            using (IDbCommand cmd = db.CreateCommand(cmdText))
            {
                retVal = cmd.ExecuteNonQuery();
            }

            if ((db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                db.Connection.Close();
            }

            return retVal;
        }
    }
}
