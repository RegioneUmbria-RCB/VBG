using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{

    [System.ComponentModel.DefaultBindingProperty("DataSource")]
    public partial class SostituzioniDocumentaliGrid : System.Web.UI.UserControl
    {
        public class SostituisciDocumentoEventArgs
        {
            public readonly OrigineDocumentoSostituibileEnum Origine;
            public readonly FileUpload FileSostitutivo;
            public readonly int CodiceOggettoOriginale;
            public readonly string NomeFileOriginale;

            public SostituisciDocumentoEventArgs(OrigineDocumentoSostituibileEnum origine, FileUpload fileSostitutivo, int codiceOggettoOriginale, string nomeFileOriginale)
            {
                this.Origine = origine;
                this.FileSostitutivo = fileSostitutivo;
                this.CodiceOggettoOriginale = codiceOggettoOriginale;
                this.NomeFileOriginale = nomeFileOriginale;
            }
        }

        public class AnnullaSostituzioneDocumentaleEventArgs
        {
            public readonly int CodiceOggettoSostitutivo;

            public AnnullaSostituzioneDocumentaleEventArgs(int codiceOggettoSostitutivo)
            {
                this.CodiceOggettoSostitutivo = codiceOggettoSostitutivo;
            }
        }

        public delegate void SostituisciDocumentoEventHandler(object sender, SostituisciDocumentoEventArgs e);
        public event SostituisciDocumentoEventHandler SostituisciDocumento;

        public delegate void AnnullaSostituzioneDocumentaleEventHandler(object sender, AnnullaSostituzioneDocumentaleEventArgs e);
        public event AnnullaSostituzioneDocumentaleEventHandler AnnullaSostituzioneDocumentale;

        IEnumerable<SostituzioniDocumentali.DocumentoSostituibileBindingItem> _ds;

        [Bindable(true)]
        public IEnumerable<SostituzioniDocumentali.DocumentoSostituibileBindingItem> DataSource
        {
            get { return this._ds; }
            set { this._ds = value; }
        }

        public bool RichiedeFirmaDigitale
        {
            get { object o = this.ViewState["RichiedeFirmaDigitale"]; return o == null ? true : (bool)o; }
            set { this.ViewState["RichiedeFirmaDigitale"] = value; }
        }



        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            this.rptDocumentiSostituibili.DataSource = this.DataSource;
            this.rptDocumentiSostituibili.DataBind();
        }

        protected void cmdSostituisciDocumento_Click(object sender, EventArgs e)
        {
            var button = (Button)sender;
            var arguments = button.CommandArgument.Split('$');
            var codiceOggettoOriginale = Convert.ToInt32(arguments[1]);
            var strOrigine = arguments[0];
            var origine = (OrigineDocumentoSostituibileEnum)Enum.Parse(typeof(OrigineDocumentoSostituibileEnum), strOrigine);
            var fuFileSostitutivo = (FileUpload)button.NamingContainer.FindControl("fuFileSostitutivo");
            var lblNomefileOriginale = (Literal)button.NamingContainer.FindControl("ltrNomeFileOriginale");
            var nomeFileOriginale = lblNomefileOriginale.Text;
            
            if (this.SostituisciDocumento != null)
            {
                this.SostituisciDocumento(this, new SostituisciDocumentoEventArgs(origine, fuFileSostitutivo, codiceOggettoOriginale, nomeFileOriginale));
            }
        }

        protected void cmdAnnullaSostituzione_Click(object sender, EventArgs e)
        {
            var button = (LinkButton)sender;
            var codiceOggettoSostitutivo = Convert.ToInt32(button.CommandArgument);

            if (this.AnnullaSostituzioneDocumentale != null)
            {
                this.AnnullaSostituzioneDocumentale(this, new AnnullaSostituzioneDocumentaleEventArgs(codiceOggettoSostitutivo));
            }
            
        }
    }
}