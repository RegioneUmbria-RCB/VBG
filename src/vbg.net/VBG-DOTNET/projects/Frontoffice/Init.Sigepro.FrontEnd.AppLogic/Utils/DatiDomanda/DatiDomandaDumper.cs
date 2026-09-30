using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using Microsoft.VisualStudio.Threading;
using System.IO;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.Utils.DatiDomanda
{
    public class DatiDomandaDumper
    {
        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
        private readonly IPathMapper _pathMapper;
        private readonly ISalvataggioDomandaStrategy _caricamentoDomandaStrategy;

        public DatiDomandaDumper(IIstanzaSigeproAdapterService istanzaSigeproAdapterService, IPathMapper pathMapper, ISalvataggioDomandaStrategy caricamentoDomandaStrategy)
        {
            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
            this._pathMapper = pathMapper;
            this._caricamentoDomandaStrategy = caricamentoDomandaStrategy;
        }

        public void DumpDatiDomanda(int idDomanda)
        {
            new JoinableTaskFactory(new JoinableTaskContext()).Run(() => this.DumpDatiDomandaAsync(idDomanda));
        }

        public async Task DumpDatiDomandaAsync(int idDomanda)
        {
            var domanda = await this._caricamentoDomandaStrategy.GetByIdAsync(idDomanda);

            var identificativoDomanda = domanda.DataKey.ToString();
            var flags = new IstanzaSigeproAdapterFlags
            {
                AggiungiPdfSchedeAListaAllegati = true,
                RecuperaMetadatiToken = true
            };

            var istanzaXml = this._istanzaSigeproAdapterService.ToIstanzaBackoffice(domanda.ReadInterface, flags)
                                                                .ToXmlModelloRiepilogo(identificativoDomanda);

            if (this._pathMapper.IsPathMappingSupported)
            {
                var path = this._pathMapper.MapPath("~/Logs/");
                path = Path.Combine(path, $"riepilogo_{identificativoDomanda}.xml");
                using (var fs = File.Open(path, FileMode.CreateNew))
                {
                    await fs.WriteAsync(Encoding.UTF8.GetBytes(istanzaXml), 0, Encoding.UTF8.GetByteCount(istanzaXml));
                }
            }
        }

    }
}
