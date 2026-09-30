using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti;
using Microsoft.AspNetCore.Components.Forms;

namespace Vbg.CoreControls.EditFormControls
{
    public class UploadInputFormModel
    {
        public int? CodiceOggetto { get; set; }
        public string? FileName { get; set; }
        public IBrowserFile? File { get; set; }
        public bool SignRequired { get; set; } = false;
        // public bool ModalitaInserimento { get; set; } = true;
    }

    public class UploadInputFormModelExtended : UploadInputFormModel
    {
        public int Id { get; set; }
        public BinaryFile? BinaryFile { get; set; }
        public IValidPostedFileSpecificationAsync? ValidPostedFileSpecificationAsync { get; set; }
    }

    public class UploadInputFormModelWithItem<T> : UploadInputFormModelExtended where T : class
    {
        public UploadInputFormModelWithItem(T item)
        {
            this.Item = item;
        }

        public T Item { get; }
    }
}
