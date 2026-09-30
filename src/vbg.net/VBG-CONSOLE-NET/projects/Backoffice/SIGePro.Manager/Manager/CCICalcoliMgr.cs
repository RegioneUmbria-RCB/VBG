using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.Data;

namespace Init.SIGePro.Manager
{
	public partial class CCICalcoliMgr
	{


		private CCICalcoli ChildInsert(CCICalcoli cls)
		{
			/*if (cls.Tabella1.Count == 0)
			{
				Istanze ist = new IstanzeMgr(db).GetById(cls.Codiceistanza.ToString(), cls.Idcomune);

				CCClassiSuperfici filtro = new CCClassiSuperfici();
				filtro.Idcomune = ist.IDCOMUNE;
				filtro.Software = ist.SOFTWARE;

				List<CCClassiSuperfici> lst = new CCClassiSuperficiMgr(db).GetList(filtro);

				lst.ForEach(delegate(CCClassiSuperfici cs)
				{
					CCITabella1 t1 = new CCITabella1();
					t1.Idcomune = cls.Idcomune;
					t1.FkCcicId = cls.Id;
					t1.FkCccsId = cs.Id;
					t1.Incremento = cs.Incremento;

					cls.Tabella1.Add(t1);
				});
			}

			CCITabella1Mgr t1Mgr = new CCITabella1Mgr(db);

			cls.Tabella1.ForEach(delegate(CCITabella1 t1)
			{
				t1Mgr.Insert(t1);
			});
			*/
			return cls;
		}

		#region Righe in tabella1
		public List<CCITabella1> VerificaEsistenzaRigheInTabella1(string idComune, string software, int idCalcolo , int codiceIstanza)
		{
			List<CCITabella1> righeInTabella1 = RigheInTabella1(idComune, idCalcolo);

			if (righeInTabella1.Count == 0)
				righeInTabella1 = CreaRigheTabella1(idComune, software, idCalcolo, codiceIstanza);

			return righeInTabella1;

		}
		
		protected List<CCITabella1> RigheInTabella1(string idComune, int idCalcolo)
		{
			CCITabella1 t1 = new CCITabella1();
			t1.Idcomune = idComune;
			t1.FkCcicId = idCalcolo;

			return new CCITabella1Mgr(db).GetList(t1);
		}

		protected List<CCITabella1> CreaRigheTabella1(string idComune, string software, int idCalcolo, int codiceIstanza)
		{
			CCITabella1Mgr t1Mgr = new CCITabella1Mgr(db);

			CCClassiSuperfici filtro = new CCClassiSuperfici();
			filtro.Idcomune = idComune;
			filtro.Software = software;
			filtro.OrderBy = "DA asc";

			List<CCClassiSuperfici> lst = new CCClassiSuperficiMgr(db).GetList(filtro);

			List<CCITabella1> ret = new List<CCITabella1>();

			lst.ForEach(delegate(CCClassiSuperfici cs)
			{
				CCITabella1 t1 = new CCITabella1();
				t1.Idcomune = idComune;
				t1.FkCcicId = idCalcolo;
				t1.FkCccsId = cs.Id;
				t1.Incremento = cs.Incremento;
				t1.Codiceistanza = codiceIstanza;
				t1.Alloggi = 0;
				t1.Su = 0.0f;
				t1.RapportoSu = 0.0f;
				t1.Incrementoxclassi = 0.0f;

				ret.Add( t1Mgr.Insert( t1 ) );
			});


			return ret;
		}
		#endregion

		#region Righe in tabella2
		public List<CCITabella2> VerificaEsistenzaRigheInTabella2(string idComune, string software, int idCalcolo, int codiceIstanza)
		{
			List<CCITabella2> righeInTabella2 = RigheInTabella2(idComune, idCalcolo);

			if (righeInTabella2.Count == 0)
				righeInTabella2 = CreaRigheTabella2(idComune, software, idCalcolo , codiceIstanza);

			return righeInTabella2;

		}

		protected List<CCITabella2> RigheInTabella2(string idComune, int idCalcolo)
		{
			CCITabella2 t1 = new CCITabella2();
			t1.Idcomune = idComune;
			t1.FkCcicId = idCalcolo;

			return new CCITabella2Mgr(db).GetList(t1);
		}

