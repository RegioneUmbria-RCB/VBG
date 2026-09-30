#if NET9_0_OR_GREATER
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.Server.Standard
{
    public class PathMapper : IPathMapper
    {
        public bool IsPathMappingSupported => true;

        public string MapPath(string relative)
        {
            var root = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
            var parts = relative.Split('/');

            if (parts[0].StartsWith('~'))
            {
                parts[0] = parts[0].Replace("~", "");
                parts = parts.Where(x => x.Length > 0).ToArray();
            }

            return Path.GetFullPath(Path.Combine(root, String.Join(Path.DirectorySeparatorChar.ToString(), parts)));
        }
    }
}
#endif