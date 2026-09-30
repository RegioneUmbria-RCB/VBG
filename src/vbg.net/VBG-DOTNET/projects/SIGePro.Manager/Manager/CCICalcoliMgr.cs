using Init.SIGePro.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public partial class CCICalcoliMgr
    {
        #region Righe in tabella1
        public List<CCITabella1> VerificaEsistenzaRigheInTabella1(string idComune, string software, int idCalcolo, int codiceIstanza)
        {
            var righeInTabella1 = this.RigheInTabella1(idComune, idCalcolo);

            if (righeInTabella1.Count == 0)
                righeInTabella1 = this.CreaRigheTabella1(idComune, software, idCalcolo, codiceIstanza);

            return righeInTabella1;

        }

        protected List<CCITabella1> RigheInTabella1(string idComune, int idCalcolo)
        {
            var t1 = new CCITabella1();
            t1.Idcomune = idComune;
            t1.FkCcicId = idCalcolo;

            return new CCITabella1Mgr(this.db).GetList(t1);
        }

        private List<CCITabella1> CreaRigheTabella1(string idComune, string software, int idCalcolo, int codiceIstanza)
        {
            var t1Mgr = new CCITabella1Mgr(this.db);

            var filtro = new CCClassiSuperfici();
            filtro.Idcomune = idComune;
            filtro.Software = software;
            filtro.OrderBy = "DA asc";

            var classiSuperfici = new CCClassiSuperficiMgr(this.db).GetList(filtro);

            var ret = new List<CCITabella1>();

            foreach (var cs in classiSuperfici)
            {
                var t1 = new CCITabella1();
                t1.Idcomune = idComune;
                t1.FkCcicId = idCalcolo;
                t1.FkCccsId = cs.Id;
                t1.Incremento = cs.Incremento;
                t1.Codiceistanza = codiceIstanza;
                t1.Alloggi = 0;
                t1.Su = 0.0m;
                t1.RapportoSu = 0.0m;
                t1.Incrementoxclassi = 0.0m;

                ret.Add(t1Mgr.Insert(t1));
            }
            ;

            return ret;
        }
        #endregion

        #region Righe in tabella2
        public List<CCITabella2> VerificaEsistenzaRigheInTabella2(string idComune, string software, int idCalcolo, int codiceIstanza)
        {
            var righeInTabella2 = this.RigheInTabella2(idComune, idCalcolo);

            if (righeInTabella2.Count == 0)
                righeInTabella2 = this.CreaRigheTabella2(idComune, software, idCalcolo, codiceIstanza);

            return righeInTabella2;

        }

        protected List<CCITabella2> RigheInTabella2(string idComune, int idCalcolo)
        {
            var t1 = new CCITabella2();
            t1.Idcomune = idComune;
            t1.FkCcicId = idCalcolo;

            return new CCITabella2Mgr(this.db).GetList(t1);
        }

        protected List<CCITabella2> CreaRigheTabella2(string idComune, string software, int idCalcolo, int codiceIstanza)
        {
            var t2Mgr = new CCITabella2Mgr(this.db);

            var filtro = new CCDettagliSuperficie();
            filtro.Idcomune = idComune;
            filtro.Software = software;
            filtro.FkCcTsId = new CCConfigurazioneMgr(this.db).GetById(idComune, software).Tab2FkTsId;

            var lst = new CCDettagliSuperficieMgr(this.db).GetList(filtro);

            var ret = new List<CCITabella2>();

            lst.ForEach(delegate (CCDettagliSuperficie ds)
            {
                var t2 = new CCITabella2();
                t2.Idcomune = idComune;
                t2.FkCcicId = idCalcolo;
                t2.FkCcdsId = ds.Id;
                t2.Codiceistanza = codiceIstanza;
                t2.Superficie = 0.0m;

                ret.Add(t2Mgr.Insert(t2));
            });


            return ret;
        }
        #endregion

        #region Righe in tabella3
        public List<CCITabella3> VerificaEsistenzaRigheInTabella3(string idComune, string software, int idCalcolo, int codiceIstanza)
        {
            var righeInTabella3 = this.RigheInTabella3(idComune, idCalcolo);

            if (righeInTabella3.Count == 0)
                righeInTabella3 = this.CreaRigheTabella3(idComune, software, idCalcolo, codiceIstanza);

            return righeInTabella3;

        }

        protected List<CCITabella3> RigheInTabella3(string idComune, int idCalcolo)
        {
            var t1 = new CCITabella3();
            t1.Idcomune = idComune;
            t1.FkCcicId = idCalcolo;

            return new CCITabella3Mgr(this.db).GetList(t1);
        }

        protected List<CCITabella3> CreaRigheTabella3(string idComune, string software, int idCalcolo, int codiceIstanza)
        {
            var t1Mgr = new CCITabella3Mgr(this.db);

            var filtro = new CCTabella3();
            filtro.Idcomune = idComune;
            filtro.Software = software;
            filtro.OrderBy = "rapporto_su_snr_da asc";

            var lst = new CCTabella3Mgr(this.db).GetList(filtro);

            var ret = new List<CCITabella3>();

            lst.ForEach(delegate (CCTabella3 t3)
            {
                var it3 = new CCITabella3();
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
            var righeInTabella4 = this.RigheInTabella4(idComune, idCalcolo);

            if (righeInTabella4.Count == 0)
                righeInTabella4 = this.CreaRigheTabella4(idComune, software, idCalcolo, codiceIstanza);

            return righeInTabella4;

        }

        protected List<CCITabella4> RigheInTabella4(string idComune, int idCalcolo)
        {
            var t1 = new CCITabella4();
            t1.Idcomune = idComune;
            t1.FkCcicId = idCalcolo;

            return new CCITabella4Mgr(this.db).GetList(t1);
        }

        protected List<CCITabella4> CreaRigheTabella4(string idComune, string software, int idCalcolo, int codiceIstanza)
        {
            var t4Mgr = new CCITabella4Mgr(this.db);

            var filtro = new CCTabellaCaratterist();
            filtro.Idcomune = idComune;
            filtro.Software = software;

            var lst = new CCTabellaCaratteristMgr(this.db).GetList(filtro);

            var ret = new List<CCITabella4>();

            lst.ForEach(delegate (CCTabellaCaratterist tblCar)
            {
                var it4 = new CCITabella4();
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


        // TODO: Modificare questa parte per prendere in considerazione gli interventi di dettaglio e destinazioni d'uso di dettaglio
        public decimal GetCostoAlMetroQuadro(string idComune, int idCalcolo)
        {
            // Estraggo il coefficiente in base all'id calcolo, se non riesco a estrarlo lancio un'eccezione
            FormattableString sql = $@"select 
                              CC_ICALCOLOTOT.fk_ccvc_id
                            FROM 
							  CC_ICALCOLOTOT,
							  CC_ICALCOLO_TCONTRIBUTO
							WHERE
							  CC_ICALCOLOTOT.IdComune = CC_ICALCOLO_TCONTRIBUTO.IdComune AND
							  CC_ICALCOLOTOT.Id       = CC_ICALCOLO_TCONTRIBUTO.FK_CCICT_ID AND
							  CC_ICALCOLO_TCONTRIBUTO.IdComune = {idComune} AND							  						  
							  CC_ICALCOLO_TCONTRIBUTO.FK_CCIC_ID = {idCalcolo}";

            var idCoefficiente = this.db.ExecuteScalar(sql, -1);

            var costoMq = this.GetCostoAlMetroQuadroByIdCoefficiente(idComune, idCoefficiente);

            if (!costoMq.HasValue)
            {
                throw new InvalidOperationException("Non è stato possibile determinare il costo al metro quadro per l'id calcolo " + idCalcolo.ToString());
            }

            var costoMqSpecifico = this.GetCostoAlMetroQuadroByCalcoloInterventoDestinazione(idComune, idCalcolo);

            return costoMqSpecifico.HasValue ? costoMqSpecifico.Value : costoMq.Value;

        }

        private decimal? GetCostoAlMetroQuadroByCalcoloInterventoDestinazione(string idComune, int idCalcolo)
        {
            FormattableString sql = $@"SELECT 
	CC_VALIDITACOEFF_DETTAGLIO.COSTOMQ 
FROM 
	CC_ICALCOLO_TCONTRIBUTO
	
	INNER JOIN CC_ICALCOLOTOT ON 
		CC_ICALCOLOTOT.IdComune = CC_ICALCOLO_TCONTRIBUTO.IdComune AND
		CC_ICALCOLOTOT.Id       = CC_ICALCOLO_TCONTRIBUTO.FK_CCICT_ID
		
	INNER JOIN CC_VALIDITACOEFF_DETTAGLIO ON 
		CC_VALIDITACOEFF_DETTAGLIO.idcomune = CC_ICALCOLOTOT.IdComune AND
		CC_VALIDITACOEFF_DETTAGLIO.fk_coefficente_id = CC_ICALCOLOTOT.FK_CCVC_ID AND
		CC_VALIDITACOEFF_DETTAGLIO.fk_intervento_id = CC_ICALCOLOTOT.fk_intervento_dett_id AND
		CC_VALIDITACOEFF_DETTAGLIO.fk_destinazione_id = CC_ICALCOLOTOT.fk_destinazione_dett_id
			
WHERE
	CC_ICALCOLO_TCONTRIBUTO.IdComune = {idComune} AND							  						  
	CC_ICALCOLO_TCONTRIBUTO.FK_CCIC_ID = {idCalcolo}";

            var costoMq = this.db.ExecuteScalar(sql, -1.0m);

            // TODO: Implementare questa parte per prendere in considerazione gli interventi di dettaglio e destinazioni d'uso di dettaglio
            return costoMq > 0.0m ? (decimal?)costoMq : null;
        }

        private decimal? GetCostoAlMetroQuadroByIdCoefficiente(string idComune, int idCoefficiente)
        {
            FormattableString sql = $@"SELECT 
							  CC_VALIDITACOEFFICIENTI.costomq 
							FROM 
							  CC_VALIDITACOEFFICIENTI
							WHERE
							  CC_VALIDITACOEFFICIENTI.IdComune = {idComune} AND							  						  
							  CC_VALIDITACOEFFICIENTI.id = {idCoefficiente}";

            var val = this.db.ExecuteScalar(sql, -1.0m);

            return val < 0.0m ? (decimal?)null : val;
        }

        private void EffettuaCancellazioneACascata(CCICalcoli cls)
        {
            var a = new CCICalcoliDettaglioT();
            a.Idcomune = cls.Idcomune;
            a.FkCcicId = cls.Id;

            var lCalcoloT = new CCICalcoliDettaglioTMgr(this.db).GetList(a);
            foreach (var calcoloT in lCalcoloT)
            {
                var mgr = new CCICalcoliDettaglioTMgr(this.db);
                mgr.Delete(calcoloT);
            }

            var b = new CCITabella1();
            b.Idcomune = cls.Idcomune;
            b.FkCcicId = cls.Id;

            var lTabella1 = new CCITabella1Mgr(this.db).GetList(b);
            foreach (var tabella1 in lTabella1)
            {
                var mgr = new CCITabella1Mgr(this.db);
                mgr.Delete(tabella1);
            }

            var c = new CCITabella2();
            c.Idcomune = cls.Idcomune;
            c.FkCcicId = cls.Id;

            var lTabella2 = new CCITabella2Mgr(this.db).GetList(c);
            foreach (var tabella2 in lTabella2)
            {
                var mgr = new CCITabella2Mgr(this.db);
                mgr.Delete(tabella2);
            }

            var d = new CCITabella3();
            d.Idcomune = cls.Idcomune;
            d.FkCcicId = cls.Id;

            var lTabella3 = new CCITabella3Mgr(this.db).GetList(d);
            foreach (var tabella3 in lTabella3)
            {
                var mgr = new CCITabella3Mgr(this.db);
                mgr.Delete(tabella3);
            }

            var e = new CCITabella4();
            e.Idcomune = cls.Idcomune;
            e.FkCcicId = cls.Id;

            var lTabella4 = new CCITabella4Mgr(this.db).GetList(e);
            foreach (var tabella4 in lTabella4)
            {
                var mgr = new CCITabella4Mgr(this.db);
                mgr.Delete(tabella4);
            }
        }
    }
}
