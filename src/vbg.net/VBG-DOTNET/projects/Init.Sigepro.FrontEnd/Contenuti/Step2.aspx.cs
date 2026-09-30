using System;
//using Init.Sigepro.FrontEnd.AppLogic.Validation;

namespace Init.Sigepro.FrontEnd.Contenuti
{
    public partial class Step2 : ContenutiBasePage
    {
        private const string MESSAGGIO_COLLEGAMENTO_ATECO_NON_TROVATO_DEFAULT = "Non sono stati individuati interventi riconducibili all'attività ATECO selezionata. Verrà mostrata la lista completa degli interventi";

        // [Inject]
        // public IAtecoRepository _atecoRepository { get; set; }


        //		[RegExValidate("^[0-9]{1,10}$")]
        protected int IdAteco
        {
            get
            {
                var obj = this.Request.QueryString["idateco"];
                if (String.IsNullOrEmpty(obj))
                    return -1;
                return int.Parse(obj);
            }
        }

        public Step2()
        {
            //
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.StepId = 2;
            this.Master.MostraHelp = true;


            this.alberoInterventi.IdAteco = this.IdAteco;


            if (this.IdAteco > 0)
            {
                //    if (!this.IsPostBack)
                //    {
                //        if (!_atecoRepository.EsistonoInterventiCollegati(this.IdComune, this.Software, this.IdAteco, new AmbitoRicercaFrontofficePubblico()))
                //        {
                //            this.alberoInterventi.Note = MESSAGGIO_COLLEGAMENTO_ATECO_NON_TROVATO_DEFAULT + "<br /><br />" + this.alberoInterventi.Note;

                //        }
                //    }
            }
        }

        protected void InterventoSelezionato(object sender, int idIntervento)
        {
            this.Response.Redirect("~/Contenuti/Step3.aspx?alias=" + this.AliasComune + "&Software=" + this.Software + "&Id=" + idIntervento);
        }
    }
}
