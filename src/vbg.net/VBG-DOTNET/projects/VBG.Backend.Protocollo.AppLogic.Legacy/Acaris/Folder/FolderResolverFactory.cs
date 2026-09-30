using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder
{
    internal class FolderResolverFactory
    {
        private readonly IFolderTypeResolver _folderTypeResolver;
        private readonly string _default = FascicolazioneStandardService.Name;

        public FolderResolverFactory(ProtocolloExt protocollo, IDescrizioneFascicoloResolver descrizioneFascicoloResolver)
        {
            switch( protocollo.Configurazione.TipoFascicolo)
            {
                case "ANNUALE":
                    {
                        this._folderTypeResolver = new FascicoloRealeAnnualeResolver(protocollo, descrizioneFascicoloResolver);
                        break;
                    }
                default:
                    {
                        this._folderTypeResolver = new FascicoloRealeLiberoResolver(protocollo, descrizioneFascicoloResolver);
                        break;
                    }
            }
        }

        public FolderResolverFactory(ProtocolloExt protocollo)
        {
            switch (protocollo.Configurazione.TipoFascicolo)
            {
                case "ANNUALE":
                    {
                        this._folderTypeResolver = new FascicoloRealeAnnualeResolver(protocollo);
                        break;
                    }
                default:
                    {
                        this._folderTypeResolver = new FascicoloRealeLiberoResolver(protocollo);
                        break;
                    }
            }
        }

        public IFolderTypeResolver Get()
        {
            return this._folderTypeResolver;
        }
    }
}
