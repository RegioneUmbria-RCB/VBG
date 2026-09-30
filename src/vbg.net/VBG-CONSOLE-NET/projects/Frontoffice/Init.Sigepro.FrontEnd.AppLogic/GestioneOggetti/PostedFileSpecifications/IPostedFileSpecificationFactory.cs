namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.PostedFileSpecifications
{
    public interface IPostedFileSpecificationFactory
    {
        IValidPostedFileSpecification Get(FileValidationFlags flags);
    }
}