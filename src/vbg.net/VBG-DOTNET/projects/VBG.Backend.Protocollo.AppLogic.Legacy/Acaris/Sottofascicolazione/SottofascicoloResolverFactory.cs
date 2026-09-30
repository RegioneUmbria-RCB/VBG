using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Sottofascicolazione
{
    internal class SottofascicoloResolverFactory
    {
        private readonly IFolderTypeResolver _folderTypeResolver;

        public SottofascicoloResolverFactory(ProtocolloExt protocollo, IDescrizioneSottofascicoloResolver descrizioneSottofascicoloResolver)
        {
            this._folderTypeResolver = new SottofascicoloResolver(protocollo, descrizioneSottofascicoloResolver);
        }

        public SottofascicoloResolverFactory(ProtocolloExt protocollo)
        {
            this._folderTypeResolver = new SottofascicoloResolver(protocollo);
        }

        public IFolderTypeResolver Get()
        {
            return this._folderTypeResolver;
        }
    }
}
