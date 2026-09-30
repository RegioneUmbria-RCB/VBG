//  Copyright (c) Microsoft Corporation.  All Rights Reserved.


using System;
using System.Collections.Generic;
using System.ServiceModel.Channels;
using System.ServiceModel.Description;
using System.ServiceModel.Dispatcher;
using System.Xml;

namespace Microsoft.Samples.RouteByBody
{
    [AttributeUsage(AttributeTargets.Class | AttributeTargets.Interface)]
    internal sealed class DispatchByBodyBehaviorAttribute : Attribute, IContractBehavior
    {
        #region IContractBehavior Members

        public void AddBindingParameters(ContractDescription contractDescription, ServiceEndpoint endpoint, BindingParameterCollection bindingParameters)
        {
            // no binding parameters need to be set here
            return;
        }


        public void ApplyClientBehavior(ContractDescription contractDescription, ServiceEndpoint endpoint, ClientRuntime clientRuntime)
        {
            // this is a dispatch-side behavior which doesn't require
            // any action on the client
            return;
        }

        public void ApplyDispatchBehavior(ContractDescription contractDescription, ServiceEndpoint endpoint, DispatchRuntime dispatchRuntime)
        {
            // We iterate over the operation descriptions in the contract and
            // record the QName of the request body child element and corresponding operation name
            // to the dictionary to be used for dispatch 
            Dictionary<XmlQualifiedName, string> dispatchDictionary = new Dictionary<XmlQualifiedName, string>();
            foreach (OperationDescription operationDescription in contractDescription.Operations)
            {
                XmlQualifiedName qname =
                    new XmlQualifiedName(operationDescription.Messages[0].Body.WrapperName, operationDescription.Messages[0].Body.WrapperNamespace);

                if (qname.IsEmpty)
                {
                    qname = new XmlQualifiedName(operationDescription.Messages[0].Body.Parts[0].Name, operationDescription.Messages[0].Body.Parts[0].Namespace);
                }

                dispatchDictionary.Add(qname, operationDescription.Name);
            }

            // Lastly, we create and assign and instance of our operation selector that
            // gets the dispatch dictionary we've just created.
            dispatchRuntime.OperationSelector =
                new DispatchByBodyElementOperationSelector(dispatchDictionary);
        }

        public void Validate(ContractDescription contractDescription, ServiceEndpoint endpoint)
        {
            // 
        }

        #endregion
    }
}