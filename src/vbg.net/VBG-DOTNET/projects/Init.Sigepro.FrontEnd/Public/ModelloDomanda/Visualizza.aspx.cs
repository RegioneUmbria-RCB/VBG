using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Ninject;
using System;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Public.ModelloDomanda
{
    public partial class Visualizza : BasePage
    {
        [Inject]
        protected EndoprocedimentiService _endoprocedimentiService { get; set; }
        [Inject]
        protected FacSimileDomandaService _generazioneRiepilogoDomandaService { get; set; }
        [Inject]
        public IInterventiRepository _interventiRepository { get; set; }

        private int IdIntervento
        {
            get { return Convert.ToInt32(this.Request.QueryString["intervento"]); }
        }

        protected int[] IdEndoSelezionati
        {
            get
            {
                var obj = this.ViewState["IdEndoSelezionati"];

                if (obj == null)
                    return new int[0];

                return (int[])obj;
            }

            set { this.ViewState["IdEndoSelezionati"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
                this.DataBind();
        }

        protected string GeneraUrlModello()
        {
            return this.ResolveClientUrl(String.Format("~/Public/ModelloDomanda/ModelloDomandaHandler.ashx?IdComune={0}&Software={1}&Intervento={2}&Endo={3}", this.IdComune, this.Software, this.IdIntervento, String.Join(",", this.IdEndoSelezionati)));
        }

        public override void DataBind()
        {
            var endoprocedimenti = this._endoprocedimentiService.GetListaEndoDaIdIntervento(null, this.IdIntervento);
            this.lnkAccedi.NavigateUrl = "~/Login.aspx?IdComune=" + this.IdComune + "&Software=" + this.Software;

            // Se esistono interventi pubblicati sul primo livello dell'albero visualizzo il bottone "Accedi ai servizi online"
            this.lnkAccedi.Visible = this._interventiRepository.EsistonoVociAttivabiliTramiteAreaRiservata(this.IdComune, this.Software);

            if (endoprocedimenti.Ricorrenti.Count == 0 &&
                endoprocedimenti.Altri.Count == 0)
            {
                this.lnkSelezionaEndo.Visible = false;

                this.multiView.ActiveViewIndex = 1;
                return;
            }

            this.geProcedimentiEventuali.DataSource = endoprocedimenti.Altri;
            this.geProcedimentiEventuali.DataBind();

            this.geProcedimentiNecessari.DataSource = endoprocedimenti.Ricorrenti;
            this.geProcedimentiNecessari.DataBind();
        }



        protected void lnkSelezionaEndo_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = 0;
        }

        protected void lnkGeneraModello_Click(object sender, EventArgs e)
        {
            this.IdEndoSelezionati = this.geProcedimentiNecessari
                                    .GetIdSelezionati()
                                    .Union(
                                        this.geProcedimentiEventuali
                                            .GetIdSelezionati()
                                    ).ToArray();

            this.multiView.ActiveViewIndex = 1;
        }

        protected void lnkStampaPdf_Click(object sender, EventArgs e)
        {
            var riepilogo = this._generazioneRiepilogoDomandaService.GeneraFacSimileDomanda(this.IdIntervento, this.IdEndoSelezionati);

            this.Response.Clear();
            this.Response.ContentType = riepilogo.MimeType;
            this.Response.ContentEncoding = Encoding.Default;
            this.Response.AddHeader("content-disposition", "attachment;filename=\"fac-simile-domanda.pdf\"");
            this.Response.BinaryWrite(riepilogo.FileContent);
            this.Response.End();
        }
    }
}