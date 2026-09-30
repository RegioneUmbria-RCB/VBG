using Init.Sigepro.FrontEnd.Contenuti;
using System;

namespace Init.Sigepro.FrontEnd.Public
{
    public partial class RicercaInterventi : ContenutiBasePage
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

        protected void Page_Load(object sender, EventArgs e)
        {
            this.alberoInterventi.IdAteco = this.IdAteco;


            if (this.IdAteco > 0)
            {
                //if (!IsPostBack)
                //{
                //    if (!_atecoRepository.EsistonoInterventiCollegati(IdComune, Software, IdAteco, new AmbitoRicercaFrontofficePubblico()))
                //    {
                //        alberoInterventi.Note = MESSAGGIO_COLLEGAMENTO_ATECO_NON_TROVATO_DEFAULT + "<br /><br />" + alberoInterventi.Note;

                //    }
                //}
            }
        }

        protected void InterventoSelezionato(object sender, int idIntervento)
        {
            this.Response.Redirect("~/Public/RicercaInterventiDettaglio.aspx?IdComune=" + this.IdComune + "&Software=" + this.Software + "&Id=" + idIntervento);
        }
    }
}