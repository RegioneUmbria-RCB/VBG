using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    public class ArFileUpload: BootstrapFormControl<FileUpload>
    {
        protected override FileUpload CreateInnerControl()
        {
            return new FileUpload();
        }

        public HttpPostedFile PostedFile
        {
            get
            {
                return this.Inner.PostedFile;
            }
        }

        public override string Value
        {
            get
            {
                return this.Inner.PostedFile == null ? String.Empty : this.Inner.PostedFile.FileName;
            }
            set
            {
                throw new NotImplementedException();
            }
        }
    }
}
