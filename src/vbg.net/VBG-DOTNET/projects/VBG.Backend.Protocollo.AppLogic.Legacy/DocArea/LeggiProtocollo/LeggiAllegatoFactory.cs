using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Implementazioni.ADS.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.LeggiProtocollo
{
    public class LeggiAllegatoFactory
    {
        private FornitoreDocAreaEnum _fornitore;
        private string _endPointAddress;
        private string _userName;
        private string _password;
        private IProtocolloSerializer _serializer;

        public LeggiAllegatoFactory(FornitoreDocAreaEnum fornitore, string endPointAddress, string userName, string password, IProtocolloSerializer serializer)
        {
            if (string.IsNullOrEmpty(endPointAddress))
            {
                throw new ArgumentException($"'{nameof(endPointAddress)}' non può essere Null o vuoto", nameof(endPointAddress));
            }

            if (string.IsNullOrEmpty(userName))
            {
                throw new ArgumentException($"'{nameof(userName)}' non può essere Null o vuoto", nameof(userName));
            }

            if (string.IsNullOrEmpty(password))
            {
                throw new ArgumentException($"'{nameof(password)}' non può essere Null o vuoto", nameof(password));
            }

            this._fornitore = fornitore;
            this._endPointAddress = endPointAddress;
            this._userName = userName;
            this._password = password;
            this._serializer = serializer ?? throw new ArgumentNullException(nameof(serializer));
        }


        public ILeggiAllegatoService GetService()
        {
            switch (this._fornitore)
            {
                case FornitoreDocAreaEnum.ADS:
                    return new LeggiAllegatoService(this._endPointAddress, this._userName, this._password, this._serializer);
                case FornitoreDocAreaEnum.DATAMANAGEMENT:
                case FornitoreDocAreaEnum.DATAGRAPH:
                case FornitoreDocAreaEnum.MAGGIOLI:
                case FornitoreDocAreaEnum.NON_DEFINITO:
                default:
                    throw new NotImplementedException($"Il metodo per il download di un allegato della protocollazione non è implementato dal fornitore {this._fornitore.ToString()} ");
            }
        }
    }
}
