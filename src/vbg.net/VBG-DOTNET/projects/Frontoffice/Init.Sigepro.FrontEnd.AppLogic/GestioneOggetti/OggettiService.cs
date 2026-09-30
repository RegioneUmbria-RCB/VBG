// -----------------------------------------------------------------------
// <copyright file="OggettiService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
    public class OggettiService : IOggettiService
    {
        private readonly IOggettiRepository _oggettiRepository;
        private readonly IMetadatiOggettoProvider _metadatiOggettiProvider;

        public OggettiService(IOggettiRepository oggettiRepository, IMetadatiOggettoProvider metadatiOggettiProvider)
        {
            this._oggettiRepository = oggettiRepository;
            this._metadatiOggettiProvider = metadatiOggettiProvider;
        }

        #region IOggettiService Members

        public BinaryFile GetById(int codiceOggetto)
        {
            return this._oggettiRepository.GetOggetto(codiceOggetto);
        }

        public Task<BinaryFile> GetByIdAsync(int codiceOggetto) => this._oggettiRepository.GetOggettoAsync(codiceOggetto);


        public int InserisciOggetto(BinaryFile file)
        {
            return this.InserisciOggetto(file.FileName, file.MimeType, file.FileContent);
        }

        public Task<int> InserisciOggettoAsync(string nomeFile, string mimeType, byte[] data)
        {
            return this._oggettiRepository.InserisciOggettoAsync(nomeFile, mimeType, data, this._metadatiOggettiProvider);
        }

        public int InserisciOggetto(string nomeFile, string mimeType, byte[] data)
        {
            return this._oggettiRepository.InserisciOggetto(nomeFile, mimeType, data, this._metadatiOggettiProvider);
        }

        public void AggiornaOggetto(int codiceOggetto, byte[] data)
        {
            this._oggettiRepository.AggiornaOggetto(codiceOggetto, data);
        }

        public string GetNomeFile(int codiceOggetto)
        {
            return this._oggettiRepository.GetNomeFile(codiceOggetto);
        }

        public Task<string> GetNomeFileAsync(int codiceOggetto)
        {
            return this._oggettiRepository.GetNomeFileAsync(codiceOggetto);
        }

        public string GetMd5_non_usare_non_sempre_valorizzato(int codiceOggetto)
        {
            return this._oggettiRepository.GetMd5_non_usare_non_sempre_valorizzato(codiceOggetto);
        }

        #endregion
    }
}
