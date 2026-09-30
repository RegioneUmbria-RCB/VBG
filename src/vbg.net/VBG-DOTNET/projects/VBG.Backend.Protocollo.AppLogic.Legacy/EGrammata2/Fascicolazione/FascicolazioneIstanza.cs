using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Fascicolazione
{
    public class FascicolazioneIstanza : IFascicolazione
    {
        ResolveDatiProtocollazioneService _datiProtoService;

        public FascicolazioneIstanza(ResolveDatiProtocollazioneService datiProtoService)
        {
            _datiProtoService = datiProtoService;
        }

        public string GetFascicolo()
        {
            return String.Format("{0}.{1}.{2}", _datiProtoService.NumeroIstanza, _datiProtoService.IdComune, _datiProtoService.Software);
        }

        public string IdFascicolo
        {
            get { return null; }
        }

        public string NumeroFascicolo
        {
            get { return String.Format("{0}.{1}.{2}", _datiProtoService.NumeroIstanza, _datiProtoService.IdComune, _datiProtoService.Software); ; }
        }

        public string AnnoFascicolo
        {
            get { return DateTime.Now.ToString("yyyy"); }
        }
    }
}
