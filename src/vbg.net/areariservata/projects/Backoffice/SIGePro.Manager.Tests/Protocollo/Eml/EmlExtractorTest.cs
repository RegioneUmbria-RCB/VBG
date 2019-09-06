using Init.SIGePro.Manager.Utils;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace SIGePro.Manager.Tests.Protocollo.Zip
{
    [TestClass]
    public class EmlExtractorTest
    {

        [TestMethod]
        public void Estrai()
        {
            var zipUtils = new ZipUtils();

            var filePath = @"C:\Temp\test_zip\pratica comparto Il Mosaico.7z";

            using (var fileStream = File.OpenRead(filePath))
            {
                var stream = new MemoryStream();
                stream.SetLength(fileStream.Length);
                fileStream.Read(stream.GetBuffer(), 0, (int)fileStream.Length);
                
                var attachmentInfo = zipUtils.GetAttachmentsInfo(stream, "pratica comparto Il Mosaico.7z", 0, false);

                Assert.AreEqual("OK", "OK");
            }
        }
    }
}
