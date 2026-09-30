using CoreWCF;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace AreaRiservataCore.wcf.istanze.visura
{
    [ServiceContract(Namespace = "http://tempuri.org/")]
    public interface IRiepilogoPraticaService
    {
        [OperationContract]
        BinaryFile GeneraRiepilogo(string tokenApplicativo, string uidPratica);
    }
}
