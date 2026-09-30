using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using System.Data;

namespace Init.SIGePro.Manager
{
	[DataObject(true)]
	public partial class CCITabella3Mgr
	{
		[DataObjectMethod(DataObjectMethodType.Select)]
		public static List<CCITabella3> Find(string token, int? idCalcolo)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			CCITabella3Mgr mgr = new CCITabella3Mgr(authInfo.CreateDatabase());

			CCITabella3 filtro = new CCITabella3();

			filtro.Idcomune = authInfo.IdComune;
			filtro.FkCcicId = idCalcolo;
            filtro.OthersTables.Add("CC_TABELLA3");
            filtro.OthersWhereClause.Add("CC_ITABELLA3.IDCOMUNE = CC_TABELLA3.IDCOMUNE");
            filtro.OthersWhereClause.Add("CC_ITABELLA3.FK_CCT3_ID = CC_TABELLA3.ID");
            filtro.OrderBy = "CC_TABELLA3.FK_CCDS_ID ASC, CC_ITABELLA3.ID ASC";
            filtro.UseForeign = PersonalLib2.Sql.useForeignEnum.Recoursive;

			return mgr.GetList(filtro);
		}

        public List<CCDettagliSuperficie> GetDettagliSuperficie(string idComune, int idCalcolo)
        {
            List<CCDettagliSuperficie> _listDettagliSuperficie = new List<CCDettagliSuperficie>();
            string sql = @"Select 
                FK_CCDS_ID
                From
                  CC_ITABELLA3, CC_TABELLA3
                Where
                  CC_ITABELLA3.IDCOMUNE = {0} and
                  CC_ITABELLA3.FK_CCIC_ID = {1} and
                  CC_TABELLA3.IDCOMUNE = CC_ITABELLA3.IDCOMUNE and
                  CC_TABELLA3.ID = CC_ITABELLA3.FK_CCT3_ID
                Group By
                  FK_CCDS_ID";

            sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
                                        db.Specifics.QueryParameterName("IDCALCOLO"));

            bool closeCnn = db.Connection.State == ConnectionState.Closed;

			List<int> listaId = new List<int>();

            try
            {
                using (IDbCommand cmd = db.CreateCommand())
                {
                    if (closeCnn)
                        db.Connection.Open();

                    cmd.CommandText = sql;

                    cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
                    cmd.Parameters.Add(db.CreateParameter("IDCALCOLO", idCalcolo));

                    IDataReader _dr = cmd.ExecuteReader();

                    while (_dr.Read())
                    {
						//if (_dr["FK_CCDS_ID"] == DBNull.Value)
						//    _listDettagliSuperficie.Add(new CCDettagliSuperficie());    
						//else
						//    _listDettagliSuperficie.Add(new CCDettagliSuperficieMgr(db).GetById(idComune,Convert.ToInt32(_dr["FK_CCDS_ID"].ToString())));
                    
						listaId.Add( _dr["FK_CCDS_ID"] == DBNull.Value ? -1 : Convert.ToInt32(_dr["FK_CCDS_ID"]) );
					}
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }

			for (int i = 0; i < listaId.Count; i++)
			{
				if( listaId[i] == -1 )
					_listDettagliSuperficie.Add(new CCDettagliSuperficie());
				else
					_listDettagliSuperficie.Add(new CCDettagliSuperficieMgr(db).GetById(idComune, listaId[i] ) );
			}

			return _listDettagliSuperficie;
        }

        public bool HaTipiSuperficie(string idComune, int idCalcolo)
        { 
            string sql = @"Select Count(*) From CC_ITABELLA3, CC_TABELLA3 
                            Where 
                                CC_TABELLA3.IdComune = CC_ITABELLA3.IdComune and 
                                CC_TABELLA3.Id = CC_ITABELLA3.FK_CCT3_ID and 
                                CC_ITABELLA3.IdComune = {0} and
                                CC_ITABELLA3.FK_CCIC_ID = {1} and
                                CC_TABELLA3.FK_CCDS_ID is not null
                            ";

            sql = string.Format(sql, db.Specifics.QueryParameterName("IdComune"), db.Specifics.QueryParameterName("IdCalcolo"));

            bool closeCnn = db.Connection.State == ConnectionState.Closed;

            try
            {
                using (IDbCommand cmd = db.CreateCommand())
                {
                    if (closeCnn)
                        db.Connection.Open();

                    cmd.CommandText = sql;

                    cmd.Parameters.Add(db.CreateParameter("IDCOMUNE", idComune));
                    cmd.Parameters.Add(db.CreateParameter("IDCALCOLO", idCalcolo));

                    object ret = cmd.ExecuteScalar();

                    if (ret == null || ret == DBNull.Value || Convert.ToInt32(ret) == 0)
                        return false;

                    return true;
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }
        }
	}
}
