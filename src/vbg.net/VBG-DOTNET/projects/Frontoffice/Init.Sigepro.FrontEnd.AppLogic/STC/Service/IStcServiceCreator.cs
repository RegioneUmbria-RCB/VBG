// -----------------------------------------------------------------------
// <copyright file="StcServiceCreator.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------
namespace Init.Sigepro.FrontEnd.AppLogic.STC.Service
{
    using System.ServiceModel;
    using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Stc;
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
    using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
    using Init.Sigepro.FrontEnd.AppLogic.StcService;

    public interface IStcServiceCreator
    {
        ParametriStc ConfigurazioneStc { get; }

        ServiceInstance<StcClient> CreateClient();
    }
}