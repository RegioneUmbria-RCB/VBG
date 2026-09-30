using System;
using System.Collections.Generic;
using System.Reflection;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.PathMapper
{
    public class CorePathMapper : IPathMapper
    {
        public string MapPath(string relativePath)
        {
            var root = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
            var parts = relativePath.Split('/');

            if (parts[0].StartsWith('~'))
            {
                parts[0] = parts[0].Replace("~", "");
                parts = parts.Where(x => x.Length > 0).ToArray();
            }

            return Path.GetFullPath(Path.Combine(root, String.Join(Path.DirectorySeparatorChar.ToString(), parts)));
        }
    }
}
