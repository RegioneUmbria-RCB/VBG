using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Microsoft.AspNetCore.Components.Forms;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls.EditFormControls
{
    public class UploadInputFormResult
    {
        public IBrowserFile file;
        public bool? IsValid;
    }

    public class UploadInputFormMultipleFileResult
    {
        public UploadInputFormResult[] results;
    }

    public class UploadInputFormMultipleFileData
    {
        public bool IsValid;
        public IBrowserFile? File { get; set; }
        public BinaryFile? BinaryFile { get; set; }
        public int? CodiceOggetto;
    }
}
