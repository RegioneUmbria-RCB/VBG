using CoreWCF;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Manager;

namespace VBG.Backend.SIT.WebServices
{
    [System.CodeDom.Compiler.GeneratedCode("Microsoft.Tools.ServiceModel.Svcutil", "8.0.0")]
    [ServiceContract(Namespace = "http://init.sigepro.it", ConfigurationName = "WsSitSoap", Name = "WsSitSoap")]
    public interface IWsSit
    {

        [OperationContract(Action = "http://init.sigepro.it/GetListField")]
        //[OperationContract(Action = "http://init.sigepro.it/GetListField", ReplyAction = "*")]
        [XmlSerializerFormat(SupportFaults = true)]
        ListSit GetListField(string token, string field, Sit dataSit, string software);

        [OperationContract(Action = "http://init.sigepro.it/ValidateField")]
        //[OperationContract(Action = "http://init.sigepro.it/ValidateField", ReplyAction = "*")]
        [XmlSerializerFormat(SupportFaults = true)]
        ValidateSit ValidateField(string token, string field, Sit dataSit, string software);

        [OperationContract(Action = "http://init.sigepro.it/GetDetailField")]
        //[OperationContract(Action = "http://init.sigepro.it/GetDetailField", ReplyAction = "*")]
        [XmlSerializerFormat(SupportFaults = true)]
        DetailSit GetDetailField(string token, string field, Sit dataSit, string software);

        [OperationContract(Action = "http://init.sigepro.it/EffettuaValidazioneFormale")]
        //[OperationContract(Action = "http://init.sigepro.it/EffettuaValidazioneFormale", ReplyAction = "*")]
        [XmlSerializerFormat(SupportFaults = true)]
        bool EffettuaValidazioneFormale(string token, string software, Sit sitClass);

        [OperationContract(Action = "http://init.sigepro.it/GetCampiGestiti")]
        //[OperationContract(Action = "http://init.sigepro.it/GetCampiGestiti", ReplyAction = "*")]
        [XmlSerializerFormat(SupportFaults = true)]
        string[] GetCampiGestiti(string token, string software);

        [OperationContract(Action = "http://init.sigepro.it/GetFeatures")]
        //[OperationContract(Action = "http://init.sigepro.it/GetFeatures", ReplyAction = "*")]
        [XmlSerializerFormat(SupportFaults = true)]
        SitFeatures GetFeatures(string token, string software);

        [OperationContract(Action = "http://init.sigepro.it/GetListaVie")]
        //[OperationContract(Action = "http://init.sigepro.it/GetListaVie", ReplyAction = "*")]
        [XmlSerializerFormat(SupportFaults = true)]
        DettagliVia[] GetListaVie(string token, string software, FiltroRicercaListaVie filtro, string[] codiciComuni);
    }
}