		protected List<CCITabella2> CreaRigheTabella2(string idComune, string software, int idCalcolo, int codiceIstanza)
		{
			CCITabella2Mgr t2Mgr = new CCITabella2Mgr(db);

			CCDettagliSuperficie filtro = new CCDettagliSuperficie();
			filtro.Idcomune = idComune;
			filtro.Software = software;
			filtro.FkCcTsId = new CCConfigurazioneMgr(db).GetById(idComune, software).Tab2FkTsId;

			List<CCDettagliSuperficie> lst = new CCDettagliSuperficieMgr(db).GetList(filtro);

			List<CCITabella2> ret = new List<CCITabella2>();

			lst.ForEach(delegate(CCDettagliSuperficie ds)
			{
				CCITabella2 t2 = new CCITabella2();
				t2.Idcomune = idComune;
				t2.FkCcicId = idCalcolo;
				t2.FkCcdsId = ds.Id;
				t2.Codiceistanza = codiceIstanza;
				t2.Superficie = 0.0f;
				
				ret.Add(t2Mgr.Insert(t2));
			});


			return ret;
		}
		#endregion

		#region Righe in tabella3
		public List<CCITabella3> VerificaEsistenzaRigheInTabella3(string idComune, string software, int idCalcolo, int codiceIstanza)
		{
			List<CCITabella3> righeInTabella3 = RigheInTabella3(idComune, idCalcolo);

			if (righeInTabella3.Count == 0)
				righeInTabella3 = CreaRigheTabella3(idComune, software, idCalcolo,codiceIstanza);

			return righeInTabella3;

		}

		protected List<CCITabella3> RigheInTabella3(string idComune, int idCalcolo)
		{
			CCITabella3 t1 = new CCITabella3();
			t1.Idcomune = idComune;
			t1.FkCcicId = idCalcolo;

			return new CCITabella3Mgr(db).GetList(t1);
		}

		protected List<CCITabella3> CreaRigheTabella3(string idComune, string software, int idCalcolo, int codiceIstanza)
		{
			CCITabella3Mgr t1Mgr = new CCITabella3Mgr(db);

			CCTabella3 filtro = new CCTabella3();
			filtro.Idcomune = idComune;
			filtro.Software = software;
			filtro.OrderBy = "rapporto_su_snr_da asc";

			List<CCTabella3> lst = new CCTabella3Mgr(db).GetList(filtro);

			List<CCITabella3> ret = new List<CCITabella3>();

			lst.ForEach(delegate(CCTabella3 t3)
			{
				CCITabella3 it3 = new CCITabella3();
				it3.Idcomune = idComune;
				it3.FkCcicId = idCalcolo;
				it3.FkCct3Id = t3.Id;
				it3.Incremento = t3.Perc;
				it3.Codiceistanza = codiceIstanza;
				it3.Ipotesichericorre = 0;

				ret.Add(t1Mgr.Insert(it3));
			});


			return ret;
		}
		#endregion

		#region Righe in Tabella4
		public List<CCITabella4> VerificaEsistenzaRigheInTabella4(string idComune, string software, int idCalcolo, int codiceIstanza)
		{
			List<CCITabella4> righeInTabella4 = RigheInTabella4(idComune, idCalcolo);

			if (righeInTabella4.Count == 0)
				righeInTabella4 = CreaRigheTabella4(idComune, software, idCalcolo , codiceIstanza);

			return righeInTabella4;

		}

		protected List<CCITabella4> RigheInTabella4(string idComune, int idCalcolo)
		{
			CCITabella4 t1 = new CCITabella4();
			t1.Idcomune = idComune;
			t1.FkCcicId = idCalcolo;

			return new CCITabella4Mgr(db).GetList(t1);
		}

		protected List<CCITabella4> CreaRigheTabella4(string idComune, string software, int idCalcolo , int codiceIstanza)
		{
			CCITabella4Mgr t4Mgr = new CCITabella4Mgr(db);

			CCTabellaCaratterist filtro = new CCTabellaCaratterist();
			filtro.Idcomune = idComune;
			filtro.Software = software;

			List<CCTabellaCaratterist> lst = new CCTabellaCaratteristMgr(db).GetList(filtro);

			List<CCITabella4> ret = new List<CCITabella4>();

			lst.ForEach(delegate(CCTabellaCaratterist tblCar)
			{
				CCITabella4 it4 = new CCITabella4();
				it4.Idcomune = idComune;
				it4.FkCcicId = idCalcolo;
				it4.FkCctcId = tblCar.Id;
				it4.Incremento = tblCar.Perc;
				it4.Codiceistanza = codiceIstanza;
				it4.Selezionata = 0;

				ret.Add(t4Mgr.Insert(it4));
			});

			return ret;
		}
		#endregion

