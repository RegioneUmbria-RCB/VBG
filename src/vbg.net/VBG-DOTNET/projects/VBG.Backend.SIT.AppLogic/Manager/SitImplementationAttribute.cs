using System;

namespace VBG.Backend.SIT.AppLogic.Manager
{
    [AttributeUsage(AttributeTargets.Class, AllowMultiple = false)]
    public class SitImplementationAttribute : Attribute
    {
        public string SitName { get; }

        public SitImplementationAttribute(string sitName = "")
        {
            this.SitName = sitName;
        }
    }
}
