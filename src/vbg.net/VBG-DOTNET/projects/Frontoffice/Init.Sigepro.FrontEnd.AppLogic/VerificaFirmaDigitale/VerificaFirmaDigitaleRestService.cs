using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.VerificaFirmaRest;
using Microsoft.VisualStudio.Threading;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using static Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.VerificaFirmaRest.VerificaFirmaDigitaleRestClient;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
    public class VerificaFirmaDigitaleRestService : IVerificaFirmaDigitaleService, IFirmaDigitaleMetadataService
    {
        private readonly IOggettiService _oggettiService;
        private readonly VerificaFirmaDigitaleRestClient _verificaFirmaDigitaleRestClient;

        public VerificaFirmaDigitaleRestService(IOggettiService oggettiService, VerificaFirmaDigitaleRestClient verificaFirmaDigitaleRestClient)
        {
            this._oggettiService = oggettiService;
            this._verificaFirmaDigitaleRestClient = verificaFirmaDigitaleRestClient;
        }

        #region IFirmaDigitaleMetadataService

        public BinaryFile GetFileInChiaro(BinaryFile fileFirmato)
        {
            var jtf = new JoinableTaskFactory(new JoinableTaskContext());
            return jtf.Run(() => this.GetFileInChiaroAsync(fileFirmato));
        }
        #endregion

        public EsitoVerificaFirmaDigitale VerificaFirmaDigitale(BinaryFile file)
        {
            var jtf = new JoinableTaskFactory(new JoinableTaskContext());
            return jtf.Run(() => this.VerificaFirmaDigitaleAsync(file));
        }

        public EsitoVerificaFirmaDigitale VerificaFirmaDigitale(int codiceOggetto)
        {
            var jtf = new JoinableTaskFactory(new JoinableTaskContext());
            return jtf.Run(() => this.VerificaFirmaDigitaleAsync(codiceOggetto));
        }

        public ValidationCfResultDTO VerificaPresenzaSoggetti(BinaryFile file, IEnumerable<string> codiciFiscaliSoggetti) 
        {
            var jtf = new JoinableTaskFactory(new JoinableTaskContext());
            return jtf.Run(() => this.VerificaPresenzaSoggettiAsync(file, codiciFiscaliSoggetti));
        }

        public async Task<EsitoVerificaFirmaDigitale> VerificaFirmaDigitaleAsync(BinaryFile file)
        {
            var result = await this._verificaFirmaDigitaleRestClient.VerificaFirmaDigitaleAsync(file);

            return new EsitoVerificaFirmaDigitale(result);
        }

        public async Task<EsitoVerificaFirmaDigitale> VerificaFirmaDigitaleAsync(int codiceOggetto)
        {
            var oggetto = await this._oggettiService.GetByIdAsync(codiceOggetto);

            return await this.VerificaFirmaDigitaleAsync(oggetto);
        }

        public async Task<BinaryFile> GetFileInChiaroAsync(BinaryFile fileFirmato) 
        {
            return await this._verificaFirmaDigitaleRestClient.ScaricaFileNonFirmatoAsync(fileFirmato);
        }

        public async Task<ValidationCfResultDTO> VerificaPresenzaSoggettiAsync(BinaryFile file, IEnumerable<string> codiciFiscaliSoggetti) 
        {
            return await this._verificaFirmaDigitaleRestClient.ValidaFirmatariAsync(file, codiciFiscaliSoggetti);
        }
    }
}