		public float GetCostoAlMetroQuadro(string idComune, int idCalcolo)
		{
			string sql = @"SELECT 
							  CC_VALIDITACOEFFICIENTI.costomq 
							FROM 
							  CC_VALIDITACOEFFICIENTI,
							  CC_ICALCOLOTOT,
							  CC_ICALCOLO_TCONTRIBUTO
							WHERE
							  CC_ICALCOLOTOT.IdComune = CC_ICALCOLO_TCONTRIBUTO.IdComune AND
							  CC_ICALCOLOTOT.Id       = CC_ICALCOLO_TCONTRIBUTO.FK_CCICT_ID AND
							  CC_VALIDITACOEFFICIENTI.IdComune    = CC_ICALCOLOTOT.IdComune AND
							  CC_VALIDITACOEFFICIENTI.id  = CC_ICALCOLOTOT.fk_ccvc_id AND
							  CC_ICALCOLO_TCONTRIBUTO.IdComune = {0} AND							  						  
							  CC_ICALCOLO_TCONTRIBUTO.FK_CCIC_ID = {1}";

			sql = String.Format(sql, db.Specifics.QueryParameterName("IDCOMUNE"),
										db.Specifics.QueryParameterName("IDCALCOLO"));

			bool closeCnn = db.Connection.State == ConnectionState.Closed;


			try
			{
				using (IDbCommand cmd = db.CreateCommand())
				{
					if (closeCnn)
						db.Connection.Open();

					cmd.CommandText = sql;

					cmd.Parameters.Add( db.CreateParameter("IDCOMUNE" , idComune ) );
					cmd.Parameters.Add(db.CreateParameter("IDCALCOLO", idCalcolo));

					object ret = cmd.ExecuteScalar();

					if (ret == null || ret == DBNull.Value)
						throw new InvalidOperationException("Non è stato possibile determinare il costo al metro quadro per l'id calcolo " + idCalcolo.ToString() );

					return Convert.ToSingle(ret);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

        private void EffettuaCancellazioneACascata(CCICalcoli cls)
        {
            CCICalcoliDettaglioT a = new CCICalcoliDettaglioT();
            a.Idcomune = cls.Idcomune;
            a.FkCcicId = cls.Id;

            List<CCICalcoliDettaglioT> lCalcoloT = new CCICalcoliDettaglioTMgr(db).GetList(a);
            foreach (CCICalcoliDettaglioT calcoloT in lCalcoloT)
            {
                CCICalcoliDettaglioTMgr mgr = new CCICalcoliDettaglioTMgr(db);
                mgr.Delete(calcoloT);
            }

            CCITabella1 b = new CCITabella1();
            b.Idcomune = cls.Idcomune;
            b.FkCcicId = cls.Id;

            List<CCITabella1> lTabella1 = new CCITabella1Mgr(db).GetList(b);
            foreach (CCITabella1 tabella1 in lTabella1)
            {
                CCITabella1Mgr mgr = new CCITabella1Mgr(db);
                mgr.Delete(tabella1);
            }

            CCITabella2 c = new CCITabella2();
            c.Idcomune = cls.Idcomune;
            c.FkCcicId = cls.Id;

            List<CCITabella2> lTabella2 = new CCITabella2Mgr(db).GetList(c);
            foreach (CCITabella2 tabella2 in lTabella2)
            {
                CCITabella2Mgr mgr = new CCITabella2Mgr(db);
                mgr.Delete(tabella2);
            }

            CCITabella3 d = new CCITabella3();
            d.Idcomune = cls.Idcomune;
            d.FkCcicId = cls.Id;

            List<CCITabella3> lTabella3 = new CCITabella3Mgr(db).GetList(d);
            foreach (CCITabella3 tabella3 in lTabella3)
            {
                CCITabella3Mgr mgr = new CCITabella3Mgr(db);
                mgr.Delete(tabella3);
            }

            CCITabella4 e = new CCITabella4();
            e.Idcomune = cls.Idcomune;
            e.FkCcicId = cls.Id;

            List<CCITabella4> lTabella4 = new CCITabella4Mgr(db).GetList(e);
            foreach (CCITabella4 tabella4 in lTabella4)
            {
                CCITabella4Mgr mgr = new CCITabella4Mgr(db);
                mgr.Delete(tabella4);
            }
        }
	}
}
