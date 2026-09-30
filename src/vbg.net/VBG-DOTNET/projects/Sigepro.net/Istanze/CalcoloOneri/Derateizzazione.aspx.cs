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
using log4net;

namespace Sigepro.net.Istanze.CalcoloOneri
{
    public partial class Istanze_CalcoloOneri_Derateizzazione : BasePage
    {
        #region Proprietà
        // public string NumeroDocumento { get; set; } = null;
        protected int CodiceIstanza => Convert.ToInt32(Request.QueryString["codiceistanza"]);
        protected int TipoCausale => string.IsNullOrEmpty(Request.QueryString["tipocausale"]) ?
                                        int.MinValue :
                                        Convert.ToInt32(Request.QueryString["tipocausale"]);

        //private int m_CodiceRaggruppamento;
        protected int CodiceRaggruppamento => string.IsNullOrEmpty(Request.QueryString["codiceraggruppamento"]) ?
                                                    int.MinValue :
                                                    Convert.ToInt32(Request.QueryString["codiceraggruppamento"]);
        #endregion

        ILog _log = LogManager.GetLogger(typeof(Istanze_CalcoloOneri_Derateizzazione));

        protected void Page_Load(object sender, EventArgs e)
        {
            Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            if (!IsPostBack)
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
            //Derateizzazione per causale
            if (TipoCausale != int.MinValue)
            {
                IstanzeOneriMgr mgrio = new IstanzeOneriMgr(Database, AuthenticationInfo);
                return mgrio.GetListaOneri(IdComune, CodiceIstanza, TipoCausale);
            }
            //Derateizzazione per raggruppamento
            if (CodiceRaggruppamento != int.MinValue)
            {
                RaggruppamentoCausaliOneriMgr mgrrco = new RaggruppamentoCausaliOneriMgr(Database);
                return mgrrco.GetListaOneri(IdComune, CodiceIstanza, CodiceRaggruppamento);
            }

            return null;
        }

        protected void cmdOk_Click(object sender, EventArgs e)
        {
            if (gvLista.Rows.Count != 0)
            {
                //Derateizzazione per causale

                if (TipoCausale != int.MinValue)
                {
                    DataSet ds = new IstanzeOneriMgr(Database, AuthenticationInfo).GetListaTestate(IdComune, CodiceIstanza, TipoCausale);
                    if ((ds.Tables[0] != null) && (ds.Tables[0].Rows.Count != 0))
                    {
                        foreach (DataRow dr in ds.Tables[0].Rows)
                        {
                            int idTestata = (dr["fk_idtestata"] != DBNull.Value ? Convert.ToInt32(dr["fk_idtestata"]) : int.MinValue);
                            string numeroDocumento = null;
                            CalcolaDerateizzazione(TipoCausale, idTestata, numeroDocumento);
                        }
                    }

                    NumerazioneRate(this.TipoCausale);
                }

                //Derateizzazione per raggruppamento
                if (CodiceRaggruppamento != int.MinValue)
                {
                    var list = new TipiCausaliOneriMgr(Database).GetList(new TipiCausaliOneri
                    {
                        FkRcoId = CodiceRaggruppamento,
                        Idcomune = IdComune
                    });

                    foreach (TipiCausaliOneri elem in list)
                    {
                        DataSet ds = new IstanzeOneriMgr(Database, AuthenticationInfo).GetListaTestate(IdComune, CodiceIstanza, elem.CoId.Value);
                        if ((ds.Tables[0] == null) || (ds.Tables[0].Rows.Count == 0))
                        {
                            continue;
                        }


                        var tipoCausale = elem.CoId.Value;
                        foreach (DataRow dr in ds.Tables[0].Rows)
                        {

                            int idTestata = (dr["fk_idtestata"] != DBNull.Value ? Convert.ToInt32(dr["fk_idtestata"]) : int.MinValue);
                            string numeroDocumento = null;
                            CalcolaDerateizzazione(tipoCausale, idTestata, numeroDocumento);
                        }

                        NumerazioneRate(tipoCausale);
                    }
                }
            }

            base.CloseCurrentPage();
        }

        private void CalcolaDerateizzazione(int iTipoCausale, int idTestata, string numeroDocumento)
        {
            var mgrio = new IstanzeOneriMgr(Database, AuthenticationInfo);
            mgrio.CalcolaDerateizzazione(CodiceIstanza, iTipoCausale, idTestata, numeroDocumento);
        }


        private void NumerazioneRate(int tipoCausale)
        {
            //Numerazione delle rate per data scadenza crescente
            //Lista delle rate da rinumerare
            //DataSet ds = null;
            IstanzeOneriMgr mgrio = new IstanzeOneriMgr(Database, AuthenticationInfo);
            //ds = mgrio.GetListaOneri(IdComune, CodiceIstanza, TipoCausale);
            var idOneriNonPagati = mgrio.GetListaIdNonPagatiDaTipoCausale(IdComune, CodiceIstanza, tipoCausale);

            //Lista delle rate pagate
            var filtroOneriPagati = new IstanzeOneri();
            filtroOneriPagati.IDCOMUNE = IdComune;
            filtroOneriPagati.CODICEISTANZA = CodiceIstanza.ToString();
            filtroOneriPagati.FKIDTIPOCAUSALE = tipoCausale.ToString();
            filtroOneriPagati.OthersWhereClause.Add("DATAPAGAMENTO is not null");
            var oneriPagati = mgrio.GetList(filtroOneriPagati);
            int numeroRata = 1;
            // bool numeroRataGiaUsato = true;
            foreach (var id in idOneriNonPagati)
            {
                while (oneriPagati.Where(x => x.NUMERORATA == numeroRata.ToString()).Any())
                {
                    numeroRata++;
                }
                /* ERA:
                for (; ; ) // AAAAHHHHH!!!! Dovrebbe cercare il prossimo numero rata disponibile per ricalcolarlo 
                {
                    foreach (IstanzeOneri onerePagato in oneriPagati)
                    {
                        if (onerePagato.NUMERORATA == numeroRata.ToString())
                        {
                            numeroRataGiaUsato = false;
                            break;
                        }
                    }

                    if (numeroRataGiaUsato)
                    {
                        break;
                    }
                    else
                    {
                        numeroRata++;
                        numeroRataGiaUsato = true;
                    }
                }
                */

                mgrio.UpdateNumeroRata(IdComune, id, numeroRata);

                numeroRata++;
            }
        }


        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }
        #endregion

    }
}
