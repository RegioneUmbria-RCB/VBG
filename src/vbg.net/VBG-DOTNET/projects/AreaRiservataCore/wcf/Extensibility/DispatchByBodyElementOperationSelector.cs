//  Copyright (c) Microsoft Corporation.  All Rights Reserved.

using CoreWCF.Channels;
using CoreWCF.Dispatcher;

using System.Xml;

namespace Microsoft.Samples.RouteByBody
{
    internal class DispatchByBodyElementOperationSelector : IDispatchOperationSelector
    {
        private readonly Dictionary<XmlQualifiedName, string> dispatchDictionary;

        public DispatchByBodyElementOperationSelector(Dictionary<XmlQualifiedName, string> dispatchDictionary)
        {
            this.dispatchDictionary = dispatchDictionary;
        }

        #region IDispatchOperationSelector Members

        private Message CreateMessageCopy(Message message, XmlDictionaryReader body)
        {
            Message copy = Message.CreateMessage(message.Version, message.Headers.Action, body);
            copy.Headers.CopyHeaderFrom(message, 0);
            copy.Properties.CopyProperties(message.Properties);
            return copy;
        }

        public string SelectOperation(ref Message message)
        {
            XmlDictionaryReader bodyReader = message.GetReaderAtBodyContents();

            XmlQualifiedName lookupQName = new XmlQualifiedName(bodyReader.LocalName, bodyReader.NamespaceURI);
            message = this.CreateMessageCopy(message, bodyReader);
            if (this.dispatchDictionary.ContainsKey(lookupQName))
            {
                return this.dispatchDictionary[lookupQName];
            }
            else
            {
                return null;
            }
        }

        #endregion
    }
}