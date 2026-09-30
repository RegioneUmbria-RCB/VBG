namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications
{
    public interface IPostedFileSpecificationFactory
    {
        IValidPostedFileSpecification Get(FileValidationFlags flags);
    }
}