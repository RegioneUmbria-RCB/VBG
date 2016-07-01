using System;
using System.Linq;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Collections.Generic;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.SIGePro.Data;
using SIGePro.Net;
using SIGePro.Net.Navigation;
using Init.SIGePro.Manager;
using Init.SIGePro.Exceptions.IstanzeAllegati;
using Init.SIGePro.Manager.Logic.GestioneOneri;

namespace Sigepro.net.Istanze.CalcoloOneri
{
    public partial class Istanze_CalcoloOneri_Derateizzazione : BasePage
    {
        #region Proprietà
        string m_NumeroDocumento = null;
        public string NumeroDocumento
        {
            get { return m_NumeroDocumento; }
            set { m_NumeroDocumento = value; }
        }
        private int m_CodiceIstanza;
        protected int CodiceIstanza
        {
            get { return m_CodiceIstanza; }
            set { m_CodiceIstanza = value; }
        }

        private int m_TipoCausale;
        protected int TipoCausale
        {
            get { return m_TipoCausale; }
            set { m_TipoCausale = value; }
        }

        private int m_CodiceRaggruppamento;
        protected int CodiceRaggruppamento
        {
            get { return m_CodiceRaggruppamento; }
            set { m_CodiceIstanza = value; }
        }

        private DateTime m_DataScadenza;
        protected DateTime DataScadenza
        {
            get { return m_DataScadenza; }
            set { m_DataScadenza = value; }
        }

        private DateTime m_Data;
        protected DateTime Data
        {
            get { return m_Data; }
            set { m_Data = value; }
        }
        #endregion

        protected void Page_Load(object sender, EventArgs e)
		{
            Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            m_CodiceIstanza = string.IsNullOrEmpty(Request.QueryString["codiceistanza"]) ? int.MinValue : Convert.ToInt32(Request.QueryString["codiceistanza"]);
            m_TipoCausale = string.IsNullOrEmpty(Request.QueryString["tipocausale"]) ? int.MinValue : Convert.ToInt32(Request.QueryString["tipocausale"]);
            m_CodiceRaggruppamento = string.IsNullOrEmpty(Request.QueryString["codiceraggruppamento"]) ? int.MinValue : Convert.ToInt32(Request.QueryString["codiceraggruppamento"]);

            if(!IsPostBack)
                BindGrid();
		}


		#region Scheda dettaglio
        protected void BindGrid()
        {
            gvLista.DataSource = GetListaRate();
            gvLista.DataBind();
        }

        protected DataSet GetListaRate()
        {
            DataSet ds = null;

            //Derateizzazione per causale
            if (CodiceRaggruppamento == int.MinValue)
            {
                IstanzeOneriMgr mgrio = new IstanzeOneriMgr(Database);
                ds = mgrio.GetListaOneri(IdComune, CodiceIstanza, TipoCausale);
            }
            //Derateizzazione per raggruppamento
            if (TipoCausale == int.MinValue)
            {
                RaggruppamentoCausaliOneriMgr mgrrco = new RaggruppamentoCausaliOneriMgr(Database);
                ds = mgrrco.GetListaOneri(IdComune, CodiceIstanza, CodiceRaggruppamento);
            }

            return ds;
        }

		protected void cmdOk_Click(object sender, EventArgs e)
		{
            if (gvLista.Rows.Count != 0)
            {
                //Derateizzazione per causale
                if (CodiceRaggruppamento == int.MinValue)
                {
                    DataSet ds = new IstanzeOneriMgr(Database).GetListaTestate(IdComune, CodiceIstanza, TipoCausale);
                    if ((ds.Tables[0] != null) && (ds.Tables[0].Rows.Count != 0))
                    {
                        foreach (DataRow dr in ds.Tables[0].Rows)
                            CalcolaDerateizzazione(TipoCausale, (dr["fk_idtestata"] != DBNull.Value ? Convert.ToInt32(dr["fk_idtestata"]) : int.MinValue));
                    }

                    NumerazioneRate();
                }

                //Derateizzazione per raggruppamento
                if (TipoCausale == int.MinValue)
                {
                    TipiCausaliOneriMgr mgrtco = new TipiCausaliOneriMgr(Database);
                    TipiCausaliOneri tco = new TipiCausaliOneri();
                    tco.FkRcoId = CodiceRaggruppamento;
                    tco.Idcomune = IdComune;

                    List<TipiCausaliOneri> list = mgrtco.GetList(tco);

                    foreach (TipiCausaliOneri elem in list)
                    {
                        NumeroDocumento = null;
                        TipoCausale = elem.CoId.Value;
                        DataSet ds = new IstanzeOneriMgr(Database).GetListaTestate(IdComune, CodiceIstanza, elem.CoId.Value);
                        if ((ds.Tables[0] != null) && (ds.Tables[0].Rows.Count != 0))
                        {
                            foreach (DataRow dr in ds.Tables[0].Rows)
                                CalcolaDerateizzazione(TipoCausale, (dr["fk_idtestata"] != DBNull.Value ? Convert.ToInt32(dr["fk_idtestata"]) : int.MinValue));
                        }

                        NumerazioneRate();
                    }
                }
            }

			base.CloseCurrentPage(); 
		}

        private void CalcolaDerateizzazione(int iTipoCausale, int idTestata)
        {
            DataSet ds = new IstanzeOneriMgr(Database).GetSommeDateListaOneri(IdComune, CodiceIstanza, iTipoCausale, idTestata);

            foreach (DataRow dr in ds.Tables[0].Rows)
            {
                UpdateIstanzeOneri((dr["prezzo"] != DBNull.Value ? Convert.ToDouble(dr["prezzo"]) : double.MinValue), (dr["prezzoistruttoria"] != DBNull.Value ? Convert.ToDouble(dr["prezzoistruttoria"]) : double.MinValue), dr["datascadenza"] != DBNull.Value ? Convert.ToDateTime(dr["datascadenza"]) : DateTime.MinValue, dr["data"] != DBNull.Value ? Convert.ToDateTime(dr["data"]) : DateTime.MinValue, idTestata);
            }
        }

        private void UpdateIstanzeOneri(double dImporto, double dImportoIstruttoria, DateTime DataScadenza, DateTime Data, int idTestata)
        {
            try
            {
                Database.BeginTransaction();

                //Cancello gli oneri con una precisa causale ed i record collegati
                IstanzeOneriMgr mgrio = new IstanzeOneriMgr(Database);
                mgrio.DeleteOnere(IdComune, CodiceIstanza, TipoCausale, idTestata);

                IstanzeOneri io = new IstanzeOneri();
                io.IDCOMUNE = IdComune;
                io.CODICEISTANZA = CodiceIstanza.ToString();
                io.FKIDTIPOCAUSALE = TipoCausale.ToString();

                //io.NUMERORATA = iNumeroRata.ToString();
                io.NR_DOCUMENTO = NumeroDocumento;
                io.PREZZO = dImporto;
                io.PREZZOISTRUTTORIA = dImportoIstruttoria;

                io.FLENTRATAUSCITA = "1";
                io.DATA = Data;
                io.DATASCADENZA = DataScadenza;

                //Verifico se l'onere era collegato ad un record nella tabella ISTANZECALCOLOCANONI_O (ISTANZEONERI_CANONI non viene gestita)
				var svc = new OneriService(Token, IdComune);

				var result = svc.Inserisci(new[] { io });

				if (idTestata != int.MinValue)
                {
                    List<IstanzeOneri> listRate = new List<IstanzeOneri>();
					listRate.Add(result.ElementAt(0));
                    new IstanzeCalcoloCanoniOMgr(Database).InserisciOneri(IdComune, idTestata, listRate);
                }

                Database.CommitTransaction();
            }
            catch (Exception)
            {
                Database.RollbackTransaction();
            }
        }

        private void NumerazioneRate()
        {
            //Numerazione delle rate per data scadenza crescente
            //Lista delle rate da rinumerare
            DataSet ds = null;
            IstanzeOneriMgr mgrio = new IstanzeOneriMgr(Database);
            ds = mgrio.GetListaOneri(IdComune, CodiceIstanza, TipoCausale);

            //Lista delle rate pagate
            IstanzeOneri ioNumeroRata = new IstanzeOneri();
            ioNumeroRata.IDCOMUNE = IdComune;
            ioNumeroRata.CODICEISTANZA = CodiceIstanza.ToString();
            ioNumeroRata.FKIDTIPOCAUSALE = TipoCausale.ToString();
            ioNumeroRata.OthersWhereClause.Add("DATAPAGAMENTO is not null");
            List<IstanzeOneri> list = mgrio.GetList(ioNumeroRata);
            int iNumeroRata = 1;
            bool bNumeroRata = true;
            foreach (DataRow dr in ds.Tables[0].Rows)
            {
                for (; ; )
                {
                    foreach (IstanzeOneri elem in list)
                    {
                        if (elem.NUMERORATA == iNumeroRata.ToString())
                        {
                            bNumeroRata = false;
                            break;
                        }
                    }

                    if (bNumeroRata)
                        break;
                    else
                    {
                        iNumeroRata++;
                        bNumeroRata = true;
                    }
                }

                IstanzeOneri ioUpd = mgrio.GetById(IdComune, Convert.ToString(dr["id"]));
                ioUpd.NUMERORATA = iNumeroRata.ToString();
                iNumeroRata++;
                mgrio.Update(ioUpd);
            }
        }


		protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
		{
			base.CloseCurrentPage(); 
		}
		#endregion

	}
}
